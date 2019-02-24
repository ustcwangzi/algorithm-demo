/**
 * <p>Title: PostOfficeTest</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/2/24</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz;

import com.wz.others.PostOffice;

import java.util.Arrays;

/**
 * <p>array代表居民点坐标，number代表邮局数量，选择合适的坐标，使总距离最短</p>
 *
 * @author wangzi
 */
public class PostOfficeTest {
    private static int solution(int[] array, int number) {
        if (array == null || number < 1 || array.length <= number) {
            return 0;
        }

        // w[i][j]代表array[i...j]上只建一个邮局的最短距离，显然，建在中间时总距离最短
        // 为了避免下面进行越界判断，此处大小设为(N+1)*(N+1)
        int[][] w = new int[array.length + 1][array.length + 1];
        // w[i][j]与w[i][j-1]相差的是array[j]到中间点的距离
        for (int i = 0; i < array.length; i++) {
            for (int j = i + 1; j < array.length; j++) {
                w[i][j] = w[i][j - 1] + array[j] - array[(i + j) / 2];
            }
        }

        // dp[i][j]代表array[0...j]建i+1个邮局的最短距离
        int[][] dp = new int[number][array.length];
        // 显然dp[0][j]代表array[0...j]建一个邮局的最短距离，即等于w[0][j]
        for (int j = 0; j < array.length; j++) {
            dp[0][j] = w[0][j];
        }
        for (int i = 1; i < number; i++) {
            for (int j = 0; j < array.length; j++) {
                int min = Integer.MAX_VALUE;
                for (int k = 0; k <= j; k++) {
                    // 前i个邮局负责array[0...k]，最后一个邮局负责array[k+1...j]
                    min = Math.min(min, dp[i - 1][k] + w[k + 1][j]);
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
                if (solution(array, j + 1) != PostOffice.minDistance(array, j + 1)) {
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
