package org.alx.article._31_java_util_concurrent_2;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class CompletableFutureExample {
    static void completableFutureExample() {
        CompletableFuture<String> future = CompletableFuture.completedFuture("value");
        CompletableFuture<Void> asyncFuture = CompletableFuture.runAsync(() -> {
            // Asynchronous task
        });
        CompletableFuture<Integer> supplyFuture = CompletableFuture.supplyAsync(() -> {
            // Asynchronous computation
            return 42;
        });
    }

    static void completableFutureApplyExample() {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> 10)
                .thenApplyAsync(result -> result * 2)
                .thenApplyAsync(result -> result + 5);
    }

    static void completableFutureSupplyAsyncExample() {
        CompletableFuture<Integer> future = CompletableFuture.<Integer>supplyAsync(() -> {
            // Asynchronous computation
            throw new RuntimeException("error");
        }).exceptionally(ex -> {
            System.out.println("Exception occurred: " + ex.getMessage());
            return 0;
        });
    }

    static void completableFutureCombineExample() {
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> 10);
        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> 20);
        CompletableFuture<Integer> combinedFuture = future1.thenCombine(future2, (result1, result2) -> result1 + result2);
    }

    static void completableFutureCancellationTimeoutExample() {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            // Asynchronous computation
            return null;
        });
        future.cancel(true); // Cancel the CompletableFuture
        CompletableFuture<Integer> timedFuture = future.completeOnTimeout(0, 1, TimeUnit.SECONDS); // Complete with default value after timeout
    }
}
