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
 * <p>
 *     如果一个数组在排序后，每两个相邻的数值之差的绝对值都是1，则该数组为可整合数组。
 *     例如，[5,3,4,6,2]排序后为[2,3,4,5,6]，符合每两个数值之差的绝对值都为1，是可整合数组。
 *     先给定一个数组array，求出其中最大可整合子数组的长度。
 *     例如，[5,5,3,2,6,4,3]的最大可整合数组为[5,3,4,6,2]，返回5。
 *     解决方案一：
 *        对array中每个子数组array[i...j],(0<=i<=j<=N-1)，都验证是否满足可整合数组。即对array[i...j]进行排序，检查是否依次递增，
 *        并且每次递增1，然后在所有是可整合的子数组中，记录最大的那个长度即可。
 *     解决方案二：
 *        一个数组中如果没有重复元素，并且最大值减去最小值加1的结果，等于数组中的元素个数(max-min+1 == 元素个数)，那么就是可整合数组。
 * </p>
 * <p>
 *     方案一检查是否为可整合数组时间复杂度为O(N*logN)，依次遍历每个子数组array[i...j]共有O(N*N)个，因此总时间复杂度为O(N^3*logN)。
 *     方案二时间复杂度为O(N*N)
 * </p>
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
                // (max-min+1) == (j-i+1) 即(最大值减最小值加1) 等于 (元素个数)
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
