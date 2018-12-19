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
 * <p>
 *     给定两个32为整数a和b，不用任何比较判断，返回a和b中较大的。
 *     方案一：
 *        根据a-b的符号，判断两者的大小。sign()返回整数n的符号，正数和0返回1，负数返回0；flip()如果n为1，返回0，如果n为0，返回1。
 *        所以，如果a-b为正数或0，则signC为1，flip(signC)为0；如果a-b为负数，则signC为0，flip(signC)为1。
 *        即signC和flip(signC)必然一个为0，另一个为1，所以返回 a*signC+b*flip(signC)
 *        方案一有局限性，就是如果a-b的值出现溢出，则返回的结果不正确。
 *     方案二：
 *        方案二可以彻底解决溢出问题。
 *        如果a与b的符号不同，此时diffSignAB=1，sameSignAB=0，有：
 *          如果a为0或正数，那么b为负数，signA=1，signB=0，应该返回a；
 *          如果a为负数，那么b为0或正数，signA=0，signB=1，应该返回b。
 *        如果a与b的符号相同，此时diffSignAB=0，sameSignAB=1，这种情况下a-b的值绝不会溢出，有：
 *          如果a-b为0或正数，signC=1，应该返回a；
 *          如果a-b为负数，signC=0，应该返回b。
 *        综上所述，应该返回 a*(diffSignAB*signA+sameSignAB*signC)+b*flip(diffSignAB*signA+sameSignAB*signC)
 * </p>
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
