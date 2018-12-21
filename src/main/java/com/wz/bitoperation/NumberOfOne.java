/**
 * <p>Title: NumberOfOne</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/12/21</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.bitoperation;

/**
 * <p>整数的二进制中有多少个1</p>
 * <p>
 *     给定一个32为的整数n，可为正、可为负、可为0，求出该整数二进制表达式中1的个数。
 *     方案一：
 *        对整数n每次进行无符号右移一位，检查最右边的bit是否为1来进行统计。
 *     方案二：
 *        方案一在最复杂情况下需要循环32次，方案二循环次数只和1的个数相关。每次进行n&(n-1)，接下来的循环中就可以忽略掉bit位上为0的部分。
 *        比如n=01000100，n-1=01000011，n&(n-1)=01000000，说明处理到01000100之后，下一步还要处理，因为01000000!=0；
 *        n=01000000，n-1=00111111，n&(n-1)=00000000，说明处理到01000000之后，下一步不用处理，因为接下来没有1。
 *        所以n&(n-1)操作的实质是抹掉最右边的1。
 * </p>
 *
 * @author wangzi
 */
public class NumberOfOne {

    public static int countOne(int n) {
        int count = 0;
        while (n != 0) {
            count += n & 1;
            n >>>= 1;
        }
        return count;
    }

    public static int countTwo(int n) {
        int count = 0;
        while (n != 0) {
            count++;
            n &= (n - 1);
        }
        return count;
    }

    private static void printNumBit(int n) {
        for (int i = 31; i != -1; i--) {
            if ((n & (1 << i)) != 0) {
                System.out.print(1);
            } else {
                System.out.print(0);
            }
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int n = -1;
        System.out.println(countOne(n));
        System.out.println(countTwo(n));
        printNumBit(n);

        n = 1000;
        System.out.println(countOne(n));
        System.out.println(countTwo(n));
        printNumBit(n);
    }
}
