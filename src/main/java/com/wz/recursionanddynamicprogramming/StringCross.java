/**
 * <p>Title: StringCross</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/11/17</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.recursionanddynamicprogramming;

/**
 * <p>字符串的交错组成</p>
 *
 * @author wangzi
 */
public class StringCross {
    /**
     * 经典动态规划解法
     */
    public static boolean isCrossOne(String self, String other, String aim) {
        if (self == null || other == null || aim == null) {
            return false;
        }

        char[] chSelf = self.toCharArray();
        char[] chOther = other.toCharArray();
        char[] chAim = aim.toCharArray();
        if (chAim.length != chSelf.length + chOther.length) {
            return false;
        }

        // 动态规划矩阵
        boolean[][] dp = new boolean[chSelf.length + 1][chOther.length + 1];
        dp[0][0] = true;
        // 第一列，只能使用chSelf[0...i-1]
        for (int i = 1; i <= chSelf.length; i++) {
            if (chSelf[i - 1] != chAim[i - 1]) {
                break;
            }
            dp[i][0] = true;
        }
        // 第一行，只能使用chOther[0...i-1]
        for (int j = 1; j <= chOther.length; j++) {
            if (chOther[j - 1] != chAim[j - 1]) {
                break;
            }
            dp[0][j] = true;
        }
        // 除第一列、第一行之外的其他位置
        for (int i = 1; i <= chSelf.length; i++) {
            for (int j = 1; j <= chOther.length; j++) {
                if ((chSelf[i - 1] == chAim[i + j - 1] && dp[i - 1][j]) ||
                        (chOther[j - 1] == chAim[i + j - 1] && dp[i][j - 1])) {
                    dp[i][j] = true;
                }
            }
        }

        return dp[chSelf.length][chOther.length];
    }

    /**
     * 动态规划基础上的空间压缩解法
     */
    public static boolean isCrossTwo(String self, String other, String aim) {
        if (self == null || other == null || aim == null) {
            return false;
        }

        char[] chSelf = self.toCharArray();
        char[] chOther = other.toCharArray();
        char[] chAim = aim.toCharArray();
        if (chAim.length != chSelf.length + chOther.length) {
            return false;
        }

        char[] longs = chSelf.length >= chOther.length ? chSelf : chOther;
        char[] shorts = chSelf.length < chOther.length ? chSelf : chOther;
        // 长度较小的最为列对应的字符串
        boolean[] dp = new boolean[shorts.length + 1];
        dp[0] = true;

        for (int i = 1; i <= shorts.length; i++) {
            if (shorts[i - 1] != chAim[i - 1]) {
                break;
            }
            dp[i] = true;
        }

        // 滚动更新
        for (int i = 1; i <= longs.length; i++) {
            dp[0] = dp[0] && longs[i - 1] == chAim[i - 1];
            for (int j = 1; j <= shorts.length; j++) {
                if ((longs[i - 1] == chAim[i + j - 1] && dp[j]) ||
                        (shorts[j - 1] == chAim[i + j - 1] && dp[j - 1])) {
                    dp[j] = true;
                }
            }
        }

        return dp[shorts.length];
    }

    public static void main(String[] args) {
        String self = "1234";
        String other = "abcd";
        String aim = "1a23bcd4";
        System.out.println(isCrossOne(self, other, aim));
        System.out.println(isCrossTwo(self, other, aim));
    }
}
