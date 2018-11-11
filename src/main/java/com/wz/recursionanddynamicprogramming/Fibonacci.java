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
 * <p>
 *     问题一：斐波那契数列，给定N，求解斐波那契数列的第N项
 *        举例：1, 1, 2, 3, 5, 8, ...
 *     问题二：给定台阶数N，一次可跨2个或1个，求解有多少种走法
 *        举例：N=3时，三次均跨一个台阶；先跨一个台阶、再跨两个台阶；先跨两个台阶、再跨一个台阶；因此共有3种走法
 *     问题三：成熟的母牛每年生1头小母牛，且永不死去，从第二年开始母牛开始生小母牛，小母牛三年成熟可以生小母牛，求N年后母牛数量
 *        举例：N=6时，第1年1头成熟母牛a；第2年a生了b，共有2头；第3年a生了c，共有3头；第4年，a生了d，共有4头；
 *             第5年，a和b分别都生了新的小牛，共有6头；第6年，a、b和c分别生了新的小牛，共有9头。
 *     求解问题一：
 *        递归方式：F(N) = F(N-1) + F(N-2)
 *        动态规划方式：从左到右依次求出每一项的值
 *        矩阵乘法方式：
 *           F(n) = F(n-1) + F(n-2)是一个二阶递推数列，可以用矩阵乘法表示：
 *                                               |a b|
 *           (F(n), F(n-1)) = (F(n-1), F(n-2)) * |c d|
 *           把前四项F(1)=1, F(2)=1, F(3)=2, F(4)=3代入，可以求得：
 *           |a b|   |1 1|
 *           |c d| = |1 0|
 *           因此，当n>2时，原公式可化简为：
 *                                         |1 1|           |1 1|
 *           (F(3), F(2)) = (F(2), F(1)) * |1 0| = (1,1) * |1 0|
 *                                         |1 1|           |1 1|^2
 *           (F(4), F(3)) = (F(3), F(2)) * |1 0| = (1,1) * |1 0|
 *                         ... ...
 *                                               |a b|           |1 1|^(n-2)
 *           (F(n), F(n-1)) = (F(n-1), F(n-2)) * |c d| = (1,1) * |1 0|
 *           原问题变成了如何求解一个矩阵的N次方问题，求解矩阵的N次方和求解整数的N次方是同理的，只是细节上有些不同
 *           假设一个整数为10，如何快速求解10的75次方：
 *           1、75的二进制形式为1001011
 *           2、10的75次方=10^64 * 10^8 * 10^2 * 10^1
 *           在这个过程中，先求出10^1，然后根据10^1求出10^2，再根据10^2求出10^4 ... 最后求出10^64
 *           3、把步骤二中，次方对应到75的二进制数中相应位为1的结果相乘即可，即64、8、2、1
 *     求解问题二：
 *        递归方式：S(N) = S(N-1) + S(N-2)
 *           台阶只有1级时，方法只有1种；台阶有2级时，方法有2种；台阶有N级时，上第N级的情况，要么是从N-2级直接跨2级，
 *           要么是从N-1级跨1级，因此台阶有N级的方法数为跨到N-2级的方法数加上跨到N-1级的方法数
 *        动态规划方式：从左到右依次求出每一项的值
 *        矩阵乘法方式：
 *           S(n) = S(n-1) + S(n-2)是一个二阶递推公式，把前四项S(1)=1, S(2)=2, S(3)=3, S(4)=5代入, 可以求得状态矩阵：
 *           |a b|   |1 1|
 *           |c d| = |1 0|
 *           同样根据上面的解法得到：
 *                                           |a b|^(n-2)           |1 1|^(n-2)
 *           (S(n), S(n-1)) = (S(2), S(1)) * |c d|       = (2,1) * |1 0|
 *     求解问题三：
 *        递归方式：C(N) = C(N-1) + C(N-3)
 *           第N-1年的牛都会活到第N年，同时所有成熟的牛都会生1头小牛，成熟牛的数量就是第N-3年的所有牛
 *        动态规划方式：从左到右依次求出每一项的值
 *        矩阵乘法方式：
 *           C(n) = C(n-1) + C(n-3)是一个三阶递推数列，把前五项C(1)=1, C(2)=2, C(3)=3, C(4)=4, C(5)=6代入，可得状态矩阵：
 *           |a b c|   |1 1 0|
 *           |d e f|   |0 0 1|
 *           |g h i| = |1 0 0|
 *           同样根据上面的解法得到：
 *                                                         |1 1 0|^(n-3)             |1 1 0|^(n-3)
 *                                                         |0 0 1|                   |0 0 1|
 *           (C(n), C(n-1), C(n-2)) = (C(3), C(2), C(1)) * |1 0 0|       = (3,2,1) * |1 0 0|
 * </p>
 * <p>
 *     如果递归式严格符合F(n) = a*F(n-1) + b*F(n-2) +...+ k*F(n-i)，那么它就是一个i阶递推式，
 *     必然有与i*i的状态矩阵有关矩阵乘法的表达。一律可使用矩阵乘法的动态规划将时间复杂度将为O(logN)
 * </p>
 *
 * <p>直接递归求解时间复杂度为O(2^N)，动态规划求解时间复杂度为O(N)，矩阵乘法求解时间复杂度为O(logN)</p>
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
