/**
 * <p>Title: LongestCommonSubString</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/11/18</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.recursionanddp;

/**
 * <p>最长公共子串</p>
 * <p>
 *     方案一：
 *        求动态规划表：
 *        如果self的长度为M，other的长度为N，生成大小为M*N的矩阵dp，dp[i][j]表示把self[i]与other[j]当作
 *        公共子串最后一个字符的情况下，公共子串最长能达到的长度，从左到右，再从上到下计算矩阵dp。
 *        1、第一列dp[0...M-1][0]，如果self[i]==other[0]，则dp[i][0]=1，否则dp[i][0]=0。
 *        2、第一行dp[0][0...N-1]，与步骤一同理，如果self[0]==other[j]，则dp[0][j]=1，否则dp[0][j]=0。
 *        3、对于其他位置(i,j)，dp[i][j]的值只可能来自以下两种情况：
 *        3.1、如果self[i]!=other[j]，说明将self[i]与other[j]作为公共子串最后一个字符是不可能的，令dp[i][j]=0；
 *        3.2、如果self[i]==other[j]，此时dp[i][j] = dp[i-1][j-1]+1
 *        根据动态规划表逆推决策路径：
 *        遍历动态规划表找到最大值及其位置，该最大值就是最长公共子串的长度，位置就是最长公共子串的结束字符的位置。
 *     方案二：
 *        根据方案一可知计算每个dp[i][j]时，最多只需要左上方的dp[i-1][j-1]的值，
 *        因此可以按照从左上到右下的斜线方向来计算所有的值，只需要一个变量就可以计算出所有位置的值。
 *        整个求解过程如LongestCommonSubString.png所示。
 *        每条斜线在计算之前生成变量len，len表示左上方位置的值，初识时len=0，从斜线左上方的位置开始向右下方依次计算每个位置的值。
 *        假设计算到位置(i,j)，此时len表示位置(i-1,j-1)的值，如果self[i]==other[j]，那么位置(i,j)的值为len+1，否则为0。
 *        计算后将len更新成位置(i,j)的值，然后计算下一个位置(i+1,j+1)，依次计算下去可以得到斜线上每个位置的值，然后计算下一条斜线。
 *        用全局变量max记录所有位置的值中的最大值，最大值出现时，用全局变量end记录其位置即可。
 * </p>
 * <p>
 *     方案一时间复杂度为O(M*N)、空间复杂度为O(M*N)
 *     方案二时间复杂度为O(M*N)、空间复杂度为O(1)
 * </p>
 *
 * @author wangzi
 */
public class LongestCommonSubString {

    public static String lcsOne(String self, String other) {
        if (self == null || other == null || self.length() == 0 || other.length() == 0) {
            return "";
        }

        char[] chSelf = self.toCharArray();
        char[] chOther = other.toCharArray();
        // 生成动态规划矩阵
        int[][] dp = getDp(chSelf, chOther);
        // 最长公共子串的结尾位置
        int end = 0;
        // 最大长度
        int max = 0;
        for (int i = 0; i < chSelf.length; i++) {
            for (int j = 0; j < chOther.length; j++) {
                if (dp[i][j] > max) {
                    end = i;
                    max = dp[i][j];
                }
            }
        }

        return self.substring(end - max + 1, end + 1);
    }

    private static int[][] getDp(char[] self, char[] other) {
        int[][] dp = new int[self.length][other.length];
        // 第一列，直接比较self[i]与other[0]
        for (int i = 0; i < self.length; i++) {
            if (self[i] == other[0]) {
                dp[i][0] = 1;
            }
        }
        // 第一列，直接比较self[0]与other[j]
        for (int j = 1; j < other.length; j++) {
            if (self[0] == other[j]) {
                dp[0][j] = 1;
            }
        }
        // 除第一行、第一列之外的其他位置
        for (int i = 1; i < self.length; i++) {
            for (int j = 1; j < other.length; j++) {
                if (self[i] == other[j]) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                }
            }
        }

        return dp;
    }

    public static String lcsTwo(String self, String other) {
        if (self == null || other == null || self.length() == 0 || other.length() == 0) {
            return "";
        }

        char[] chSelf = self.toCharArray();
        char[] chOther = other.toCharArray();
        // 斜线开始位置的行、列
        int row = 0, col = chOther.length - 1;
        // 最大长度
        int max = 0;
        // 最长公共子串的结尾位置
        int end = 0;
        while (row < chSelf.length) {
            int i = row, j = col, len = 0;
            // 从(i,j)开始向右下方遍历
            while (i < chSelf.length && j < chOther.length) {
                if (chSelf[i] != chOther[j]) {
                    len = 0;
                } else {
                    len++;
                }
                // 记录最大值，以及结束字符串的位置
                if (len > max) {
                    end = i;
                    max = len;
                }
                i++;
                j++;
            }
            if (col > 0) {
                // 斜线开始位置的列先向左移动
                col--;
            } else {
                // 列移动到最左之后，行向下移动
                row++;
            }
        }

        return self.substring(end - max + 1, end + 1);
    }

    public static void main(String[] args) {
        String self = "1AB2345CD";
        String other = "12345EF";
        System.out.println(lcsOne(self, other));
        System.out.println(lcsTwo(self, other));
    }
}
