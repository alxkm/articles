package org.alx.article._27_synchronization_java;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SynchronizationExample {
    public synchronized void synchronizedMethod() {
        // Synchronized method body
    }

    public void someMethod() {
        // Non-synchronized code

        synchronized (this) {
            // Synchronized code block
        }

        // Non-synchronized code
    }

    private final Lock lock = new ReentrantLock();

    public void synchronizedMethodWithLock() {
        lock.lock(); // Acquire the lock
        try {
            // Synchronized method body
        } finally {
            lock.unlock(); // Release the lock in a finally block
        }
    }

    public void synchronizedMethodWithLockAndWithAdditionalLogic() {
        // Non-synchronized code

        lock.lock(); // Acquire the lock
        try {
            // Synchronized code block
        } finally {
            lock.unlock(); // Release the lock in a finally block
        }

        // Non-synchronized code
    }
}
