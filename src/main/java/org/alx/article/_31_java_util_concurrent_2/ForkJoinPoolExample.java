package org.alx.article._31_java_util_concurrent_2;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

class MyTask extends RecursiveTask<Integer> {
    protected Integer compute() {
        // Task execution logic
        return 42;
    }
}

public class ForkJoinPoolExample {
    static void forkJoinPoolExample() {
        ForkJoinPool pool = new ForkJoinPool();
        Integer result = pool.invoke(new MyTask());
    }
}
