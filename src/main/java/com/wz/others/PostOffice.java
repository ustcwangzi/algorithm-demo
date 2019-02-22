/**
 * <p>Title: PostOffice</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/2/22</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.others;

/**
 * <p>邮局选址问题</p>
 *
 * @author wangzi
 */
public class PostOffice {

    public static int minDistance(int[] array, int number) {
        if (array == null || number < 1 || array.length <= number) {
            return 0;
        }

        // array[i...j]上只建立一个邮局，最短的总距离
        int[][] w = new int[array.length + 1][array.length + 1];
        for (int i = 0; i < array.length; i++) {
            for (int j = i + 1; j < array.length; j++) {
                w[i][j] = w[i][j - 1] + array[j] - array[(i + j) / 2];
            }
        }

        int[][] dp = new int[number][array.length];
        // array[0...j]上建立一个邮局最短的总距离
        for (int j = 0; j < array.length; j++) {
            dp[0][j] = w[0][j];
        }
        // array[0...j]上建立i+1个邮局的最短总距离
        for (int i = 1; i < number; i++) {
            for (int j = i + 1; j < array.length; j++) {
                dp[i][j] = Integer.MAX_VALUE;
                // 0～j逐个尝试最优解
                for (int k = 0; k <= j; k++) {
                    // 前i个邮局负责array[0...k]，最后一个邮局负责array[k+1...j]
                    dp[i][j] = Math.min(dp[i][j], dp[i - 1][k] + w[k + 1][j]);
                }
            }
        }
        return dp[number - 1][array.length - 1];
    }

    public static void main(String[] args) {
        int[] array = {-2, -1, 0, 1, 2, 1000};
        for (int i = 1; i < array.length + 1; i++) {
            System.out.println(minDistance(array, i));
        }
    }
}
