package com.wz;

import com.wz.recursionanddp.EditCost;

import java.util.Random;

public class EditCostTest {
    private static int solution(String self, String other, int ic, int dc, int rc) {
        char[] selfArray = self.toCharArray();
        char[] otherArray = other.toCharArray();
        int m = selfArray.length, n = otherArray.length;
        int[][] dp = new int[m + 1][n + 1];

        for (int i = 1; i <= m; i++) {
            dp[i][0] = dc * i;
        }
        for (int j = 1; j <= n; j++) {
            dp[0][j] = ic * j;
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (selfArray[i - 1] == otherArray[j - 1]) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = dp[i - 1][j - 1] + rc;
                }
                dp[i][j] = Math.min(dp[i][j], dp[i][j - 1] + ic);
                dp[i][j] = Math.min(dp[i][j], dc + dp[i - 1][j]);
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
            Random random = new Random();
            int ic = random.nextInt(9) + 1, dc = random.nextInt(9) + 1, rc = random.nextInt(9) + 1;
            if (solution(self, other, ic, dc, rc) != EditCost.minCostOne(self, other, ic, dc, rc)) {
                result = false;
                System.out.println("Error, self:" + self + ", other:" + other);
            }
        }
        if (result) {
            System.out.println("Past");
        }
    }
}
