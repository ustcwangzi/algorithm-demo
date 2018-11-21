/**
 * <p>Title: DungeonGame</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/11/17</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.recursionanddynamicprogramming;

/**
 * <p>龙与地下城游戏问题</p>
 *
 * @author wangzi
 */
public class DungeonGame {
    public static int minHPOne(int[][] map) {
        if (map == null || map.length == 0 || map[0] == null || map[0].length == 0) {
            return 1;
        }

        int row = map.length, col = map[0].length;
        // 动态规划矩阵
        int[][] dp = new int[row--][col--];
        // 最右下角的需要的血量
        dp[row][col] = map[row][col] > 0 ? 1 : 1 - map[row][col];
        // 最后一行，只能向右走
        for (int j = col - 1; j >= 0; j--) {
            dp[row][j] = Math.max(dp[row][j + 1] - map[row][j], 1);
        }
        // 向右走到达当前位置
        int right;
        // 向下走到达当前位置
        int down;
        for (int i = row - 1; i >= 0; i--) {
            // 最后一列，只能向下走
            dp[i][col] = Math.max(dp[i + 1][col] - map[i][col], 1);
            // 计算向右或向下两种选择中，那种所需血量最少
            for (int j = col - 1; j >= 0; j--) {
                right = Math.max(dp[i][j + 1] - map[i][j], 1);
                down = Math.max(dp[i + 1][j] - map[i][j], 1);
                dp[i][j] = Math.min(right, down);
            }
        }

        return dp[0][0];
    }

    public static int minHPTwo(int[][] map) {
        if (map == null || map.length == 0 || map[0] == null || map[0].length == 0) {
            return 1;
        }

        int more = Math.max(map.length, map[0].length);
        int less = Math.min(map.length, map[0].length);
        boolean rowMore = more == map.length;

        int[] dp = new int[less];
        int tmp = map[map.length - 1][map[0].length - 1];
        dp[less - 1] = tmp > 0 ? 1 : 1 - tmp;

        int row, col;
        for (int j = less - 2; j >= 0; j--) {
            row = rowMore ? more - 1 : j;
            col = rowMore ? j : more - 1;
            dp[j] = Math.max(dp[j + 1] - map[row][col], 1);
        }

        int right, down;
        for (int i = more - 2; i >= 0; i--) {
            row = rowMore ? i : less - 1;
            col = rowMore ? less - 1 : i;
            dp[less - 1] = Math.max(dp[less - 1] - map[row][col], 1);
            for (int j = less - 2; j >= 0; j--) {
                row = rowMore ? i : j;
                col = rowMore ? j : i;
                down = Math.max(dp[j] - map[row][col], 1);
                right = Math.max(dp[j + 1] - map[row][col], 1);
                dp[j] = Math.min(right, down);
            }
        }
        return dp[0];
    }

    public static void main(String[] args) {
        int[][] map = {{-2, -3, 3}, {-5, -10, 1}, {10, 30, -5}};
        System.out.println(minHPOne(map));
        System.out.println(minHPTwo(map));
    }
}
