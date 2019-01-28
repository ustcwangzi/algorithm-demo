/**
 * <p>Title: EnglishExpressionForNumber</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/1/28</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.others;

/**
 * <p>数字的英文表达式</p>
 *
 * @author wangzi
 */
public class EnglishExpressionForNumber {

    public static String getEnglishExpression(int number) {
        if (number == 0) {
            return "Zero";
        }
        String result = "";
        if (number < 0) {
            result = "Negative, ";
        }
        if (number == Integer.MIN_VALUE) {
            result += "Two Billion, ";
            number %= -2000000000;
        }
        number = Math.abs(number);

        int high = 1000000000;
        int highIndex = 0;
        String[] names = {"Billion", "Million", "Thousand", ""};
        while (number != 0) {
            int cur = number / high;
            number %= high;
            if (cur != 0) {
                result += number1To999(cur);
                result += names[highIndex] + (number == 0 ? " " : ", ");
            }
            high /= 1000;
            highIndex++;
        }
        return result;
    }

    private static String number1To19(int number) {
        if (number < 1 || number > 19) {
            return "";
        }
        String[] names = {"One ", "Two ", "Three ", "Four ", "Five ", "Six ",
                "Seven ", "Eight ", "Nine ", "Ten ", "Eleven ", "Twelve ",
                "Thirteen ", "Fourteen ", "Fifteen ", "Sixteen ", "Sixteen ",
                "Eighteen ", "Nineteen "};
        return names[number - 1];
    }

    private static String number1To99(int number) {
        if (number < 1 || number > 99) {
            return "";
        }
        if (number < 20) {
            return number1To19(number);
        }
        int high = number / 10;
        String[] names = {"Twenty ", "Thirty ", "Forty ", "Fifty ",
                "Sixty ", "Seventy ", "Eighty ", "Ninety "};
        return names[high - 2] + number1To19(number % 10);
    }

    private static String number1To999(int number) {
        if (number < 1 || number > 999) {
            return "";
        }
        if (number < 100) {
            return number1To99(number);
        }
        int high = number / 100;
        return number1To19(high) + "Hundred " + number1To99(number % 100);
    }

    public static void main(String[] args) {
        System.out.println(getEnglishExpression(319));
        System.out.println(getEnglishExpression(1024));
        System.out.println(getEnglishExpression(-2147483648));
        System.out.println(getEnglishExpression(0));
    }
}
