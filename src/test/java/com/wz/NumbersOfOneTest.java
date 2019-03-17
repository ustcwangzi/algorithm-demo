/**
 * <p>Title: NumbersOfOneTest</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/3/17</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz;

import com.wz.others.NumbersOfOne;

import java.util.Random;

/**
 * <p>1～N中数字1出现的次数</p>
 *
 * @author wangzi
 */
public class NumbersOfOneTest {
    private static int solution(int number) {
        if (number < 1) {
            return 0;
        }
        int count = 0;
        int high = number, cur, low;
        // 从右至左的位数
        int i = 1;
        while (high > 0) {
            int power = (int) Math.pow(10, i);
            // 高位
            high = number / power;
            // 去除高位后的余数
            int tmp = number % power;
            power = (int) Math.pow(10, i - 1);
            // 当前位
            cur = tmp / power;
            // 低位
            low = tmp % power;

            if (cur == 0) {
                // 高位 * 10^(i-1)
                count += high * power;
            } else if (cur == 1) {
                // 高位 * 10^(i-1) + (低位+1)
                count += high * power + low + 1;
            } else {
                // (高位+1) * 10^(i-1)
                count += (high + 1) * power;
            }
            i++;
        }
        return count;
    }

    public static void main(String[] args) {
        int times = 100;
        boolean result = true;
        for (int i = 0; i < times; i++) {
            int m = new Random().nextInt(Integer.MAX_VALUE);
            if (solution(m) != NumbersOfOne.countTwo(m)) {
                result = false;
                System.out.println("Error, m:" + m);
            }
        }
        if (result) {
            System.out.println("Past");
        }
    }
}
