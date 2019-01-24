/**
 * <p>Title: SmallestUnFormedSum</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/1/24</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.others;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>正数数组的最小不可组成和</p>
 *
 * @author wangzi
 */
public class SmallestUnFormedSum {

    /**
     * 暴力递归
     */
    public static int unformedSumOne(int[] array) {
        if (array == null || array.length == 0) {
            return 1;
        }

        int min = Integer.MAX_VALUE;
        int max = 0;
        for (int cur : array) {
            min = Math.min(cur, min);
            max += cur;
        }

        Set<Integer> set = new HashSet<>();
        // 计算所有子集的和
        process(0, 0, array, set);

        for (int num = min + 1; num < max; num++) {
            if (!set.contains(num)) {
                return num;
            }
        }

        return max + 1;
    }

    /**
     * 计算所有子集的累加和
     * 0～i位置上已经完成决策，和为sum，决定i位置上的元素要或者不要
     */
    private static void process(int i, int sum, int[] array, Set<Integer> set) {
        if (i == array.length) {
            set.add(sum);
            return;
        }
        // 不包含当前数array[i]的情况
        process(i + 1, sum, array, set);
        // 包含当前数array[i]的情况
        process(i + 1, sum + array[i], array, set);
    }

    /**
     * 动态规划
     */
    public static int unformedSumTwo(int[] array) {
        if (array == null || array.length == 0) {
            return 1;
        }

        int min = Integer.MAX_VALUE;
        int max = 0;
        for (int cur : array) {
            min = Math.min(cur, min);
            max += cur;
        }

        // dp[j]代表j这个累加和能否array的子集相加得到
        boolean[] dp = new boolean[max + 1];
        dp[0] = true;
        // 如果array[0...i]组成的子集可以累加出k，那么array[0...i+1]组成的子集必然可以累加出k+array[i+1]
        for (int cur : array) {
            for (int j = max; j >= cur; j--) {
                dp[j] = dp[j - cur] || dp[j];
            }
        }

        for (int num = min + 1; num < max; num++) {
            if (!dp[num]) {
                return num;
            }
        }
        return max + 1;
    }

    /**
     * array中一定包含1的情况
     */
    public static int unformedSumContainOne(int[] array) {
        if (array == null || array.length == 0) {
            return 1;
        }

        // 将array排序，必然有array[0]==1
        Arrays.sort(array);

        // [1,range]区间内得所有正数都可以被array[0...i-1]的某个子集相加表示
        int range = 0;
        for (int cur : array) {
            // array是有序的，array[i]往后的数都不会再出现range+1
            if (cur > range + 1) {
                return range + 1;
            }
            range += cur;
        }

        return range + 1;
    }

    public static void main(String[] args) {
        int[] array = {3, 2, 5};
        System.out.println(unformedSumOne(array));
        System.out.println(unformedSumTwo(array));

        array = new int[]{1, 2, 4};
        System.out.println(unformedSumOne(array));
        System.out.println(unformedSumTwo(array));
        System.out.println(unformedSumContainOne(array));
    }
}
