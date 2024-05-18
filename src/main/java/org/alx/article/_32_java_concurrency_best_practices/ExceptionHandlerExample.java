package org.alx.article._32_java_concurrency_best_practices;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class ExceptionHandlerExample {
    static void setDefaultUncaughtExceptionHandlerExample() {
        Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> {
            System.err.println("Uncaught exception in thread: " + thread.getName());
            throwable.printStackTrace();
        });
    }

    static void setDefaultUncaughtExceptionHandlerExecutorExample() {
        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<?> future = executorService.submit(() -> {
            // Task code that may throw an exception
        });
        try {
            future.get(); // Waits for the task to complete and retrieves the result
        } catch (ExecutionException e) {
            Throwable cause = e.getCause();
            // Handle the exception
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    static void completableFutureExceptionHandleExample() {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            // Task code that may throw an exception
            return 42;
        }).thenApply(result -> {
            // Task dependent on the previous result
            // Handle exceptions thrown by the previous task
            return result * 2;
        }).exceptionally(throwable -> {
            // Handle exceptions from the preceding tasks
            return -1; // Default value or error handling logic
        });
    }

    static void executorServiceShutdownExample() {
        ExecutorService executorService = Executors.newCachedThreadPool();
        try {
            // Submit tasks to the executor service
        } finally {
            executorService.shutdown(); // Shutdown the executor service
            try {
                executorService.awaitTermination(1, TimeUnit.MINUTES); // Wait for tasks to complete
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Restore interrupted status
            }
        }
    }
}
