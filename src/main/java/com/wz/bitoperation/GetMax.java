/**
 * <p>Title: GetMax</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/12/19</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.bitoperation;

/**
 * <p>不用任何比较判断找出两个数中较大的数</p>
 *
 * @author wangzi
 */
public class GetMax {

    /**
     * (a-b)溢出时结果不正确
     */
    public static int getMaxOne(int a, int b) {
        int c = a - b;
        int signC = sign(c);
        // signC和flip(signC)必然一个为1，另一个为0
        return a * signC + b * flip(signC);
    }

    /**
     * 解决(a-b)溢出问题
     */
    public static int getMaxTwo(int a, int b) {
        int c = a - b;
        int signA = sign(a);
        int signB = sign(b);
        int signC = sign(c);
        int diffSignAB = signA ^ signB;
        int sameSignAB = flip(diffSignAB);
        // a与b符号不同时，c可能会溢出，但此时sameSignAB为0
        // a与b符号相同时，c绝不会溢出，signC是可信的
        int returnA = diffSignAB * signA + sameSignAB * signC;
        int returnB = flip(returnA);
        return a * returnA + b * returnB;
    }

    /**
     * n为1，返回0；n为0，返回1
     */
    private static int flip(int n) {
        return n ^ 1;
    }

    /**
     * 返回n的符号
     * 整数和0返回1，负数返回0
     */
    private static int sign(int n) {
        return flip((n >> 31) & 1);
    }

    public static void main(String[] args) {
        int a = -16;
        int b = 1;
        System.out.println(getMaxOne(a, b));
        System.out.println(getMaxTwo(a, b));

        a = 2147483647;
        b = -2147480000;
        // wrong answer because of overflow
        System.out.println(getMaxOne(a, b));
        System.out.println(getMaxTwo(a, b));
    }
}
