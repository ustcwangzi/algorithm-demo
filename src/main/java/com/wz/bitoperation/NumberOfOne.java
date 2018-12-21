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
