package com.wz;

import com.wz.string.PalindromeString;

public class PalindromeStringTest {
    private static String solution(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }
        char[] array = str.toCharArray();
        int[][] dp = generateDp(array);
        char[] result = new char[array.length + dp[0][array.length - 1]];
        int i = 0, j = array.length - 1;
        int left = 0, right = result.length - 1;
        while (i <= j) {
            if (array[i] == array[j]) {
                result[left++] = array[i++];
                result[right--] = array[j--];
            } else if (dp[i + 1][j] > dp[i][j - 1]) {
                result[left++] = array[j];
                result[right--] = array[j--];
            } else {
                result[left++] = array[i];
                result[right--] = array[i++];
            }
        }
        return String.valueOf(result);
    }

    private static int[][] generateDp(char[] array) {
        int[][] dp = new int[array.length][array.length];
        for (int j = 1; j < array.length; j++) {
            dp[j - 1][j] = array[j - 1] == array[j] ? 0 : 1;
            for (int i = j - 2; i > -1; i--) {
                if (array[i] == array[j]) {
                    dp[i][j] = dp[i + 1][j - 1];
                } else {
                    dp[i][j] = Math.min(dp[i + 1][j], dp[i][j - 1]) + 1;
                }
            }
        }
        return dp;
    }

    public static void main(String[] args) {
        int times = 100;
        boolean result = true;
        for (int i = 0; i < times; i++) {
            String str = RandomUtils.genRandomString();
            if (!solution(str).equals(PalindromeString.getPalindromeOne(str))) {
                result = false;
                System.out.println("Error, str:" + str);
            }
        }
        if (result) {
            System.out.println("Past");
        }
    }
}
