package com.wz;

import com.wz.string.PalindromeMinCut;

public class PalindromeMinCutTest {
    private static int solution(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        char[] array = str.toCharArray();
        int length = array.length;
        int[] dp = new int[length + 1];
        dp[length] = -1;
        boolean[][] isPalindrome = new boolean[length][length];
        for (int i = length - 1; i >= 0; i--) {
            dp[i] = Integer.MAX_VALUE;
            for (int j = i; j < length; j++) {
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
