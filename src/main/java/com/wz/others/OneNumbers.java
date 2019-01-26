/**
 * <p>Title: OneNumbers</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/1/26</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.others;

/**
 * <p>1至n中数字1出现的次数</p>
 *
 * @author wangzi
 */
public class OneNumbers {

    public static int countOne(int number) {
        if (number < 1) {
            return 0;
        }
        int count = 0;
        // 每个数单独计算含有1的个数
        for (int i = 1; i < number + 1; i++) {
            count += getOneNumbers(i);
        }
        return count;
    }

    private static int getOneNumbers(int number) {
        int result = 0;
        while (number != 0) {
            if (number % 10 == 1) {
                result++;
            }
            number /= 10;
        }
        return result;
    }

    public static int countTwo(int number) {
        if (number < 1) {
            return 0;
        }
        int count = 0;
        int high = number, low, cur, remainder, i = 1;
        while (high != 0) {
            int power = (int) Math.pow(10, i);
            // 第i位的高位
            high = number / power;
            // 去除高位后的余数
            remainder = number % power;
            power = (int) Math.pow(10, i - 1);
            // 第i位
            cur = remainder / power;
            // 第i位的低位
            low = remainder % power;
            if (cur == 0) {
                count += high * power;
            } else if (cur == 1) {
                count += high * power + low + 1;
            } else {
                count += (high + 1) * power;
            }
            i++;
        }
        return count;
    }

    public static void main(String[] args) {
        int number = 87654321;

        System.out.println(countOne(number));
        System.out.println(countTwo(number));
    }
}
