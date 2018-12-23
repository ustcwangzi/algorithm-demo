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
 * <p>
 *     给定一个N*N的矩阵matrix，把这个矩阵调整成顺时针转动90度的形式。
 *     例如：
 *        1  2  3  4
 *        5  6  7  8
 *        9  10 11 12
 *        13 14 15 16
 *        调整后为：
 *        13 9   5  1
 *        14 10  6  2
 *        15 11  7  3
 *        16 12  8  4
 *     解决方案：
 *        在矩阵中用左上角坐标(upperLeftX,upperLeftY)和右下角坐标(lowerRightX,lowerRightY)可以表示一个子矩阵。
 *        比如例子中的矩阵，当(upperLeftX,upperLeftY)=(0,0)，(lowerRightX,lowerRightY)=(3,3)时，表示的子矩阵为整个矩阵。
 *        那么这个子矩阵的最外层为：
 *        1  2  3  4
 *        5        8
 *        9        12
 *        13 14 15 16
 *        在这个外层圈中，1，4，16，13为一组，让1占据4的位置，4占据16的位置，16占据13的位置，13占据1的位置，一组就调整完了；
 *        然后，2，8，15，9为一组，继续调整；最后，3，12，14，5为一组，继续调整。
 *        此时(upperLeftX,upperLeftY)=(0,0)，(lowerRightX,lowerRightY)=(3,3)的子矩阵外层调整完毕。
 *        接下来令upperLeftX和upperLeftY加1，lowerRightX和lowerRightY减1，
 *        即(upperLeftX,upperLeftY)=(1,1)，(lowerRightX,lowerRightY)=(2,2)，此时子矩阵为：
 *        6  7
 *        10 11
 *        这个子矩阵外层只有一组：6，7，11，10，直接进行调整即可。
 * </p>
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
        // 按组进行调整
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
