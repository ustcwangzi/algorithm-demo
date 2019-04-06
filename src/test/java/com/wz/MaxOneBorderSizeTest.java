/**
 * <p>Title: MaxOneBorderSizeTest</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/4/6</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz;

import com.wz.arrayandmatrix.MaxOneBorderSize;


/**
 * <p>边界都是1的最大正方形大小</p>
 *
 * @author wangzi
 */
public class MaxOneBorderSizeTest {

    private static int solution(int[][] matrix) {
        // 从(i,j)出发向右，有多少个连续的1
        int[][] right = new int[matrix.length][matrix[0].length];
        // 从(i,j)出发向下，有多少个连续的1
        int[][] down = new int[matrix.length][matrix[0].length];
        buildBorderMap(matrix, right, down);
        for (int size = Math.min(matrix.length, matrix[0].length); size > 0; size--) {
            for (int i = 0; i < matrix.length - size + 1; i++) {
                for (int j = 0; j < matrix[0].length - size + 1; j++) {
                    // (i,j)向右、向下，以及右上角向下、左下角向右
                    if (right[i][j] >= size && down[i][j] >= size &&
                            right[i + size - 1][j] >= size && down[i][j + size - 1] >= size) {
                        return size;
                    }
                }
            }
        }
        return 0;
    }

    private static void buildBorderMap(int[][] matrix, int[][] right, int[][] down) {
        int row = matrix.length, col = matrix[0].length;
        // 最后一个位置
        if (matrix[row - 1][col - 1] == 1) {
            right[row - 1][col - 1] = 1;
            down[row - 1][col - 1] = 1;
        }
        // 最后一行
        for (int j = col - 2; j > -1; j--) {
            if (matrix[row - 1][j] == 1) {
                right[row - 1][j] = right[row - 1][j + 1] + 1;
                down[row - 1][j] = 1;
            }
        }
        // 最后一列
        for (int i = row - 2; i > -1; i--) {
            if (matrix[i][col - 1] == 1) {
                right[i][col - 1] = 1;
                down[i][col - 1] = down[i + 1][col - 1] + 1;
            }
        }
        // 其他位置
        for (int i = row - 2; i > -1; i--) {
            for (int j = col - 2; j > -1; j--) {
                if (right[i][j] == 1) {
                    right[i][j] = right[i][j + 1] + 1;
                    down[i][j] = down[i + 1][j] + 1;
                }
            }
        }
    }

    public static void main(String[] args) {
        int[][] matrix = {{0, 1, 1, 1, 1}, {0, 1, 0, 0, 1}, {0, 1, 0, 0, 1}, {0, 1, 1, 1, 1}, {0, 1, 0, 1, 1}};
        if (solution(matrix) == MaxOneBorderSize.getMaxSize(matrix)) {
            System.out.println("Past");
        } else {
            System.out.println("Error");
        }
    }
}
