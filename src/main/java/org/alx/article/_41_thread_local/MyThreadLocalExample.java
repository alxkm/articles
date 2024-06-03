package org.alx.article._41_thread_local;

public class MyThreadLocalExample {
    // Define a ThreadLocal variable
    private static ThreadLocal<Integer> threadLocalValue = new ThreadLocal<Integer>() {
        @Override
        protected Integer initialValue() {
            return 0; // Default value for each thread
        }
    };

    public static void main(String[] args) {
        // Set the value of the thread-local variable for the main thread
        threadLocalValue.set(10);

        // Create and start a new thread
        Thread newThread = new Thread(() -> {
            // Get the value of the thread-local variable for this thread
            int value = threadLocalValue.get();
            System.out.println("Thread-local value in newThread: " + value);
        });
        newThread.start();

        // Get the value of the thread-local variable for the main thread
        int mainThreadValue = threadLocalValue.get();
        System.out.println("Thread-local value in main thread: " + mainThreadValue);
    }
}
