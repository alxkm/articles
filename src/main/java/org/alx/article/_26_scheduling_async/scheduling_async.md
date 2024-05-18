# Mastering Task Scheduling in Spring Boot: A Comprehensive Guide to @Scheduled with @Asyc


In Java, the @Scheduled annotation is used in Spring Framework to schedule tasks to be executed at a specific time or at regular intervals. This annotation can be applied to methods within Spring-managed beans to indicate when those methods should be invoked.

![image](source/spring.jpeg)

A brief introduction
You apply the @Scheduled annotation to the method you want to schedule. You typically configure scheduling in the Spring application context XML file or through Java configuration classes. This configuration defines the scheduling details like when the method should run. When the application starts, Spring initializes the beans and checks for methods annotated with @Scheduled. It then schedules the execution of these methods based on the specified timing or interval. The @Scheduled annotation supports cron expressions, fixed delays, and fixed rates. You can use a cron expression for more complex scheduling, or specify fixed delays or fixed rates for simpler periodic tasks.

Example using @Scheduled annotation:

```java
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MyScheduledTasks {
    @Scheduled(cron = "*/1 * * * * *") // Execute every 1 second
    public void task1() {
        // Task logic goes here
        System.out.println("Task 1 executed.");
    }

    @Scheduled(cron = "*/5 * * * * *") // Execute every 5 second
    public void task2() {
        // Task logic goes here
        System.out.println("Task 2 executed.");
        Thread.sleep(3000);
    }
}
```


In this scenario, while the heavy job takes 3 seconds to execute, the second job doesn’t run at all because the single thread is occupied.

This could pose a problem in a commercial project, as it may take a significant amount of time to understand why your @Scheduled method isn’t executing at the specified time.

To solve this you can use two approaches:

- Explicitly delegate the execution of code to a separate thread, for instance, using the @Async annotation.
- Configure the @Scheduled annotation so that a method marked with this annotation always executes in a separate thread.


### @Async approach
To avoid blocking the execution of task1() due to the potentially long-running task2(), you can configure them to run in separate threads. You can achieve this by setting the @Async annotation on the methods. Here’s how you can modify your code:

```java
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MyScheduledTasks {
    @Scheduled(cron = "*/1 * * * * *") // Execute every 1 second
    @Async // Execute in a separate thread
    public void task1() {
        // Task logic goes here
        System.out.println("Task 1 executed.");
    }

    @Scheduled(cron = "*/5 * * * * *") // Execute every 5 seconds
    @Async // Execute in a separate thread
    public void task2() {
        // Task logic goes here
        System.out.println("Task 2 executed.");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            // Handle interrupted exception
            e.printStackTrace();
        }
    }
}
```

By adding the @Async annotation, Spring will execute these methods in separate threads, allowing them to run concurrently without waiting for each other to finish. This way, task1() will not be blocked by the potentially long-running task2().

When using the @Async annotation, Spring by default creates an Executor that has no limit on the number of threads it can create. As a result, if your job is triggered more frequently than it completes, it may lead to memory leaks due to continuously created threads.

Also we can customize @Async(“jobExecutor”). This is more efficient way to manage your resources. By default, Spring uses a default TaskExecutor bean for executing asynchronous methods. However, you can specify a custom Executor bean to be used instead by providing its bean name as an argument to @Async. You need to configure a custom Executor bean with the specified bean name (in this case, "jobExecutor") elsewhere in your Spring configuration.

You can customize your executor for example in such way below. But this is tricky topic with a lot of hidden details:

```java
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean(name = "jobExecutor")
    public Executor jobExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(30);
        executor.setThreadNamePrefix("jobExecutor-");
        executor.initialize();
        return executor;
    }
}
```

But this is tricky topic with a lot of hidden details.

Task Executor approach

```java
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
public class SchedulerConfig implements SchedulingConfigurer {

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(taskExecutor());
    }

    @Bean(destroyMethod = "shutdown")
    public Executor taskExecutor() {
        return Executors.newScheduledThreadPool(10);
    }
}
```

In such way we create ScheduledThreadPoolExecutor with pool size of 10 threads.

### Summary comparing
Using @Async Annotation:

Pros:

- Convenient: Simple annotation-based approach to mark methods for asynchronous execution.
- Quick Setup: Requires minimal configuration and can be applied directly to methods.
- Built-in Features: Utilizes Spring’s built-in features for asynchronous execution.

Cons:

- Default Executor: By default, Spring creates an Executor without a limit on the number of threads, which can lead to memory leaks if tasks are triggered more frequently than they complete.
- Limited Control: Limited control over the thread pool configuration, potentially leading to inefficient resource usage.

Custom Configuration of ThreadPoolTaskExecutor:

Pros:

- Fine-Grained Control: Allows customization of the thread pool configuration, including core pool size, maximum pool size, queue capacity, and thread names.
- Resource Management: Enables efficient resource management by setting appropriate limits on the number of threads.
- Flexibility: Offers flexibility to tailor the thread pool configuration to match specific application requirements.

Cons:

- Configuration Overhead: Requires additional configuration code to set up and manage the ThreadPoolTaskExecutor bean.
- Complexity: May introduce complexity, especially for developers unfamiliar with configuring thread pools.

Summary:

- @Async Annotation: Best suited for simple scenarios where quick setup and minimal configuration are sufficient. However, careful attention is needed to avoid potential memory leaks due to default Executor settings.
- Custom Configuration of ThreadPoolTaskExecutor: Provides more control and flexibility, making it suitable for applications with specific resource management requirements. Although it requires more configuration overhead, it offers better resource utilization and scalability in the long run.


n summary, the choice between using @Async annotation and custom configuration of ThreadPoolTaskExecutor depends on the complexity of your application, resource management requirements, and the level of control you need over the thread pool configuration. For simple cases, @Async may suffice, but for more demanding scenarios, custom configuration offers better control and scalability.

