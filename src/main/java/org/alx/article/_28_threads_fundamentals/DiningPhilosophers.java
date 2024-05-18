package org.alx.article._28_threads_fundamentals;

import java.util.concurrent.Semaphore;

public class DiningPhilosophers {
    private static final int NUM_PHILOSOPHERS = 5;
    private static Semaphore[] forks = new Semaphore[NUM_PHILOSOPHERS];
    private static Semaphore dining = new Semaphore(NUM_PHILOSOPHERS - 1);

    public static void main(String[] args) {
        for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
            forks[i] = new Semaphore(1);
        }

        for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
            final int philosopherId = i;
            new Thread(() -> {
                try {
                    while (true) {
                        think(philosopherId);
                        eat(philosopherId);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    private static void think(int philosopherId) throws InterruptedException {
        System.out.println("Philosopher " + philosopherId + " is thinking.");
        Thread.sleep((long) (Math.random() * 1000));
    }

    private static void eat(int philosopherId) throws InterruptedException {
        dining.acquire(); // Limit the number of philosophers dining

        forks[philosopherId].acquire(); // Acquire left fork
        forks[(philosopherId + 1) % NUM_PHILOSOPHERS].acquire(); // Acquire right fork

        System.out.println("Philosopher " + philosopherId + " is eating.");
        Thread.sleep((long) (Math.random() * 1000));

        forks[(philosopherId + 1) % NUM_PHILOSOPHERS].release(); // Release right fork
        forks[philosopherId].release(); // Release left fork

        dining.release(); // Allow another philosopher to dine
    }
}