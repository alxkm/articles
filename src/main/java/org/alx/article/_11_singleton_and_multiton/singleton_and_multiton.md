# Java: Singleton vs Multiton

![image](source/singleton_logo.jpeg)

### Introduction
When designing Java applications, developers often encounter scenarios where they need to restrict the instantiation of a class to a single instance. The Singleton and Multiton patterns are two design patterns that address this requirement. While both patterns ensure that a class has only one instance, they differ in their approach and applicability. In this article, we’ll explore the Singleton and Multiton patterns in Java, compare their characteristics, and discuss situations where each pattern is most suitable.

### Singleton Pattern
The Singleton pattern is one of the simplest design patterns and is used to ensure that a class has only one instance and provides a global point of access to that instance. In Java, the Singleton pattern typically involves:

1. Declaring a private constructor to prevent instantiation of the class from external sources.
2. Providing a static method that returns the sole instance of the class.
3. Lazily initializing the instance upon the first invocation of the static method.

Here’s a basic implementation of the Singleton pattern in Java:

```java
public class Singleton {
    private static Singleton instance;

    private Singleton() {}

    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
}
```

But for multithread environment this example is not good.

Creating a thread-safe Singleton in Java involves ensuring that the instance is properly initialized and accessed in a multi-threaded environment. There are several ways to achieve thread safety in Singleton implementation. One common approach is to use double-checked locking along with volatile keyword. Here’s an example of a thread-safe Singleton in Java:


```java
public class ThreadSafeSingleton {
    private static volatile ThreadSafeSingleton instance;

    private ThreadSafeSingleton() {
        // Private constructor to prevent instantiation from outside
    }

    public static ThreadSafeSingleton getInstance() {
        if (instance == null) {
            synchronized (ThreadSafeSingleton.class) {
                if (instance == null) {
                    instance = new ThreadSafeSingleton();
                }
            }
        }
        return instance;
    }
}
```
In this implementation:
- The volatile keyword ensures that changes made to the instance variable are visible to all threads. Without volatile, the thread may see a partially initialized instance.
- Double-checked locking is used to avoid unnecessary synchronization once the instance is initialized. It checks if the instance is null first without acquiring the lock, and if it is null, it enters a synchronized block to initialize the instance.


This implementation guarantees thread safety and efficient performance by minimizing synchronization overhead after the singleton instance is created.

Alternatively, you can use the initialization-on-demand holder idiom (also known as the “Bill Pugh Singleton”) for a thread-safe singleton without using explicit synchronization:

```java
public class ThreadSafeSingleton {
    private ThreadSafeSingleton() {
        // Private constructor to prevent instantiation from outside
    }

    private static class SingletonHolder {
        private static final ThreadSafeSingleton INSTANCE = new ThreadSafeSingleton();
    }

    public static ThreadSafeSingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }
}
```

In this approach, the singleton instance is created inside a static inner class SingletonHolder, and it's guaranteed to be lazily initialized and thread-safe due to the nature of class loading in Java. This idiom leverages the fact that inner classes are not loaded until they are referenced, providing a lazy-loading mechanism for the singleton instance while ensuring thread safety without explicit synchronization.

Certainly, let’s delve into the advantages and disadvantages of the Singleton pattern.


### Advantages of Singleton Pattern
- Controlled Access to the Sole Instance. The Singleton pattern ensures that a class has only one instance, providing a global point of access to that instance. This allows for controlled access to shared resources or services throughout the application.
- Lazy Initialization. Singleton instances can be lazily initialized, meaning the instance is created only when it is first requested. This can improve performance by deferring the creation of the instance until it is actually needed.
- Reduced Memory Footprint. Since there is only one instance of the class, memory consumption is reduced compared to having multiple instances of the same class.
- Global Access. Singleton instances can be accessed from anywhere within the application, making it easy to share state or functionality across different parts of the codebase.
- Thread Safety (with Proper Implementation). Singleton implementations can be made thread-safe, ensuring that the instance is properly initialized and accessed in a multi-threaded environment.

### Disadvantages of Singleton Pattern
- Global State. Singleton pattern introduces global state into the application, which can make it harder to track dependencies and debug issues. Changes to the singleton instance can have unintended consequences throughout the application.
- Testing Challenges. Singleton classes can be difficult to test in isolation since they often rely on static methods or global state. Mocking or stubbing the singleton instance for testing purposes can be cumbersome.
- Tight Coupling. Classes that depend on the singleton instance are tightly coupled to it, making it harder to refactor or replace the singleton with a different implementation in the future.
- Potential for Abuse. Overuse of the Singleton pattern can lead to code that is harder to understand and maintain. Developers may be tempted to make everything a singleton, resulting in a monolithic design with hidden dependencies.
- Concurrency Issues (in Some Implementations). If not implemented carefully, singleton instances may not be thread-safe, leading to race conditions or inconsistent behavior in multi-threaded environments. Lazy initialization, in particular, can introduce concurrency issues if not synchronized properly.

In summary, while the Singleton pattern provides several advantages such as controlled access and reduced memory footprint, it also comes with drawbacks such as global state and testing challenges. It’s important to carefully consider whether the Singleton pattern is the appropriate solution for a given problem and to implement it with care to avoid potential pitfalls.


### The Multiton pattern

The Multiton pattern is a variation of the Singleton pattern where instead of having only one instance of a class, you maintain a map of named instances, each identified by a key. This allows you to have multiple singletons, one for each key.

Here’s an example implementation of the Multiton pattern in Java:

```java
import java.util.HashMap;
import java.util.Map;

public class Multiton {
    private static Map<String, Multiton> instances = new HashMap<>();
    private String name;

    private Multiton(String name) {
        this.name = name;
    }

    public static Multiton getInstance(String name) {
        if (!instances.containsKey(name)) {
            synchronized (Multiton.class) {
                if (!instances.containsKey(name)) {
                    instances.put(name, new Multiton(name));
                }
            }
        }
        return instances.get(name);
    }

    public void doSomething() {
        System.out.println("I am " + name);
    }
}
```

Usage example:

```java
public class Main {
    public static void main(String[] args) {
        Multiton instance1 = Multiton.getInstance("first");
        Multiton instance2 = Multiton.getInstance("second");
        Multiton instance3 = Multiton.getInstance("first");

        instance1.doSomething(); // Output: I am first
        instance2.doSomething(); // Output: I am second
        instance3.doSomething(); // Output: I am first

        System.out.println(instance1 == instance2); // Output: false
        System.out.println(instance1 == instance3); // Output: true
    }
}
```

In this example, Multiton is the class for which we want to have multiple singletons. The getInstance() method is responsible for creating or retrieving instances based on a given name. If an instance with the given name already exists, it returns that instance; otherwise, it creates a new one.

The Multiton pattern can be applied in situations where you want to have a limited number of instances of a class, each serving a specific purpose. For example:

- Database Connection Pooling. You can use the Multiton pattern to manage a pool of database connections, where each connection is identified by a unique name (e.g., database URL).
- Resource Managers. In scenarios where you need to manage limited resources, such as network connections, thread pools, or caching mechanisms, the Multiton pattern can ensure that you have control over the number of instances created and their lifecycle.
- Configuration Management. If you have different configurations for different environments (e.g., development, staging, production), you can use the Multiton pattern to manage instances of configuration objects, ensuring that each environment has its own configuration settings.

### Comparison

Number of Instances:

- Singleton: Only one instance exists for the entire application.
- Multiton: Multiple instances can exist, each identified by a unique key.

Access:

- Singleton: Accessed through a static method that returns the sole instance.
- Multiton: Accessed through a static method that takes a key parameter and returns the instance associated with that key.

Instance Management:

- Singleton: Instances are managed internally within the class.
- Multiton: Instances are managed externally through a registry, allowing retrieval by key.

Thread Safety:

Singleton: Requires special attention to ensure thread safety, especially during lazy initialization.
Multiton: Thread safety considerations are similar to those of Singleton, depending on how instances are created and accessed.

Flexibility:

- Singleton: Provides a global point of access to a single instance, suitable for scenarios where only one instance is needed.
- Multiton: Allows for multiple instances, each serving a specific purpose identified by a key, suitable for scenarios requiring multiple instances with distinct configurations or contexts.

### Conclusion
Both the Singleton and Multiton patterns are useful in managing the instantiation of classes in Java applications. The Singleton pattern ensures that a class has only one instance, while the Multiton pattern extends this concept to manage multiple instances, each identified by a unique key. When deciding between the two patterns, consider the specific requirements of your application and choose the pattern that best fits the desired behavior and usage scenarios.

Full example of  [Singleton](https://github.com/alxkm/articles/blob/master/src/main/java/org/alx/article/_11_singleton_and_multiton/Singleton.java) and [Multiton](https://github.com/alxkm/articles/blob/master/src/main/java/org/alx/article/_11_singleton_and_multiton/Multiton.java) you can find on [Github](https://github.com/alxkm/articles/blob/master/src/main/java/org/alx/article/_11_singleton_and_multiton/ThreadSafeSingletonWithHolder.java).