/**
 * <p>Title: MaxOneBorderSize</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/1/10</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.arrayandmatrix;

/**
 * <p>边界都是1的最大正方形大小</p>
 *
 * @author wangzi
 */
public class MaxOneBorderSize {

    public static int getMaxSize(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }

        // 从(i,j)出发向右，有多少个连续的1
        int[][] right = new int[matrix.length][matrix[0].length];
        // 从(i,j)出发向下，有多少个连续的1
        int[][] down = new int[matrix.length][matrix[0].length];
        buildBorderMap(matrix, right, down);
        for (int size = Math.min(matrix.length, matrix[0].length); size > 0; size--) {
            if (hasSizeOfBorder(size, right, down)) {
                return size;
            }
        }
        return 0;
    }

    private static boolean hasSizeOfBorder(int size, int[][] right, int[][] down) {
        for (int i = 0; i < right.length - size + 1; i++) {
            for (int j = 0; j < right[0].length - size + 1; j++) {
                // 检查(i,j)的又右边和下边、正方形左下角的右边、正方形右上角的下边
                if (right[i][j] >= size && down[i][j] >= size &&
                        right[i + size - 1][j] >= size && down[i][j + size - 1] >= size) {
                    return true;
                }
            }
        }
        return false;
    }

    private static void buildBorderMap(int[][] matrix, int[][] right, int[][] down) {
        int row = matrix.length, col = matrix[0].length;
        // 矩阵最右下角
        if (matrix[row - 1][col - 1] == 1) {
            right[row - 1][col - 1] = 1;
            down[row - 1][col - 1] = 1;
        }
        // 矩阵最后一列
        for (int i = row - 2; i > -1; i--) {
            if (matrix[i][col - 1] == 1) {
                right[i][col - 1] = 1;
                down[i][col - 1] = down[i + 1][col - 1] + 1;
            }
        }
        // 矩阵最后一行
        for (int j = col - 2; j > -1; j--) {
            if (matrix[row - 1][j] == 1) {
                right[row - 1][j] = right[row - 1][j + 1] + 1;
                down[row - 1][j] = 1;
            }
        }
        // 矩阵其他位置
        for (int i = row - 2; i > -1; i--) {
            for (int j = col - 2; j > -1; j--) {
                if (matrix[i][j] == 1) {
                    right[i][j] = right[i][j + 1] + 1;
                    down[i][j] = down[i + 1][j] + 1;
                }
            }
        }
    }

    public static void main(String[] args) {
        int[][] matrix = {{0, 1, 1, 1, 1}, {0, 1, 0, 0, 1}, {0, 1, 0, 0, 1}, {0, 1, 1, 1, 1}, {0, 1, 0, 1, 1}};
        System.out.println(getMaxSize(matrix));
    }
}
