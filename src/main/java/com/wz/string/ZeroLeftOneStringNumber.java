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
 * <p>
 *     给定一个整数N，求出由"0"和"1"组成的位数为N的所有字符串中，满足"0"的左边必有"1"的字符串数量。
 *     例如：
 *     N=1，共有两个字符串"0"、"1"，其中"1"满足要求，返回1；
 *     N=2，共有四个字符串"00"、"01"、"10"、"11"，其中"10"、"11"满足要求，返回2；
 *     N=3，共有八个字符串"000"、"001"、"010"、"011"、"100"、"101"、"110"、"111"，其中"101"、"110"、"111"满足要求，返回3。
 *     可以直接使用暴力求解，即检查每一个长度为N的二进制字符串，看有多少符合要求，总共的字符串数量为2^N，检查是否符合要求时间复杂度为O(N)，
 *     因此该方法总的时间复杂度为O(N*2^N)，此处不再详述。
 *     分析：
 *        p(i)表示0～i-1位置上已确定符合要求，并且第i-1位置上是1的情况下，i～N-1位置上能产生多少种符合要求的字符串。
 *        比如N=5，p(3)表示0～2位置上已确定符合要求且位置2上是'1'，然后穷举3～4位置所有符合要求的字符串。
 *        根据p(i)的定义可知，不管N为多少，最高位字符只能为'1'，因此只要求出p(1)就是所有符合要求的字符串数量。
 *        在位置i-1的字符已经为1的情况下，位置i可以是'1'，也可以是'0'。如果位置i上是'1'，那么剩下的符合要求的字符串数量为p(i+1)；
 *        如果位置i上是'0'，那么位置i+1上必须是'1'，剩下的符合要求的字符串数量为p(i+2)，因此p(i)=p(i+1)+p(i+2)。
 *        p(N-1)表示除最后一个字符，前面的子串全部符合要求且倒数第二个字符为'1'，此时最后一个字符可以为'1'或'0'，因此p(N-1)=2；
 *        p(N)表示所有字符串已确定符合要求，并且最后一个字符(N-1)为'1'，不再有后续的可能性，因此p(N)=1。由上可得：
 *        p(i)=p(i+1)+p(i+2)，i<N-1
 *        p(i)=2，i=N-1
 *        p(i)=1，i=N
 *     解决方案一：
 *        根据上面的公式进行递归。com.wz.recursionanddp.Fibonacci#fibonacciOne(int)
 *     解决方案二：
 *        当N为1，2，3，4，5，6时，结果为1，2，3，5，8，13，可以看出这类似斐波那契数列，区别在于斐波那契数列初始项为1，1，本数列为1，2。
 *        com.wz.recursionanddp.Fibonacci#fibonacciTwo(int)
 *     解决方案三：
 *        类似矩阵乘法求解斐波那契数列，本数列也可以采用矩阵乘法求解。com.wz.recursionanddp.Fibonacci#fibonacciThree(int)
 * </p>
 * <p>
 *     方案一时间复杂度O(2^N)，方案二时间复杂度O(N)，方案三时间复杂度O(logN)
 * </p>
 *
 * @author wangzi
 */
public class ZeroLeftOneStringNumber {
    /**
     * 递归求解
     */
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

    /**
     * 动态规划求解
     */
    public static int getNumTwo(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        int pre = 1, result = 1, tmp;
        for (int i = 2; i < n + 1; i++) {
            tmp = result;
            result += pre;
            pre = tmp;
        }
        return result;
    }

    /**
     * 矩阵乘法求解
     */
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
