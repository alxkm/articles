package org.alx.article._30_java_util_concurrent_1;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ExecutorServiceExample {
    public static void simpleExecutorService() {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.submit(() -> {
            // Task logic here
        });
        executor.shutdown();
    }

    public static void simpleThreadPoolExecutor() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                5, // core pool size
                10, // maximum pool size
                60, // keep-alive time for idle threads
                TimeUnit.SECONDS, // time unit for keep-alive time
                new ArrayBlockingQueue<>(100) // work queue
        );
        executor.execute(() -> {
            // Task logic here
        });
        executor.shutdown();
    }

    public static void reentrantLockExample() {
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        try {
            // Critical section
        } finally {
            lock.unlock();
        }
    }

    public static void reentrantConditionExample() throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        lock.lock();
        try {
            while (true) {
                condition.await();
            }
            // Continue execution after condition is met
        } finally {
            lock.unlock();
        }
    }
}
