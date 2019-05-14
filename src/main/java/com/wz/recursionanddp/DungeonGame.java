/**
 * <p>Title: DungeonGame</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/11/17</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.recursionanddp;

/**
 * <p>龙与地下城游戏问题</p>
 * <p>
 *     给定一个二维数组map，含义是一张地图，游戏规则如下：
 *     1、骑士从左上角除法，每次只能向下或向右走，最后达到右下角见到公主
 *     2、地图上每个位置的值代表骑士要遭遇的事情，如果是负数，会让骑士损失血量，否则骑士会增加血量
 *     3、骑士从左上角到右下角过程中，任何一个位置的血量都不能少于1
 *     为保证骑士见到公主，求出初始血量至少为多少
 *     方案一：
 *        经典的动态规划解法。
 *        生成大小为M*N的动态规划矩阵dp，dp[i][j]代表如果骑士要走上位置(i,j)，并且从该位置选一条最优路径，最后走到右下角，所需要的最少血量。
 *        根据dp的定义，我们最终需要的是dp[0][0]的结果，从右下角开始，采用从右至左、再从下到上的方式进行计算：
 *        1、矩阵的右下角的位置dp[M-1][N-1]，是骑士到达的最后位置，只要血量不少于1即可
 *        2、矩阵最后一行dp[M-1][0...N-1]，骑士只能向右走，满足当前位置(M-1，j)加上血或者扣完血之后的血量等于dp[M-1][j+1]即可，
 *           那么，骑士在加上血或者扣完血之前的血量要求为dp[M-1][j+1] - map[M-1][j]
 *        3、矩阵最后一列dp[0..M-1][N-1]，骑士只能向下走，满足当前位置(i，N-1)加上血或者扣完血之后的血量等于dp[i+1][N-1]即可，
 *           那么，骑士在加上血或者扣完血之前的血量要求为dp[i+1][N-1] - map[i][N-1]
 *        4、对于其他位置(i,j)，骑士可选择向右或向下，向右时要满足当前位置(i，j)加上血或者扣完血之后的血量等于dp[i+1][j]，
 *           即之前的血量为dp[i][j+1] - map[i][j]，向下时要满足当前位置(i，j)加上血或者扣完血之后的血量等于dp[i][j+1]，
 *           即之前的血量为dp[i+1][j] - map[i][j]，同时任何位置都不能小于1。
 *        在以上情况中，选择min{向右的要求, 向下的要求}即可。
 *     方案二：
 *        动态规划基础上的空间压缩解法。思想与com.wz.recursionanddp.MinPathSum类似。
 * </p>
 * <p>
 *     方案一时间复杂度为O(M*N)，空间复杂度为O(M*N)
 *     方案二时间复杂度为O(M*N)，空间复杂度为O(min{M, N})
 * </p>
 *
 * @author wangzi
 */
public class DungeonGame {
    /**
     * 经典动态规划解法
     */
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

    /**
     * 动态规划基础上的空间压缩解法
     */
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
