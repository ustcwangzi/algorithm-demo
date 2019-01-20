/**
 * <p>Title: MaxAbsoluteBetweenLeftAndRight</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/1/20</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.others;

/**
 * <p>最大的leftMax与rightMax之差的绝对值</p>
 *
 * @author wangzi
 */
public class MaxAbsoluteBetweenLeftAndRight {

    public static int maxAbsoluteOne(int[] array) {
        if (array == null || array.length < 2) {
            return 0;
        }

        int result = Integer.MIN_VALUE;
        int maxLeft, maxRight;
        for (int i = 0; i < array.length - 1; i++) {
            // 每一种划分下array[0...i]的最大值
            maxLeft = Integer.MIN_VALUE;
            for (int j = 0; j < i + 1; j++) {
                maxLeft = Math.max(array[j], maxLeft);
            }

            // 每一种划分下array[i+1...N-1]的最大值
            maxRight = Integer.MIN_VALUE;
            for (int j = i + 1; j < array.length; j++) {
                maxRight = Math.max(array[j], maxRight);
            }

            // 每一种划分下maxLeft与maxRight的差值
            result = Math.max(result, Math.abs(maxLeft - maxRight));
        }
        return result;
    }

    public static int maxAbsoluteTwo(int[] array) {
        if (array == null || array.length < 2) {
            return 0;
        }
        // array[0...i]中的最大值
        int[] leftArray = new int[array.length];
        leftArray[0] = array[0];
        // array[i...N-1]中的最大值
        int[] rightArray = new int[array.length];
        rightArray[array.length - 1] = array[array.length - 1];

        // 从左到右遍历生成leftArray
        for (int i = 1; i < array.length; i++) {
            leftArray[i] = Math.max(leftArray[i - 1], array[i]);
        }
        // 从右到左遍历生成rightArray
        for (int i = array.length - 2; i >= 0; i--) {
            rightArray[i] = Math.max(rightArray[i + 1], array[i]);
        }

        int result = Integer.MIN_VALUE;
        for (int i = 0; i < array.length - 1; i++) {
            // 以i为划分的情况下，左右两部分最大值的差值
            result = Math.max(result, Math.abs(leftArray[i] - rightArray[i + 1]));
        }
        return result;
    }

    public static int maxAbsoluteThree(int[] array) {
        if (array == null || array.length < 2) {
            return 0;
        }

        int max = Integer.MIN_VALUE;
        for (int cur : array) {
            // 找出数组中的最大元素
            max = Math.max(cur, max);
        }
        // max作为左部分最大值时，右部分只含有array[N-1]是最小的情况
        // max作为右部分最大值时，左部分只含有array[0]是最小的情况
        return max - Math.min(array[0], array[array.length - 1]);
    }

    public static void main(String[] args) {
        int[] array = {2, 7, 3, 1, 1};
        System.out.println(maxAbsoluteOne(array));
        System.out.println(maxAbsoluteTwo(array));
        System.out.println(maxAbsoluteThree(array));
    }
}
