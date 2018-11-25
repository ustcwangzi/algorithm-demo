/**
 * <p>Title: StringCross</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/11/17</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.recursionanddp;

/**
 * <p>字符串的交错组成</p>
 * <p>
 *     给定三个字符串：self、other、aim，如果aim包含且仅包含来自self与other的所有字符，
 *     而且在aim中属于self的字符之间保持原来在self中的顺序，属于other的字符之间保持原来在other中的顺序，称aim是self与other交错组成的。
 *     例如，self="AB"，other="12"，那么"AB12"、"A1B2"、"1A2B"等都是self与other的交错组成。
 *     方案一：
 *        经典的动态规划解法。
 *        生成大小为(M+1)*(N+1)的动态规划矩阵dp，dp[i][j]代表aim[0...i+j-1]能否被self[0...i-1]和other[0...j-1]交错组成。
 *        1、dp[0][0]=true，因为aim为空串时，可以被self为空串和other为空串交错组成
 *        2、第一列dp[0...M-1][0]，dp[i][0]代表aim[0...i-1]能否只被self[0...i-1]交错组成，比较aim[0...i-1]与self[0...i-1]即可
 *        3、第一列dp[0][0...N-1]，dp[0][j]代表aim[0...j-1]能否只被other[0...j-1]交错组成，比较aim[0...j-1]与other[0...j-1]即可
 *        4、其他位置(i,j)，dp[i][j]的值由以下三种情况决定：
 *        4.1、dp[i-1][j]代表aim[0...i+j-2]能否被self[0...i-2]和other[0...j-1]交错组成，如果可以，那么再有self[i-1]=aim[i+j-1]，
 *            说明self[i-1]可以作为交错组成aim[0...i+j-1]的最后一个字符。此时令dp[i][j]=true
 *        4.2、dp[1][j-1]代表aim[0...i+j-2]能否被self[0...i-1]和other[0...j-2]交错组成，如果可以，那么再有other[j-1]=aim[i+j-1]，
 *            说明other[j-1]可以作为交错组成aim[0...i+j-1]的最后一个字符。此时令dp[i][j]=true
 *        4.3、其他情况下，令dp[i][j]=false
 *     方案二：
 *        动态规划基础上的空间压缩解法。思想与com.wz.recursionanddp.MinPathSum类似。
 *        实际进行空间压缩的时候，选择长度较小的那个作为列对应的字符串，然后生成和较短字符串长度一样的一维数组dp，滚动更新即可。
 * </p>
 * <p>
 *     方案一时间复杂度为O(M*N)，空间复杂度为O(M*N)
 *     方案二时间复杂度为O(M*N)，空间复杂度为O(min{M, N})
 * </p>
 *
 * @author wangzi
 */
public class StringCross {
    /**
     * 经典动态规划解法
     */
    public static boolean isCrossOne(String self, String other, String aim) {
        if (self == null || other == null || aim == null) {
            return false;
        }

        char[] chSelf = self.toCharArray();
        char[] chOther = other.toCharArray();
        char[] chAim = aim.toCharArray();
        if (chAim.length != chSelf.length + chOther.length) {
            return false;
        }

        // 动态规划矩阵
        boolean[][] dp = new boolean[chSelf.length + 1][chOther.length + 1];
        dp[0][0] = true;
        // 第一列，只能使用chSelf[0...i-1]
        for (int i = 1; i <= chSelf.length; i++) {
            if (chSelf[i - 1] != chAim[i - 1]) {
                break;
            }
            dp[i][0] = true;
        }
        // 第一行，只能使用chOther[0...i-1]
        for (int j = 1; j <= chOther.length; j++) {
            if (chOther[j - 1] != chAim[j - 1]) {
                break;
            }
            dp[0][j] = true;
        }
        // 除第一列、第一行之外的其他位置
        for (int i = 1; i <= chSelf.length; i++) {
            for (int j = 1; j <= chOther.length; j++) {
                if ((chSelf[i - 1] == chAim[i + j - 1] && dp[i - 1][j]) ||
                        (chOther[j - 1] == chAim[i + j - 1] && dp[i][j - 1])) {
                    dp[i][j] = true;
                }
            }
        }

        return dp[chSelf.length][chOther.length];
    }

    /**
     * 动态规划基础上的空间压缩解法
     */
    public static boolean isCrossTwo(String self, String other, String aim) {
        if (self == null || other == null || aim == null) {
            return false;
        }

        char[] chSelf = self.toCharArray();
        char[] chOther = other.toCharArray();
        char[] chAim = aim.toCharArray();
        if (chAim.length != chSelf.length + chOther.length) {
            return false;
        }

        char[] longs = chSelf.length >= chOther.length ? chSelf : chOther;
        char[] shorts = chSelf.length < chOther.length ? chSelf : chOther;
        // 长度较小的最为列对应的字符串
        boolean[] dp = new boolean[shorts.length + 1];
        dp[0] = true;

        for (int i = 1; i <= shorts.length; i++) {
            if (shorts[i - 1] != chAim[i - 1]) {
                break;
            }
            dp[i] = true;
        }

        // 滚动更新
        for (int i = 1; i <= longs.length; i++) {
            dp[0] = dp[0] && longs[i - 1] == chAim[i - 1];
            for (int j = 1; j <= shorts.length; j++) {
                if ((longs[i - 1] == chAim[i + j - 1] && dp[j]) ||
                        (shorts[j - 1] == chAim[i + j - 1] && dp[j - 1])) {
                    dp[j] = true;
                }
            }
        }

        return dp[shorts.length];
    }

    public static void main(String[] args) {
        String self = "1234";
        String other = "abcd";
        String aim = "1a23bcd4";
        System.out.println(isCrossOne(self, other, aim));
        System.out.println(isCrossTwo(self, other, aim));
    }
}
