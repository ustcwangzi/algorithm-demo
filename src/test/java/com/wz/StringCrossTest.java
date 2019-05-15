package com.wz;

import com.wz.recursionanddp.StringCross;

public class StringCrossTest {
    private static boolean solution(String self, String other, String target) {
        if (target.length() != self.length() + other.length()) {
            return false;
        }
        char[] selfArray = self.toCharArray();
        char[] otherArray = other.toCharArray();
        char[] targetArray = target.toCharArray();
        int m = selfArray.length, n = otherArray.length;
        boolean[][] dp = new boolean[m + 1][n + 1];
        dp[0][0] = true;

        for (int i = 1; i <= m; i++) {
            if (selfArray[i - 1] != targetArray[i - 1]) {
                break;
            }
            dp[i][0] = true;
        }
        for (int j = 1; j <= n; j++) {
            if (otherArray[j - 1] != targetArray[j - 1]) {
                break;
            }
            dp[0][j] = true;
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if ((dp[i - 1][j] && selfArray[i - 1] == targetArray[i + j - 1]) ||
                        (dp[i][j - 1] && otherArray[j - 1] == targetArray[i + j - 1])) {
                    dp[i][j] = true;
                }
            }
        }
        return dp[m][n];
    }

    public static void main(String[] args) {
        int times = 100;
        boolean result = true;
        for (int i = 0; i < times; i++) {
            String self = RandomUtils.genRandomString();
            String other = RandomUtils.genRandomString();
            String target = RandomUtils.shuffle(self + other);
            if (solution(self, other, target) != StringCross.isCrossOne(self, other, target)) {
                result = false;
                System.out.println("Error, self:" + self + ", target:" + target);
            }
        }
        if (result) {
            System.out.println("Past");
        }
    }
}
