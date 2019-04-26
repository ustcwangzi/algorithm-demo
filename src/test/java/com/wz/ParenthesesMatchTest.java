package com.wz;

import com.wz.string.ParenthesesMatch;

/**
 * <p>括号字符串的最长有效长度</p>
 *
 * @author wangzi
 */
public class ParenthesesMatchTest {
    private static int solution(String str) {
        int[] dp = new int[str.length()];
        int result = 0;
        for (int i = 1; i < dp.length; i++) {
            if (str.charAt(i) == ')') {
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
