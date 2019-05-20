package com.wz;

import com.wz.recursionanddp.CoinsMin;

import java.util.Arrays;
import java.util.Random;

/**
 * <p>可重复使用时换钱的最小货币数</p>
 *
 * @author wangzi
 */
public class CoinsMinWithCycle {
    private static int solution(int[] array, int target) {
        int[][] dp = new int[array.length][target + 1];

        for (int j = 1; j <= target; j++) {
            dp[0][j] = Integer.MAX_VALUE;
            if (j - array[0] >= 0 && dp[0][j - array[0]] != Integer.MAX_VALUE) {
                dp[0][j] = dp[0][j - array[0]] + 1;
            }
        }

        for (int i = 1; i < array.length; i++) {
            for (int j = 1; j <= target; j++) {
                dp[i][j] = dp[i - 1][j];
                if (j - array[i] >= 0 && dp[i][j - array[i]] != Integer.MAX_VALUE) {
                    dp[i][j] = Math.min(dp[i][j], dp[i][j - array[i]] + 1);
                }
            }
        }
        return dp[array.length - 1][target] == Integer.MAX_VALUE ? -1 : dp[array.length - 1][target];
    }

    public static void main(String[] args) {
        int times = 100;
        boolean result = true;
        for (int i = 0; i < times; i++) {
            int[] array = RandomUtils.genRandomArrayNoRepeat(i + 1);
            int target = new Random().nextInt(10) + 1;
            if (solution(array, target) != CoinsMin.minCoinsOne(array, target)) {
                result = false;
                System.out.println("Error, array:" + Arrays.toString(array) + ", target:" + target);
            }
        }
        if (result) {
            System.out.println("Past");
        }
    }
}
