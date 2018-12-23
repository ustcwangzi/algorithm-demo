/**
 * <p>Title: RotateMatrix</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/12/23</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.arrayandmatrix;

/**
 * <p>将正方形矩阵顺时针转动90度</p>
 *
 * @author wangzi
 */
public class RotateMatrix {

    public static void rotate(int[][] matrix) {
        // 左上角坐标
        int upperLeftX = 0, upperLeftY = 0;
        // 右下角坐标
        int lowerRightX = matrix.length - 1, lowerRightY = matrix[0].length - 1;
        while (upperLeftX < lowerRightX) {
            rotateEdge(matrix, upperLeftX++, upperLeftY++, lowerRightX--, lowerRightY--);
        }
    }

    /**
     * 左上角-右下角形成子矩阵，调整这个子矩阵的最外层
     */
    private static void rotateEdge(int[][] matrix, int upperLeftX, int upperLeftY, int lowerRightX, int lowerRightY) {
        int tmp;
        for (int i = 0; i < lowerRightY - upperLeftY; i++) {
            tmp = matrix[upperLeftX][upperLeftY + i];
            matrix[upperLeftX][upperLeftY + i] = matrix[lowerRightX - i][upperLeftY];
            matrix[lowerRightX - i][upperLeftY] = matrix[lowerRightX][lowerRightY - i];
            matrix[lowerRightX][lowerRightY - i] = matrix[upperLeftX + i][lowerRightY];
            matrix[upperLeftX + i][lowerRightY] = tmp;
        }
    }

    private static void printMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int[][] matrix = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 16}};
        printMatrix(matrix);
        rotate(matrix);
        System.out.println("===========");
        printMatrix(matrix);
    }
}
