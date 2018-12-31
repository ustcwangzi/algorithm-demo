/**
 * <p>Title: LongestIntegratedLength</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/12/31</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.arrayandmatrix;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>最长可整合子数组长度</p>
 *
 * @author wangzi
 */
public class LongestIntegratedLength {

    public static int getMaxLengthOne(int[] array) {
        if (array == null || array.length == 0) {
            return 0;
        }
        int result = 0;
        for (int i = 0; i < array.length; i++) {
            for (int j = i; j < array.length; j++) {
                if (isIntegrated(array, i, j)) {
                    result = Math.max(result, j - i + 1);
                }
            }
        }
        return result;
    }

    /**
     * 对array[left...right]排序，检查每两个元素之间是否相差1
     */
    private static boolean isIntegrated(int[] array, int left, int right) {
        int[] copy = Arrays.copyOfRange(array, left, right + 1);
        // O(N*logN)
        Arrays.sort(copy);
        for (int i = 1; i < copy.length; i++) {
            if (copy[i - 1] != copy[i] - 1) {
                return false;
            }
        }
        return true;
    }

    public static int getMaxLengthTwo(int[] array) {
        if (array == null || array.length == 0) {
            return 0;
        }

        int result = 0;
        // 记录每次循环中的最大最小元素
        int min, max;
        // 判断重复元素
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < array.length; i++) {
            max = Integer.MIN_VALUE;
            min = Integer.MAX_VALUE;
            for (int j = i; j < array.length; j++) {
                if (set.contains(array[j])) {
                    break;
                }
                set.add(array[j]);
                max = Math.max(max, array[j]);
                min = Math.min(min, array[j]);
                if (max - min == j - i) {
                    result = Math.max(result, j - i + 1);
                }
            }
            set.clear();
        }
        return result;
    }

    public static void main(String[] args) {
        int[] array = {5, 5, 3, 2, 6, 4, 3};
        System.out.println(getMaxLengthOne(array));
        System.out.println(getMaxLengthTwo(array));
    }
}
