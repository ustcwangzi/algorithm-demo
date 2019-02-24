/**
 * <p>Title: ArtistTest</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/2/24</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz;

import com.wz.others.Artist;

import java.util.Arrays;

/**
 * <p>array表示每幅画所需时间，number表示画匠数量，只能负责连续的画，求最短完成时间</p>
 *
 * @author wangzi
 */
public class ArtistTest {
    private static int solution(int[] array, int number) {
        if (array == null || number < 1 || array.length == 0) {
            return 0;
        }

        // sum[i]表示一个画匠完成array[0...i]所需时间
        int[] sum = new int[array.length];
        sum[0] = array[0];
        // dp[i][j]表示i+1个画匠完成array[0...j]所需时间
        int[][] dp = new int[number][array.length];
        dp[0][0] = array[0];
        for (int j = 1; j < array.length; j++) {
            sum[j] = sum[j - 1] + array[j];
            // 显然dp[0][j]表示1个画匠完成array[0...j]所需时间，等于sum[j]
            dp[0][j] = sum[j];
        }

        for (int i = 1; i < number; i++) {
            for (int j = 0; j < array.length; j++) {
                int min = Integer.MAX_VALUE;
                for (int k = 0; k <= j; k++) {
                    // 前i个画匠负责array[0...k]，最后一个画匠负责array[k+1...j]
                    min = Math.min(min, Math.max(dp[i - 1][k], sum[j] - sum[k]));
                }
                dp[i][j] = min;
            }
        }
        return dp[number - 1][array.length - 1];
    }

    public static void main(String[] args) {
        int times = 100;
        boolean result = true;
        for (int i = 0; i < times; i++) {
            int[] array = RandomUtils.genRandomArray(i + 1);
            for (int j = 0; j < array.length; j++) {
                if (solution(array, j + 1) != Artist.solutionOne(array, j + 1)) {
                    result = false;
                    System.out.println("Error, array:" + Arrays.toString(array) + ", number:" + j + 1);
                }
            }
        }
        if (result) {
            System.out.println("Past");
        }
    }
}
