/**
 * <p>Title: Fibonacci</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/11/11</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.recursionanddynamicprogramming;

/**
 * <p>斐波那契数列系列问题</p>
 *
 * @author wangzi
 */
public class Fibonacci {
    /**
     * 递归求解斐波那契数列
     */
    public static int fibonacciOne(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1 || n == 2) {
            return 1;
        }

        return fibonacciOne(n - 1) + fibonacciOne(n - 2);
    }

    /**
     * 动态规划求解斐波那契数列
     */
    public static int fibonacciTwo(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1 || n == 2) {
            return 1;
        }

        int result = 1, pre = 1, tmp;
        for (int i = 3; i <= n; i++) {
            tmp = result;
            result += pre;
            pre = tmp;
        }
        return result;
    }

    /**
     * 矩阵乘法求解斐波那契数列
     */
    public static int fibonacciThree(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1 || n == 2) {
            return 1;
        }

        int[][] base = {{1, 1}, {1, 0}};
        int[][] result = matrixPower(base, n - 2);
        return result[0][0] + result[1][0];
    }

    /**
     * 递归求解台阶问题
     */
    public static int stepOne(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1 || n == 2) {
            return n;
        }

        return stepOne(n - 1) + stepOne(n - 2);
    }

    /**
     * 动态规划求解台阶问题
     */
    public static int stepTwo(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1 || n == 2) {
            return n;
        }

        int result = 2, pre = 1, tmp;
        for (int i = 3; i <= n; i++) {
            tmp = result;
            result += pre;
            pre = tmp;
        }
        return result;
    }

    /**
     * 矩阵乘法求解台阶问题
     */
    public static int stepThree(int n) {
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
     * 递归求解母牛问题
     */
    public static int cowOne(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1 || n == 2 || n == 3) {
            return n;
        }

        return cowOne(n - 1) + cowOne(n - 3);
    }

    /**
     * 动态规划求解母牛问题
     */
    public static int cowTwo(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1 || n == 2 || n == 3) {
            return n;
        }

        int result = 3, pre = 2, prePre = 1, tmp1, tmp2;
        for (int i = 4; i <= n; i++) {
            tmp1 = result;
            tmp2 = pre;
            result += prePre;
            pre = tmp1;
            prePre = tmp2;
        }
        return result;
    }

    /**
     * 矩阵乘法求解母牛问题
     */
    public static int cowThree(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1 || n == 2 || n == 3) {
            return n;
        }

        int[][] base = {{1, 1, 0}, {0, 0, 1}, {1, 0, 0}};
        int[][] result = matrixPower(base, n - 3);
        return 3 * result[0][0] + 2 * result[1][0] + result[2][0];
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
        System.out.println(fibonacciOne(n));
        System.out.println(fibonacciTwo(n));
        System.out.println(fibonacciThree(n));
        System.out.println("====");
        System.out.println(stepOne(n));
        System.out.println(stepTwo(n));
        System.out.println(stepThree(n));
        System.out.println("====");
        System.out.println(cowOne(n));
        System.out.println(cowTwo(n));
        System.out.println(cowThree(n));
    }
}
