/**
 * <p>Title: AddMinusMultiDivideByBit</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/12/20</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.bitoperation;

/**
 * <p>只用位运算实现整数的加减乘除运算</p>
 * <p>
 *     给定两个32位整数a和b，可正、可负、可0，不能使用算术运算符，实现a和b的加减乘除运算。不用考虑运算后的结果溢出问题。
 *     加法：
 *        在不考虑进位情况下，a^b就是正确的结果，例如：
 *        a:               001010101
 *        b:               000101111
 *        无进位相加,即a^b:  001111010
 *        在只考虑进位的情况下，也就是只考虑a加b过程中的进位是什么，结果就是(a&b)<<1，例如：
 *        a:                      001010101
 *        b:                      000101111
 *        只考虑进位值,即(a&b)<<1:  000001010
 *        把不考虑进位的产生值和只考虑进位的产生值再相加，就是最终的结果，一只重复这样的过程，直到进位产生的值完全消失，说明加完了。例如：
 *        a^b:      001010101
 *        (a&b)<<1: 000101111
 *        --------------------
 *        a^b:      001111010
 *        (a&b)<<1: 000001010
 *        --------------------
 *        a^b:      001110000
 *        (a&b)<<1: 000010100
 *        --------------------
 *        a^b:      001100100
 *        (a&b)<<1: 000100000
 *        --------------------
 *        a^b:      001000100
 *        (a&b)<<1: 001000000
 *        --------------------
 *        a^b:      000000100
 *        (a&b)<<1: 010000000
 *        --------------------
 *        a^b:      010000100
 *        (a&b)<<1: 000000000
 *        进位为0，过程结束，返回010000100。
 *     减法：
 *        实现a-b，只要实现a+(-b)即可，而一个数的负数在机器中的表示形式是补码，即取反加1，然后相加即可。
 *     乘法：
 *        a*b可以写成b0*a*2^0+b1*a*2^1+...+bi*a*2^i+...+b31*a*2^31，例如a=22=000010110，b=13=000001101
 *        a:   000010110
 *        b:   000001101
 *        res: 000000000
 *        b最右侧为1，res=res+a，同时b右移一位，a左移一位。
 *        a:   000101100
 *        b:   000000110
 *        res: 000010110
 *        b最右侧为0，res不变，同时b右移一位，a左移一位。
 *        a:   001011000
 *        b:   000000011
 *        res: 000010110
 *        b最右侧为1，res=res+a，同时b右移一位，a左移一位。
 *        a:   010110000
 *        b:   000000001
 *        res: 001101110
 *        b最右侧为1，res=res+a，同时b右移一位，a左移一位。
 *        a:   101100000
 *        b:   000000000
 *        res: 100011110
 *        此时b为0，过程结束，返回100011110，即286。
 * </p>
 *
 * @author wangzi
 */
public class AddMinusMultiDivideByBit {

    public static int add(int a, int b) {
        int sum = 0;
        while (b != 0) {
            sum = a ^ b;
            b = (a & b) << 1;
            a = sum;
        }
        return sum;
    }

    public static int minus(int a, int b) {
        return add(a, negNum(b));
    }

    public static int multi(int a, int b) {
        int result = 0;
        while (b != 0) {
            if ((b & 1) != 0) {
                result = add(result, a);
            }
            a <<= 1;
            b >>>= 1;
        }
        return result;
    }

    public static int divide(int a, int b) {
        if (b == 0) {
            throw new RuntimeException("divisor is 0");
        }
        if (a == Integer.MIN_VALUE && b == Integer.MIN_VALUE) {
            return 1;
        } else if (b == Integer.MIN_VALUE) {
            return 0;
        } else if (a == Integer.MIN_VALUE) {
            int c = div(add(a, 1), b);
            return add(c, divide(minus(a, multi(c, b)), b));
        } else {
            return div(a, b);
        }
    }

    private static int div(int a, int b) {
        int result = 0;
        int x = isNeg(a) ? negNum(a) : a;
        int y = isNeg(b) ? negNum(b) : b;
        for (int i = 31; i > -1; i = minus(i, 1)) {
            if ((x >> i) >= y) {
                result |= (1 << i);
                x = minus(x, y << i);
            }
        }
        return isNeg(a) ^ isNeg(b) ? negNum(result) : result;
    }

    /**
     * 补码，取反加1
     */
    private static int negNum(int n) {
        return add(~n, 1);
    }

    /**
     * 是否是负数
     */
    private static boolean isNeg(int n) {
        return n < 0;
    }

    public static void main(String[] args) {
        int a = (int) (Math.random() * 100000) - 50000;
        int b = (int) (Math.random() * 100000) - 50000;
        System.out.println("a = " + a + ", b = " + b);
        System.out.println(add(a, b));
        System.out.println(a + b);
        System.out.println("=========");
        System.out.println(minus(a, b));
        System.out.println(a - b);
        System.out.println("=========");
        System.out.println(multi(a, b));
        System.out.println(a * b);
        System.out.println("=========");
        System.out.println(divide(a, b));
        System.out.println(a / b);
        System.out.println("=========");

        a = Integer.MIN_VALUE;
        b = 32;
        System.out.println(divide(a, b));
        System.out.println(a / b);
    }
}
