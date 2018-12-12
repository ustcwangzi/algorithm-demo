/**
 * <p>Title: ZeroLeftOneStringNumber</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/12/12</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.string;

/**
 * <p>0左边必有1的二进制字符串数量</p>
 *
 * @author wangzi
 */
public class ZeroLeftOneStringNumber {

    public static int getNumOne(int n) {
        if (n < 1) {
            return 0;
        }
        return process(1, n);
    }

    private static int process(int i, int n) {
        if (i == n - 1) {
            return 2;
        }
        if (i == n) {
            return 1;
        }
        return process(i + 1, n) + process(i + 2, n);
    }

    public static int getNumTwo(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        int pre = 1, cur = 1, tmp;
        for (int i = 2; i < n + 1; i++) {
            tmp = cur;
            cur += pre;
            pre = tmp;
        }
        return cur;
    }

    public static int getNumThree(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1 || n == 2) {
            return n;
        }
        int[][] base = {{1, 1}, {1, 0}};
        int[][] result = matrixPower(base, n - 2);
        return 2 * result[0][0] + result[1][0];
    }

    /**
     * 矩阵matrix的power次方
     */
    private static int[][] matrixPower(int[][] matrix, int power) {
        int[][] result = new int[matrix.length][matrix[0].length];
        // 把result初始化为单位矩阵
        for (int i = 0; i < result.length; i++) {
            result[i][i] = 1;
        }
        int[][] tmp = matrix;
        for (; power != 0; power >>= 1) {
            if ((power & 1) != 0) {
                result = matrixMultiply(result, tmp);
            }
            tmp = matrixMultiply(tmp, tmp);
        }
        return result;
    }

    /**
     * 两矩阵相乘
     */
    private static int[][] matrixMultiply(int[][] self, int[][] other) {
        int[][] result = new int[self.length][other[0].length];
        for (int i = 0; i < self.length; i++) {
            for (int j = 0; j < other[0].length; j++) {
                for (int k = 0; k < other.length; k++) {
                    result[i][j] += self[i][k] * other[k][j];
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int n = 20;
        System.out.println(getNumOne(n));
        System.out.println(getNumTwo(n));
        System.out.println(getNumThree(n));
    }
}
