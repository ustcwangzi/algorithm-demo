package com.wz;

import com.wz.others.LeastCommonMultiple;

/**
 * <p>最小公倍数</p>
 *
 * @author wangzi
 */
public class LeastCommonMultipleTest {
    private static int solution(int m, int n) {
        if (m == 0 && n == 0) {
            return 0;
        }
        // 两个数的最小公倍数=两个数的乘积／最大公约数
        return m * n / gcd(m, n);
    }

    private static int gcd(int m, int n) {
        return n == 0 ? m : gcd(n, m % n);
    }

    public static void main(String[] args) {
        int times = 100;
        boolean result = true;
        for (int i = 0; i < times; i++) {
            int m = RandomUtils.genRandomNumber(), n = RandomUtils.genRandomNumber();
            if (solution(m, n) != LeastCommonMultiple.lcm(m, n)) {
                result = false;
                System.out.println("Error, m:" + m + ", n:" + n);
            }
        }
        if (result) {
            System.out.println("Past");
        }
    }
}
