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

    public static boolean isMatchDP(String str, String exp) {
        if (str == null || exp == null) {
            return false;
        }

        char[] strArray = str.toCharArray();
        char[] expArray = exp.toCharArray();
        if (!isValid(strArray, expArray)) {
            return false;
        }
        // 动态规划矩阵
        boolean[][] dp = initDPMap(strArray, expArray);
        for (int i = strArray.length - 1; i > -1; i--) {
            for (int j = expArray.length - 2; j > -1; j--) {
                if (expArray[j + 1] != '*') {
                    dp[i][j] = (strArray[i] == expArray[j] || expArray[j] == '.')
                            && dp[i + 1][j + 1];
                } else {
                    int strIndex = i;
                    while (strIndex != strArray.length && (strArray[strIndex] == expArray[j] || expArray[j] == '.')) {
                        if (dp[strIndex][j + 2]) {
                            dp[i][j] = true;
                            break;
                        }
                        strIndex++;
                    }
                    if (!dp[i][j]) {
                        dp[i][j] = dp[strIndex][j + 2];
                    }
                }
            }
        }
        return dp[0][0];
    }

    private static boolean[][] initDPMap(char[] str, char[] exp) {
        int strLen = str.length;
        int expLen = exp.length;
        boolean[][] dp = new boolean[strLen + 1][expLen + 1];
        dp[strLen][expLen] = true;
        for (int j = expLen - 2; j > -1; j = j - 2) {
            if (exp[j] != '*' && exp[j + 1] == '*') {
                dp[strLen][j] = true;
            } else {
                break;
            }
        }
        if (strLen > 0 && expLen > 0) {
            if (exp[expLen - 1] == '.' || str[strLen - 1] == exp[expLen - 1]) {
                dp[strLen - 1][expLen - 1] = true;
            }
        }
        return dp;
    }

    public static void main(String[] args) {
        String str = "abcccdefg";
        String exp = "ab.*d.*e.*";
        System.out.println(isMatch(str, exp));
        System.out.println(isMatchDP(str, exp));
    }
}
