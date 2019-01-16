/**
 * <p>Title: Factorial</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/1/15</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.others;

/**
 * <p>阶乘相关问题</p>
 *
 * @author wangzi
 */
public class Factorial {

    public static int zeroNumOne(int num) {
        if (num < 0) {
            return 0;
        }

        int result = 0;
        int cur;
        for (int i = 5; i <= num; i++) {
            cur = i;
            while (cur % 5 == 0) {
                result++;
                cur /= 5;
            }
        }

        return result;
    }

    public static int zeroNumTwo(int num) {
        if (num < 0) {
            return 0;
        }

        int result = 0;
        while (num > 0) {
            result += num / 5;
            num /= 5;
        }

        return result;
    }

    public static int rightOneOne(int num) {
        if (num < 1) {
            return -1;
        }

        int result = 0;
        while (num > 0) {
            // 无符号右移，即除2
            num >>>= 1;
            result += num;
        }

        return result;
    }

    public static int rightOneTwo(int num) {
        if (num < 1) {
            return -1;
        }

        // num二进制中1的数量
        int oneNumbers = 0;
        int tmp = num;
        while (tmp > 0) {
            oneNumbers += (tmp & 1) != 0 ? 1 : 0;
            tmp >>>= 1;
        }

        return num - oneNumbers;
    }

    public static void main(String[] args) {
        int num = 1000000000;
        System.out.println(zeroNumOne(num));
        System.out.println(zeroNumTwo(num));
        System.out.println(rightOneOne(num));
        System.out.println(rightOneTwo(num));
    }
}
