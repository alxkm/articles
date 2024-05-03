package org.alx.article._13_exploring_the_biggest_rectangle_in_a_histogram;

import java.util.Stack;

public class MaximalRectangleExample {
    public int maximalRectangle(char[][] matrix) {
        int maxArea = 0;
        int n = matrix[0].length;
        int[] height = new int[n];

        for (char[] chars : matrix) {
            for (int j = 0; j < n; j++) {
                if (chars[j] == '1') height[j]++;
                else height[j] = 0;
            }
            maxArea = Math.max(largestRectangleArea(height), maxArea);
        }
        return maxArea;
    }

    public int largestRectangleArea(int[] heights) {
        Stack<Integer> st = new Stack<>();
        int maxArea = 0;
        for (int i = 0; i <= heights.length; i++) {
            while (!st.isEmpty() && (i == heights.length || heights[st.peek()] > heights[i])) {
                int height = heights[st.pop()];
                int width = i;
                if (!st.isEmpty()) width = (i - st.peek() - 1);
                int curArea = height * width;
                maxArea = Math.max(maxArea, curArea);
            }
            st.push(i);
        }
        return maxArea;
    }
}
