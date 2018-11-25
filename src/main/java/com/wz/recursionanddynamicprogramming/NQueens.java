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
 * <p>
 *     在N*N的棋盘上要摆N个皇后，要求任何两个皇后不同行、不同列，也不在同一条斜线上。给定整数N，求出有多少种摆法。
 *     方案一：
 *        如果位置(i,j)放置了一个皇后，那么接下来下列位置不能放置皇后：
 *        1、整个第i行的其他位置均不能放置
 *        2、整个第j行的其他位置均不能放置
 *        3、如果位置(a,b)满足|a-i|==|b-j|，说明(a,b)与(i,j)在同一条斜线上，也不能放置
 *        因此可使用逐行放置的方式，避开哪些不能放置的位置，具体过程：
 *        1、用一个长度为N的数组record保存已放置皇后的位置，record[i]表示第i行皇后所在的列数
 *        2、计算到第i行第j列时，查看record[0...k](k<i)中是否存在与j相等的值，否有，说明不能在(i,j)放置皇后
 *        3、再查看是否有|k-i|==|record[k]-j|，若有，说明不能在(i,j)放置皇后
 *     方案二：
 *        基本过程与方案一类似，只是使用了位运算来加速。
 *        upperLim：表示当前行哪些位置是可以放置皇后的，比如8皇后问题，upperLim为00000000000000000000000011111111
 *        colLim：表示递归计算到上一行为止，哪些列上已经放置了皇后
 *        leftDiaLim：表示递归计算到上一行为止，因为受已放置的所有皇后的左下方斜线影响，导致当前行不能放置皇后的位置。
 *                    例如，若第0行第2列放置了皇后；计算到第1行时，受影响的是第1行第1列；计算到第2行时，受影响的是第2行第0列；
 *                    计算到第3行时，不受影响，且后面的行均不受第0行第2列左下方斜线的影响。
 *                    leftDiaLim每次左移一位，就可以得到之前皇后的左下方斜线对当前行的影响。
 *        rightDiaLim：表示递归计算到上一行为止，因为受已放置的所有皇后的右下方斜线影响，导致当前行不能放置皇后的位置。
 *                     与leftDiaLim类似，rightDiaLim每次右移一位，就可以得到之前皇后的右下方斜线对当前行的影响。
 *        pos：代表当前行在colLim、leftDiaLim和rightDiaLim影响下，还有哪些位置可以选择。
 *        mostRightOne：代表在pos中，最右边的1所在的位置。然后从右到左依次选出pos中可选择的位置进行递归尝试。
 * </p>
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
     * @param leftDiaLim  递归到上一行为止，受已放置皇后的左下方斜线影响，导致当前行不能放置皇后的位置
     * @param rightDiaLim 递归到上一行为止，受已放置皇后的右下方斜线影响，导致当前行不能放置皇后的位置
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
