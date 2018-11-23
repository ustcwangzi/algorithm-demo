/**
 * <p>Title: ExpressionNumber</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/11/23</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.recursionanddynamicprogramming;

/**
 * <p>表达式得到期望结果的组成种数</p>
 *
 * @author wangzi
 */
public class ExpressionNumber {

    public static int numOne(String expression, boolean desired) {
        if (expression == null || expression.length() == 0) {
            return 0;
        }
        char[] exp = expression.toCharArray();
        if (!isValid(exp)) {
            return 0;
        }

        return process(exp, desired, 0, exp.length - 1);
    }

    private static int process(char[] exp, boolean desired, int left, int right) {
        if (left == right) {
            if (exp[left] == '1') {
                return desired ? 1 : 0;
            } else {
                return desired ? 0 : 1;
            }
        }

        int result = 0;
        if (desired) {
            for (int i = left + 1; i < right; i += 2) {
                // 奇数位置
                switch (exp[i]) {
                    case '&':
                        // 左右均为true
                        result += process(exp, true, left, i - 1) * process(exp, true, i + 1, right);
                        break;
                    case '|':
                        // 左true，右false
                        result += process(exp, true, left, i - 1) * process(exp, false, i + 1, right);
                        // 左false，右true
                        result += process(exp, false, left, i - 1) * process(exp, true, i + 1, right);
                        // 左右均为true
                        result += process(exp, true, left, i - 1) * process(exp, true, i + 1, right);
                        break;
                    case '^':
                        // 左true，右false
                        result += process(exp, true, left, i - 1) * process(exp, false, i + 1, right);
                        // 左false，右true
                        result += process(exp, false, left, i - 1) * process(exp, true, i + 1, right);
                        break;
                }
            }
        } else {
            for (int i = left + 1; i < right; i += 2) {
                // 奇数位置
                switch (exp[i]) {
                    case '&':
                        // 左true，右false
                        result += process(exp, true, left, i - 1) * process(exp, false, i + 1, right);
                        // 左false，右true
                        result += process(exp, false, left, i - 1) * process(exp, true, i + 1, right);
                        // 左右均为false
                        result += process(exp, false, left, i - 1) * process(exp, false, i + 1, right);
                        break;
                    case '|':
                        // 左右均为false
                        result += process(exp, false, left, i - 1) * process(exp, false, i + 1, right);
                        break;
                    case '^':
                        /// 左右均为true
                        result += process(exp, true, left, i - 1) * process(exp, true, i + 1, right);
                        // 左右均为false
                        result += process(exp, false, left, i - 1) * process(exp, false, i + 1, right);
                        break;
                }
            }
        }

        return result;
    }

    public static int numTwo(String expression, boolean desired) {
        if (expression == null || expression.length() == 0) {
            return 0;
        }
        char[] exp = expression.toCharArray();
        if (!isValid(exp)) {
            return 0;
        }

        int[][] t = new int[exp.length][exp.length];
        int[][] f = new int[exp.length][exp.length];
        t[0][0] = exp[0] == '0' ? 0 : 1;
        f[0][0] = exp[0] == '1' ? 0 : 1;

        for (int i = 2; i < exp.length; i += 2) {
            // t[i][j]表示exp[j...i]组成true的种数
            t[i][i] = exp[i] == '0' ? 0 : 1;
            // f[i][j]表示exp[j...i]组成false的种数
            f[i][i] = exp[i] == '1' ? 0 : 1;
            // 对exp[j...i]进行划分
            for (int j = i - 2; j >= 0; j -= 2) {
                for (int k = j; k < i; k += 2) {
                    if (exp[k + 1] == '&') {
                        t[j][i] += t[j][k] * t[k + 2][i];
                        f[j][i] += (f[j][k] + t[j][k]) * f[k + 2][i] + f[j][k] * t[k + 2][i];
                    } else if (exp[k + 1] == '|') {
                        t[j][i] += (f[j][k] + t[j][k]) * t[k + 2][i] + t[j][k] * f[k + 2][i];
                        f[j][i] += f[j][k] * f[k + 2][i];
                    } else {
                        t[j][i] += f[j][k] * t[k + 2][i] + t[j][k] * f[k + 2][i];
                        f[j][i] += f[j][k] * f[k + 2][i] + t[j][k] * t[k + 2][i];
                    }
                }
            }
        }

        return desired ? t[0][t.length - 1] : f[0][f.length - 1];
    }

    private static boolean isValid(char[] exp) {
        // 说明exp长度为奇数
        if ((exp.length & 1) == 0) {
            return false;
        }
        // 偶数位置必须为'0'或'1'
        for (int i = 0; i < exp.length; i += 2) {
            if (exp[i] != '0' && exp[i] != '1') {
                return false;
            }
        }
        // 奇数位必须为'&'或'|'或'^'
        for (int i = 1; i < exp.length; i += 2) {
            if ((exp[i] != '&') && (exp[i] != '|') && (exp[i] != '^')) {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        String express = "1^0&0|1&1^0&0^1|0|1&1";
        boolean desired = true;
        System.out.println(numOne(express, desired));
        System.out.println(numTwo(express, desired));
    }
}
