package org.alx.article._30_java_util_concurrent_1;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionExample {
    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();
    private boolean conditionMet = false;

    public void awaitCondition() {
        lock.lock();
        try {
            while (!conditionMet) {
                condition.await();
            }
            // Continue execution after condition is met
            System.out.println("Condition met, continuing execution.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Handle interrupt appropriately
        } finally {
            lock.unlock();
        }
    }

    public void signalCondition() {
        lock.lock();
        try {
            conditionMet = true;
            condition.signal(); // Signal the waiting thread
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        ConditionExample example = new ConditionExample();

        // Create a thread to wait for the condition
        Thread waitingThread = new Thread(example::awaitCondition);
        waitingThread.start();

        // Simulate some work before signaling the condition
        try {
            Thread.sleep(2000); // Simulate work
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Handle interrupt appropriately
        }

        // Signal the condition from the main thread
        example.signalCondition();
    }
}