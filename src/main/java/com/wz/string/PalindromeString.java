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
 * <p>
 *     问题一：
 *        给定一个字符串str，可以在str的任意位置添加字符，在添加字符最少的情况下，让str变成回文字符串。
 *     问题二：
 *        给定一个字符串str，在给定str的最长回文子序列字符串lps，在添加字符最少的情况下，让str变成回文字符串。
 *     问题一解答：
 *        动态规划方法，生成一个N*N的矩阵dp[][]，dp[i][j]代表子串str[i...j]最少添加几个字符可以使得str[i...j]变为回文串。
 *        求出动态规划矩阵：
 *        1、如果str[i...j]只有一个字符，那必然已经是回文了，不必添加任何字符
 *        2、如果str[i...j]只有两个字符
 *        2.1、若两个字符相等，那么不需要添加任何字符(即dp[i][j]=0)，比如"AA"；
 *        2.2、若两个字符不相等，那么需要添加一个字符(即dp[i][j]=1)，比如"AB"，可添加'A'变成"ABA"，或添加'B'变成"BAB"
 *        3、如果str[i...j]多于两个字符
 *        3.1、若str[i] == str[j]，那么dp[i][j]=dp[i+1][j-1]，比如"A123A"与"123"变成回文所需要添加的字符数是相等的
 *        3.2、若str[i] != str[j]，那么有两种方法可使str[i...j]变为回文，一种是先让str[i...j-1]变成回文，然后在左侧加上str[j]；
 *             另一种是先让str[i+1...j]变成回文，然后在右侧加上str[i]；选择代价最小的，即min{dp[i][j-1], dp[i+1][j]} + 1
 *        结果字符串长度为dp[0][N-1] + str.length，根据动态规划矩阵让str变成回文字符串：
 *        1、如果str[i] == str[j]，那么result = str[i] + (str[i+1...j-1]的回文结果) + str[j]
 *        2、如果str[i] != str[j]
 *        2.1、dp[i][j-1] < dp[i+1][j]，那么result = str[j] + (str[i...j-1]的回文结果) + str[j]
 *        2.1、dp[i][j-1] >= dp[i+1][j]，那么result = str[i] + (str[i+1...j]的回文结果) + str[i]
 *     问题二解答：
 *        "剥洋葱"解法，以str = "A1B21C"，lps="121"为例，说明该过程。
 *        若str长度为N，lps长度为M，则最终结果长度为2*N-M。
 *        第0层由lps[0]和lps[M-1]组成，即"1...1"，从str最左侧开始找'1'，发现左侧第0层洋葱圈外部分为"A"，记为leftPart；
 *        从str最右侧开始找'1'，发现右侧第0层洋葱圈外部分为"C"，记为rightPart。把(leftPart + rightPart的逆序)复制到result左侧，
 *        把(rightPart + leftPart的逆序)复制到result右侧，即"AC...CA"，再把洋葱第0层复制到result，即"AC1...1CA"。
 *        第1层由lps[1]和lps[M-2]组成，即"2...2"，重复以上过程，leftPart为"B"，rightPart为""，result变为"AC1B2B1CA"
 * </p>
 * <p>
 *     问题一时间复杂度为O(N*N)，问题二时间复杂度为O(N)
 * </p>
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
                // array[i] + (array[i+1...j-1]的回文结果) + array[j]
                result[left++] = array[i++];
                result[right--] = array[j--];
            } else if (dp[i][j - 1] < dp[i + 1][j]) {
                // array[j] + (array[i...j-1]的回文结果) + array[j]
                result[left++] = array[j];
                result[right--] = array[j--];
            } else {
                // array[i] + (array[i+1...j]的回文结果) + array[i]
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
            // (leftPart + rightPart的逆序)加到左侧，(rightPart + leftPart的逆序)加到右侧，以达到对称
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
