/**
 * <p>Title: SmallestUnFormedSumTest</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/3/23</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz;

import com.wz.others.SmallestUnFormedSum;

import java.util.Arrays;

/**
 * <p>正数数组的最小不可组成和</p>
 *
 * @author wangzi
 */
public class SmallestUnFormedSumTest {

    private static int solution(int[] array) {
        int min = Integer.MAX_VALUE;
        int sum = 0;
        for (int cur : array) {
            min = Math.min(min, cur);
            sum += cur;
        }

        // dp[i]表示i能否被array的子集累加得到
        boolean[] dp = new boolean[sum + 1];
        dp[0] = true;
        for (int cur : array) {
            for (int j = sum; j >= cur; j--) {
                // array[0...i]能够累加得到j，那么array[0...i+1]能够累加得到j+array[i]
                dp[j] = dp[j - cur] || dp[j];
            }
        }

        for (int i = min + 1; i <= sum; i++) {
            if (!dp[i]) {
                return i;
            }
        }
        return sum + 1;
    }

    public static void main(String[] args) {
        int times = 100;
        boolean result = true;
        for (int i = 0; i < times; i++) {
            int[] array = RandomUtils.genRandomArray();
            if (solution(array) != SmallestUnFormedSum.unformedSumTwo(array)) {
                result = false;
                System.out.println("Error, array:" + Arrays.toString(array));
            }
        }
        if (result) {
            System.out.println("Past");
        }
    }
}
