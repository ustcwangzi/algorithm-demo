/**
 * <p>Title: PrintMatrixSpiralOrder</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/12/23</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.arrayandmatrix;

/**
 * <p>转圈打印矩阵</p>
 * <p>
 *     给定一个整型矩阵matrix，请按照转圈的方式打印。
 *     例如：
 *        1  2  3  4
 *        5  6  7  8
 *        9  10 11 12
 *        13 14 15 16
 *        打印结果为：1 2 3 4 8 12 16 15 14 13 9 5 6 7 11 10
 *     解决方案：
 *        矩阵分圈处理。在矩阵中用左上角坐标(upperLeftX,upperLeftY)和右下角坐标(lowerRightX,lowerRightY)可以表示一个子矩阵，
 *        比如例子中的矩阵，当(upperLeftX,upperLeftY)=(0,0)，(lowerRightX,lowerRightY)=(3,3)时，表示的子矩阵为整个矩阵。
 *        那么这个子矩阵的最外层为：
 *        1  2  3  4
 *        5        8
 *        9        12
 *        13 14 15 16
 *        打印子矩阵的最外层，为1 2 3 4 8 12 16 15 14 13 9 5。然后令upperLeftX和upperLeftY加1，lowerRightX和lowerRightY减1，
 *        即(upperLeftX,upperLeftY)=(1,1)，(lowerRightX,lowerRightY)=(2,2)，此时子矩阵为：
 *        6  7
 *        10 11
 *        打印子矩阵的最外层，为6 7 11 10。然后令upperLeftX和upperLeftY加1，lowerRightX和lowerRightY减1，
 *        即(upperLeftX,upperLeftY)=(2,2)，(lowerRightX,lowerRightY)=(1,1)，发现左上角坐标跑到了右下角坐标的右方或下方，停止。
 *        整个打印的结果就是最终的结果。
 * </p>
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
     * 左上角-右下角形成子矩阵，打印这个子矩阵的最外层
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
