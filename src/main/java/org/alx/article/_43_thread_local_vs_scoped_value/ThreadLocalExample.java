package org.alx.article._43_thread_local_vs_scoped_value;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadLocalExample {
    private static ThreadLocal<Integer> threadLocalValue = ThreadLocal.withInitial(() -> 0);

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        Runnable task = () -> {
            int value = threadLocalValue.get();
            value += (int) (Math.random() * 100);
            threadLocalValue.set(value);
            System.out.println(Thread.currentThread().getName() + " initial value: " + value);

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(Thread.currentThread().getName() + " final value: " + threadLocalValue.get());
        };

        executor.submit(task);
        executor.submit(task);

        executor.shutdown();
    }
}