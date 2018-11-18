/**
 * <p>Title: LongestCommonSubSequence</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/11/18</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.recursionanddynamicprogramming;

/**
 * <p>最长公共子序列</p>
 * <p>
 *     经典的动态规划问题。先求出动态规划表，然后逆推决策路径。
 *     求动态规划表：
 *        如果self的长度为M，other的长度为N，生成大小为M*N的矩阵result，result[i][j]表示self[0...i]与other[0...j]的
 *        最长公共子序列的长度，从左到右，再从上到下计算矩阵result。
 *        1、第一列result[0...M-1][0]，result[i][0]的含义是self[0...i]与other[0]的最长公共子序列长度，other[0]只有一个字符，
 *           因此result[i][0]最大为1，如果self[i]==other[0]，则result[i][0]=1，且之后的result[i+1...M-1][0]都为1。
 *        2、第一行result[0][0...N-1]，与步骤一同理，如果self[0]==other[j]，则result[0][j]=1，且result[0][j+1...N-1]都为1。
 *        3、对于其他位置(i,j)，result[i][j]的值只可能来自以下三种情况：
 *        3.1、result[i-1][j]，即self[0...i-1]与other[0...j]的最长公共子序列长度；
 *        3.2、result[i][j-1]，即self[0...i]与other[0...j-1]的最长公共子序列长度；
 *        3.3、如果self[i]==other[j]，则此时最大长度可能为result[i-1][j-1]+1
 *        这三个值中，选最大的作为result[i][j]的值。
 *     矩阵中最右下角的值代表self与other整体的最长公共子序列长度，通过矩阵状态，可以得到最长公共子序列。
 *     1、从矩阵右下角开始，有三种移动方式：向上、向左、向左上，假设移动过程中，i代表行数，j代表列数，ch代表最长公共子序列
 *     2、如果result[i][j]大于result[i-1][j]和result[i][j-1]，说明之前的决策中选择的是result[i][j]+1，即self[i]==other[j]，
 *        这个字符一定属于最长公共子序列，将这个字符放入ch，然后向左上方移动
 *     3、如果result[i][j]等于result[i-1][j]，说明之前的决策中result[i][j]+1不是必选的决策，向上方移动即可
 *     4、如果result[i][j]等于result[i][j-1]，与步骤三同理，向左方移动即可
 *     5、如果result[i][j]同时等于result[i-1][j]和result[i][j-1]，向上还是向左移动都行，反正不会错过必须选择的字符。
 * </p>
 * <p>
 *     计算动态规划矩阵的时间复杂度为O(M*N)，空间复杂度为O(M*N)，通过动态规划矩阵逆推决策路径时间复杂度为O(M+N)
 *     所以，总的时间复杂度为O(M*N)，空间复杂度为O(M*N)
 * </p>
 *
 * @author wangzi
 */
public class LongestCommonSubSequence {

    public static String lcs(String self, String other) {
        if (self == null || other == null || self.length() == 0 || other.length() == 0) {
            return "";
        }

        char[] chSelf = self.toCharArray();
        char[] chOther = other.toCharArray();
        // 生成动态规划表
        int[][] result = getResult(chSelf, chOther);

        int m = chSelf.length - 1;
        int n = chOther.length - 1;
        char[] chResult = new char[result[m][n]];
        int index = chResult.length - 1;
        // 根据动态规划表逆推决策路径
        while (index >= 0) {
            if (n > 0 && result[m][n] == result[m][n - 1]) {
                n--;
            } else if (m > 0 && result[m][n] == result[m - 1][n]) {
                m--;
            } else {
                chResult[index--] = chSelf[m];
                m--;
                n--;
            }
        }
        return String.valueOf(chResult);
    }

    private static int[][] getResult(char[] self, char[] other) {
        int[][] result = new int[self.length][other.length];
        result[0][0] = self[0] == other[0] ? 1 : 0;
        // 第一列，比较self[i]与other[0]即可
        for (int i = 1; i < self.length; i++) {
            result[i][0] = Math.max(result[i - 1][0], self[i] == other[0] ? 1 : 0);
        }
        // 第一行，比较other[i]与self[0]即可
        for (int j = 1; j < other.length; j++) {
            result[0][j] = Math.max(result[0][j - 1], self[0] == other[j] ? 1 : 0);
        }
        // 除第一行、第一列之外的位置
        for (int i = 1; i < self.length; i++) {
            for (int j = 1; j < other.length; j++) {
                result[i][j] = Math.max(result[i - 1][j], result[i][j - 1]);
                if (self[i] == other[j]) {
                    result[i][j] = Math.max(result[i][j], result[i - 1][j - 1] + 1);
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        String self = "1A2C3D4B56";
        String other = "B1D23CA45B6A";
        System.out.println(lcs(self, other));
    }
}
