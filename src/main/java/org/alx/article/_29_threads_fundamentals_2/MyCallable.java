package org.alx.article._29_threads_fundamentals_2;

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