package org.alx.article._28_threads_fundamentals;

public class ThreadStateExample {

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(() -> {});
        Thread thread2 = new Thread(() -> {});
        Thread thread3 = new Thread(() -> {});
        thread1.start(); // Start the other thread
        try {
            thread1.join(); // Wait for the other thread to complete
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread2.start();
        thread3.start();


        Thread t = new Thread(() -> {
            Thread self = Thread.currentThread();
            printThreadState(self, "Initial State"); // Print initial state
            try {

                Thread.sleep(1000); // Simulate some work

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        printThreadState(t, "Before start");
        t.start();
        printThreadState(t, "After start");
        t.join();
        printThreadState(t, "After Joint");
    }

    // Method to print thread state
    private static void printThreadState(Thread thread, String message) {

        System.out.println(thread.getName() + " is " + thread.getState() + " - " + message);
    }
}
