/**
 * <p>Title: ConvertStringToInteger</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/11/27</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.string;


/**
 * <p>整数字符串转成整数值</p>
 *
 * @author wangzi
 */
public class ConvertStringToInteger {

    public static int convert(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }

        char[] charArray = str.toCharArray();
        if (!isValid(charArray)) {
            return 0;
        }

        // 符号位
        boolean positive = charArray[0] != '-';
        int minQuotient = Integer.MIN_VALUE / 10;
        int minRemainder = Integer.MIN_VALUE % 10;

        int result = 0, cur;
        for (int i = positive ? 0 : 1; i < charArray.length; i++) {
            cur = '0' - charArray[i];
            if ((result < minQuotient) || (result == minQuotient && cur < minRemainder)) {
                return 0;
            }
            result = result * 10 + cur;
        }
        if (positive && result == Integer.MIN_VALUE) {
            return 0;
        }

        return positive ? -result : result;
    }

    private static boolean isValid(char[] chars) {
        if (chars[0] != '-' && (chars[0] < '0' || chars[0] > '9')) {
            return false;
        }
        if (chars[0] == '-' && (chars.length == 1 || chars[1] == '0')) {
            return false;
        }
        if (chars[0] == '0' && chars.length > 1) {
            return false;
        }
        for (int i = 1; i < chars.length; i++) {
            if (chars[i] < '0' || chars[i] > '9') {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        // max in java
        System.out.println(convert("2147483647"));

        // min in java
        System.out.println(convert("-2147483648"));

        // overflow
        System.out.println(convert("2147483648"));

        // overflow
        System.out.println(convert("-2147483649"));

        System.out.println(convert("123"));

        System.out.println(convert("-123"));
    }
}
