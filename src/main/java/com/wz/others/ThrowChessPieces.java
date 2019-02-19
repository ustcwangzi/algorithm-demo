/**
 * <p>Title: ThrowChessPieces</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/2/19</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.others;

/**
 * <p>丢棋子问题</p>
 *
 * @author wangzi
 */
public class ThrowChessPieces {

    /**
     * 暴力递归
     */
    public static int solutionOne(int n, int k) {
        if (n < 1 || k < 1) {
            return 0;
        }
        return processOne(n, k);
    }

    private static int processOne(int n, int k) {
        if (n == 0) {
            return 0;
        }
        if (k == 1) {
            return n;
        }
        int min = Integer.MAX_VALUE;
        for (int i = 1; i < n + 1; i++) {
            // 碎了，i层以上不用再试，剩下i-1层楼和k-1个棋子；没碎，i层以下不用再试，剩下N-i层楼和k个棋子
            min = Math.min(min, Math.max(processOne(i - 1, k - 1), processOne(n - i, k)));
        }
        return min + 1;
    }

    /**
     * 动态规划
     */
    public static int solutionTwo(int n, int k) {
        if (n < 1 || k < 1) {
            return 0;
        }
        if (k == 1) {
            return n;
        }

        int[][] dp = new int[n + 1][k + 1];
        // dp[N][1]=N，只使用一个棋子
        for (int i = 1; i < dp.length; i++) {
            dp[i][1] = i;
        }
        // i层j个棋子时的次数
        for (int i = 1; i < dp.length; i++) {
            for (int j = 2; j < dp[0].length; j++) {
                int min = Integer.MAX_VALUE;
                // 1～i层逐个尝试最优解
                for (int h = 1; h < i + 1; h++) {
                    // 碎了，h层以上不用再试，剩下h-1层楼和j-1个棋子；没碎，h层以下不用再试，剩下i-h层楼和j个棋子
                    min = Math.min(min, Math.max(dp[h - 1][j - 1], dp[i - h][j]));
                }
                dp[i][j] = min + 1;
            }
        }
        return dp[n][k];
    }

    /**
     * 动态规划空间压缩
     */
    public static int solutionThree(int n, int k) {
        if (n < 1 || k < 1) {
            return 0;
        }
        if (k == 1) {
            return n;
        }

        int[] preArray = new int[n + 1];
        int[] curArray = new int[n + 1];
        // 只使用一个棋子
        for (int i = 1; i < curArray.length; i++) {
            curArray[i] = i;
        }

        for (int i = 1; i < k; i++) {
            int[] tmp = preArray;
            preArray = curArray;
            curArray = tmp;
            for (int j = 1; j < curArray.length; j++) {
                int min = Integer.MAX_VALUE;
                for (int h = 1; h < j + 1; h++) {
                    // dp[N][K]只依赖了左边的数据dp[0...N-1][K-1]和它上面的数据dp[0...N-1][K]
                    min = Math.min(min, Math.max(preArray[h - 1], curArray[j - h]));
                }
                curArray[j] = min + 1;
            }
        }
        return curArray[n];
    }

    public static void main(String[] args) {
        int n = 21, k = 2;
        System.out.println(solutionOne(n, k));
        System.out.println(solutionTwo(n, k));
        System.out.println(solutionThree(n, k));
    }
}
