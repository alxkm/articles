
## Multithreaded programming anti-patterns in Java Pt.2

![](https://cdn-images-1.medium.com/max/2000/1*tuMqFmbYSdjue2WsV7srdA.jpeg)

In the previous article, we reviewed several multithreaded programming antipatterns, identifying common pitfalls and their resolutions. However, there are still a few critical antipatterns that we did not cover. In this article, we will continue our examination of these multithreading antipatterns, delving into the issues and their solutions. Addressing these antipatterns is crucial for building efficient, reliable, and maintainable concurrent applications.

**Double-Checked Locking**

* **Description**: A broken idiom for lazy initialization that was incorrectly implemented before Java 5.

* **Solution**: Use the **volatile **keyword correctly or the **Initialization-on-demand holder idiom**.

**Lock Contention**

* **Description**: Multiple threads trying to acquire the same lock, leading to reduced performance.

* **Solution**: Reduce the granularity of locks, use read-write locks, or employ lock-free data structures.

**Improper Use of ThreadLocal**

* **Description**: Using **ThreadLocal **incorrectly, leading to memory leaks or unexpected behavior.

* **Solution**: Ensure proper management and cleanup of **ThreadLocal **variables.

**Non-Atomic Compound Actions**

* **Description**: Performing compound actions (e.g., check-then-act, read-modify-write) without proper synchronization.

* **Solution**: Use atomic variables or synchronized blocks to ensure compound actions are atomic.

**Race Conditions**

* **Description**: The system’s behavior depends on the sequence or timing of uncontrollable events.

* **Solution**: Properly synchronize access to shared resources and use thread-safe collections.

**Lack of Thread Safety in Singletons**

* **Description**: Singleton instances not properly synchronized, leading to multiple instances.

* **Solution**: Use the **enum **singleton pattern or the **Initialization-on-demand holder idiom**.

**Using Threads Instead of Tasks**

* **Description**: Directly creating and managing threads instead of using the Executor framework.

* **Solution**: Use **ExecutorService **and related classes to manage thread pools and tasks efficiently.

## Double-Checked Locking Example (Pre-Java 5 Broken Idiom)

Double-checked locking is a design pattern used to reduce the overhead of acquiring a lock by first testing the locking criterion without actually acquiring the lock. However, the original implementation of this pattern was broken in Java before version 5 due to the lack of guaranteed visibility of changes to variables across threads.

### Example Class with Broken Double-Checked Locking (Pre-Java 5)

    public class Singleton {
        private static Singleton instance;
    
        /**
         * Private constructor to prevent instantiation.
         */
        private Singleton() {
        }
    
        /**
         * Returns the singleton instance.
         * This implementation of double-checked locking is broken in pre-Java 5 versions.
         * 
         * @return the singleton instance.
         */
        public static Singleton getInstance() {
            if (instance == null) { // First check (not synchronized)
                synchronized (Singleton.class) {
                    if (instance == null) { // Second check (synchronized)
                        instance = new Singleton();
                    }
                }
            }
            return instance;
        }
    
        public void showMessage() {
            System.out.println("Singleton instance method called.");
        }
    
        public static void main(String[] args) {
            Singleton singleton = Singleton.getInstance();
            singleton.showMessage();
        }
    }

In this example, the double-checked locking pattern is used to lazily initialize the singleton instance. However, this implementation is broken in Java versions before Java 5 due to issues with the Java Memory Model (JMM), which could cause the **instance **variable to appear initialized before it is fully constructed.

### Resolution: Using volatile Keyword (Java 5 and Later)

Starting from Java 5, the **volatile **keyword ensures that changes to a variable are visible to all threads, which fixes the double-checked locking pattern.

    public class Singleton {
        private static volatile Singleton instance;
    
        /**
         * Private constructor to prevent instantiation.
         */
        private Singleton() {
        }
    
        /**
         * Returns the singleton instance.
         * This implementation uses volatile keyword to ensure thread safety.
         * 
         * @return the singleton instance.
         */
        public static Singleton getInstance() {
            if (instance == null) { // First check (not synchronized)
                synchronized (Singleton.class) {
                    if (instance == null) { // Second check (synchronized)
                        instance = new Singleton();
                    }
                }
            }
            return instance;
        }
    
        public void showMessage() {
            System.out.println("Singleton instance method called.");
        }
    
        public static void main(String[] args) {
            Singleton singleton = Singleton.getInstance();
            singleton.showMessage();
        }
    }

In this example, the double-checked locking pattern is used to lazily initialize the singleton instance. However, this implementation is broken in Java versions before Java 5 due to issues with the Java Memory Model (JMM), which could cause the **instance **variable to appear initialized before it is fully constructed.

### Resolution: Using volatile Keyword (Java 5 and Later)

Starting from Java 5, the **volatile **keyword ensures that changes to a variable are visible to all threads, which fixes the double-checked locking pattern.

    public class Singleton {
        private static volatile Singleton instance;
    
        /**
         * Private constructor to prevent instantiation.
         */
        private Singleton() {
        }
    
        /**
         * Returns the singleton instance.
         * This implementation uses volatile keyword to ensure thread safety.
         * 
         * @return the singleton instance.
         */
        public static Singleton getInstance() {
            if (instance == null) { // First check (not synchronized)
                synchronized (Singleton.class) {
                    if (instance == null) { // Second check (synchronized)
                        instance = new Singleton();
                    }
                }
            }
            return instance;
        }
    
        public void showMessage() {
            System.out.println("Singleton instance method called.");
        }
    
        public static void main(String[] args) {
            Singleton singleton = Singleton.getInstance();
            singleton.showMessage();
        }
    }

In this revised example, the **volatile  **keyword is used for the **instance **variable, ensuring proper visibility of changes across threads and fixing the broken double-checked locking pattern.

## Alternative Resolution: Initialization-on-Demand Holder Idiom

Another approach to implement lazy initialization in a thread-safe manner is to use the Initialization-on-Demand Holder idiom, which leverages the class loader mechanism to ensure thread safety.

    public class Singleton {
        /**
         * Private constructor to prevent instantiation.
         */
        private Singleton() {
        }
    
        /**
         * Static inner class - inner classes are not loaded until they are referenced.
         */
        private static class Holder {
            private static final Singleton INSTANCE = new Singleton();
        }
    
        /**
         * Returns the singleton instance.
         * This implementation uses the Initialization-on-Demand Holder idiom to ensure thread safety.
         * 
         * @return the singleton instance.
         */
        public static Singleton getInstance() {
            return Holder.INSTANCE;
        }
    
        public void showMessage() {
            System.out.println("Singleton instance method called.");
        }
    
        public static void main(String[] args) {
            Singleton singleton = Singleton.getInstance();
            singleton.showMessage();
        }
    }

In this version, the **Holder **class is loaded on the first invocation of **getInstance()**, and since class loading is thread-safe, this approach ensures that the singleton instance is created in a thread-safe manner without the need for synchronization or volatile variables.

## Conclusion

The double-checked locking pattern, as originally implemented, was broken in pre-Java 5 environments due to issues with the Java Memory Model. By using the **volatile **keyword in Java 5 and later, or by employing the Initialization-on-Demand Holder idiom, we can achieve thread-safe lazy initialization for singletons, ensuring correct behavior in concurrent environments.

## Lock Contention Example

Lock contention occurs when multiple threads compete for the same lock, causing some threads to wait while another thread holds the lock. This can lead to reduced performance due to increased waiting times and underutilized CPU resources.

### Example Class with High Lock Contention

    public class LockContentionExample {
        private final Object lock = new Object();
        private int counter = 0;
    
        /**
         * Increments the counter. This method synchronizes on the same lock,
         * leading to high contention when accessed by multiple threads.
         */
        public void increment() {
            synchronized (lock) {
                counter++;
            }
        }
    
        /**
         * Returns the counter value.
         * This method also synchronizes on the same lock, contributing to contention.
         * 
         * @return the counter value.
         */
        public int getCounter() {
            synchronized (lock) {
                return counter;
            }
        }
    
        public static void main(String[] args) {
            LockContentionExample example = new LockContentionExample();
    
            Runnable task = () -> {
                for (int i = 0; i < 1000; i++) {
                    example.increment();
                }
            };
    
            Thread thread1 = new Thread(task);
            Thread thread2 = new Thread(task);
    
            thread1.start();
            thread2.start();
    
            try {
                thread1.join();
                thread2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    
            System.out.println("Final counter value: " + example.getCounter());
        }
    }

In this example, both **increment **and **getCounter **methods synchronize on the same lock. When multiple threads try to access these methods simultaneously, they experience high contention, reducing performance.

### Resolution: Reducing Lock Granularity

To resolve lock contention, we can reduce the granularity of the locks, minimizing the critical sections that need synchronization.

    import java.util.concurrent.atomic.AtomicInteger;
    
    public class LockContentionResolution {
        private final AtomicInteger counter = new AtomicInteger();
    
        /**
         * Increments the counter using an atomic operation, reducing the need for explicit locks.
         */
        public void increment() {
            counter.incrementAndGet();
        }
    
        /**
         * Returns the counter value. This method is thread-safe without explicit locks.
         * 
         * @return the counter value.
         */
        public int getCounter() {
            return counter.get();
        }
    
        public static void main(String[] args) {
            LockContentionResolution example = new LockContentionResolution();
    
            Runnable task = () -> {
                for (int i = 0; i < 1000; i++) {
                    example.increment();
                }
            };
    
            Thread thread1 = new Thread(task);
            Thread thread2 = new Thread(task);
    
            thread1.start();
            thread2.start();
    
            try {
                thread1.join();
                thread2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    
            System.out.println("Final counter value: " + example.getCounter());
        }
    }

In this revised example, we use **AtomicInteger **to manage the counter. **AtomicInteger **provides thread-safe operations without the need for explicit synchronization, significantly reducing lock contention.

## Alternative Resolution: StampedLock for Optimistic Reads

Another approach is to use **StampedLock**, which allows for more flexible lock handling, including optimistic reads.

    import java.util.concurrent.locks.StampedLock;
    
    public class StampedLockExample {
        private final StampedLock lock = new StampedLock();
        private int counter = 0;
    
        /**
         * Increments the counter using a write lock.
         */
        public void increment() {
            long stamp = lock.writeLock();
            try {
                counter++;
            } finally {
                lock.unlockWrite(stamp);
            }
        }
    
        /**
         * Returns the counter value using an optimistic read lock.
         * 
         * @return the counter value.
         */
        public int getCounter() {
            long stamp = lock.tryOptimisticRead();
            int currentCounter = counter;
            if (!lock.validate(stamp)) {
                stamp = lock.readLock();
                try {
                    currentCounter = counter;
                } finally {
                    lock.unlockRead(stamp);
                }
            }
            return currentCounter;
        }
    
        public static void main(String[] args) {
            StampedLockExample example = new StampedLockExample();
    
            Runnable task = () -> {
                for (int i = 0; i < 1000; i++) {
                    example.increment();
                }
            };
    
            Thread thread1 = new Thread(task);
            Thread thread2 = new Thread(task);
    
            thread1.start();
            thread2.start();
    
            try {
                thread1.join();
                thread2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    
            System.out.println("Final counter value: " + example.getCounter());
        }
    }

In this version, **StampedLock **is used to manage the counter. **StampedLock **allows for optimistic reads, which can improve performance by avoiding locks if the data hasn't changed.

## Conclusion

Lock contention can significantly reduce the performance of concurrent applications. By reducing the granularity of locks, using atomic variables, or leveraging advanced locking mechanisms like **StampedLock**, we can minimize contention and improve the overall performance and scalability of our applications.

## Improper Use of ThreadLocal Example

Using **ThreadLocal **incorrectly can lead to memory leaks or unexpected behavior. **ThreadLocal **variables are meant to provide thread-specific storage that each thread can independently access, but improper usage or not cleaning up properly can cause problems.

### Example Class with Improper Use of ThreadLocal

    public class ThreadLocalExample {
        private static final ThreadLocal<String> threadLocal = new ThreadLocal<>();
    
        /**
         * Sets a value in the ThreadLocal variable.
         * 
         * @param value the value to set.
         */
        public void setThreadLocalValue(String value) {
            threadLocal.set(value);
        }
    
        /**
         * Gets the value from the ThreadLocal variable.
         * 
         * @return the value from ThreadLocal.
         */
        public String getThreadLocalValue() {
            return threadLocal.get();
        }
    
        public static void main(String[] args) {
            ThreadLocalExample example = new ThreadLocalExample();
    
            Runnable task = () -> {
                example.setThreadLocalValue("ThreadLocal value");
                System.out.println(Thread.currentThread().getName() + ": " + example.getThreadLocalValue());
                // Simulating work
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // Not removing the value can cause memory leaks
            };
    
            Thread thread1 = new Thread(task);
            Thread thread2 = new Thread(task);
    
            thread1.start();
            thread2.start();
    
            try {
                thread1.join();
                thread2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

In this example, the **ThreadLocal **variable is used to store and retrieve values specific to each thread. However, the values are not removed after usage, which can lead to memory leaks, especially in environments where threads are reused, such as in thread pools.

### Resolution: Proper Cleanup of ThreadLocal

To avoid memory leaks, ensure that **ThreadLocal **variables are cleaned up after use by calling the **remove **method.

    public class ThreadLocalCleanupExample {
        private static final ThreadLocal<String> threadLocal = new ThreadLocal<>();
    
        /**
         * Sets a value in the ThreadLocal variable.
         * 
         * @param value the value to set.
         */
        public void setThreadLocalValue(String value) {
            threadLocal.set(value);
        }
    
        /**
         * Gets the value from the ThreadLocal variable.
         * 
         * @return the value from ThreadLocal.
         */
        public String getThreadLocalValue() {
            return threadLocal.get();
        }
    
        /**
         * Removes the value from the ThreadLocal variable to prevent memory leaks.
         */
        public void removeThreadLocalValue() {
            threadLocal.remove();
        }
    
        public static void main(String[] args) {
            ThreadLocalCleanupExample example = new ThreadLocalCleanupExample();
    
            Runnable task = () -> {
                try {
                    example.setThreadLocalValue("ThreadLocal value");
                    System.out.println(Thread.currentThread().getName() + ": " + example.getThreadLocalValue());
                    // Simulating work
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    // Ensure the ThreadLocal variable is cleaned up
                    example.removeThreadLocalValue();
                }
            };
    
            Thread thread1 = new Thread(task);
            Thread thread2 = new Thread(task);
    
            thread1.start();
            thread2.start();
    
            try {
                thread1.join();
                thread2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

In this revised example, the **removeThreadLocalValue **method is called in the **finally **block to ensure that the **ThreadLocal **variable is cleaned up after use, preventing memory leaks.

## Alternative Resolution: Using try-with-resources Style for ThreadLocal Cleanup

Java 7 introduced the **AutoCloseable **interface and **try-with-resources** statement, which can be adapted for **ThreadLocal **cleanup using a custom utility class.

    public class ThreadLocalWithResourceExample {
        private static final ThreadLocal<String> threadLocal = new ThreadLocal<>();
    
        /**
         * Custom class to handle ThreadLocal cleanup using AutoCloseable.
         */
        public static class ThreadLocalCleaner implements AutoCloseable {
            @Override
            public void close() {
                threadLocal.remove();
            }
        }
    
        /**
         * Sets a value in the ThreadLocal variable.
         * 
         * @param value the value to set.
         */
        public void setThreadLocalValue(String value) {
            threadLocal.set(value);
        }
    
        /**
         * Gets the value from the ThreadLocal variable.
         * 
         * @return the value from ThreadLocal.
         */
        public String getThreadLocalValue() {
            return threadLocal.get();
        }
    
        public static void main(String[] args) {
            ThreadLocalWithResourceExample example = new ThreadLocalWithResourceExample();
    
            Runnable task = () -> {
                try (ThreadLocalCleaner cleaner = new ThreadLocalCleaner()) {
                    example.setThreadLocalValue("ThreadLocal value");
                    System.out.println(Thread.currentThread().getName() + ": " + example.getThreadLocalValue());
                    // Simulating work
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            };
    
            Thread thread1 = new Thread(task);
            Thread thread2 = new Thread(task);
    
            thread1.start();
            thread2.start();
    
            try {
                thread1.join();
                thread2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

In this version, the **ThreadLocalCleaner **class implements **AutoCloseable**, allowing it to be used in a **try-with-resources** statement to ensure **ThreadLocal **cleanup.

## Conclusion

Improper use of **ThreadLocal **can lead to memory leaks and unexpected behavior. By ensuring proper cleanup using the **remove **method, or by leveraging modern Java features like **try-with-resources**, we can prevent these issues and ensure that our **ThreadLocal **variables are managed correctly.

## Non-Atomic Compound Actions Example

Non-atomic compound actions occur when compound actions (e.g., check-then-act, read-modify-write) are performed without proper synchronization, leading to race conditions and incorrect results.

### Example Class with Non-Atomic Compound Actions

    public class NonAtomicCompoundActionsExample {
        private int counter = 0;
    
        /**
         * Increments the counter if it is less than a specified limit.
         * This method is not synchronized, leading to potential race conditions.
         * 
         * @param limit the limit to check against.
         */
        public void incrementIfLessThan(int limit) {
            if (counter < limit) { // Check
                counter++; // Then act
            }
        }
    
        /**
         * Returns the counter value.
         * 
         * @return the counter value.
         */
        public int getCounter() {
            return counter;
        }
    
        public static void main(String[] args) {
            NonAtomicCompoundActionsExample example = new NonAtomicCompoundActionsExample();
    
            Runnable task = () -> {
                for (int i = 0; i < 1000; i++) {
                    example.incrementIfLessThan(10);
                }
            };
    
            Thread thread1 = new Thread(task);
            Thread thread2 = new Thread(task);
    
            thread1.start();
            thread2.start();
    
            try {
                thread1.join();
                thread2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    
            System.out.println("Final counter value: " + example.getCounter());
        }
    }

In this example, the **incrementIfLessThan **method performs a non-atomic compound action. If multiple threads execute this method concurrently, they may both pass the check before either increments the counter, leading to incorrect results.

### Resolution: Synchronize the Method

To resolve this issue, we can synchronize the method to ensure that the compound action is atomic.

    public class AtomicCompoundActionsExample {
        private int counter = 0;
    
        /**
         * Increments the counter if it is less than a specified limit.
         * This method is synchronized to ensure atomicity.
         * 
         * @param limit the limit to check against.
         */
        public synchronized void incrementIfLessThan(int limit) {
            if (counter < limit) { // Check
                counter++; // Then act
            }
        }
    
        /**
         * Returns the counter value.
         * 
         * @return the counter value.
         */
        public synchronized int getCounter() {
            return counter;
        }
    
        public static void main(String[] args) {
            AtomicCompoundActionsExample example = new AtomicCompoundActionsExample();
    
            Runnable task = () -> {
                for (int i = 0; i < 1000; i++) {
                    example.incrementIfLessThan(10);
                }
            };
    
            Thread thread1 = new Thread(task);
            Thread thread2 = new Thread(task);
    
            thread1.start();
            thread2.start();
    
            try {
                thread1.join();
                thread2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    
            System.out.println("Final counter value: " + example.getCounter());
        }
    }

In this revised example, the **incrementIfLessThan **method is synchronized, ensuring that the compound action is performed atomically. This prevents race conditions and ensures correct results.

## Alternative Resolution: Using java.util.concurrent Atomic Classes

Another approach is to use the atomic classes provided by the **java.util.concurrent** package, such as **AtomicInteger**, which provides atomic operations for integers.

    import java.util.concurrent.atomic.AtomicInteger;
    
    public class AtomicIntegerExample {
        private final AtomicInteger counter = new AtomicInteger();
    
        /**
         * Increments the counter if it is less than a specified limit.
         * This method uses atomic operations to ensure thread safety.
         * 
         * @param limit the limit to check against.
         */
        public void incrementIfLessThan(int limit) {
            while (true) {
                int current = counter.get();
                if (current >= limit) {
                    break;
                }
                if (counter.compareAndSet(current, current + 1)) {
                    break;
                }
            }
        }
    
        /**
         * Returns the counter value.
         * 
         * @return the counter value.
         */
        public int getCounter() {
            return counter.get();
        }
    
        public static void main(String[] args) {
            AtomicIntegerExample example = new AtomicIntegerExample();
    
            Runnable task = () -> {
                for (int i = 0; i < 1000; i++) {
                    example.incrementIfLessThan(10);
                }
            };
    
            Thread thread1 = new Thread(task);
            Thread thread2 = new Thread(task);
    
            thread1.start();
            thread2.start();
    
            try {
                thread1.join();
                thread2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    
            System.out.println("Final counter value: " + example.getCounter());
        }
    }

In this version, the **incrementIfLessThan **method uses **AtomicInteger **and its **compareAndSet **method to ensure that the compound action is performed atomically. This approach leverages the atomic classes in the **java.util.concurrent** package to provide thread safety without explicit synchronization.

## Conclusion

Non-atomic compound actions can lead to race conditions and incorrect results in concurrent applications. By synchronizing methods or using atomic classes from the **java.util.concurrent ** package, we can ensure that compound actions are performed atomically, preventing race conditions and ensuring correct behavior in multithreaded environments.

## Lack of Thread Safety in Singletons Example

A common issue with singletons is the lack of proper synchronization, which can lead to multiple instances being created in a multithreaded environment.

### Example Class with Lack of Thread Safety in Singleton

    public class UnsafeSingleton {
        private static UnsafeSingleton instance;
    
        /**
         * Private constructor to prevent instantiation.
         */
        private UnsafeSingleton() {
        }
    
        /**
         * Returns the singleton instance. This method is not synchronized,
         * leading to potential creation of multiple instances in a multithreaded environment.
         * 
         * @return the singleton instance.
         */
        public static UnsafeSingleton getInstance() {
            if (instance == null) {
                instance = new UnsafeSingleton();
            }
            return instance;
        }
    
        public void showMessage() {
            System.out.println("UnsafeSingleton instance method called.");
        }
    
        public static void main(String[] args) {
            Runnable task = () -> {
                UnsafeSingleton singleton = UnsafeSingleton.getInstance();
                singleton.showMessage();
            };
    
            Thread thread1 = new Thread(task);
            Thread thread2 = new Thread(task);
    
            thread1.start();
            thread2.start();
        }
    }

In this example, the **getInstance **method is not synchronized, which can lead to multiple instances being created if multiple threads access the method simultaneously.

### Resolution: Synchronize the getInstance Method

To ensure thread safety, we can synchronize the **getInstance **method.

    public class SafeSingleton {
        private static SafeSingleton instance;
    
        /**
         * Private constructor to prevent instantiation.
         */
        private SafeSingleton() {
        }
    
        /**
         * Returns the singleton instance. This method is synchronized to ensure
         * that only one instance is created in a multithreaded environment.
         * 
         * @return the singleton instance.
         */
        public static synchronized SafeSingleton getInstance() {
            if (instance == null) {
                instance = new SafeSingleton();
            }
            return instance;
        }
    
        public void showMessage() {
            System.out.println("SafeSingleton instance method called.");
        }
    
        public static void main(String[] args) {
            Runnable task = () -> {
                SafeSingleton singleton = SafeSingleton.getInstance();
                singleton.showMessage();
            };
    
            Thread thread1 = new Thread(task);
            Thread thread2 = new Thread(task);
    
            thread1.start();
            thread2.start();
        }
    }

In this revised example, the **getInstance **method is synchronized, ensuring that only one instance is created even when multiple threads access the method simultaneously.

## Alternative Resolution: Double-Checked Locking with Volatile

Another approach is to use double-checked locking with the **volatile **keyword to minimize synchronization overhead.

    public class DoubleCheckedLockingSingleton {
        private static volatile DoubleCheckedLockingSingleton instance;
    
        /**
         * Private constructor to prevent instantiation.
         */
        private DoubleCheckedLockingSingleton() {
        }
    
        /**
         * Returns the singleton instance using double-checked locking with volatile.
         * 
         * @return the singleton instance.
         */
        public static DoubleCheckedLockingSingleton getInstance() {
            if (instance == null) {
                synchronized (DoubleCheckedLockingSingleton.class) {
                    if (instance == null) {
                        instance = new DoubleCheckedLockingSingleton();
                    }
                }
            }
            return instance;
        }
    
        public void showMessage() {
            System.out.println("DoubleCheckedLockingSingleton instance method called.");
        }
    
        public static void main(String[] args) {
            Runnable task = () -> {
                DoubleCheckedLockingSingleton singleton = DoubleCheckedLockingSingleton.getInstance();
                singleton.showMessage();
            };
    
            Thread thread1 = new Thread(task);
            Thread thread2 = new Thread(task);
    
            thread1.start();
            thread2.start();
        }
    }

In this version, the **volatile **keyword ensures visibility of changes to the **instance **variable across threads, while double-checked locking minimizes synchronization overhead.

## Alternative Resolution: Initialization-on-Demand Holder Idiom

Another approach to implement a thread-safe singleton is the Initialization-on-Demand Holder idiom, which leverages the class loader mechanism to ensure thread safety.

    public class HolderSingleton {
        /**
         * Private constructor to prevent instantiation.
         */
        private HolderSingleton() {
        }
    
        /**
         * Static inner class - inner classes are not loaded until they are referenced.
         */
        private static class Holder {
            private static final HolderSingleton INSTANCE = new HolderSingleton();
        }
    
        /**
         * Returns the singleton instance.
         * This implementation uses the Initialization-on-Demand Holder idiom to ensure thread safety.
         * 
         * @return the singleton instance.
         */
        public static HolderSingleton getInstance() {
            return Holder.INSTANCE;
        }
    
        public void showMessage() {
            System.out.println("HolderSingleton instance method called.");
        }
    
        public static void main(String[] args) {
            Runnable task = () -> {
                HolderSingleton singleton = HolderSingleton.getInstance();
                singleton.showMessage();
            };
    
            Thread thread1 = new Thread(task);
            Thread thread2 = new Thread(task);
    
            thread1.start();
            thread2.start();
        }
    }

In this version, the **Holder **class is loaded on the first invocation of **getInstance()**, ensuring thread safety through the class loader mechanism.

## Conclusion

Lack of thread safety in singletons can lead to multiple instances being created in a multithreaded environment. By synchronizing the **getInstance **method, using double-checked locking with **volatile**, or employing the Initialization-on-Demand Holder idiom, we can ensure that singleton instances are created in a thread-safe manner, preventing race conditions and ensuring correct behavior.

## Using Threads Instead of Tasks Example

Directly creating and managing threads instead of using the Executor framework can lead to poor scalability and complex error handling.

### Example Class with Direct Thread Management

    public class DirectThreadManagement {
        /**
         * A simple task that prints the thread name.
         */
        public void performTask() {
            Runnable task = () -> {
                System.out.println("Task executed by: " + Thread.currentThread().getName());
            };
    
            // Directly creating and starting threads
            Thread thread1 = new Thread(task);
            Thread thread2 = new Thread(task);
    
            thread1.start();
            thread2.start();
    
            try {
                thread1.join();
                thread2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    
        public static void main(String[] args) {
            DirectThreadManagement manager = new DirectThreadManagement();
            manager.performTask();
        }
    }

In this example, tasks are executed by directly creating and managing threads. This approach can lead to problems such as unbounded thread creation, poor resource management, and difficulty in handling errors.

### Resolution: Using the Executor Framework

To resolve these issues, we can use the Executor framework, which provides a higher-level API for managing threads.

    import java.util.concurrent.ExecutorService;
    import java.util.concurrent.Executors;
    import java.util.concurrent.TimeUnit;
    
    public class ExecutorFrameworkExample {
        /**
         * A simple task that prints the thread name.
         */
        public void performTask() {
            Runnable task = () -> {
                System.out.println("Task executed by: " + Thread.currentThread().getName());
            };
    
            // Using ExecutorService to manage threads
            ExecutorService executor = Executors.newFixedThreadPool(2);
    
            executor.submit(task);
            executor.submit(task);
    
            // Shutdown the executor and wait for tasks to finish
            executor.shutdown();
            try {
                executor.awaitTermination(1, TimeUnit.MINUTES);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    
        public static void main(String[] args) {
            ExecutorFrameworkExample manager = new ExecutorFrameworkExample();
            manager.performTask();
        }
    }

In this revised example, tasks are submitted to an **ExecutorService **with a fixed thread pool. The Executor framework handles thread creation, management, and resource cleanup, providing better scalability and error handling.

## Alternative Resolution: Using CompletableFuture

Another approach is to use **CompletableFuture**, which is part of the **java.util.concurrent** package and allows for more flexible task management and asynchronous programming.

    import java.util.concurrent.CompletableFuture;
    import java.util.concurrent.ExecutionException;
    
    public class CompletableFutureExample {
        /**
         * A simple task that prints the thread name.
         */
        public void performTask() {
            CompletableFuture<Void> task1 = CompletableFuture.runAsync(() -> {
                System.out.println("Task executed by: " + Thread.currentThread().getName());
            });
    
            CompletableFuture<Void> task2 = CompletableFuture.runAsync(() -> {
                System.out.println("Task executed by: " + Thread.currentThread().getName());
            });
    
            // Wait for all tasks to complete
            try {
                CompletableFuture.allOf(task1, task2).get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    
        public static void main(String[] args) {
            CompletableFutureExample manager = new CompletableFutureExample();
            manager.performTask();
        }
    }

In this version, **CompletableFuture.runAsync** is used to execute tasks asynchronously. **CompletableFuture **provides a rich API for composing and managing asynchronous tasks, making it easier to handle complex workflows.

## Conclusion

Directly creating and managing threads can lead to scalability and resource management issues. By using the Executor framework or **CompletableFuture**, we can achieve better performance, scalability, and error handling in concurrent applications. The Executor framework provides a simple and powerful way to manage thread pools, while **CompletableFuture **offers advanced features for asynchronous programming.

## Afterwords

In this article, we examined several critical multithreading antipatterns that are crucial for Java developers to understand and address:

* **Double-Checked Locking**: A flawed idiom for lazy initialization that can lead to race conditions if not implemented correctly.

* **Lock Contention**: Performance degradation caused by multiple threads competing for the same lock.

* **Improper Use of ThreadLocal**: Misuse of **ThredLocal **leading to memory leaks or unexpected behavior.

* **Non-Atomic Compound Actions**: Performing compound actions without proper synchronization, resulting in data inconsistency.

* **Race Conditions**: Situations where the output depends on the timing of uncontrollable events, leading to unpredictable behavior.

* **Lack of Thread Safety in Singletons**: Singleton instances not properly synchronized, leading to the creation of multiple instances.

* **Using Threads Instead of Tasks**: Directly managing threads instead of utilizing the Executor framework, resulting in inefficient and error-prone code.

Understanding these antipatterns is essential for developing robust, efficient, and maintainable multithreaded applications. By being aware of these common pitfalls and knowing how to avoid them, developers can leverage Java’s concurrency features more effectively, building high-quality software that performs well and scales efficiently.

Properly managing concurrency is not just about avoiding bugs and crashes; it involves writing code that runs smoothly and efficiently under various conditions. With modern applications increasingly relying on concurrent processing for high performance and responsiveness, mastering these techniques becomes indispensable.

Incorporating these best practices into your development workflow ensures that your multithreaded applications are reliable and performant. Whether optimizing for speed, ensuring data consistency, or avoiding race conditions and resource leaks, the principles discussed here form the foundation of effective multithreading in Java.

By continuously refining your understanding and application of these techniques, you can enhance your ability to create sophisticated, high-performing applications that meet the demands of today’s complex computing environments.
