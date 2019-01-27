/**
 * <p>Title: PalindromeNumber</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/1/27</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.others;

/**
 * <p>判断一个数是否是回文数</p>
 *
 * @author wangzi
 */
public class PalindromeNumber {
    public static boolean isPalindrome(int number) {
        if (number == Integer.MIN_VALUE) {
            return false;
        }
        // 防止number是负数
        number = Math.abs(number);
        // help位数与number保持一致
        int help = 1;
        while (number / help >= 10) {
            help *= 10;
        }

        while (number != 0) {
            // 比较当前数的最高位和最低位
            if (number / help != number % 10) {
                return false;
            }
            // 去除最高位和最低位
            number = (number % help) / 10;
            number /= 100;
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(isPalindrome(90009));
        System.out.println(isPalindrome(-10001));
    }
}
