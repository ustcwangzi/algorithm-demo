/**
 * <p>Title: SubMatrixMaxSum</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/1/5</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.arrayandmatrix;

/**
 * <p>子矩阵的最大累加和</p>
 *
 * @author wangzi
 */
public class SubMatrixMaxSum {

    public static int maxSum(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }

        int max = Integer.MIN_VALUE;
        int sum = 0;
        // 累加数组
        int[] sumArray;
        for (int i = 0; i < matrix.length; i++) {
            sumArray = new int[matrix[0].length];
            for (int j = i; j < matrix.length; j++) {
                sum = 0;
                for (int k = 0; k < sumArray.length; k++) {
                    sumArray[k] += matrix[j][k];
                    sum += sumArray[k];
                    max = Math.max(max, sum);
                    sum = sum < 0 ? 0 : sum;
                }
            }
        }
        return max;
    }

    public static void main(String[] args) {
        int[][] matrix = {{-90, 48, 78}, {64, -40, 64}, {-81, -7, 66}};
        System.out.println(maxSum(matrix));
    }
}
