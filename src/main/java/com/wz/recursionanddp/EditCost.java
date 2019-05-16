/**
 * <p>Title: EditCost</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/11/17</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.recursionanddp;

/**
 * <p>最小编辑代价</p>
 * <p>
 *     给定两个字符串self和other，在给定三个整数ic、dc和rc，分别代表插入、删除和替换一个字符的代价，求出将self编辑成other的最小代价
 *     例如，self="abc"，other="adc"，ic=5，dc=3，rc=2。从"abc"编辑到"adc"，将'b'替换为'd'代价最小，为2。
 *          self="abc"，other="adc"，ic=5，dc=3，rc=10。从"abc"编辑到"adc"，删除'b'，然后插入'd'代价最小，为8。
 *     方案一：
 *        经典动态规划解法。
 *        生成大小为(M+1)*(N+1)的动态规划矩阵dp，dp[i][j]代表将self[0...i-1]编辑成other[0...j-1]的最小代价。
 *        1、dp[0][0]=0，表示self为空的字串编辑成other为空的字串，代价为0
 *        2、矩阵第一列dp[0...M-1][0]，dp[i][0]表示self[0...i-1]编辑成空串的最小代价，即删除self[0...i-1]所有字符，dp[i][0]=dc*i
 *        3、矩阵第一行dp[0][0...N-1]，dp[0][j]表示空串编辑成other[0...j-1]的最小代价，即插入other[0...j-1]所有字符，dp[0][j]=ic*j
 *        4、其他位置按照从左到右、从上到下计算，dp[i][j]的值只可能来自以下四种情况：
 *        4.1、self[0...i-1]删除self[i-1]变成self[0...i-2]，再由self[0...i-2]编辑成other[0...j-1]，dp[i-1][j]表示
 *            self[0...i-2]编辑成other[0...j-1]的最小代价，因此dp[i][j]=dc+dp[i-1][j]
 *        4.2、self[0...i-1]先编辑成other[0...j-2]，再插入other[j-1]变成other[0...j-1]，dp[i][j-1]表示
 *            self[0...i-1]编辑成other[0...j-2]的最小代价，因此dp[i][j]=dp[i][j-1]+ic
 *        4.3、如果self[i-1]!=other[j-1]，先把self[0...i-2]编辑成other[0...j-2]，然后把self[i-1]替换成other[j-1]，
 *            dp[i-1][j-1]表示self[0...i-2]编辑成other[0...j-2]的最小代价，因此dp[i][j]=dp[i-1][j-1]+rc
 *        4.3、如果self[i-1]==other[j-1]，把self[0...i-2]编辑成other[0...j-2]即可，dp[i][j]=dp[i-1][j-1]
 *        以上四种可能的值中，选择最小的值就是dp[i][j]
 *     方案二：
 *        动态规划基础上的空间压缩解法。思想与com.wz.recursionanddp.MinPathSum的方案二类似。
 *        不同之处在于MinPathSum中dp[i][j]依赖两个位置的值dp[i-1][j]和dp[i][j-1]，滚动数组从左到右更新是没问题的，因为在求dp[j]时，
 *        dp[j]没有更新之前相当于dp[i-1][j]的值，dp[j-1]的值又已经更新过相当于dp[i][j-1]的值。
 *        而本题中，dp[i][j]依赖dp[i-1][j]、dp[i][j-1]和dp[i-1][j-1]的值，所以滚动数组从左到右更新时，需要一个变量保存dp[j-1]
 *        没更新之前的值，也就是左上角的dp[i-1][j-1]。
 *        过程中只使用了一个dp数组，但dp长度为other的长度加1，而不是min{M,N}，所以还要把self和other中较短的作为列对应的字符串，较长的
 *        作为行对应的字符串，方案一的动态规划方法都是把other作为列对应的字符串，如果self做了列对应的字符串，把ic和dc交换一个即可。
 * </p>
 * <p>
 *     方案一时间复杂度为O(M*N)、空间复杂度为O(M*N)
 *     方案二时间复杂度为O(M*N)、空间复杂度为O(N)
 * </p>
 *
 * @author wangzi
 */
public class EditCost {
    /**
     * 经典动态规划解法
     */
    public static int minCostOne(String self, String other, int ic, int dc, int rc) {
        if (self == null || other == null) {
            return 0;
        }

        char[] chSelf = self.toCharArray();
        char[] chOther = other.toCharArray();
        int row = chSelf.length + 1;
        int col = chOther.length + 1;
        // 动态规划矩阵
        int[][] dp = new int[row][col];
        // 第一列，把chSelf[0...i-1]编辑成空串的代价，即删除chSelf[0...i-1]所有的字符
        for (int i = 1; i < row; i++) {
            dp[i][0] = dc * i;
        }
        // 第一列，把空串编辑成chOther[0...i-1]的代价，即插入chOther[0...i-1]所有的字符
        for (int j = 1; j < col; j++) {
            dp[0][j] = ic * j;
        }
        // 除第一行、第一列的其他位置
        for (int i = 1; i < row; i++) {
            for (int j = 1; j < col; j++) {
                if (chSelf[i - 1] == chOther[j - 1]) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = dp[i - 1][j - 1] + rc;
                }
                dp[i][j] = Math.min(dp[i][j], dp[i][j - 1] + ic);
                dp[i][j] = Math.min(dp[i][j], dp[i - 1][j] + dc);
            }
        }

        return dp[row - 1][col - 1];
    }

    /**
     * 动态规划基础上的空间压缩解法
     */
    public static int minCostTwo(String self, String other, int ic, int dc, int rc) {
        if (self == null || other == null) {
            return 0;
        }

        char[] chSelf = self.toCharArray();
        char[] chOther = other.toCharArray();
        char[] longs = chSelf.length >= chOther.length ? chSelf : chOther;
        char[] shorts = chSelf.length < chOther.length ? chSelf : chOther;
        // chOther较长就交换ic和dc的值
        if (chSelf.length < chOther.length) {
            int tmp = ic;
            ic = dc;
            dc = tmp;
        }

        int[] dp = new int[shorts.length + 1];
        for (int i = 1; i <= shorts.length; i++) {
            dp[i] = ic * i;
        }
        for (int i = 1; i <= longs.length; i++) {
            // pre表示左上角的值
            int pre = dp[0];
            dp[0] = dc * i;
            for (int j = 1; j <= shorts.length; j++) {
                // 保存dp[j]没更新前的值
                int tmp = dp[j];
                if (longs[j - 1] == shorts[j - 1]) {
                    dp[j] = pre;
                } else {
                    dp[j] = pre + rc;
                }
                dp[j] = Math.min(dp[j], dp[j - 1] + ic);
                dp[j] = Math.min(dp[j], tmp + dc);
                // pre变成dp[j]没更新前的值
                pre = tmp;
            }
        }

        return dp[shorts.length];
    }

    public static void main(String[] args) {
        String self = "abc";
        String other = "adc";
        System.out.println(minCostOne(self, other, 5, 3, 2));
        System.out.println(minCostOne(self, other, 5, 3, 10));
        System.out.println(minCostTwo(self, other, 5, 3, 2));
        System.out.println(minCostTwo(self, other, 5, 3, 10));
    }
}
