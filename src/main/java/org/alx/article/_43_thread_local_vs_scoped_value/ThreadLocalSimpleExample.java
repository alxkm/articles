package org.alx.article._43_thread_local_vs_scoped_value;

public class ThreadLocalSimpleExample {
    private static ThreadLocal<Integer> threadLocalValue = ThreadLocal.withInitial(() -> 0);

    public static void main(String[] args) {
        threadLocalValue.set(123);
        Integer value = threadLocalValue.get(); // Retrieves 123 for the current thread
    }
}
