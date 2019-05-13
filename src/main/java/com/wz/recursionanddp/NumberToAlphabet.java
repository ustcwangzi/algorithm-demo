/**
 * <p>Title: NumberToAlphabet</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/11/22</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.recursionanddp;

/**
 * <p>数字字符串转换为字符组合的种数</p>
 * <p>
 *     给定一个字符串str，str全部由数字字符组成，如果str中某一个或某相邻两个字符组成的子串值在1～26之间，则这个子串可以转换成一个字母。
 *     规定"1"转换成"A"，'2'转换成"B"，"3"转换成"C" ... "26"转换成"Z"。求str有多少中不同的转换结果。
 *     例如，str = "1111"，能转换出的结果有"AAAA"，"LAA"，"ALA"，"AAL"和"LL"，故返回5。
 *          str = "01"，"0"没有对应字母，而"01"不可转换，返回0。
 *          str = "10"，能转换出的结果是"J"，返回1。
 *     方案一：
 *        暴力递归求解。
 *        假设str的长度是N，定义递归函数p(i)(0<=i<=N)，p(i)表示str[0…i-1]已经转换完毕，而str[i…N-1]还没有转换完成的情况下，
 *        剩下的未转换完的字符有多少种转换结果。例如，对于str="1123"，p(2)表示str[0…1]="11"已经转换完成，具体是什么结果我们不必关心，
 *        没转换完的部分时str[2..3]="23"，那么可以转换成"BC"和"W"两种情况，所以p(2)=2。p(i)计算：
 *        1、若i==N，p(N)表示整个字符串str[0…N-1]已经转换完成，没有后续的字符串可以进行转换了，p(N)=1
 *        2、如果当前字符str[i] == '0'。str[0…i-1]转换完成，此时又以'0'开头，这时候str[i…N]不可能合法转化，直接返回0
 *        3、如果不满足条件1和2，说明此时str[i]在'1'到'9'之间，这个时候str[i]能转换的种数至少包含p(i+1)。
 *          如果str[i]和str[i+1]的组合又在'10'到'26'之间,则str[i]能转换的种数还要包含p(i+2)，即p(i)=p(i+1)或者p(i)=p(i+1)+p(i+2)
 *     方案二：
 *        动态规划解法。
 *        根据方案一可知p(i)最多依赖于p(i+1)和p(i+2)，因此可以先计算p(i+1)和p(i+2)，并将结果保存到一个数组dp中，然后再计算p(i)的值。
 *     方案三：
 *        空间压缩后的动态规划解法。
 *        根据方案二可知dp[i]依赖于dp[i]和dp[i+1]，每次计算时最多只需要dp[i]和dp[i+1]这两个变量，
 *        因此我们只需要记录每一步需要用的dp[i]和dp[i+1]就可以了，这样可以优化方案二的空间复杂度。
 * </p>
 * <p>
 *     方案一有两个递归的分支p(i+1)和p(i+2)，一共有N层递归，所以时间复杂度时O(2^N)，额外空间复杂度是递归使用的函数栈，大小为O(N)
 *     方案二时间复杂度时O(N)，空间复杂度为O(N)
 *     方案三时间复杂度时O(N)，空间复杂度为O(1)
 * </p>
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
        String str = "7812317831610182310";
        System.out.println(numOne(str));
        System.out.println(numTwo(str));
        System.out.println(numThree(str));
    }
}
