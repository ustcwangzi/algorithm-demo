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
 * <p>
 *     给定一个N*N的矩阵matrix，在这个矩阵中只有0和1两种值，获取边框全是1的最大正方形的边长长度。
 *     例如，matrix为：
 *        0 1 1 1 1
 *        0 1 0 0 1
 *        0 1 0 0 1
 *        0 1 1 1 1
 *        0 1 0 1 1
 *        其中，边框全是1的最大正方形大小为4*4，因此返回4。
 *     解决方案：
 *        可以直接使用暴力解法：
 *        1、矩阵中共有N*N个位置，O(N)
 *        2、对于每个位置都检查是否可以成为边长为N-1的正方形的左上角，比如对于(0,0)，一次检查是否为边长为5的正方形的左上角，
 *           然后检查边长为4、3等，O(N)
 *        3、检查一个位置能否成为边长为N的正方形的左上角，可以遍历边长为N的正方形边界，看是否全部由1构成，O(4*N)
 *        优化解法：
 *        可以将步骤三的时间复杂度由O(N)降到O(1)，就是在O(1)时间内检查一个位置能够最为边长为a的边界为1的正方形的左上角。
 *        1、根据matrix得到两个矩阵right和down，right[i][j]表示从(i,j)出发向右，有多少个连续的1，
 *           down[i][j]表示从(i,j)出发向下，有多少个连续的1
 *        2、计算right和down矩阵
 *        2.1、矩阵右下角(N-1,N-1)，如果matrix[N-1][N-1]==1，那么right[N-1][N-1]=1且down[N-1][N-1]=1，否则都等于0；
 *        2.2、矩阵最后一列(i,N-1)，对于right矩阵来说，最后一列右边没有内容，因此只要matrix[i][N-1]==1，则right[i][N-1]=1，否则为0；
 *             对于down矩阵来说，如果matrix[i][N-1]==1，down[i][N-1]=down[i+1][N-1]+1，否则为0；
 *        2.3、矩阵最后一行(N-1,j)，对于down矩阵来说，最后一行下面没有内容，因此只要matrix[N-1][j]==1，则down[N-1][j]=1，否则为0；
 *             对于right矩阵来说，如果matrix[N-1][j]==1，则right[N-1][j]=right[N-1][j+1]+1，否则为0；
 *        2.4、其他位置(i,j)，如果matrix[i][j]==1，则right[i][j]=[i][j+1]+1，down[i][j]=down[i+1][j]=1，否则都等于0。
 *        3、得到right和down矩阵后，检查位置(i,j)是否可作为边长为a的边界为1的正方形左上角
 *        3.1、位置(i,j)的右边和下边连续为1的数量必须都大于等于a，即right[i][j]>=a && down[i][j]>=a
 *        3.2、位置(i,j)向右跳到(i,j+a-1)，即正方形的右上角，该位置的下边连续为1的数量必须大于等于a，即down[i][j+a-1]>=a
 *        3.3、位置(i,j)向下跳到(i+a-1,j)，即正方形的左下角，该位置的右边连续为1的数量必须大于等于a，即right[i+a-1][j]>=a
 *        满足这三个条件，说明位置(i,j)符合要求。
 * </p>
 * <p>
 *     暴力解法时间复杂度为O(N^4)，优化解法时间复杂度为O(N^3)
 * </p>
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
                // 检查(i,j)的右边和下边、正方形左下角的右边、正方形右上角的下边
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
