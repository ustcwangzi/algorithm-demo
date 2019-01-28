/**
 * <p>Title: ChineseExpressionForNumber</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/1/28</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.others;

/**
 * <p>数字的中文表达</p>
 * <p>
 *     给定一个32位正数number，获取number的中文表达字符串。
 * </p>
 *
 * @author wangzi
 */
public class ChineseExpressionForNumber {

    public static String getChineseExpression(int number) {
        if (number == 0) {
            return "零";
        }
        String result = number < 0 ? "负" : "";
        int yi = Math.abs(number / 100000000);
        int remainder = Math.abs((number % 100000000));
        if (yi == 0) {
            return result + number1To99999999(remainder);
        }
        result += number1To9999(yi) + "亿";
        if (remainder == 0) {
            return result;
        }
        if (remainder < 10000000) {
            return result + "零" + number1To99999999(remainder);
        } else {
            return result + number1To99999999(remainder);
        }
    }

    private static String number1To9(int number) {
        if (number < 1 || number > 9) {
            return "";
        }
        String[] names = {"一", "二", "三", "四", "五", "六", "七", "八", "九"};
        return names[number - 1];
    }

    private static String number1To99(int number, boolean hasHundred) {
        if (number < 1 || number > 99) {
            return "";
        }
        if (number < 10) {
            return number1To9(number);
        }
        int ten = number / 10;
        // 是否存在百位
        if (ten == 1 && !hasHundred) {
            return "十" + number1To9(number % 10);
        } else {
            return number1To9(ten) + "十" + number1To9(number % 10);
        }
    }

    private static String number1To999(int number) {
        if (number < 1 || number > 999) {
            return "";
        }
        if (number < 100) {
            return number1To99(number, false);
        }
        String result = number1To9(number / 100) + "百";
        int remainder = number % 100;
        if (remainder == 0) {
            return result;
        }
        if (remainder >= 10) {
            result += number1To99(remainder, true);
        } else {
            result += "零" + number1To9(remainder);
        }
        return result;
    }

    private static String number1To9999(int number) {
        if (number < 1 || number > 9999) {
            return "";
        }
        if (number < 1000) {
            return number1To999(number);
        }
        String result = number1To9(number / 1000) + "千";
        int remainder = number % 1000;
        if (remainder == 0) {
            return result;
        }
        if (remainder >= 100) {
            result += number1To999(remainder);
        } else {
            result += "零" + number1To99(remainder, false);
        }
        return result;
    }

    private static String number1To99999999(int number) {
        if (number < 1 || number > 99999999) {
            return "";
        }
        int wan = number / 10000;
        int remainder = number % 10000;
        if (wan == 0) {
            return number1To9999(remainder);
        }
        String result = number1To9999(wan) + "万";
        if (remainder == 0) {
            return result;
        }
        if (remainder < 1000) {
            return result + "零" + number1To999(remainder);
        } else {
            return result + number1To9999(remainder);
        }
    }

    public static void main(String[] args) {
        System.out.println(getChineseExpression(319));
        System.out.println(getChineseExpression(1024));
        System.out.println(getChineseExpression(-2147483648));
        System.out.println(getChineseExpression(0));
    }
}
