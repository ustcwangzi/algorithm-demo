/**
 * <p>Title: LongestIncreaseSubSequence</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/11/17</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.recursionanddynamicprogramming;

import java.util.Arrays;

/**
 * <p>最长递增子序列</p>
 *
 * @author wangzi
 */
public class LongestIncreaseSubSequence {

    public static int[] lisOne(int[] array) {
        if (array == null || array.length == 0) {
            return null;
        }

        int[] result = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = 1;
            for (int j = 0; j < i; j++) {
                if (array[i] > array[j]) {
                    result[i] = Math.max(result[i], result[j] + 1);
                }
            }
        }

        return generateLIS(array, result);
    }

    private static int[] generateLIS(int[] array, int[] result) {
        int length = 0;
        int index = 0;
        for (int i = 0; i < result.length; i++) {
            if (result[i] > length) {
                length = result[i];
                index = i;
            }
        }

        int[] lis = new int[length];
        lis[--length] = array[index];
        for (int i = index; i >= 0; i--) {
            if (array[i] < array[index] && result[i] == result[index] - 1) {
                lis[--length] = array[i];
                index = i;
            }
        }
        return lis;
    }

    public static int[] lisTwo(int[] array) {
        if (array == null || array.length == 0) {
            return null;
        }

        int[] result = new int[array.length];
        int[] ends = new int[array.length];
        ends[0] = array[0];
        result[0] = 1;

        int partition = 0;
        int left = 0, mid = 0, right = 0;
        for (int i = 1; i < array.length; i++) {
            left = 0;
            right = partition;
            while (left <= right) {
                mid = (left + right) / 2;
                if (array[i] > ends[mid]) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
            partition = Math.max(partition, left);
            ends[left] = array[i];
            result[i] = left + 1;
        }

        return generateLIS(array, result);
    }

    public static void main(String[] args) {
        int[] array = {2, 1, 5, 3, 6, 4, 8, 9, 7};
        System.out.println(Arrays.toString(lisOne(array)));
        System.out.println(Arrays.toString(lisTwo(array)));
    }
}
