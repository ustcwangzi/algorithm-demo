/**
 * <p>Title: LongestCommonSubString</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/11/18</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.recursionanddynamicprogramming;

/**
 * <p>最长公共子串</p>
 *
 * @author wangzi
 */
public class LongestCommonSubString {

    public static String lscOne(String self, String other) {
        if (self == null || other == null || self.length() == 0 || other.length() == 0) {
            return "";
        }

        char[] chSelf = self.toCharArray();
        char[] chOther = other.toCharArray();
        // 生成动态规划矩阵
        int[][] result = getResult(chSelf, chOther);
        // 最大长度
        int end = 0;
        // 最长公共子串的结尾位置
        int max = 0;
        for (int i = 0; i < chSelf.length; i++) {
            for (int j = 0; j < chOther.length; j++) {
                if (result[i][j] > max) {
                    end = i;
                    max = result[i][j];
                }
            }
        }

        return self.substring(end - max + 1, end + 1);
    }

    private static int[][] getResult(char[] self, char[] other) {
        int[][] result = new int[self.length][other.length];
        // 第一列，直接比较self[i]与other[0]
        for (int i = 0; i < self.length; i++) {
            if (self[i] == other[0]) {
                result[i][0] = 1;
            }
        }
        // 第一列，直接比较self[0]与other[j]
        for (int j = 1; j < other.length; j++) {
            if (self[0] == other[j]) {
                result[0][j] = 1;
            }
        }
        // 除第一行、第一列之外的其他位置
        for (int i = 1; i < self.length; i++) {
            for (int j = 1; j < other.length; j++) {
                if (self[i] == other[j]) {
                    result[i][j] = result[i - 1][j - 1] + 1;
                }
            }
        }

        return result;
    }

    public static String lscTwo(String self, String other) {
        if (self == null || other == null || self.length() == 0 || other.length() == 0) {
            return "";
        }

        char[] chSelf = self.toCharArray();
        char[] chOther = other.toCharArray();
        // 斜线开始位置的行、列
        int row = 0, col = chOther.length - 1;
        // 最大长度
        int max = 0;
        // 最长公共子串的结尾位置
        int end = 0;
        while (row < chSelf.length) {
            int i = row, j = col, len = 0;
            // 从(i,j)开始向右下方遍历
            while (i < chSelf.length && j < chOther.length) {
                if (chSelf[i] != chOther[j]) {
                    len = 0;
                } else {
                    len++;
                }
                // 记录最大值，以及结束字符串的位置
                if (len > max) {
                    end = i;
                    max = len;
                }
                i++;
                j++;
            }
            if (col > 0) {
                // 斜线开始位置的列先向左移动
                col--;
            } else {
                // 列移动到最左之后，行向下移动
                row++;
            }
        }

        return self.substring(end - max + 1, end + 1);
    }

    public static void main(String[] args) {
        String self = "1AB2345CD";
        String other = "12345EF";
        System.out.println(lscOne(self, other));
        System.out.println(lscTwo(self, other));
    }
}
