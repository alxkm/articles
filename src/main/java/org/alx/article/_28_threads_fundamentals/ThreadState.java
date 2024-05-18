package org.alx.article._28_threads_fundamentals;

public class ThreadState {

    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(new Runnable() {
            public void run() {
                Thread self = Thread.currentThread();
                printThreadState(self); // Print thread state
            }
        });

        printThreadState(t); // Print thread state

        t.start();
        t.join();

        if (t.getState() == Thread.State.TERMINATED) {
            System.out.println(t.getName() + " has terminated"); // Indicate termination
        }
    }

    // Method to print thread state
    private static void printThreadState(Thread thread) {
        System.out.println(thread.getName() + " is " + thread.getState());
    }
}