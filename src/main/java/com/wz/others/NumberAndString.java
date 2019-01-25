/**
 * <p>Title: NumberAndString</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/1/25</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.others;

/**
 * <p>一种字符串和数字的对应关系</p>
 *
 * @author wangzi
 */
public class NumberAndString {
    public static String convertToString(char[] array, int number) {
        if (array == null || array.length == 0 || number < 1) {
            return "";
        }
        // 伪base进制
        int base = array.length;
        // array的每一位代表的基数
        int cur = 1;
        // 能用到位数
        int validLength = 0;
        while (number >= cur) {
            validLength++;
            number -= cur;
            cur *= base;
        }

        char[] result = new char[validLength];
        int k;
        for (int index = 0; index < result.length; index++) {
            cur /= base;
            // 转换成array的某一位
            k = number / cur;
            result[index] = (k < 0 || k > array.length - 1) ? 0 : array[k];
            number %= cur;
        }
        return String.valueOf(result);
    }

    public static int convertToNumber(char[] array, String str) {
        if (array == null || array.length == 0 || str == null || str.length() == 0) {
            return 0;
        }
        char[] strArray = str.toCharArray();
        // 伪base进制
        int base = array.length;
        // array的每一位代表的基数
        int cur = 1;
        int result = 0;
        for (int i = strArray.length - 1; i > -1; i--) {
            result += getPositionFromChar(array, strArray[i]) * cur;
            cur *= base;
        }
        return result;
    }

    private static int getPositionFromChar(char[] array, char ch) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == ch) {
                return i + 1;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        char[] array = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
                'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
        System.out.println(convertToString(array, 273980105));
        System.out.println(convertToNumber(array, "WANGZI"));
    }
}
