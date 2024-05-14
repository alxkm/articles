package org.alx.article._8_binary_search;

public class BinarySearch {
    public static int binarySearch(int[] arr, int target) {
        int left = 0;
        int right = arr.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            // Check if the target is found at the middle
            if (arr[mid] == target) {
                return mid;
            }

            // If the target is greater, ignore left half
            else if (arr[mid] < target) {
                left = mid + 1;
            }

            // If the target is smaller, ignore right half
            else {
                right = mid - 1;
            }
        }

        // If the target is not found in the array
        return -1;
    }

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        int target = 5;
        int result = binarySearch(arr, target);
        if (result != -1) {
            System.out.println("Element " + target + " is present at index " + result);
        } else {
            System.out.println("Element " + target + " is not present in the array");
        }
    }
}
