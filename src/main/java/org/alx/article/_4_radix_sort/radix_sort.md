# Radix Sort: The Most Efficient Number Sorting

Organizing data through sorting is essential for optimizing program efficiency. When dealing with large volumes of data, effective organization facilitates quick searches and processing. While the impact of sorting may be negligible for small datasets, its significance becomes pronounced when managing hundreds of thousands of data pieces. The choice of sorting algorithm greatly influences the time taken for this process.

If you want to sort only numbers you can choose radix sort.

Radix sort is particularly effective for sorting integer data, especially when the range of values and the number of digits in the numbers are not excessively large. It is commonly used for sorting strings of characters as well.

The time complexity of radix sort depends on the implementation and the size of the input. For an array of n elements, each with d digits, radix sort typically runs in O(d * (n + k)) time, where k is the range of possible digit values. In cases where d is relatively small compared to n, radix sort can perform very efficiently. However, if d is comparable to or larger than n, the performance of radix sort can degrade, potentially making other sorting algorithms more suitable.

One advantage of the radix sorting algorithm is that it sorts based on digit values rather than entire numbers. It operates on the units, tens, hundreds, etc., levels, gradually refining the sorting process. By focusing on digit values first, it avoids the need for exhaustive comparisons between all numbers, thus saving time.

Radix sort relies on the principle of using a stable sorting algorithm, often counting sort, to sort elements based on each digit position from the least significant digit (LSD) to the most significant digit (MSD). Here’s how radix sort works using counting sort:

1. Initialization: Determine the maximum number in the array to know the maximum number of digits in any element.
2. Counting Sort Passes: For each digit position from the LSD to the MSD:
3. Combine and Repeat: After sorting based on each digit position, combine the elements back into the original array. Repeat this process for each subsequent digit position, moving from LSD to MSD.
4. Result: After processing all digit positions, the array will be sorted in ascending order

Counting Sort:

1. Counting Phase: Create a count array to store the frequency of each digit (0–9) at the current position for all elements in the input array.
2. Cumulative Counting: Adjust the count array to represent the actual position of each digit in the sorted sequence.
3. Sorting Phase: Using the count array, place each element into its correct position in the output array.

By using counting sort as the stable sorting algorithm for each digit position, radix sort ensures that the elements are sorted correctly at each step, leading to a fully sorted array at the end. The overall time complexity of radix sort using counting sort is O(d * (n + k)), where n is the number of elements, d is the number of digits in the maximum number, and k is the radix (usually 10 for decimal numbers).

Implementation on on Java.

```java
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
```

Leetcode for practice:

https://leetcode.com/problems/maximum-gap/
https://leetcode.com/problems/sort-an-array/

Full example you can find on [Github](https://github.com/alxkm/articles/blob/master/src/main/java/org/alx/article/_4_radix_sort/RadixSort.java).

Thanks for reading!
