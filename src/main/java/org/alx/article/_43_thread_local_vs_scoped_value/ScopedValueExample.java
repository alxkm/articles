package org.alx.article._43_thread_local_vs_scoped_value;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ScopedValueExample {
    private static final ScopedValue<String> scopedValue = ScopedValue.newInstance();

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        Runnable task = () -> {
            ScopedValue.where(scopedValue, "Value in " + Thread.currentThread().getName()).run(() -> {
                System.out.println(Thread.currentThread().getName() + " initial value: " + scopedValue.get());

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(Thread.currentThread().getName() + " final value: " + scopedValue.get());
            });
        };

        executor.submit(task);
        executor.submit(task);

        executor.shutdown();
    }
}