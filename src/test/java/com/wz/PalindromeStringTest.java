package com.wz;

import com.wz.string.PalindromeString;

/**
 * <p>添加最少的字符使字符串变为回文字符串</p>
 *
 * @author wangzi
 */
public class PalindromeStringTest {
    private static String solution(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }

        char[] array = str.toCharArray();
        // dp[i][j]代表子串str[i...j]最少添加几个字符可以使得str[i...j]变为回文串
        int[][] dp = generateDp(array);
        // 最终的结果
        char[] result = new char[array.length + dp[0][array.length - 1]];
        int i = 0, j = array.length - 1;
        int left = 0, right = result.length - 1;
        while (i <= j) {
            if (array[i] == array[j]) {
                // array[i] + (array[i+1...j-1]的回文结果) + array[j]
                result[left++] = array[i++];
                result[right--] = array[j--];
            } else if (dp[i + 1][j] > dp[i][j - 1]) {
                // array[j] + (array[i...j-1]的回文结果) + array[j]，即左侧增加array[j]
                result[left++] = array[j];
                result[right--] = array[j--];
            } else {
                // array[i] + (array[i+1...j]的回文结果) + array[i]，即右侧增加array[i]
                result[left++] = array[i];
                result[right--] = array[i++];
            }
        }
        return String.valueOf(result);
    }

    /**
     * 1、str[i...j]只有一个字符，不必添加任何字符
     * 2、str[i...j]只有两个字符，两个字符相等，那么不需要添加任何字符，若不相等，那么需要添加一个字符
     * 3、str[i...j]多于两个字符，若str[i] == str[j]，那么dp[i][j]=dp[i+1][j-1]，比如"A123A"与"123"
     * 若str[i] != str[j]，那么可先让str[i...j-1]变成回文，然后在左侧加上str[j]，
     * 或先让str[i+1...j]变成回文，然后在右侧加上str[i]，选择代价最小的，即min{dp[i][j-1], dp[i+1][j]} + 1
     */
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
