package org.alx.article._41_thread_local;

public class ThreadLocalSimpleExample {
    private static final ThreadLocal<Object> threadLocal = ThreadLocal.withInitial(Object::new);

    public static void main(String[] args) {
        try {
            Object obj = threadLocal.get();
            // Use obj
        } finally {
            threadLocal.remove();
        }
    }
}
