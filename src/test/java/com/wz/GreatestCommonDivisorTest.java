package com.wz;

import com.wz.others.GreatestCommonDivisor;

/**
 * <p>最大公约数，辗转相除法</p>
 *
 * @author wangzi
 */
public class GreatestCommonDivisorTest {
    private static int solution(int m, int n) {
        // 如果q和r分别是m除以n的商和余数，即m=n*q+r，则m和n的最大公约数等于n和r的最大公约数
        return n == 0 ? m : solution(n, m % n);
    }

    public static void main(String[] args) {
        int times = 100;
        boolean result = true;
        for (int i = 0; i < times; i++) {
            int m = RandomUtils.genRandomNumber(), n = RandomUtils.genRandomNumber();
            if (solution(m, n) != GreatestCommonDivisor.gcd(m, n)) {
                result = false;
                System.out.println("Error, m:" + m + ", n:" + n);
            }
        }
        if (result) {
            System.out.println("Past");
        }
    }
}
