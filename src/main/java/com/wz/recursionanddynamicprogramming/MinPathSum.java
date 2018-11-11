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
 *
 * @author wangzi
 */
public class MinPathSum {
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
        // 计算其他路径路径
        for (int i = 1; i < row; i++) {
            for (int j = 1; j < col; j++) {
                path[i][j] = Math.min(path[i - 1][j], path[i][j - 1]) + matrix[i][j];
            }
        }

        return path[row - 1][col - 1];
    }

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
