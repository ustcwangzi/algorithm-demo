package com.wz;

import com.wz.recursionanddp.StringCross;

/**
 * <p>判断target是否是由self与other交错组成的且字符的相对位置不变</p>
 *
 * @author wangzi
 */
public class StringCrossTest {
    private static boolean solution(String self, String other, String target) {
        if (target.length() != self.length() + other.length()) {
            return false;
        }
        char[] selfArray = self.toCharArray();
        char[] otherArray = other.toCharArray();
        char[] targetArray = target.toCharArray();
        int m = selfArray.length, n = otherArray.length;

        // dp[i][j]表示target[0...i+j-1]是否由self[0...i-1]与other[0...j-1]交错组成
        boolean[][] dp = new boolean[m + 1][n + 1];
        // 空字符
        dp[0][0] = true;

        // 第一列，只能由self[0...M-1]组成
        for (int i = 1; i <= m; i++) {
            if (selfArray[i - 1] != targetArray[i - 1]) {
                break;
            }
            dp[i][0] = true;
        }
        // 第一行，只能由other[0...N-1]组成
        for (int j = 1; j <= n; j++) {
            if (otherArray[j - 1] != targetArray[j - 1]) {
                break;
            }
            dp[0][j] = true;
        }

        // dp[i-1][j]为true说明target[0...i+j-2]是由self[0...i-2]与other[0...j-1]交错组成，
        // 那么再有self[i-1]==target[i+j-1]，则self[i-1]就可以作为交错组成target[0...i+j-1]的最后一个字符
        // 同理dp[i][j-1]为true说明target[0...i+j-2]是由self[0...i-1]与other[0...j-2]交错组成，
        // 那么再有other[j-1]==target[i+j-1]，则other[j-1]就可以作为交错组成target[0...i+j-1]的最后一个字符
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if ((dp[i - 1][j] && selfArray[i - 1] == targetArray[i + j - 1]) ||
                        (dp[i][j - 1] && otherArray[j - 1] == targetArray[i + j - 1])) {
                    dp[i][j] = true;
                }
            }
        }
        return dp[m][n];
    }

    public static void main(String[] args) {
        int times = 100;
        boolean result = true;
        for (int i = 0; i < times; i++) {
            String self = RandomUtils.genRandomString();
            String other = RandomUtils.genRandomString();
            String target = RandomUtils.shuffle(self + other);
            if (solution(self, other, target) != StringCross.isCrossOne(self, other, target)) {
                result = false;
                System.out.println("Error, self:" + self + ", target:" + target);
            }
        }
        if (result) {
            System.out.println("Past");
        }
    }
}
