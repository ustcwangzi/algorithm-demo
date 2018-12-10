/**
 * <p>Title: ParenthesesMatch</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/12/10</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.string;

/**
 * <p></p>
 *
 * @author wangzi
 */
public class ParenthesesMatch {
    public static boolean isValid(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }
        char[] array = str.toCharArray();
        int match = 0;
        for (char cur : array) {
            if (cur != '(' && cur != ')') {
                return false;
            }
            if (cur == '(') {
                match++;
            }
            if (cur == ')' && --match < 0) {
                return false;
            }
        }

        return match == 0;
    }

    public static int maxLength(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }

        char[] array = str.toCharArray();
        // 动态规划数组
        int[] dp = new int[array.length];
        int pre, result = 0;
        for (int i = 1; i < array.length; i++) {
            if (array[i] == ')') {
                pre = i - dp[i - 1] - 1;
                // 如果pre位置上是'('，就能与当前位置的字符再匹配出一对有效括号，dp[i - 1] + 2
                // 还需要检查是否存在str[i-dp[i-1]-2]，若存在，需要将以str[i-dp[i-1]-2]结尾的最长有效括号字串加到前面
                if (pre >= 0 && array[pre] == '(') {
                    dp[i] = dp[i - 1] + 2 + (pre > 0 ? dp[pre - 1] : 0);
                }
            }
            result = Math.max(result, dp[i]);
        }

        return result;
    }

    public static void main(String[] args) {
        String str = "((())())";
        System.out.println(isValid(str));
        System.out.println(maxLength(str));

        str = "()(()()(";
        System.out.println(isValid(str));
        System.out.println(maxLength(str));
    }
}
