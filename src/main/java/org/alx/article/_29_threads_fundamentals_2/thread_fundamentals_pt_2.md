# Navigating Java’s Multithreading Terrain Pt. 2

![image](source/title.jpeg)

If you are interested in multithreading and concurrency in Java, you may be interested in these articles:

1. [Mastering Synchronization: Best Practices and Patterns in Java](https://medium.com/@alxkm/mastering-synchronization-best-practices-and-patterns-in-java-86214b53211d)
2. [Navigating Java’s Multithreading Terrain Pt. 1. Dining Philosophers](https://medium.com/@alxkm/navigating-javas-multithreading-terrain-dining-philosophers-34f1385e2150)
3. [Navigating Java’s Multithreading Terrain Pt. 2](https://medium.com/@alxkm/navigating-javas-multithreading-terrain-part-2-fd0d541a973c)
4. [Unlocking Concurrent Power: A Guide to java.util.concurrent Pt. 1](https://medium.com/@alxkm/unlocking-concurrent-power-a-guide-to-java-util-concurrent-pt-1-b1342edadad1)
5. [Unlocking Concurrent Power: A Guide to java.util.concurrent Pt. 2.](https://medium.com/@alxkm/unlocking-concurrent-power-a-guide-to-java-util-concurrent-pt-2-056f1da1e74a)
6. [Concurrency in Java: Best Practices and Performance Optimization](https://medium.com/@alxkm/concurrency-in-java-best-practices-and-performance-optimization-0dfd990f413b)

In this article we reviewed next topics:

- Process and Threads
- Creating and Managing Threads in Java
- Thread states
- Terminating Threads
- Thread Switching
- Thread Sleeping with Thread.sleep()
- Thread Yielding with Thread.yield()
- Synchronization Techniques in Java
- Volatile Keyword in Java and its Use Cases
- Semaphores and Mutex
- The Dining Philosophers problem

So if you are interesting on this topics, welcome to the previous article.

In this article, our focus will delve into more intricate topics, including:

- Memory usage
- Callable interface
- Possible Pitfalls: Deadlocks and Race Conditions
- CompletableFuture
- Atomic, java.util.concurrent.atomic
- Concurrency and Non-blocking Synchronization
- Possible Pitfalls: Deadlocks and Race Conditions

### Memory usage

The more threads are created, the more memory is used. Many systems may have a limit on the number of threads. Even if there is no such limitation, in any case there is a natural limitation in the form of the maximum processor speed. Each thread creates its own memory stack.
All local variables and a number of other data related to the execution of the thread are placed there.

The amount of memory consumed by a Java thread can vary depending on factors such as the JVM implementation, the operating system, and the thread’s runtime behavior. However, on average, each Java thread typically consumes a few megabytes of memory for its stack and other associated data structures. The exact memory usage of a thread is influenced by several factors:


1. Stack Size: Each thread has its own stack, which is used for method calls, local variables, and other thread-specific data. The default stack size varies depending on the JVM implementation and the platform, but it’s typically a few megabytes. You can adjust the stack size using the -Xss parameter when launching the JVM.
2. Thread-Specific Data: In addition to the stack, each thread may also have other data structures associated with it, such as thread-local variables or synchronization primitives (e.g., locks, monitors). These data structures consume additional memory, but the exact amount depends on the application’s threading behavior.
3. Thread Management Overhead: The JVM incurs some overhead for managing threads, such as maintaining thread state information, scheduling, and context switching. While this overhead is typically small compared to the stack size, it can still contribute to the overall memory usage of the application.


Overall, while individual threads in a Java application may consume relatively small amounts of memory, the cumulative memory usage can become significant as the number of threads increases. Therefore, it’s essential to carefully manage thread creation and stack size to avoid excessive memory consumption and potential performance issues.

[Callable interface](https://docs.oracle.com/en/java/javase/18/docs/api/java.base/java/util/concurrent/Callable.html)

In Java, the Callable interface is part of the java.util.concurrent package and is used to define tasks that can be executed asynchronously and potentially return a result. It is added in Java 5. It is similar to the Runnable interface but provides additional features such as the ability to return a result and throw checked exceptions.

- Definition: The Callable interface is defined as follows:

```java
@FunctionalInterface
public interface Callable<V> {
    V call() throws Exception;
}
```
- Method: The Callable interface contains a single method, call(), which represents the task to be executed asynchronously. This method can return a result of type V and can throw checked exceptions.
- Usage: To use the Callable interface, you typically create a class that implements the interface and provides an implementation for the call() method. You can then submit instances of this class to an ExecutorService for execution.

```java
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MyCallable implements Callable<String> {
    public String call() {
        // Task logic goes here
        return "Task completed successfully";
    }

    public static void main(String[] args) throws Exception {
        // Create an ExecutorService
        ExecutorService executor = Executors.newSingleThreadExecutor();

        // Create an instance of MyCallable
        Callable<String> callable = new MyCallable();

        // Submit the task to the ExecutorService
        Future<String> future = executor.submit(callable);

        // Wait for the task to complete and get the result
        String result = future.get();
        System.out.println(result);

        // Shutdown the ExecutorService
        executor.shutdown();
    }
}
```

In this example, we define a class MyCallable that implements the Callable interface. We provide an implementation for the call() method, which represents the task to be executed asynchronously. We then create an instance of MyCallable, submit it to an ExecutorService for execution, and retrieve the result using a Future object. Finally, we shut down the ExecutorService once the task is complete.

The Future interface stores the result of an asynchronous calculation.
Contains the following methods:


- The get() method waits for the calculation to complete and then returns the result. Blocks until the result is ready.
- The cancel() method attempts to cancel this task.
- The isDone() and isCancelled() methods determine the current state of the task.

[CompletableFuture](https://docs.oracle.com/en/java/javase/18/docs/api/java.base/java/util/concurrent/CompletableFuture.html)

CompletableFuture is a class introduced in Java 8 as part of the java.util.concurrent package. It represents a promise of an asynchronous computation that may produce a result or an exception. CompletableFuture provides a flexible and powerful way to perform asynchronous operations and compose them in a non-blocking manner.

```java
public class CompletableFuture<T> extends Object implements Future<T>, CompletionStage<T> {
  //
}
```

Here are some key features of CompletableFuture:

- Asynchronous Computation: CompletableFuture allows you to perform computations asynchronously, meaning that the calling thread can continue executing other tasks while waiting for the asynchronous operation to complete.
- Completion Handling: You can specify actions to be executed when the CompletableFuture completes, either successfully or exceptionally. This includes chaining dependent actions, combining results from multiple CompletableFutures, handling exceptions, and more.
- Combining and Chaining: CompletableFuture provides methods for combining multiple asynchronous computations, such as thenCombine, thenCompose, and thenAcceptBoth, allowing you to create complex processing pipelines.
- Exception Handling: You can handle exceptions that occur during the asynchronous computation using methods like exceptionally, handle, or whenComplete, enabling robust error handling in asynchronous workflows.
- Timeouts and Cancelling: CompletableFuture supports specifying timeouts for asynchronous operations using orTimeout and completeOnTimeout methods. Additionally, you can cancel a CompletableFuture using the cancel method.

Here’s a simple example demonstrating the usage:

```java
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class CompletableFutureExample {
    public static void main(String[] args) {
        // Create a CompletableFuture representing an asynchronous computation
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                // Simulate a long-running computation
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Hello, CompletableFuture!";
        });

        // Attach a callback to handle the result when the computation completes
        future.thenAccept(result -> {
            System.out.println("Result: " + result);
        });

        // Wait for the CompletableFuture to complete
        future.join();
    }
}
```

In this example, we create a CompletableFuture using the supplyAsync method, which represents an asynchronous computation that returns a string. We then attach a callback using the thenAccept method to handle the result when the computation completes. Finally, we wait for the CompletableFuture to complete using the join method.

### Atomic, java.util.concurrent.atomic

Atomic classes in Java, located in the java.util.concurrent.atomic package, provide a way to perform atomic operations on variables without the need for explicit synchronization. These classes ensure that operations on the variables are performed atomically, meaning that they are indivisible and thread-safe.

Here are some key points about atomic classes:

1. Thread-Safe Operations: Atomic classes provide thread-safe operations for common variable types such as integers, longs, booleans, and references. Operations like compare-and-set, increment-and-get, decrement-and-get, and update-and-get are guaranteed to be performed atomically.
2. No Locking Required: Unlike synchronized blocks or methods, atomic classes use low-level CPU instructions or compare-and-swap (CAS) operations provided by the hardware to achieve thread safety. This eliminates the need for explicit locking and reduces contention in multithreaded applications.
3. Performance Benefits: Atomic classes are typically more performant than synchronized blocks or methods, especially in scenarios where contention is low or moderate. They achieve this by minimizing the overhead associated with locking and context switching.
4. Memory Consistency Guarantees: Atomic classes provide stronger memory consistency guarantees than regular variables. Changes made to atomic variables by one thread are immediately visible to other threads, ensuring consistent and predictable behavior in concurrent programs.
5. Common Atomic Classes: Some common atomic classes in Java include AtomicInteger, AtomicLong, AtomicBoolean, and AtomicReference. These classes provide atomic operations specific to their respective data types.

Here’s a simple example demonstrating the usage of AtomicInteger:


```java
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicExample {
    public static void main(String[] args) {
        AtomicInteger counter = new AtomicInteger(0);

        // Increment the counter atomically
        counter.incrementAndGet();

        // Decrement the counter atomically
        counter.decrementAndGet();

        // Add a value to the counter atomically
        counter.addAndGet(5);

        // Get the current value of the counter
        int value = counter.get();
        System.out.println("Counter value: " + value);
    }
}
```

In this example, we create an AtomicInteger and perform various atomic operations on it, such as incrementing, decrementing, and adding a value atomically. The operations ensure that changes to the counter are thread-safe and consistent across multiple threads.

### Concurrency and Non-blocking Synchronization

Concurrency refers to the ability of a system to handle multiple tasks or processes simultaneously, allowing them to progress independently and potentially overlap in their execution. In Java, concurrency is often achieved using threads, which enable programs to perform multiple operations concurrently.

Non-blocking synchronization is a concurrency technique aimed at achieving thread safety and avoiding contention without relying on traditional locking mechanisms such as synchronized blocks or locks. Unlike blocking synchronization, where threads may be forced to wait for access to a shared resource, non-blocking synchronization techniques aim to enable progress even when contention occurs.

Key aspects of non-blocking synchronization include:

1. Lock-Free Data Structures: Non-blocking synchronization techniques are commonly used in the design of lock-free or wait-free data structures. These data structures allow multiple threads to access shared resources concurrently without blocking each other.
2. Atomic Operations: Non-blocking synchronization often relies on atomic operations provided by hardware or software libraries. Atomic operations are indivisible and guaranteed to be performed without interruption, ensuring that concurrent access to shared resources is thread-safe.
3. Compare-and-Swap (CAS): Compare-and-swap (CAS) is a fundamental operation in non-blocking synchronization. It involves comparing the current value of a variable with an expected value and updating it if the comparison succeeds. CAS is widely used in the implementation of non-blocking algorithms and data structures.
4. Optimistic Concurrency Control: Non-blocking synchronization techniques often employ optimistic concurrency control, where threads proceed optimistically assuming that no contention will occur. If contention is detected, threads can retry or perform corrective actions to ensure progress.
5. Benefits and Challenges: Non-blocking synchronization offers benefits such as improved scalability, reduced contention, and avoidance of deadlock and livelock situations. However, designing and implementing non-blocking algorithms can be challenging and requires a deep understanding of concurrency principles and low-level programming constructs.

Overall, non-blocking synchronization is a powerful technique for achieving concurrency in Java applications, especially in scenarios where scalability, responsiveness, and fault tolerance are critical requirements. By leveraging atomic operations, CAS, and optimistic concurrency control, developers can design efficient and robust concurrent systems that maximize performance and resource utilization.

### Possible Pitfalls: Deadlocks and Race Conditions

When working with multithreading, two common pitfalls to watch out for are deadlocks and race conditions.

1. Deadlocks: Deadlocks occur when multiple threads are waiting for resources held by each other, resulting in a situation where none of the threads can progress. This mutual blocking halts the execution of all involved threads indefinitely, leading to a deadlock scenario.
2. Race Conditions: A race condition is a flaw in the design of a multi-threaded system or application wherein the system’s behavior depends on the unpredictable order in which different parts of the code are executed. Race conditions arise when multiple threads access shared resources concurrently, and the outcome of the program depends on the timing and interleaving of their operations.

It’s important to note that while not all race conditions result in deadlocks, deadlocks can only occur in the presence of race conditions. Both scenarios can introduce unpredictability and instability into multithreaded applications, making them challenging to debug and maintain. Therefore, it’s crucial to design thread-safe code and employ synchronization mechanisms to prevent these pitfalls and ensure the reliable and correct behavior of multithreaded programs.

### Conclusion

In conclusion, this article has provided a comprehensive overview of various aspects of concurrency in Java, spanning from memory usage considerations to advanced synchronization techniques. We explored the Callable interface for asynchronous computation, discussed potential pitfalls such as deadlocks and race conditions, and delved into the powerful CompletableFuture class for asynchronous programming. Additionally, we examined atomic operations and lock-free data structures for efficient thread-safe programming. By understanding these concepts and techniques, Java developers can build robust and scalable concurrent applications while mitigating common pitfalls such as deadlocks and race conditions. As Java continues to evolve, mastering concurrency and synchronization becomes increasingly essential for developing high-performance and responsive software systems.

