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
 * <p>
 *     给定一个32位的整数number，判断number是否是回文数，如果负数的绝对值是左右对称的，也是回文数。
 *     解决方案：
 *        1、生成变量help，初始时help=1，用help不断乘以10，直到变得和number的位数一样，例如number=131时，help=100
 *        2、此时number/help就是最高位数字，number%10就是最低位数字，比较这两个数字，不相同直接返回false；
 *           相同则令number=(number%help)/10，即number变成除去最高位和最低位两个数字之后的值，
 *           同时令help/=100，即让help与number的位数保持一致
 *        3、如果number==0，表示所有的数字均已完成判断，返回true，否则重复步骤二。
 *        总之，就是让number每次去除最左和最右两个数，然后逐渐完成所有对应的判断，如果number是负数时，使用其绝对值进行判断。
 *        同时注意的是，32位整数中的最小值是-2147483648，无法转成对应的绝对值，但这个数明显不是回文数，因此可直接返回false。
 * </p>
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
            help /= 100;
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(isPalindrome(90009));
        System.out.println(isPalindrome(-10001));
    }
}
