/**
 * <p>Title: PalindromeString</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/12/9</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.string;

/**
 * <p>添加最少字符使字符串变成回文字符串</p>
 *
 * @author wangzi
 */
public class PalindromeString {

    public static String getPalindromeOne(String str) {
        if (str == null || str.length() < 2) {
            return str;
        }
        char[] array = str.toCharArray();
        // 动态规划矩阵
        int[][] dp = getDp(array);
        char[] result = new char[array.length + dp[0][array.length - 1]];
        int i = 0, j = array.length - 1;
        int left = 0, right = result.length - 1;
        while (i <= j) {
            if (array[i] == array[j]) {
                // array[i] + array[i+1...j-1] + array[j]
                result[left++] = array[i++];
                result[right--] = array[j--];
            } else if (dp[i][j - 1] < dp[i + 1][j]) {
                // array[j] + array[i...j-1] + array[j]
                result[left++] = array[j];
                result[right--] = array[j--];
            } else {
                // array[i] + array[i+1...j] + array[i]
                result[left++] = array[i];
                result[right--] = array[i++];
            }
        }

        return String.valueOf(result);
    }

    private static int[][] getDp(char[] array) {
        int[][] dp = new int[array.length][array.length];
        for (int j = 1; j < array.length; j++) {
            dp[j - 1][j] = array[j - 1] == array[j] ? 0 : 1;
            for (int i = j - 2; i > -1; i--) {
                if (array[i] == array[j]) {
                    dp[i][j] = dp[i + 1][j - 1];
                } else {
                    dp[i][j] = Math.min(dp[i + 1][j], dp[i][j - 1]) + 1;
                }
            }
        }

        return dp;
    }

    public static String getPalindromeTwo(String str, String lps) {
        if (str == null || str.length() == 0) {
            return str;
        }
        char[] array = str.toCharArray();
        char[] lpsArray = lps.toCharArray();
        char[] result = new char[2 * array.length - lpsArray.length];
        int arrayLeft = 0, arrayRight = array.length - 1;
        int lpsLeft = 0, lpsRight = lpsArray.length - 1;
        int resultLeft = 0, resultRight = result.length - 1;
        int leftStart = 0, rightEnd = 0;
        while (lpsLeft <= lpsRight) {
            leftStart = arrayLeft;
            rightEnd = arrayRight;
            // 左侧外圈
            while (array[arrayLeft] != lpsArray[lpsLeft]) {
                arrayLeft++;
            }
            // 右侧外圈
            while (array[arrayRight] != lpsArray[lpsRight]) {
                arrayRight--;
            }
            // 右侧部分逆序加到左侧，左侧部分逆序加到右侧，以达到对称的
            fillResult(result, resultLeft, resultRight, array, leftStart, arrayLeft, arrayRight, rightEnd);

            resultLeft += arrayLeft - leftStart + rightEnd - arrayRight;
            resultRight -= arrayLeft - leftStart + rightEnd - arrayRight;
            result[resultLeft++] = array[arrayLeft++];
            result[resultRight--] = array[arrayRight--];
            lpsLeft++;
            lpsRight--;
        }

        return String.valueOf(result);
    }

    private static void fillResult(char[] result, int left, int right, char[] array, int leftStart, int leftEnd,
                                   int rightStart, int rightEnd) {
        for (int i = leftStart; i < leftEnd; i++) {
            result[left++] = array[i];
            result[right--] = array[i];
        }
        for (int i = rightEnd; i > rightStart; i--) {
            result[left++] = array[i];
            result[right--] = array[i];
        }
    }

    public static void main(String[] args) {
        String str = "A1B21C";
        System.out.println(getPalindromeOne(str));
        System.out.println(getPalindromeTwo(str, "121"));
    }
}
