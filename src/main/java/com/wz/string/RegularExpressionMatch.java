/**
 * <p>Title: RegularExpressionMatch</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/12/16</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.string;

/**
 * <p>字符串匹配问题</p>
 *
 * @author wangzi
 */
public class RegularExpressionMatch {

    public static boolean isMatch(String str, String exp) {
        if (str == null || exp == null) {
            return false;
        }
        char[] strArray = str.toCharArray();
        char[] expArray = exp.toCharArray();
        return isValid(strArray, expArray) && process(strArray, expArray, 0, 0);
    }

    private static boolean process(char[] str, char[] exp, int strIndex, int expIndex) {
        if (expIndex == exp.length) {
            return strIndex == str.length;
        }
        if (expIndex + 1 == exp.length || exp[expIndex + 1] != '*') {
            return strIndex != str.length && (exp[expIndex] == str[strIndex] || exp[expIndex] == '.')
                    && process(str, exp, strIndex + 1, expIndex + 1);
        }
        while (strIndex != str.length && (exp[expIndex] == str[strIndex] || exp[expIndex] == '.')) {
            if (process(str, exp, strIndex, expIndex + 2)) {
                return true;
            }
            strIndex++;
        }
        return process(str, exp, strIndex, expIndex + 2);
    }

    private static boolean isValid(char[] str, char[] exp) {
        // str中不能含有'*'或'.'
        for (char s : str) {
            if (s == '*' || s == '.') {
                return false;
            }
        }
        // exp首字符不能是'*'，任意两个'*'不能相邻
        for (int i = 0; i < exp.length; i++) {
            if (exp[i] == '*' && (i == 0 || exp[i - 1] == '*')) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        String str = "abcccdefg";
        String exp = "ab.*d.*e.*";
        System.out.println(isMatch(str, exp));
    }
}
