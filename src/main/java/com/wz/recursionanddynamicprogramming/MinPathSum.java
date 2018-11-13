/**
 * <p>Title: MinPathSum</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/11/11</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.recursionanddynamicprogramming;

/**
 * <p>矩阵的最小路径和</p>
 * <p>
 *     给定一个矩阵，从左上角开始每次只能向右或向下走，达到右下角，路径数字累加就是路径和，求出最小的路径和
 *     方案一：
 *        生成一个与原矩阵大小相等的矩阵，用以存放所有路径和
 *        对于第一行来说，从(0,0)到(0,j)只能向右走，路径和就是matrix[0][0...j]的累加
 *        对于第一列来说，从(0,0)到(i,0)只能向下走，路径和就是matrix[0...i][0]的累加
 *        除第一行和第一列的其他位置，从(0,0)到(i,j)必然经过(i-1,j)或(i,j-1)，
 *        最小路径和为min{matrix[i-1][j],matrix[i][j-1]} + matrix[i][j]
 *     方案二：
 *        只生成一个大小为min{M,N}的数组，假设M >= N，此时生成一个长度为N的数组
 *        1、从(0,0)到达第一行的每一个位置，最小路径和就是从(0,0)开始依次累加，此时array[j]代表(0,0)到达(0,j)的最小路径和
 *        2、想把array[j]更新为(0,0)到(1,j)的最小路径和，更新前array[0]代表(0,0)到达(0,0)的最小路径和，
 *           更新前想让array[0]代表(0,0)到达(1,0)的最小路径和，直接令array[0]=array[0] + matrix[1][0]即可；
 *           更新前array[1]代表(0,0)到达(0,1)的最小路径和，更新后想让array[1]代表(0,0)到达(1,1)的最小路径和，
 *           (0,0)到达(1,1)有两种选择：经过(0,1)或经过(1,0)，而array[0]此时已代表(0,0)到达(1,0)的最小路径和，
 *           因此array[1]=min{array[0],array[1]} + matrix[1][1]，同理可以求的array[2]，array[3] ...
 *        3、重复步骤二的更新过程，直到滚动更新完毕。
 *     eg. matrix = {{1, 3, 5, 9}, {8, 1, 3, 4}, {5, 0, 6, 1}, {8, 8, 4, 0}}
 *         方案一生成：
 *            {{1, 4, 9, 8}, {9, 5, 8, 12}, {14, 5, 11, 12}, {22, 13, 15, 12}}
 *         方案二每一步生成：
 *            {1, 9, 14, 22}，向右滚动依次得到：{4, 5, 5, 13}，{9, 8, 11, 15}，{18, 12, 12, 12}
 * </p>
 * <p>
 *     方案一时间复杂度为O(M*N)，空间复杂度为O(M*N)
 *     方案二时间复杂度为O(M*N)，空间复杂度为O(min{M, N})
 * </p>
 *
 * @author wangzi
 */
public class MinPathSum {
    /**
     * 经典动态规划解法
     */
    public static int minPathSunOne(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) {
            return 0;
        }

        int row = matrix.length, col = matrix[0].length;
        int[][] path = new int[row][col];
        path[0][0] = matrix[0][0];
        // 第一列
        for (int i = 1; i < row; i++) {
            path[i][0] = path[i - 1][0] + matrix[i][0];
        }
        // 第一行
        for (int j = 1; j < col; j++) {
            path[0][j] = path[0][j - 1] + matrix[0][j];
        }
        // 计算其他路径
        for (int i = 1; i < row; i++) {
            for (int j = 1; j < col; j++) {
                path[i][j] = Math.min(path[i - 1][j], path[i][j - 1]) + matrix[i][j];
            }
        }

        return path[row - 1][col - 1];
    }

    /**
     * 动态规划基础上的空间压缩解法
     */
    public static int minPathSunTwo(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) {
            return 0;
        }

        // 行数与列数较大的
        int more = Math.max(matrix.length, matrix[0].length);
        // 行数与列数较小
        int less = Math.min(matrix.length, matrix[0].length);
        // 行数是不是大于等于列数
        boolean rowMore = more == matrix.length;
        // 辅助数组
        int[] array = new int[less];
        array[0] = matrix[0][0];
        // 第一行或第一列的值累加
        for (int i = 1; i < less; i++) {
            array[i] = array[i - 1] + (rowMore ? matrix[0][i] : matrix[i][0]);
        }
        for (int i = 1; i < more; i++) {
            array[0] = array[0] + (rowMore ? matrix[0][i] : matrix[i][0]);
            for (int j = 1; j < less; j++) {
                array[j] = Math.min(array[j - 1], array[j]) + (rowMore ? matrix[i][j] : matrix[j][i]);
            }
        }

        return array[less - 1];
    }

    public static void main(String[] args) {
        int[][] matrix = {{1, 3, 5, 9}, {8, 1, 3, 4}, {5, 0, 6, 1}, {8, 8, 4, 0}};
        System.out.println(minPathSunOne(matrix));
        System.out.println(minPathSunTwo(matrix));
    }
}
