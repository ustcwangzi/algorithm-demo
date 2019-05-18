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
        int[] dp = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            dp[i] = 1;
            for (int k = 0; k < i; k++) {
                if (array[k] < array[i]) {
                    dp[i] = Math.max(dp[i], dp[k] + 1);
                }
            }
        }

        int max = 0, end = 0;
        for (int i = 0; i < dp.length; i++) {
            if (dp[i] > max) {
                max = dp[i];
                end = i;
            }
        }

        int[] result = new int[max];
        result[--max] = array[end];
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
