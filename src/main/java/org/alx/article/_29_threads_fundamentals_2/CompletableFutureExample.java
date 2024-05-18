package org.alx.article._29_threads_fundamentals_2;

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