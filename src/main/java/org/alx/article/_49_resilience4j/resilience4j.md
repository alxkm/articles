# Building Resilient Spring Boot Applications with Resilience4j

![image](source/title.jpeg)

Empowering Resilience: Strengthening Spring Boot Applications with Resilience4j Strategies

![image](source/resilience4j.jpeg)

Resilience4j is a lightweight, easy-to-use library designed to help developers build resilient and fault-tolerant applications. It provides various patterns to handle potential failures in microservices and distributed systems, such as circuit breakers, rate limiters, retry mechanisms, bulkheads, and time limiters. When integrated with Spring Boot, Resilience4j can enhance the robustness of applications by preventing cascading failures, managing traffic spikes, and ensuring graceful degradation.

Resilience4j integrates seamlessly with Spring Boot applications through the use of Spring Boot starters and annotations. 

Include the Resilience4j dependencies in your build.gradle file.

```java
dependencies {
   implementation "org.springframework.boot:spring-boot-starter-aop"  
    implementation "io.github.resilience4j:resilience4j-spring-boot3:${resilience4jVersion}" 
    implementation "io.github.resilience4j:resilience4j-reactor:${resilience4jVersion}"
}
```

Resilience4j configuration looks like but it will be configured for each pattern

```java
resilience4j:
  circuitbreaker:
    configs:
      default:
        registerHealthIndicator: true
        ringBufferSizeInClosedState: 10
        ringBufferSizeInHalfOpenState: 5
        waitDurationInOpenState: 10000
        failureRateThreshold: 50
  retry:
    instances:
      backendA:
        maxAttempts: 3
        waitDuration: 5000
```

# Main Patterns in Resilience4j

* Circuit Breaker
* Retry
* Rate Limiter
* Retry
* Bulkhead
* Time Limiter

# Benefits of Using Resilience4j

* Modularity: Each resilience pattern is implemented as a separate module, allowing selective use as needed.
* Lightweight: Minimal overhead compared to similar libraries.
* Flexibility: Easy configuration and integration with existing Spring Boot applications.
* Monitoring and Metrics: Provides built-in metrics that can be integrated with monitoring systems like Prometheus.

# Circuit Breaker

The Circuit Breaker is the most popular tool in this library. Many people are already familiar with this pattern. When a server application repeatedly responds with errors or responds too slowly, the circuit breaker on the client side detects this and temporarily stops sending requests (opens). During this time, instead of sending requests, it returns a standard response (fallback) or throws an exception. This approach gives the server application some time to recover without being overwhelmed by additional requests. This is especially useful in high-load systems where thousands of requests may be sent per second.

Prevents repeated failures in a service call by breaking the circuit for a specified time after a failure threshold is met.

The Circuit Breaker has three main states:

* CLOSED: The application behaves normally, and all requests are sent as usual.
* OPEN: The request chain is interrupted, and no requests are sent.
* HALF_OPEN: After a timeout, the circuit breaker moves from OPEN to HALF_OPEN, sending a limited number of test requests to the server. If the server responds successfully, the circuit breaker returns to CLOSED; otherwise, it goes back to OPEN.

And, two special states:

* DISABLED: The circuit breaker is turned off.
* FORCED_OPEN: The circuit breaker is forcibly set to OPEN.

### Usage example

```java
@CircuitBreaker(name = "backendA", fallbackMethod = "fallback")
public String doSomething() {
    // Your business logic
    return "response";
}

public String fallback(Throwable t) {
    return "fallback-response";
}
```

Let's review parameters:

* name is required parameter, shows configured circuitBreaker
* fallbackMethod, not required, calls when in in OPEN state. If OPEN state reached but no fallbackMethod, throws CallNotPermittedException.

### Sliding window

Resilience4j offers a sliding window mechanism for its Circuit Breaker pattern to provide a more nuanced approach to monitoring the success and failure rates of requests. The sliding window helps maintain a balance between responsiveness and stability by considering a fixed number of requests or a fixed time duration for decision-making.

### How Sliding Window Works

* COUNT_BASED: The sliding window considers a fixed number of latest calls. For instance, if slidingWindowSize is set to 10, the circuit breaker will track the success and failure rates of the last 10 calls.
* TIME_BASED: The sliding window considers the calls within a fixed time frame. For example, if slidingWindowSize is set to 10 seconds, the circuit breaker will monitor the calls made in the last 10 seconds.
* Minimum Number of Calls: Before making any decisions, the circuit breaker waits until at least the minimum number of calls (defined by minimumNumberOfCalls ) have been made.
* Failure Rate Threshold: Once the number of calls meets the minimum threshold, the circuit breaker evaluates the failure rate. If the failure rate exceeds the defined failureRateThreshold, the circuit breaker transitions to the OPEN state.
* Open State Duration: The circuit breaker remains in the OPEN state for the duration defined by waitDurationInOpenState. After this period, it transitions to the HALF_OPEN state, where it allows a limited number of test calls to determine if the service has recovered.

### Sliding window configuration

```java
resilience4j:
  circuitbreaker:
    instances:
      backendA:
        slidingWindowType: COUNT_BASED  # or TIME_BASED
```

### Other settings

* failureRateThreshold: the threshold for invalid requests, default is 50.
* slowCallRateThreshold: the threshold for slow requests (a request is considered slow if it takes longer than specified in the slowCallDurationThreshold setting), default is 100.
* slowCallDurationThreshold: the time used to determine if a request is slow, default is 60000 milliseconds.
* permittedNumberOfCallsInHalfOpenState: the number of requests that need to be executed in the HALF_OPEN state, default is 10.
* maxWaitDurationInHalfOpenState: the time during which the CircuitBreaker remains in the HALF_OPEN state before switching to OPEN, default value is 0(time is unlimited).
* slidingWindowType: two possible values: COUNT_BASED, TIME_BASED, default is COUNT_BASED.
* slidingWindowSize: the size of the sliding window. For COUNT_BASED, it denotes the number of requests; for TIME_BASED, it denotes the number of seconds, default is 100.
* minimumNumberOfCalls: the minimum number of calls required to make a decision to switch states. If the sliding window type is TIME_BASED, this minimum number of calls must be made within the specified period in slidingWindowSize, default is 100.
* waitDurationInOpenState: the time the state remains OPEN before transitioning to HALF_OPEN, default is 60000 milliseconds.

automaticTransitionFromOpenToHalfOpenEnabled when set to true, automatically transitions to the HALF_OPEN state without waiting for the next call. However, this launches a separate thread that performs the state transition, default is false.

recordExceptions a list of exceptions where throwing them will consider the method execution invalid. Default is empty and throwing any exceptions is considered invalid.

ignoreExceptions a list of exceptions that will be explicitly ignored when determining invalid requests. Throwing these exceptions will neither be considered valid nor invalid, default is empty.

recordFailurePredicate the condition under which an exception will be considered an invalid response, default is throwable(true).

ignoreExceptionPredicate the condition under which an exception will be ignored, default is throwable(false).

### Code configuration

```java
// Create a custom configuration for a CircuitBreaker
CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
  .failureRateThreshold(50)
  .slowCallRateThreshold(50)
  .waitDurationInOpenState(Duration.ofMillis(1000))
  .slowCallDurationThreshold(Duration.ofSeconds(2))
  .permittedNumberOfCallsInHalfOpenState(3)
  .minimumNumberOfCalls(10)
  .slidingWindowType(SlidingWindowType.TIME_BASED)
  .slidingWindowSize(5)
  .recordException(e -> INTERNAL_SERVER_ERROR
                 .equals(getResponse().getStatus()))
  .recordExceptions(IOException.class, TimeoutException.class)
  .ignoreExceptions(BusinessException.class, OtherBusinessException.class)
  .build();

// Create a CircuitBreakerRegistry with a custom global configuration
CircuitBreakerRegistry circuitBreakerRegistry = 
  CircuitBreakerRegistry.of(circuitBreakerConfig);

// Get or create a CircuitBreaker from the CircuitBreakerRegistry 
// with the global default configuration
CircuitBreaker circuitBreakerWithDefaultConfig = 
  circuitBreakerRegistry.circuitBreaker("backendA");

// Get or create a CircuitBreaker from the CircuitBreakerRegistry 
// with a custom configuration
CircuitBreaker circuitBreakerWithCustomConfig = circuitBreakerRegistry
  .circuitBreaker("backendB", circuitBreakerConfig);
```

### YAML configuration

```java
resilience4j:
  circuitbreaker:
    configs:
      default:
        slidingWindowSize: 100
        permittedNumberOfCallsInHalfOpenState: 10
        slowCallDurationThreshold: 4s
        slowCallRateThreshold: 50
        failureRateThreshold: 50
        waitDurationInOpenState: 10s
        minimumNumberOfCalls: 10
    instances:
      backendA:
        baseConfig: default
        waitDurationInOpenState: 20s
      backendB:
        baseConfig: default
        waitDurationInOpenState: 30s
```

# Retry

Automatically retries a failed operation a configurable number of times before giving up. This tool is convenient if you need to retry the same conditions in several places in your code, since you can set all the necessary settings once and use the annotation in several places

### Usage example

```java
@Retry(name = "backendA")
public String doSomething() {
    return "retry response";
}
```

Retry settings

* maxAttempts: the maximum number of attempts, including the initial call, default is 3.
* waitDuration: the fixed time between attempts, default is 500 milliseconds.
* intervalFunction: the function to determine the interval, default is numOfAttempts waitDuration.
* intervalBiFunction: similar to intervalFunction, but takes the number of attempts and the method execution result as arguments, default is (numOfAttempts, Either<? extends Throwable, Result>) waitDuration.
* retryOnResultPredicate: the condition under which retry should be performed based on a specific result, default is result false.
* retryExceptionPredicate: the condition under which retry should be performed based on an error, default is throwable true, for all errors.
* retryExceptions: a list of errors for which retry should be performed, default is empty.
* ignoreExceptions: a list of ignored errors, default is empty.
* failAfterMaxAttempts: whether to throw a MaxRetriesExceededException if the maximum number of attempts is exceeded, default is false.

Configuration example

```java
resilience4j:
  retry:
    instances:
      exampleRetry:
        maxAttempts: 5                 # Retry up to 5 times
        waitDuration: 1000             # Wait for 1 second between retries
        retryOnResultPredicate: false # Do not retry based on result
        retryExceptionPredicate: true # Retry on any exception
        failAfterMaxAttempts: true     # Throw MaxRetriesExceededException if max attempts exceeded
        retryExceptions:              # Specify specific exceptions for retry
          - java.net.SocketTimeoutException
          - org.springframework.dao.DataAccessException
        ignoreExceptions:             # Specify exceptions to ignore
          - java.lang.IllegalArgumentException
          - org.springframework.security.access.AccessDeniedException
```

Also config not require setup each instance, it can be configured for all.

# Rate Limiter

A Rate Limiter is a resilience pattern used to control the rate of requests sent to a service or resource. It helps prevent overload by limiting the number of requests allowed within a specific time period. The main goal of a Rate Limiter is to ensure that the system can handle incoming requests without being overwhelmed, thus improving its stability and performance. If we want to limit the number of calls to an external service to no more than 100 times per second. If we exceed this load, threads will either wait, return a specified response (fallback), or throw an error.

### Usage example

```java
@RateLimiter(name = "backendA", fallbackMethod = "backendAFallback")
public String doSomething() {
    ...
}

private String backendAFallback(Exception ex) {
    log.warn(ex.getMessage(), ex);
    return SOME_DEFAULT_STRING;
}
```

# Rate Limiter settings

* timeoutDuration: the time during which a thread waits for permission, default is 5 seconds.
* limitRefreshPeriod: the period over which the number of calls is restricted, default is 500 nanoseconds.
* limitForPeriod: the maximum number of calls within the time specified in limitRefreshPeriod, default is 50.

# Configuration example

```java
resilience4j.rateLimiter:
  configs:
    default:
      timeoutDuration: 5s
      limitForPeriod: 5
      limit-refresh-period: 1s
```

This config setup, if the number of method calls exceeds 5 per second, then the calling threads will wait 5 seconds before trying to call the method again. If the number of method calls exceeds limitForPeriod and the threads do not wait for their turn, then the RequestNotPermitted exception will be thrown or the fallback method will be called if we have specified one.

# Bulkhead

Limits the number of concurrent calls to a service to avoid resource exhaustion. Bulkhead pattern is a resilience mechanism used to isolate components within a system, preventing failures in one component from affecting the performance or availability of other components. The Bulkhead pattern is named after the physical bulkheads on ships, which compartmentalize sections of the vessel to prevent flooding in case of a breach.

```java
@Bulkhead(name = "backendA", fallbackMethod = "backendAFallback", type = Bulkhead.Type.THREADPOOL)
public SomeResponse someRequest() {
    ...
}
```

There are two implementations:

* SemaphoreBulkhead  based on semaphores
* FixedThreadPoolBulkhead based on a fixed thread pool

### Common Bulkhead settings

* maxConcurrentCalls: maximum number of parallel method calls, default is 25
* maxWaitDuration:  maximum time to wait for a free thread, default is 0

### Additional settings for ThreadPoolBulkhead

* maxThreadPoolSize:  maximum number of threads in the pool, default is the number of CPU cores
* coreThreadPoolSize: number of core threads in the pool, default is the number of CPU cores minus 1
* queueCapacity:  maximum number of threads in the queue, default is 100
* keepAliveDuration: when the number of threads exceeds the number of core threads, this setting defines their lifetime if they remain idle, default is 20 ms
* writableStackTraceEnabled: determines whether to output the bulkhead exception stack trace; by default, it's true, meaning the full stack trace is printed. If set to false, only a single line will be logged in case of an error. Default is true.

The implementation type can be specified directly in the annotation (type = Bulkhead.Type.THREADPOOL)

# TimeLimiter

Limits the time a service call can take, returning a fallback response if the call exceeds the specified duration. Pattern designed to restrict the execution time of a potentially long-running operation. It ensures that the execution of a task does not exceed a specified time limit, thus preventing the system from becoming unresponsive due to prolonged or blocked operations. Supports methods only Flux/Mono and CompletableFuture.

```java
@TimeLimiter(name = "backendA", fallbackMethod = "fallbackMethod")
public CompletableFuture<String> doSomething() {
    return CompletableFuture.supplyAsync(() -> "time limited response");
}
```

### Common TimeLimiter settings

* timeoutDuration: execution timeout, default is 1 second
* cancelRunningFuture: flag indicating whether to issue a cancel signal if the response is wrapped in a future, default is false

```java
resilience4j:
  timelimiter:
    instances:
      exampleTimeLimiter:
        timeoutDuration: 1000      # Timeout duration set to 1 second
        cancelRunningFuture: false # Do not cancel running future by default
```

This configuration sets up a TimeLimiter named "exampleTimeLimiter" with the following parameters:

* timeoutDuration: The maximum allowed execution time for a task, set to 1000 milliseconds (1 second).
* cancelRunningFuture: Specifies whether to issue a cancel signal if the response is wrapped in a future. By default, it's set to false, indicating that running futures are not cancelled automatically.

# Conclusion

Resilience4j stands as a robust and versatile library for building resilient Java applications. Through its comprehensive suite of resilience patterns, including Circuit Breaker, Rate Limiter, Retry, Bulkhead, and TimeLimiter, Resilience4j empowers developers to design systems that gracefully handle failures, latency, and resource constraints.
In today's distributed and unpredictable computing environments, resilience is not just a desirable feature but a necessity. Resilience4j equips developers with the tools and techniques needed to build resilient, fault-tolerant systems that can withstand the challenges of modern software development. By embracing resilience as a core design principle, developers can build applications that deliver consistent performance, even in the face of adversity, ensuring a seamless user experience and maintaining business continuity.

