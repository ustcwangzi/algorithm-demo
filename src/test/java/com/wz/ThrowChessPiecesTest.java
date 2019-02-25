/**
 * <p>Title: ThrowChessPiecesTest</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/2/25</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz;

import com.wz.others.ThrowChessPieces;

public class ThrowChessPiecesTest {
    private static int solution(int m, int n) {
        if (m < 1 || n < 1) {
            return 0;
        }
        // dp[i][j]表示i层楼j个棋子时的次数
        int[][] dp = new int[m + 1][n + 1];
        // 只有一个棋子时，显然需要每一层都尝试
        for (int i = 1; i < m + 1; i++) {
            dp[i][1] = i;
        }
        for (int i = 1; i < m + 1; i++) {
            for (int j = 2; j < n + 1; j++) {
                int min = Integer.MAX_VALUE;
                for (int k = 1; k <= i; k++) {
                    min = Math.min(min, Math.max(dp[i - k][j], dp[k - 1][j - 1]));
                }
                dp[i][j] = min + 1;
            }
        }
        return dp[m][n];
    }

    public static void main(String[] args) {
        int times = 100;
        boolean result = true;
        for (int i = 0; i < times; i++) {
            int m = RandomUtils.genRandomNumber();
            for (int j = 0; j < m; j++) {
                if (solution(m, j + 1) != ThrowChessPieces.solutionTwo(m, j + 1)) {
                    result = false;
                    System.out.println("Error, m:" + m + ", n:" + j + 1);
                }
            }
        }
        if (result) {
            System.out.println("Past");
        }
    }
}
