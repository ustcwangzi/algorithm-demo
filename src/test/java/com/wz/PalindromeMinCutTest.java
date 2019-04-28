package com.wz;

import com.wz.string.PalindromeMinCut;

/**
 * <p>把字符串全部切成回文子串的最小分割数</p>
 *
 * @author wangzi
 */
public class PalindromeMinCutTest {
    private static int solution(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }

        char[] array = str.toCharArray();
        int length = array.length;
        // dp[i]表示str[i...N-1]至少要切割的次数，那么dp[0]就是结果
        int[] dp = new int[length + 1];
        dp[length] = -1;
        // isPalindrome[i][j]表示str[i...j]是否是回文，三种情况是回文：
        // str[i...j]由1个字符组成；str[i...j]由2个字符组成，并且2个字符相等；
        // str[i+1...j-1]是回文串，并且str[i]==str[j]
        boolean[][] isPalindrome = new boolean[length][length];

        // 假设j位于i与N-1之间，如果str[i...j]是回文串，那么dp[i]可能是dp[j+1]+1，
        // 因为在str[i...N-1]上，除去str[i...j]是回文，剩下部分str[j+1...N-1]需要继续做切割
        // 让j在i到N-1上枚举，其中的最小值就是dp[i]的值，即dp[i]=Min{dp[j+1]+1}
        for (int i = length - 1; i >= 0; i--) {
            dp[i] = Integer.MAX_VALUE;
            for (int j = i; j < length; j++) {
                // i从右到左遍历，j从i向右遍历，因此对于每个isPalindrome[i][j]，isPalindrome[i+1][j-1]一定是计算过的
                if (array[i] == array[j] && (j - i < 2 || isPalindrome[i + 1][j - 1])) {
                    isPalindrome[i][j] = true;
                    dp[i] = Math.min(dp[i], dp[j + 1] + 1);
                }
            }
        }
        return dp[0];
    }

    public static void main(String[] args) {
        int times = 100;
        boolean result = true;
        for (int i = 0; i < times; i++) {
            String str = RandomUtils.genRandomString();
            if (solution(str) != PalindromeMinCut.minCut(str)) {
                result = false;
                System.out.println("Error, str:" + str);
            }
        }
        if (result) {
            System.out.println("Past");
        }
    }
}
