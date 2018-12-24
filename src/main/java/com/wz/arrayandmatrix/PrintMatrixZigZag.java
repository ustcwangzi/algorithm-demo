/**
 * <p>Title: PrintMatrixZigZag</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/12/24</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.arrayandmatrix;

/**
 * <p>之字形打印矩阵</p>
 *
 * @author wangzi
 */
public class PrintMatrixZigZag {

    public static void printMatrixZigZag(int[][] matrix) {
        // 上坐标
        int topX = 0, topY = 0;
        // 下坐标
        int downX = 0, downY = 0;
        int endX = matrix.length - 1, endY = matrix[0].length - 1;
        // 从下往上
        boolean fromUp = false;
        while (topX < endX + 1) {
            printLevel(matrix, topX, topY, downX, downY, fromUp);
            // 到达行最右边时，再沿着最后一列移动
            topX = topY == endY ? topX + 1 : topX;
            // 沿着行移动
            topY = topY == endY ? topY : topY + 1;
            // 到达列最下边时，沿着最后一行移动
            downY = downX == endX ? downY + 1 : downY;
            // 沿着列移动
            downX = downX == endX ? downX : downX + 1;
            fromUp = !fromUp;
        }
    }

    private static void printLevel(int[][] matrix, int topX, int topY, int downX, int downY, boolean fromUp) {
        if (fromUp) {
            // 从上往下
            while (topX < downX + 1) {
                System.out.print(matrix[topX++][topY--] + " ");
            }
        } else {
            // 从下往上
            while (downX > topX - 1) {
                System.out.print(matrix[downX--][downY++] + " ");
            }
        }
    }

    public static void main(String[] args) {
        int[][] matrix = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}};
        printMatrixZigZag(matrix);
    }
}
