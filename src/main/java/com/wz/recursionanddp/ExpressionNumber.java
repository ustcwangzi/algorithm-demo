/**
 * <p>Title: ExpressionNumber</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/11/23</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.recursionanddp;

/**
 * <p>表达式得到期望结果的组成数量</p>
 * <p>
 *     给定一个只由0(假)、1(真)、&(逻辑与)、|(逻辑或)、^(逻辑非)五种字符组成的字符串express，在给定一个布尔值desired。
 *     求出express有多少中组合方式，可以达到desired。
 *     例如，express="1^0|0|1"，desired=false。
 *          只有"1^((0|0)|1)"和"1^(0|(0|1))"能组合得到false，即两种。
 *     首先判断表达式是否有效，有三个标准：
 *     1、表达式长度必须为奇数
 *     2、表达式偶数位置的字符必须为'0'或'1'
 *     3、表达式奇数位置的字符必须为'&'或'|'或'^'
 *     方案一：
 *        暴力递归。
 *        将express划分为左右两部分，求出各种划分的情况下，能得到desired的数量是多少。
 *        1、划分符号为^、desired为true时：
 *           result = 左部分为真的数量 * 右部分为假的数量 + 左部分为假的数量 * 右部分为真的数量
 *        2、划分符号为^、desired为false时：
 *           result = 左部分为真的数量 * 右部分为真的数量 + 左部分为假的数量 * 右部分为假的数量
 *        3、划分符号为&、desired为true时：
 *           result = 左部分为真的数量 * 右部分为真的数量
 *        4、划分符号为&、desired为false时：
 *           result = 左部分为真的数量 * 右部分为假的数量 + 左部分为假的数量 * 右部分为真的数量
 *        5、划分符号为|、desired为true时：
 *           result = 左部分为真的数量 * 右部分为假的数量 + 左部分为假的数量 * 右部分为真的数量 + 左部分为真的数量 * 右部分为真的数量
 *        6、划分符号为|、desired为false时：
 *           result = 左部分为假的数量 * 右部分为假的数量
 *     方案二：
 *        动态规划。
 *        生成两个N*N的矩阵t和f，t[i][j]表示express[i...j]组成true的数量，f[i][j]表示express[i...j]组成false的数量。
 *        t[i][j]和f[i][j]的计算方式还是枚举express[i...j]上的每种划分。
 * </p>
 * <p>
 *     方案一时间复杂度为O(N!)，空间复杂度为O(N)
 *     方案二时间复杂度为O(N^3)，空间复杂度为O(N*N)
 * </p>
 *
 * @author wangzi
 */
public class ExpressionNumber {

    /**
     * 暴力递归
     */
    public static int numOne(String express, boolean desired) {
        if (express == null || express.length() == 0) {
            return 0;
        }
        char[] exp = express.toCharArray();
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
                    default:
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
                        // 左右均为true
                        result += process(exp, true, left, i - 1) * process(exp, true, i + 1, right);
                        // 左右均为false
                        result += process(exp, false, left, i - 1) * process(exp, false, i + 1, right);
                        break;
                    default:
                        break;
                }
            }
        }

        return result;
    }

    /**
     * 动态规划
     */
    public static int numTwo(String express, boolean desired) {
        if (express == null || express.length() == 0) {
            return 0;
        }
        char[] exp = express.toCharArray();
        if (!isValid(exp)) {
            return 0;
        }

        int[][] t = new int[exp.length][exp.length];
        int[][] f = new int[exp.length][exp.length];
        t[0][0] = exp[0] == '0' ? 0 : 1;
        f[0][0] = exp[0] == '1' ? 0 : 1;

        for (int j = 2; j < exp.length; j += 2) {
            // t[i][j]表示exp[i...j]组成true的数量
            t[j][j] = exp[j] == '0' ? 0 : 1;
            // f[i][j]表示exp[i...j]组成false的数量
            f[j][j] = exp[j] == '1' ? 0 : 1;
            // 对exp[i...j]进行划分
            for (int i = j - 2; i >= 0; i -= 2) {
                for (int k = i; k < j; k += 2) {
                    if (exp[k + 1] == '&') {
                        t[i][j] += t[i][k] * t[k + 2][j];
                        f[i][j] += (f[i][k] + t[i][k]) * f[k + 2][j] + f[i][k] * t[k + 2][j];
                    } else if (exp[k + 1] == '|') {
                        t[i][j] += (f[i][k] + t[i][k]) * t[k + 2][j] + t[i][k] * f[k + 2][j];
                        f[i][j] += f[i][k] * f[k + 2][j];
                    } else {
                        t[i][j] += f[i][k] * t[k + 2][j] + t[i][k] * f[k + 2][j];
                        f[i][j] += f[i][k] * f[k + 2][j] + t[i][k] * t[k + 2][j];
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
        desired = false;
        System.out.println(numOne(express, desired));
        System.out.println(numTwo(express, desired));
    }
}
