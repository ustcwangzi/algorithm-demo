/**
 * <p>Title: EnglishExpressionForNumber</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/1/28</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.others;

/**
 * <p>数字的英文表达</p>
 * <p>
 *     给定一个32位正数number，获取number的英文表达字符串。
 *     例如：
 *        319：Three Hundred Nineteen
 *        1024：One Thousand, Twenty Four
 *        -2147483648：Negative, Two Billion, One Hundred Forty Seven Million, Four Hundred Eighty Three Thousand,
 *                    Six Hundred Forty Eight
 *     解决方案：
 *        英文的表达是以三个数为一组的，所以只要能表达出1～999，然后将数字分解成十亿组，百万组，千组，1～999组，
 *        每组都用1～999表达再把组与组之间各自的表达字符串连接起来即可。
 *        如何实现1～999的表达？从简单场景出发，依次实现1～19，1～99，1～999即可。
 *        注意的是负数要加一个前缀Negative，number如果为最小负整数的时候要考虑取绝对值时会溢出的问题。
 * </p>
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
