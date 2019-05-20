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
        // dp[i][j]表示使用array[0...i]组成的j的最小货币张数
        int[][] dp = new int[array.length][target + 1];

        // 第一列dp[0...M-1][0]组成0，不需要任何货币，因此全是0
        // 第一行dp[0][0...N-1]只使用array[0]组成j，能整除的结果为商，不能整除的设为MAX
        for (int j = 1; j <= target; j++) {
            dp[0][j] = Integer.MAX_VALUE;
            if (j - array[0] >= 0 && dp[0][j - array[0]] != Integer.MAX_VALUE) {
                dp[0][j] = dp[0][j - array[0]] + 1;
            }
        }

        // 组成dp[i][j]，可以不使用array[i]，此时货币数为dp[i-1][j]；只使用一张array[i]，此时货币数为dp[i-1][j-array[i]]+1
        // 只使用k张array[i]，此时方法数为dp[i-1][j-k*array[i]]+k；以上各种可能中最小的，就是结果
        // dp[i][j] = min{dp[i-1][j-k*array[i]]+k} (k>=0)
        //          = min{dp[i-1][j], min{dp[i-1][j-h*array[i]]+h}}  (h>=1)
        // min{dp[i-1][j-h*array[i]]+h} (h>=1) = min{dp[i-1][j-array[i]-y*array[i]]+y+1} (y>=0)
        //                                     = dp[i][j-array[i]]+1
        // 因此 dp[i][j] = min{dp[i-1][j], dp[i][j-array[i]]+1}
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
