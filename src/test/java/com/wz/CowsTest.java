package com.wz;

import com.wz.recursionanddp.Fibonacci;

/**
 * <p>成熟母牛每年生1头小母牛，从第二年开始母牛开始生小母牛，小母牛三年成熟(从第四年生小牛)，求N年后母牛数量</p>
 *
 * @author wangzi
 */
public class CowsTest {
    private static int solution(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1 || n == 2 || n == 3) {
            return n;
        }
        // 第N-1年的牛都会活到第N年，同时所有成熟的牛都会生1头小牛，而成熟牛的数量就是第N-3年的所有牛
        // 因此f(n)=f(n-1)+f(n-3)，a为f(n-1)，b为f(n-2)，c为f(n-3)，result为f(n)
        int a = 1, b = 2, c = 3, result = 0;
        for (int i = 4; i <= n; i++) {
            result = a + c;
            a = b;
            b = c;
            c = result;
        }
        return result;
    }

    public static void main(String[] args) {
        int times = 100;
        boolean result = true;
        for (int i = 0; i < times; i++) {
            int n = RandomUtils.genRandomNumber();
            if (solution(n) != Fibonacci.cowThree(n)) {
                result = false;
                System.out.println("Error, n:" + n);
            }
        }
        if (result) {
            System.out.println("Past");
        }
    }
}
