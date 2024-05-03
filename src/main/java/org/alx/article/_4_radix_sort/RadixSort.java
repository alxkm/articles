package org.alx.article._4_radix_sort;

import java.util.Arrays;

public class RadixSort {
    static void countingSort(int[] arr, int exp) {
        int n = arr.length;
        int[] output = new int[n];
        int[] count = new int[10];
        Arrays.fill(count, 0);

        // count of occurrences in count[]
        for (int j : arr) {
            count[(j / exp) % 10]++;
        }

        // change count[i] so that count[i] now contains
        // actual position of this digit in output[]
        for (int i = 1; i < 10; i++) {
            count[i] += count[i - 1];
        }

        for (int i = n - 1; i >= 0; i--) {
            output[count[(arr[i] / exp) % 10] - 1] = arr[i];
            count[(arr[i] / exp) % 10]--;
        }

        System.arraycopy(output, 0, arr, 0, n);
    }

    static void radixSort(int[] arr) {
        // find the maximum number to know the number of digits
        int max = Arrays.stream(arr).max().getAsInt();

        // do counting sort for every digit. Note that
        // instead of passing digit number, exp is passed.
        // exp is 10^i where i is the current digit number
        for (int exp = 1; max / exp > 0; exp *= 10) {
            countingSort(arr, exp);
        }
    }

    public static void main(String[] args) {
        int[] arr = {100, 1, 101, 111, 59, 89, 97};
        radixSort(arr);
        System.out.println("Sorted array:");
        for (int j : arr) {
            System.out.print(j + " ");
        }
        System.out.println();
    }
}
