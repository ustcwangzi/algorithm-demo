/**
 * <p>Title: NumberToAlphabet</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/11/22</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.recursionanddynamicprogramming;

/**
 * <p>数字字符串转换为字符组合的种数</p>
 *
 * @author wangzi
 */
public class NumberToAlphabet {
    /**
     * 暴力递归
     */
    public static int numOne(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }

        return process(str.toCharArray(), 0);
    }

    private static int process(char[] chars, int i) {
        if (i == chars.length) {
            return 1;
        }
        if (chars[i] == '0') {
            return 0;
        }
        // str[i]在'1'到'9'之间，str[i]能转换的种数至少包含p(i+1)
        int result = process(chars, i + 1);
        // str[i]和str[i+1]的组合在'10'到'26'之间，p(i) = p(i+1) + p(i+2)
        if (i + 1 < chars.length && (chars[i] - '0') * 10 + chars[i + 1] - '0' < 27) {
            result += process(chars, i + 2);
        }

        return result;
    }

    /**
     * 动态规划
     */
    public static int numTwo(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        char[] chars = str.toCharArray();
        // 动态规划数组
        int[] dp = new int[chars.length + 1];
        dp[chars.length] = 1;
        for (int i = chars.length - 1; i >= 0; i--) {
            if (chars[i] == '0') {
                dp[i] = 0;
            } else {
                dp[i] = dp[i + 1];
                if (i + 1 <= chars.length - 1 && (chars[i] - '0') * 10 + chars[i + 1] - '0' < 27) {
                    dp[i] += dp[i + 2];
                }
            }
        }
        return dp[0];
    }

    /**
     * 动态规划基础上的空间压缩
     */
    public static int numThree(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        char[] chars = str.toCharArray();
        // cur记录当前值dp[i]
        int cur = chars[chars.length - 1] == '0' ? 0 : 1;
        // dp2记录dp[i + 2]
        int dp2 = 1;
        // dp1记录dp[i + 1]
        int dp1 = cur;
        for (int i = chars.length - 2; i >= 0; i--) {
            if (chars[i] == '0') {
                cur = 0;
            } else {
                cur = dp1;
                if ((chars[i] - '0') * 10 + chars[i + 1] - '0' < 27) {
                    cur += dp2;
                }
            }
            dp2 = dp1;
            dp1 = cur;
        }
        return cur;
    }

    public static void main(String[] args) {
        String str = "781231783161018231";
        System.out.println(numOne(str));
        System.out.println(numTwo(str));
        System.out.println(numThree(str));
    }
}
