/**
 * <p>Title: LeastCommonMultiple</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/1/15</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.others;

/**
 * <p>最小公倍数</p>
 * <p>
 *     给定两个不为0的整数M和N，求M和N的最小公倍数。
 *     解决方案：
 *        两个数的最小公倍数=两个数的乘积／最大公约数
 * </p>
 *
 * @author wangzi
 */
public class LeastCommonMultiple {
    public static int lcm(int m, int n) {
        return m * n / gcd(m, n);
    }

    private static int gcd(int m, int n) {
        return n == 0 ? m : gcd(n, m % n);
    }

    public static void main(String[] args) {
        System.out.println(lcm(27, 18));
    }
}
