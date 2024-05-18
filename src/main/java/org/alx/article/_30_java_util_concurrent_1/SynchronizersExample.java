package org.alx.article._30_java_util_concurrent_1;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class SynchronizersExample {

    static void reentrantLockExample() {
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        try {
            // Critical section
        } finally {
            lock.unlock();
        }
    }

    static void semaphoreExample() throws InterruptedException {
        Semaphore semaphore = new Semaphore(5); // permits = 5
        semaphore.acquire(); // Acquire a permit
        try {
            // Access shared resource
        } finally {
            semaphore.release(); // Release the permit
        }
    }
}
