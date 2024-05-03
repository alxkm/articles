package org.alx.article._13_exploring_the_biggest_rectangle_in_a_histogram;

public class LargestRectangleInHistogramSimpleExample {
    public static int largestRectangleArea(int[] heights) {
        int maxArea = 0;
        int n = heights.length;

        for (int i = 0; i < n; i++) {
            int minHeight = heights[i];
            for (int j = i; j < n; j++) {
                minHeight = Math.min(minHeight, heights[j]);
                int width = j - i + 1;
                int area = minHeight * width;
                maxArea = Math.max(maxArea, area);
            }
        }
        return maxArea;
    }

    public static void main(String[] args) {
        int[] histogram = {2, 1, 5, 6, 2, 3};
        //Output 10
        System.out.println("Largest rectangle area: " + largestRectangleArea(histogram));
    }
}
