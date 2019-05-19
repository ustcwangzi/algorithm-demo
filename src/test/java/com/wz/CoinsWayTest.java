/**
 * <p>Title: CoinsWayTest</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019-05-19</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz;

import com.wz.recursionanddp.CoinsWay;

import java.util.Arrays;
import java.util.Random;

/**
 * <p>换钱的方法数</p>
 *
 * @author wangzi
 */
public class CoinsWayTest {
    private static int solution(int[] array, int target) {
        int[][] dp = new int[array.length][target + 1];
        for (int i = 0; i < array.length; i++) {
            dp[i][0] = 1;
        }
        for (int j = 1; j * array[0] <= target; j++) {
            dp[0][j * array[0]] = 1;
        }
        for (int i = 1; i < array.length; i++) {
            for (int j = 1; j <= target; j++) {
                int num = 0;
                for (int k = 0; k * array[i] <= j; k++) {
                    num += dp[i - 1][j - k * array[i]];
                }
                dp[i][j] = num;
            }
        }
        return dp[array.length - 1][target];
    }

    public static void main(String[] args) {
        int times = 5;
        boolean result = true;
        for (int i = 0; i < times; i++) {
            int[] array = RandomUtils.genRandomArrayNoRepeat(i + 1);
            int target = new Random().nextInt(10) + 1;
            if (solution(array, target) != CoinsWay.coinsWayFive(array, target)) {
                result = false;
                System.out.println("Error, array:" + Arrays.toString(array) + ", target:" + target);
            }
        }
        if (result) {
            System.out.println("Past");
        }
    }
}
