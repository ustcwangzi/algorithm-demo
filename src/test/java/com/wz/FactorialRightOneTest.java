/**
 * <p>Title: FactorialRightOneTest</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/3/31</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz;

import com.wz.others.Factorial;

/**
 * <p>N阶乘二进制表达式中最右侧1的位置，最右位置为0，只要出现一个因子2，最低位的1就会向左移动一位</p>
 *
 * @author wangzi
 */
public class FactorialRightOneTest {
    private static int solution(int n) {
        int result = 0;
        while (n > 0) {
            result += n / 2;
            n /= 2;
        }
        return result;
    }

    public static void main(String[] args) {
        int times = 100;
        boolean result = true;
        for (int i = 0; i < times; i++) {
            int number = RandomUtils.genRandomNumber() + 1;
            int position = solution(number);
            if (position != Factorial.rightOneOne(number) || position != Factorial.rightOneTwo(number)) {
                result = false;
                System.out.println("Error, number:" + number);
            }
        }
        if (result) {
            System.out.println("Past");
        }
    }
}
