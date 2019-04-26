/**
 * <p>Title: ParenthesesMatch</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/12/10</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.string;

/**
 * <p>括号字符串的有效性和最长有效长度</p>
 * <p>
 *     问题一：
 *        给定一个字符串str，判断是不是整体有效的括号字符串。
 *        例如"()"返回true，"(()())"返回true，"())"返回false，"()a()"返回false。
 *     问题二：
 *        给定一个括号字符串str，返回最长的有效括号字串长度。
 *     问题一解答：
 *        1、从左到右遍历字符串，判断每一个字符是否是'('或')'，如果不是，直接返回false；
 *        2、遍历到每一个字符时，都检查到目前为止'('和')'的数量，如果')'更多，直接返回false；
 *        3、遍历后检查'('和')'的数量是否相等，相等则返回true，否则返回false。
 *     问题二解答：
 *        用动态规划求解，可做到时间复杂度为O(N)，空间复杂度为O(N)。
 *        生成长度与字符串长度一样的数组dp[]，dp[i]表示str[0...i]中必须以字符str[i]结尾的最长有效括号字串长度。
 *        1、dp[0]=0，只含有一个字符肯定不是有效的括号字符串
 *        2、从左到右遍历str[1...N-1]，假设当前遍历到str[i]
 *        2.1、如果str[i]=='('，有效括号字符串必然是以')'结尾，所以dp[i]=0
 *        2.2、如果str[i]==')'，那么以str[i]尾款的最长有效括号子串可能存在。dp[i-1]表示必须以str[i-1]结尾的最长有效括号子串长度，
 *             所以如果i-dp[i-1]-1位置上的字符是'('，就能够与当前位置的str[i]再匹配出一对有效括号，
 *             如"(()())"，假设遍历到最后一个')'，以倒数第二个字符结尾的最长有效括号字串是"()()"，
 *             找到这个子串之前的字符，即i-dp[i-1]-1位置的字符，发现是'('，可以与当前')'再匹配出一对有效括号，此时dp[i]=dp[i-1]+2；
 *             另外，还有一种情况，如"()(())"，假设遍历到最后一个')'，通过上面的过程找到的最长有效括号子串为"(())"，但是前面还有"()"，
 *             可以与"(())"结合一起形成更长的有效括号子串，即应该把以str[i-dp[i-1]-2]结尾的最长有效括号子串接到前面，
 *             也就是把dp[i-dp[i-1]-2]的值加到dp[i]中。
 *        3、dp[0...N-1]中的最大值就是最终的结果。
 * </p>
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
