package org.alx.article._29_threads_fundamentals_2;

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