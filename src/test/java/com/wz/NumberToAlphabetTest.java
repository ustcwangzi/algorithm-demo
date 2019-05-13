package com.wz;

import com.wz.recursionanddp.NumberToAlphabet;

/**
 * <p>数字字符串转换为字符组合的数量</p>
 *
 * @author wangzi
 */
public class NumberToAlphabetTest {
    private static int solution(String str) {
        char[] array = str.toCharArray();
        int len = array.length;
        if (len == 1) {
            return array[len - 1] == '0' ? 0 : 1;
        }

        // dp[i] 表示array[i...N-1]能够转换的数量
        // 若array[i]为'0'，则dp[i]直接就是0
        // array[i]不为'0'时，判断array[i]和array[i+1]是否可以组成一个字母
        // 如果可以，那么dp[i]=dp[i+1]+dp[i+2]，如果不可以，那么dp[i]=dp[i+1]
        int[] dp = new int[len];
        // 计算dp[N-1]与dp[N-2]
        if (array[len - 1] == '0') {
            dp[len - 1] = 0;
            // 注意"10"只能转换为一种:"J"，而不是"1"、"10"两种
            dp[len - 2] = array[len - 2] == '1' || array[len - 2] == '2' ? 1 : 0;
        } else {
            dp[len - 1] = 1;
            dp[len - 2] = (array[len - 2] - '0') * 10 + array[len - 1] - '0' < 27 ? 2 : 1;
        }

        // 计算其他位置
        for (int i = len - 3; i > -1; i--) {
            if (array[i] == '0') {
                dp[i] = 0;
            } else {
                dp[i] = dp[i + 1];
                if ((array[i] - '0') * 10 + array[i + 1] - '0' < 27) {
                    dp[i] += dp[i + 2];
                }
            }
        }
        return dp[0];
    }

    public static void main(String[] args) {
        int times = 100;
        boolean result = true;
        for (int i = 0; i < times; i++) {
            String str = RandomUtils.genRandomNumberString();
            if (solution(str) != NumberToAlphabet.numTwo(str)) {
                result = false;
                System.out.println("Error, str:" + str);
            }
        }
        if (result) {
            System.out.println("Past");
        }
    }
}
