/**
 * <p>Title: LongestCommonSubSequence</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/11/18</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.recursionanddynamicprogramming;

/**
 * <p>最长公共子序列</p>
 *
 * @author wangzi
 */
public class LongestCommonSubSequence {

    public static String lcs(String self, String other) {
        if (self == null || other == null || self.length() == 0 || other.length() == 0) {
            return "";
        }

        char[] chSelf = self.toCharArray();
        char[] chOther = other.toCharArray();
        int[][] result = getResult(chSelf, chOther);

        int m = chSelf.length - 1;
        int n = chOther.length - 1;
        char[] chResult = new char[result[m][n]];
        int index = chResult.length - 1;
        while (index >= 0) {
            if (n > 0 && result[m][n] == result[m][n - 1]) {
                n--;
            } else if (m > 0 && result[m][n] == result[m - 1][n]) {
                m--;
            } else {
                chResult[index--] = chSelf[m];
                m--;
                n--;
            }
        }
        return String.valueOf(chResult);
    }

    private static int[][] getResult(char[] self, char[] other) {
        int[][] result = new int[self.length][other.length];
        result[0][0] = self[0] == other[0] ? 1 : 0;
        // 第一列，比较self[i]与other[0]即可
        for (int i = 1; i < self.length; i++) {
            result[i][0] = Math.max(result[i - 1][0], self[i] == other[0] ? 1 : 0);
        }
        // 第一行，比较other[i]与self[0]即可
        for (int j = 1; j < other.length; j++) {
            result[0][j] = Math.max(result[0][j - 1], self[0] == other[j] ? 1 : 0);
        }
        // 除第一行、第一列之外的位置
        for (int i = 1; i < self.length; i++) {
            for (int j = 1; j < other.length; j++) {
                result[i][j] = Math.max(result[i - 1][j], result[i][j - 1]);
                if (self[i] == other[j]) {
                    result[i][j] = Math.max(result[i][j], result[i - 1][j - 1] + 1);
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        String self = "1A2C3D4B56";
        String other = "B1D23CA45B6A";
        System.out.println(lcs(self, other));
    }
}
