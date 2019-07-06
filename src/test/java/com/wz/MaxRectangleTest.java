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
 * <p>最大全1子矩阵的面积</p>
 *
 * @author wangzi
 */
public class MaxRectangleTest {

    /**
     * 逐行求解，每行中元素为其对应位置向上的1的数量
     */
    private static int solution(int[][] map) {
        if (map == null || map.length == 0 || map[0].length == 0) {
            return 0;
        }

        int result = 0;
        int[] height = new int[map[0].length];
        // 以每一行做切割，统计以当前行为底时，每个位置往上的1的数量，当前位置为0则清空，结果存在height中
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                height[j] = map[i][j] == 0 ? 0 : 1 + height[j];
            }
            // 计算以每一行为底时，最大的矩形面积
            result = Math.max(result, maxArea(height));
        }
        return result;
    }

    /**
     * 以array为底时，最大的矩形面积
     * array可理解为一个直方图，每个值为柱子高度，求出每个柱子向左右扩展出去的最大矩形
     * 对任意位置i，需要找到其左右两边第一个小于array[i]的元素位置p1，p2，则位置i对应的面积为(p2-p1-1)*array[i]
     * 用栈来实现快速求解：
     * 1、若array[i]大于栈顶array[j]，压入i；
     * 2、否则，弹出j，此时栈顶为k，同时计算弹出的j对应的面积，然后继续判断。
     * 对于j，其右边第一个比它小的数为array[i], 其左边第一个比它小的数为array[k]，因此对j来说，其对应的面积为(i-k-1)*array[j]
     */
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

        // 压入所有数之后，右边界为array.length
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
