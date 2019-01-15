/**
 * <p>Title: GreatestCommonDivisor</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/1/15</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.others;

/**
 * <p>最大公约数</p>
 *
 * @author wangzi
 */
public class GreatestCommonDivisor {
    public static int gcd(int m, int n) {
        return n == 0 ? m : gcd(n, m % n);
    }

    public static void main(String[] args) {
        System.out.println(gcd(27, 18));
    }
}
