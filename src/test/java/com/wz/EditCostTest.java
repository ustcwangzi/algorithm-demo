package com.wz;

import com.wz.recursionanddp.EditCost;

import java.util.Random;

/**
 * <p>将self通过插入、删除、替换操作编辑成other的最小代价</p>
 *
 * @author wangzi
 */
public class EditCostTest {
    private static int solution(String self, String other, int ic, int dc, int rc) {
        char[] selfArray = self.toCharArray();
        char[] otherArray = other.toCharArray();
        int m = selfArray.length, n = otherArray.length;

        // dp[i][j]表示将self[0...i-1]编辑成other[0...j-1]的最小代价
        int[][] dp = new int[m + 1][n + 1];

        // 第一列表示将self[0...M-1]编辑成空的最小代价，直接删除
        for (int i = 1; i <= m; i++) {
            dp[i][0] = dc * i;
        }
        // 第一行表示将空编辑成other[0...N-1]的最小代价，直接插入
        for (int j = 1; j <= n; j++) {
            dp[0][j] = ic * j;
        }

        // 1、self[0...i-1]编辑到other[0...j-2]，再插入other[j-1]
        // 2、self[0...i-1]删除self[0...i-1]变为self[0...i-2]，再编辑到other[0...j-1]
        // 3、self[i-1]==other[j-1]，则直接由self[0...i-2]编辑到other[0...j-2]
        // 4、self[i-1]!=other[j-1]，由self[0...i-2]编辑到other[0...j-2]，再将self[i-1]替换为other[j-1]
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
