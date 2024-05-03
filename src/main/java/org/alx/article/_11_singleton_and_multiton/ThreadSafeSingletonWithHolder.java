package org.alx.article._11_singleton_and_multiton;

public class ThreadSafeSingletonWithHolder {
    private ThreadSafeSingletonWithHolder() {
        // Private constructor to prevent instantiation from outside
    }

    private static class SingletonHolder {
        private static final ThreadSafeSingletonWithHolder INSTANCE = new ThreadSafeSingletonWithHolder();
    }

    public static ThreadSafeSingletonWithHolder getInstance() {
        return SingletonHolder.INSTANCE;
    }
}