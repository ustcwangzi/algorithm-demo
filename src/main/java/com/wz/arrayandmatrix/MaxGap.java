/**
 * <p>Title: MaxGap</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/1/13</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.arrayandmatrix;

/**
 * <p>数组排序后相邻数的最大差值</p>
 *
 * @author wangzi
 */
public class MaxGap {
    public static int maxGap(int[] array) {
        if (array == null || array.length < 2) {
            return 0;
        }
        int length = array.length;
        int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
        for (int cur : array) {
            min = Math.min(min, cur);
            max = Math.max(max, cur);
        }
        if (max == min) {
            return 0;
        }

        // 桶中是否有元素
        boolean[] hasNum = new boolean[length + 1];
        // 每个桶中的最大值
        int[] maxArray = new int[length + 1];
        // 每个桶中的最小值
        int[] minArray = new int[length + 1];
        int bucket;
        for (int cur : array) {
            // 桶号
            bucket = bucketNum(cur, length, min, max);
            minArray[bucket] = hasNum[bucket] ? Math.min(minArray[bucket], cur) : cur;
            maxArray[bucket] = hasNum[bucket] ? Math.max(maxArray[bucket], cur) : cur;
            hasNum[bucket] = true;
        }

        int result = 0;
        // 上一个桶中的最大值
        int lastMax = maxArray[0];
        int index = 1;
        for (; index <= length; index++) {
            if (hasNum[index]) {
                result = Math.max(result, minArray[index] - lastMax);
                lastMax = maxArray[index];
            }
        }

        return result;
    }

    private static int bucketNum(long num, int length, long min, long max) {
        return (int) ((num - min) * length / (max - min));
    }

    public static void main(String[] args) {
        int[] array = {11, 10, 9, 3, 1, 12};
        System.out.println(maxGap(array));
    }
}
