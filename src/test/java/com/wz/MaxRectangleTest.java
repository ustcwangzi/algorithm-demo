/**
 * <p>Title: MaxRectangleTest</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019-07-06</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz;

import java.util.Stack;

/**
 * <p>最大子矩阵的面积</p>
 *
 * @author wangzi
 */
public class MaxRectangleTest {

    private static int solution(int[][] map) {
        if (map == null || map.length == 0 || map[0].length == 0) {
            return 0;
        }

        int result = 0;
        int[] height = new int[map[0].length];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                height[j] = map[i][j] == 0 ? 0 : 1 + height[j];
            }
            result = Math.max(result, maxArea(height));
        }
        return result;
    }

    private static int maxArea(int[] array) {
        if (array == null || array.length == 0) {
            return 0;
        }

        int result = 0;
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < array.length; i++) {
            while (!stack.isEmpty() && array[stack.peek()] >= array[i]) {
                int j = stack.pop();
                int k = stack.isEmpty() ? -1 : stack.peek();
                int curArea = (i - k - 1) * array[j];
                result = Math.max(result, curArea);
            }
            stack.push(i);
        }

        while (!stack.isEmpty()) {
            int j = stack.pop();
            int k = stack.isEmpty() ? -1 : stack.peek();
            int curArea = (array.length - k - 1) * array[j];
            result = Math.max(result, curArea);
        }

        return result;
    }

    public static void main(String[] args) {
        int[][] map = {{1, 0, 1, 1}, {1, 1, 1, 1}, {1, 1, 1, 0}};
        System.out.println(solution(map));
    }
}
