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
 * <p>
 *     给定一个矩阵matrix，按照之字形的方式打印这个矩阵。
 *     例如：
 *       1  2  3  4
 *       5  6  7  8
 *       9  10 11 12
 *       打印结果为：1 2 5 9 6 3 4 7 10 11 8 12
 *     解决方案：
 *        1、上坐标(topX,topY)初始为(0,0)，先沿着矩阵第一行移动(topY++)，到达第一行最右边时，再沿着矩阵最后一列移动(topX++)
 *        2、下坐标(downX,downY)初始为(0,0)，先沿着矩阵第一列移动(downX++)，到达第一列最下边时，再沿着矩阵最后一行移动(downY++)
 *        3、上坐标和下坐标同步移动，每次移动后的上坐标和下坐标的连线就是矩阵中的一条斜线，打印斜线上的元素即可
 *        4、如果上次斜线是从坐下往右上打印的，这次一定是从右上往坐下打印，反之亦然。
 * </p>
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
        // false表示从左下往右上
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
            // 从右上往左下
            while (topX < downX + 1) {
                System.out.print(matrix[topX++][topY--] + " ");
            }
        } else {
            // 从左下往右上
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
