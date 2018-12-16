/**
 * <p>Title: PalindromeMinCut</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/12/16</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.string;

/**
 * <p>回文最小分割数</p>
 *
 * @author wangzi
 */
public class PalindromeMinCut {

    public static int minCut(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }

        char[] array = str.toCharArray();
        int len = array.length;
        // 动态规划数组
        int[] dp = new int[len + 1];
        dp[len] = -1;
        boolean[][] isPalindrome = new boolean[len][len];
        for (int i = len - 1; i >= 0; i--) {
            dp[i] = Integer.MAX_VALUE;
            for (int j = i; j < len; j++) {
                if (array[i] == array[j] && (j - i < 2 || isPalindrome[i + 1][j - 1])) {
                    isPalindrome[i][j] = true;
                    dp[i] = Math.min(dp[i], dp[j + 1] + 1);
                }
            }
        }

        return dp[0];
    }

    public static void main(String[] args) {
        System.out.println(minCut("ABA"));
        System.out.println(minCut("ACDCDCDAD"));
    }
}
