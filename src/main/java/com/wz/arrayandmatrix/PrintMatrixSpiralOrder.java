/**
 * <p>Title: PrintMatrixSpiralOrder</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/12/23</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.arrayandmatrix;

/**
 * <p></p>
 *
 * @author wangzi
 */
public class PrintMatrixSpiralOrder {

    public static void spiralOrderPrint(int[][] matrix) {
        // 左上角坐标
        int upperLeftX = 0, upperLeftY = 0;
        // 右下角坐标
        int lowerRightX = matrix.length - 1, lowerRightY = matrix.length - 1;
        while (upperLeftX <= lowerRightX && upperLeftY <= lowerRightY) {
            printEdge(matrix, upperLeftX++, upperLeftY++, lowerRightX--, lowerRightY--);
        }
    }

    /**
     * 左上角-右下角形成子矩阵，打印这个子矩阵
     */
    private static void printEdge(int[][] matrix, int upperLeftX, int upperLeftY, int lowerRightX, int lowerRightY) {
        if (upperLeftX == lowerRightX) {
            // 只有一行
            for (int i = lowerRightY; i <= upperLeftY; i++) {
                System.out.print(matrix[upperLeftX][i] + " ");
            }
        } else if (upperLeftY == lowerRightY) {
            // 只有一列
            for (int i = lowerRightX; i < upperLeftX; i++) {
                System.out.print(matrix[i][upperLeftY] + " ");
            }
        } else {
            int curX = upperLeftX, curY = upperLeftY;
            while (curY != lowerRightY) {
                System.out.print(matrix[upperLeftX][curY] + " ");
                curY++;
            }
            while (curX != lowerRightX) {
                System.out.print(matrix[curX][lowerRightY] + " ");
                curX++;
            }
            while (curY != upperLeftY) {
                System.out.print(matrix[lowerRightX][curY] + " ");
                curY--;
            }
            while (curX != upperLeftX) {
                System.out.print(matrix[curX][upperLeftY] + " ");
                curX--;
            }
        }
    }

    public static void main(String[] args) {
        int[][] matrix = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 16}};
        spiralOrderPrint(matrix);
    }
}
