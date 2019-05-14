package com.wz;

import com.wz.recursionanddp.DungeonGame;

/**
 * <p>从左上角到右下角所需的最少血量，每个位置上都不能小于1</p>
 *
 * @author wangzi
 */
public class DungeonGameTest {
    private static int solution(int[][] map) {
        int row = map.length, col = map[0].length;
        // dp[i][j]代表如果要走上位置(i,j)，并且从该位置选一条最优路径，最后走到右下角所需要的最少血量
        int[][] dp = new int[row][col];

        // 最后一个位置所需的血量
        dp[row - 1][col - 1] = map[row - 1][col - 1] > 0 ? 1 : 1 - map[row - 1][col - 1];
        // 最后一行，只能向右走
        for (int j = col - 2; j > -1; j--) {
            dp[row - 1][j] = Math.max(dp[row - 1][j + 1] - map[row - 1][j], 1);
        }
        for (int i = row - 2; i > -1; i--) {
            // 最后一列，只能向下走
            dp[i][col - 1] = Math.max(dp[i + 1][col - 1] - map[i][col - 1], 1);

            // 其他位置，可以向右或向下，选择所需血量最小的
            for (int j = col - 2; j > -1; j--) {
                int right = Math.max(dp[i][j + 1] - map[i][j], 1);
                int down = Math.max(dp[i + 1][j] - map[i][j], 1);
                dp[i][j] = Math.min(right, down);
            }
        }
        return dp[0][0];
    }

    public static void main(String[] args) {
        int[][] map = {{-2, -3, 3}, {-5, -10, 1}, {10, 30, -5}};
        if (solution(map) != DungeonGame.minHPOne(map)) {
            System.out.println("Error");
        } else {
            System.out.println("Past");
        }
    }
}
