package com.wz;

import com.wz.recursionanddp.LongestCommonSubString;

public class LongestCommonSubStringTest {
    private static String solution(String self, String other) {
        if (self.length() == 0 || other.length() == 0) {
            return "";
        }
        char[] selfArray = self.toCharArray();
        char[] otherArray = other.toCharArray();
        int m = selfArray.length, n = otherArray.length;
        int[][] dp = new int[m][n];

        int max = 0, end = 0;
        for (int i = 0; i < m; i++) {
            dp[i][0] = selfArray[i] == otherArray[0] ? 1 : 0;
        }
        for (int j = 0; j < n; j++) {
            dp[0][j] = otherArray[j] == selfArray[0] ? 1 : 0;
        }
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = selfArray[i] == otherArray[j] ? dp[i - 1][j - 1] + 1 : 0;
            }
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (dp[i][j] > max) {
                    end = i;
                    max = dp[i][j];
                }
            }
        }

        return self.substring(end - max + 1, end + 1);
    }

    public static void main(String[] args) {
        int times = 100;
        boolean result = true;
        for (int i = 0; i < times; i++) {
            String self = RandomUtils.genRandomString();
            String other = RandomUtils.genRandomString();
            if (!solution(self, other).equals(LongestCommonSubString.lcsOne(self, other))) {
                result = false;
                System.out.println("Error, self:" + self + ", other:" + other);
            }
        }
        if (result) {
            System.out.println("Past");
        }
    }
}
