/**
 * <p>Title: FindNumInSortedMatrix</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/12/29</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.arrayandmatrix;

/**
 * <p>在行列都有序的矩阵中找数</p>
 * <p>
 *     给定一个N*M的矩阵matrix和一个整数K，matrix的每一行和每一列都是排好序的，判断K是否在matrix中。
 *     例如：
 *        0 1 2 5
 *        2 3 4 7
 *        4 4 4 8
 *        5 7 7 9
 *        如果K为7，返回true；如果K为6，返回false。
 *     解决方案：
 *        1、从矩阵最右上角(row=0,clo=M-1)的数开始寻找
 *        2、比较当前数matrix[row][col]与K的关系：
 *        2.1、如果与K相等，说明已找到，直接返回true
 *        2.2、如果大于K，说明第col列均大于K，令col=col-1，重复步骤二
 *        2.3、如果小于K，说明第row行均小于K，令row=row+1，重复步骤二
 *        3、如果找到越界都没有发现与K相等的数，返回false。
 * </p>
 * <p>时间复杂度为O(N+M)，空间复杂度为O(1)</p>
 *
 * @author wangzi
 */
public class FindNumInSortedMatrix {
    public static boolean isContains(int[][] matrix, int k) {
        // 右上角坐标
        int row = 0, col = matrix[0].length - 1;
        while (row < matrix.length && col > -1) {
            if (matrix[row][col] == k) {
                return true;
            } else if (matrix[row][col] > k) {
                col--;
            } else {
                row++;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        int[][] matrix = new int[][]{
                {0, 1, 2, 3, 4, 5, 6},
                {10, 12, 13, 15, 16, 17, 18},
                {23, 24, 25, 26, 27, 28, 29},
                {44, 45, 46, 47, 48, 49, 50},
                {65, 66, 67, 68, 69, 70, 71},
                {96, 97, 98, 99, 100, 111, 122},
                {166, 176, 186, 187, 190, 195, 200},
                {233, 243, 321, 341, 356, 370, 380}
        };
        System.out.println(isContains(matrix, 233));
        System.out.println(isContains(matrix, 234));
    }
}
