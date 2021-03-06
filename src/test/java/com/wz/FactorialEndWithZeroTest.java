/**
 * <p>Title: FactorialEndWithZeroTest</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/3/31</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz;

import com.wz.others.Factorial;

/**
 * <p>N阶乘中末尾有多少个0，等价于N中有多少个因子5</p>
 *
 * @author wangzi
 */
public class FactorialEndWithZeroTest {
    private static int solution(int n) {
        int result = 0;
        while (n > 0) {
            result += n / 5;
            n /= 5;
        }
        return result;
    }

    public static void main(String[] args) {
        int times = 100;
        boolean result = true;
        for (int i = 0; i < times; i++) {
            int number = RandomUtils.genRandomNumber() + 1;
            int count = solution(number);
            if (count != Factorial.zeroNumOne(number) || count != Factorial.zeroNumTwo(number)) {
                result = false;
                System.out.println("Error, number:" + number);
            }
        }
        if (result) {
            System.out.println("Past");
        }
    }
}
