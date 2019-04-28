/**
 * <p>Title: PalindromeMinCut</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/12/16</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.string;

/**
 * <p>回文最小分割数</p>
 * <p>
 *     给定一个字符串str，求出把str全部切成回文子串的最小分割数。
 *     例如：
 *     str="ABA"，本身就是回文串，返回0；
 *     str="ACDCDCDAD"，至少要切割2次变成："A"、"CDCDC"和"DAD"，返回2。
 *     解决方案：
 *        典型的动态规划问题，定义动态规划数组dp，dp[i]表示子串str[i...len-1]至少要切割几次，才能把str[i...len-1]全部切成回文子串。
 *        从右到左依次计算dp[i]，i初始为len-1，计算过程如下：
 *        1、假设j位于i与len-1之间(即i<=j<len)，如果str[i...j]是回文串，那么dp[i]可能是dp[j+1]+1，因为在str[i...len-1]上，
 *           除去str[i...j]是回文，剩下部分str[j+1...len-1]需要继续做切割，而dp[j+1]含义就是str[j+1...len-1]的最小回文分割数
 *        2、根据步骤1，让j在i到len-1上枚举，其中的最小值就是dp[i]的值，即dp[i]=Min{dp[j+1]+1}，(i<=j<len，且str[i...j]是回文)
 *        3、如何快速判断str[i...j]是否是回文，定义一个二维数组boolean[][] p，如果p[i][j]为true则str[i...j]是回文，否则不是
 *           在计算dp数组的过程中，可同步计算矩阵p。p[i][j]如果为true，一定来自以下三种情况：
 *           str[i...j]由1个字符组成；
 *           str[i...j]由2个字符组成，并且2个字符相等；
 *           str[i+1...j-1]是回文串，即p[i+1][j-1]为true，并且str[i]==str[j]，即str[i...j]上首尾两个字符相等。
 *           计算dp过程中，i是从右到左依次计算的，而对于每个i，又依次从i位置向右枚举所有的j(i<=j<len)，依次决策出dp[i]，
 *           所以对于每个p[i][j]来说，p[i+1][j-1]一定是计算过的，这使得判断一个子串是否为回文子串变得极为方便
 *        4、最终dp[0]就是最后的结果。
 * </p>
 *
 * @author wangzi
 */
public class PalindromeMinCut {

    public static int minCut(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }

        char[] array = str.toCharArray();
        int len = array.length;
        // 动态规划数组，dp[i]表示str[i...len-1]至少要切割几次才能全部切成回文子串
        int[] dp = new int[len + 1];
        dp[len] = -1;
        boolean[][] isPalindrome = new boolean[len][len];
        // 从右向左依次计算dp[i]
        for (int i = len - 1; i >= 0; i--) {
            dp[i] = Integer.MAX_VALUE;
            // 对于每个i，从i开始向右枚举所有位置来计算出dp[i]
            for (int j = i; j < len; j++) {
                // 如果str[i...j]是回文子串，那么dp[i]可能是dp[j+1]+1
                if (array[i] == array[j] && (j - i < 2 || isPalindrome[i + 1][j - 1])) {
                    isPalindrome[i][j] = true;
                    dp[i] = Math.min(dp[i], dp[j + 1] + 1);
                }
            }
        }

        return dp[0];
    }

    public static void main(String[] args) {
        System.out.println(minCut("ABA"));
        System.out.println(minCut("ACDCDCDAD"));
    }
}
