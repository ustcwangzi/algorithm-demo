package com.wz;

import com.wz.recursionanddp.MinPathSum;

import java.util.Arrays;

/**
 * <p>从左上角到右下角的最小路径和</p>
 *
 * @author wangzi
 */
public class MinPathSumTest {
    private static int solution(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;
        int[][] dp = new int[m][n];
        dp[0][0] = matrix[0][0];

        for (int i = 1; i < m; i++) {
            dp[i][0] = dp[i - 1][0] + matrix[i][0];
        }
        for (int j = 1; j < n; j++) {
            dp[0][j] = dp[0][j - 1] + matrix[0][j];
        }

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + matrix[i][j];
            }
        }
        return dp[m - 1][n - 1];
    }

    public static void main(String[] args) {
        int times = 20;
        boolean result = true;
        for (int i = 0; i < times; i++) {
            int[][] matrix = RandomUtils.genRandomMatrix();
            if (solution(matrix) != MinPathSum.minPathSunOne(matrix)) {
                result = false;
                System.out.println("Error, matrix:" + Arrays.toString(matrix));
            }
        }
        if (result) {
            System.out.println("Past");
        }
    }
}
