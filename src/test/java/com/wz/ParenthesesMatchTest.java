package com.wz;

import com.wz.string.ParenthesesMatch;

/**
 * <p>括号字符串的最长有效长度</p>
 *
 * @author wangzi
 */
public class ParenthesesMatchTest {
    private static int solution(String str) {
        // dp[i]表示必须以字符str[i]结尾的最长有效长度
        int[] dp = new int[str.length()];
        int result = 0;
        for (int i = 1; i < dp.length; i++) {
            if (str.charAt(i) == ')') {
                // 若i-dp[i-1]-1位置上是'('，就能与当前的')'匹配，如"(()())"遍历到最后一个')'时
                // 同时，假设遍历到倒数第二个')'，那么计算到的最长有效字符串为"()"，但是前面还有一个"()"
                // 因此还需要加上以str[pre-1]结尾的最长有效字符串长度
                int pre = i - dp[i - 1] - 1;
                if (pre >= 0 && str.charAt(pre) == '(') {
                    dp[i] = dp[i - 1] + 2 + (pre > 0 ? dp[pre - 1] : 0);
                }
            }
            result = Math.max(result, dp[i]);
        }
        return result;
    }

    public static void main(String[] args) {
        int times = 100;
        boolean result = true;
        for (int i = 0; i < times; i++) {
            String str = RandomUtils.genRandomParentheses();
            if (solution(str) != ParenthesesMatch.maxLength(str)) {
                result = false;
                System.out.println("Error, str:" + str);
            }
        }
        if (result) {
            System.out.println("Past");
        }
    }
}
