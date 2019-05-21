package com.wz;

import com.wz.recursionanddp.Fibonacci;

/**
 * <p>斐波那契数列求值</p>
 *
 * @author wangzi
 */
public class FibonacciTest {
    private static int solution(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1 || n == 2) {
            return 1;
        }
        // f(n)=f(n-1)+f(n-2)，a为f(n-1)，b为f(n-2)，result为f(n)
        int a = 1, b = 1, result = 0;
        for (int i = 3; i <= n; i++) {
            result = a + b;
            a = b;
            b = result;
        }
        return result;
    }

    public static void main(String[] args) {
        int times = 20;
        boolean result = true;
        for (int i = 0; i < times; i++) {
            int n = RandomUtils.genRandomNumber();
            if (solution(n) != Fibonacci.fibonacciThree(n)) {
                result = false;
                System.out.println("Error, n:" + n);
            }
        }
        if (result) {
            System.out.println("Past");
        }
    }
}
