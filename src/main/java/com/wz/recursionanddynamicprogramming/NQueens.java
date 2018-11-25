/**
 * <p>Title: NQueens</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/11/25</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.recursionanddynamicprogramming;

/**
 * <p>N皇后问题</p>
 *
 * @author wangzi
 */
public class NQueens {

    public static int numOne(int n) {
        if (n < 1) {
            return 0;
        }
        int[] record = new int[n];
        return processOne(0, record, n);
    }

    /**
     * 第i行时，逐个判断第j列能否放置皇后
     */
    private static int processOne(int i, int[] record, int n) {
        if (i == n) {
            return 1;
        }
        int result = 0;
        for (int j = 0; j < n; j++) {
            if (isValid(record, i, j)) {
                record[i] = j;
                result += processOne(i + 1, record, n);
            }
        }
        return result;
    }

    /**
     * 判断位置(i,j)是否能放置皇后
     */
    private static boolean isValid(int[] record, int i, int j) {
        for (int k = 0; k < i; k++) {
            if (j == record[k] || Math.abs(record[k] - j) == Math.abs(i - k)) {
                return false;
            }
        }
        return true;
    }

    public static int numTwo(int n) {
        // 因为本方法中位运算的载体是int型变量，所以该方法只能算1~32皇后问题
        // 如果想计算更多的皇后问题，需使用包含更多位的变量
        if (n < 1 || n > 32) {
            return 0;
        }
        int upperLim = n == 32 ? -1 : (1 << n) - 1;
        return processTwo(upperLim, 0, 0, 0);
    }

    /**
     * @param upperLim    当前行哪些位置是有效位置
     * @param colLim      递归到上一行为止，哪些列上已经放置了皇后
     * @param leftDiaLim  递归到上一行为止，受已放置皇后的左下方斜线影响，导致当前行不能放置皇后
     * @param rightDiaLim 递归到上一行为止，受已放置皇后的右下方斜线影响，导致当前行不能放置皇后
     */
    private static int processTwo(int upperLim, int colLim, int leftDiaLim,
                                  int rightDiaLim) {
        if (colLim == upperLim) {
            return 1;
        }
        // 当前行在colLim、leftDiaLim和rightDiaLim影响下，还有哪些位置选择
        int pos = upperLim & (~(colLim | leftDiaLim | rightDiaLim));
        int mostRightOne;
        int result = 0;
        while (pos != 0) {
            // 在pos中，最右边的1所在的位置
            mostRightOne = pos & (~pos + 1);
            pos = pos - mostRightOne;
            result += processTwo(upperLim, colLim | mostRightOne,
                    (leftDiaLim | mostRightOne) << 1,
                    (rightDiaLim | mostRightOne) >>> 1);
        }
        return result;
    }

    public static void main(String[] args) {
        int n = 16;
        long start = System.currentTimeMillis();
        System.out.println(numOne(n));
        System.out.println("cost time: " + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        System.out.println(numTwo(n));
        System.out.println("cost time: " + (System.currentTimeMillis() - start));
    }
}
