/**
 * <p>Title: LongestIncreaseSubSequenceTest</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019-05-18</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz;

import com.wz.recursionanddp.LongestIncreaseSubSequence;

import java.util.Arrays;

/**
 * <p>最长递增子序列</p>
 *
 * @author wangzi
 */
public class LongestIncreaseSubSequenceTest {
    private static int[] solution(int[] array) {
        // dp[i]表示以array[i]为结尾的最长递增子序列长度
        int[] dp = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            dp[i] = 1;
            // 在array[0...i-1]上找到小于array[i]的元素，并且以该元素结尾的最长递增子序列长度最大
            // 该元素的最长递增子序列长度加一就是dp[i]
            for (int k = 0; k < i; k++) {
                if (array[k] < array[i]) {
                    dp[i] = Math.max(dp[i], dp[k] + 1);
                }
            }
        }

        // 找到最长递增子序列的最大长度及位置
        int max = 0, end = 0;
        for (int i = 0; i < dp.length; i++) {
            if (dp[i] > max) {
                max = dp[i];
                end = i;
            }
        }

        int[] result = new int[max];
        result[--max] = array[end];
        // 从最大长度的位置逆序遍历，逐步找到每个元素
        // 该元素值小于当前已找到的最大值、并且长度为当前长度减一
        for (int i = end; i > -1; i--) {
            if (array[i] < array[end] && dp[i] == dp[end] - 1) {
                result[--max] = array[i];
                end = i;
            }
        }

        return result;
    }

    public static void main(String[] args) {
        int times = 100;
        boolean result = true;
        for (int i = 0; i < times; i++) {
            int[] array = RandomUtils.genRandomArray();
            if (!Arrays.equals(solution(array), LongestIncreaseSubSequence.lisOne(array))) {
                result = false;
                System.out.println("Error, array:" + Arrays.toString(array));
            }
        }
        if (result) {
            System.out.println("Past");
        }
    }
}
