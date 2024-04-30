package org.alx.article._1_prefix_sum;

public class PrefixSumExample {
    public int[] findPrefix(int[] a) {
        int n = a.length;
        int[] prefix = new int[n + 1];
        for (int i = 0; i < n; i++) {
            prefix[i + 1] = prefix[i] + a[i];
        }
        return prefix;
    }

    public static void main(String[] args) {
        int[] inputArray = {1, 2, 3, 4, 5};
        PrefixSumExample prefixSumExample = new PrefixSumExample();
        int[] prefixSum = prefixSumExample.findPrefix(inputArray);
        System.out.println("Prefix sum array:");
        for (int num : prefixSum) {
            System.out.print(num + " ");
        }
    }
}
