package org.alx.article._31_java_util_concurrent_2;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Phaser;

public class CountdownLatchExample {
    static void countdownLatchExample() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(3); // Initialize with count 3
        // In each thread:
        latch.countDown(); // Decrement latch count when task completes
        latch.await(); // Block until all tasks complete
    }

    static void phaserExample() throws InterruptedException {
        Phaser phaser = new Phaser();
        phaser.register(); // Register the current thread
        // Perform some work
        phaser.arriveAndAwaitAdvance(); // Synchronize at the barrier
        // Continue execution after synchronization
    }
}
