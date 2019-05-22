package com.wz;

import com.wz.recursionanddp.Fibonacci;

/**
 * <p>台阶数N，一次可跨2个或1个，有多少种走法</p>
 *
 * @author wangzi
 */
public class StepsTest {
    private static int solution(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1 || n == 2) {
            return n;
        }
        // 对于第N级，有两种走法：1、从N-2级直接跨2级，2、从N-1级跨1级
        // 因此f(n)=f(n-1)+f(n-2)，a为f(n-1)，b为f(n-2)，result为f(n)
        int a = 1, b = 2, result = 0;
        for (int i = 3; i <= n; i++) {
            result = a + b;
            a = b;
            b = result;
        }
        return result;
    }

    public static void main(String[] args) {
        int times = 100;
        boolean result = true;
        for (int i = 0; i < times; i++) {
            int n = RandomUtils.genRandomNumber();
            if (solution(n) != Fibonacci.stepThree(n)) {
                result = false;
                System.out.println("Error, n:" + n);
            }
        }
        if (result) {
            System.out.println("Past");
        }
    }
}
