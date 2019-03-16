/**
 * <p>Title: PalindromeNumberTest</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/3/16</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz;

import com.wz.others.PalindromeNumber;

/**
 * <p>判断一个32位整数是否是回文数</p>
 *
 * @author wangzi
 */
public class PalindromeNumberTest {
    private static boolean solution(int number) {
        if (number == Integer.MIN_VALUE) {
            return false;
        }
        number = Math.abs(number);
        // help与number位数保持一致
        int help = 1;
        while (number / help >= 10) {
            help *= 10;
        }

        while (number > 0) {
            // 比较最高位与最低位
            if (number / help != number % 10) {
                return false;
            }
            // 去除最高位与最低位
            number = (number % help) / 10;
            help /= 100;
        }
        return true;
    }

    public static void main(String[] args) {
        int times = 100;
        boolean result = true;
        for (int i = 0; i < times; i++) {
            int m = RandomUtils.genRandomNumber();
            if (solution(m) != PalindromeNumber.isPalindrome(m)) {
                result = false;
                System.out.println("Error, m:" + m);
            }
        }
        if (result) {
            System.out.println("Past");
        }
    }
}
