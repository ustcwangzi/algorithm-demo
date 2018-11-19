/**
 * <p>Title: EditCost</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/11/17</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.recursionanddynamicprogramming;

/**
 * <p>最小编辑代价</p>
 *
 * @author wangzi
 */
public class EditCost {
    public static int minCostOne(String self, String other, int ic, int dc, int rc) {
        if (self == null || other == null) {
            return 0;
        }

        char[] chSelf = self.toCharArray();
        char[] chOther = other.toCharArray();
        int row = chSelf.length + 1;
        int col = chOther.length + 1;
        // 动态规划矩阵
        int[][] dp = new int[row][col];
        // 第一列，把chSelf[0...i-1]编辑成空串的代价，即删除chSelf[0...i-1]所有的字符
        for (int i = 1; i < row; i++) {
            dp[i][0] = dc * i;
        }
        // 第一列，把空串编辑成chOther[0...i-1]的代价，即插入chOther[0...i-1]所有的字符
        for (int j = 1; j < col; j++) {
            dp[0][j] = ic * j;
        }
        // 除第一行、第一列的其他位置
        for (int i = 1; i < row; i++) {
            for (int j = 1; j < col; j++) {
                if (chSelf[i - 1] == chOther[j - 1]) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = dp[i - 1][j - 1] + rc;
                }
                dp[i][j] = Math.min(dp[i][j], dp[i][j - 1] + ic);
                dp[i][j] = Math.min(dp[i][j], dp[i - 1][j] + dc);
            }
        }

        return dp[row - 1][col - 1];
    }

    public static int minCostTwo(String self, String other, int ic, int dc, int rc) {
        if (self == null || other == null) {
            return 0;
        }

        char[] chSelf = self.toCharArray();
        char[] chOther = other.toCharArray();
        char[] longs = chSelf.length >= chOther.length ? chSelf : chOther;
        char[] shorts = chSelf.length < chOther.length ? chSelf : chOther;
        // chOther较长就交换ic和dc的值
        if (chSelf.length < chOther.length) {
            int tmp = ic;
            ic = dc;
            dc = tmp;
        }

        int[] dp = new int[shorts.length + 1];
        for (int i = 1; i <= shorts.length; i++) {
            dp[i] = ic * i;
        }
        for (int i = 1; i <= longs.length; i++) {
            // pre表示左上角的值
            int pre = dp[0];
            dp[0] = dc * i;
            for (int j = 1; j <= shorts.length; j++) {
                // 保存dp[j]没更新前的值
                int tmp = dp[j];
                if (longs[j - 1] == shorts[j - 1]) {
                    dp[j] = pre;
                } else {
                    dp[j] = pre + rc;
                }
                dp[j] = Math.min(dp[j], dp[j - 1] + ic);
                dp[j] = Math.min(dp[j], tmp + dc);
                // pre变成dp[j]没更新前的值
                pre = tmp;
            }
        }

        return dp[shorts.length];
    }

    public static void main(String[] args) {
        String self = "abc";
        String other = "adc";
        System.out.println(minCostOne(self, other, 5, 3, 2));
        System.out.println(minCostOne(self, other, 5, 3, 10));
        System.out.println(minCostTwo(self, other, 5, 3, 2));
        System.out.println(minCostTwo(self, other, 5, 3, 10));
    }
}
