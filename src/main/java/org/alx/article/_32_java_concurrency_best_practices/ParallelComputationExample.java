package org.alx.article._32_java_concurrency_best_practices;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelComputationExample {

    // RecursiveTask to compute sum of an array using ForkJoinPool
    static class SumTask extends RecursiveTask<Long> {
        private static final int THRESHOLD = 1000;
        private final long[] array;
        private final int start;
        private final int end;

        SumTask(long[] array, int start, int end) {
            this.array = array;
            this.start = start;
            this.end = end;
        }

        @Override
        protected Long compute() {
            if (end - start <= THRESHOLD) {
                // Compute sum directly if array size is small
                long sum = 0;
                for (int i = start; i < end; i++) {
                    sum += array[i];
                }
                return sum;
            } else {
                // Split task into subtasks for parallel processing
                int mid = (start + end) / 2;
                SumTask leftTask = new SumTask(array, start, mid);
                SumTask rightTask = new SumTask(array, mid, end);
                leftTask.fork();
                return rightTask.compute() + leftTask.join();
            }
        }
    }

    public static void main(String[] args) {
        // Example array for computation
        long[] array = new long[1_000_000];
        Arrays.fill(array, 1); // Fill array with 1s

        // Using ForkJoinPool for parallel computation
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        long sum = forkJoinPool.invoke(new SumTask(array, 0, array.length));
        System.out.println("Sum using ForkJoinPool: " + sum);

        // Using parallel stream for parallel computation
        sum = Arrays.stream(array).parallel().sum();
        System.out.println("Sum using parallel stream: " + sum);
    }
}