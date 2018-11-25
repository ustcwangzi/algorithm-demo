/**
 * <p>Title: CoinsMin</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/11/12</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.recursionanddp;

/**
 * <p>换钱的最小货币数</p>
 * <p>
 *     问题一：给定数组array，数组中所有值都为正数且不重复，每个代表一种面额的货币，每种货币可使用任意张，给定整数aim，求组成aim的最少货币数
 *     方案一：
 *        生成行数为N、列数为aim+1的动态规划表result，dp[i][j]代表在任意使用array[i][j]货币的情况下，组成j所需的最小货币张数，计算：
 *        1、dp[0...N][0]的值，即矩阵第一列的值，表示aim为0时需要的最小张数，不需要任何货币，全设为0即可
 *        2、dp[0][0...aim]的值，即矩阵第一行的值，表示只能使用array[0]货币时组成j所需的最小张数。比如array[0]=2，
 *           那么能组成的aim为2,4,6,...所以令result[0][2]=1，dp[0][4]=2，...其余设为整数的最大值，记为MAX
 *        3、剩余的位置依次从左到右，再从上到下计算。假设计算到位置result[i][j]，dp[i][j]的值可能来自下面的情况：
 *           完全不使用当前货币array[i]下的最小张数，即result[i-1][j]
 *           只使用1张当前货币array[i]下的最小张数，即result[i-1][j-array[i]]+1
 *           只使用2张当前货币array[i]下的最小张数，即result[i-1][j-2*array[i]]+2
 *            ... ...
 *           只使用k张当前货币array[i]下的最小张数，即result[i-1][j-k*array[i]]+k
 *           所有情况下，最终取张数最小的：
 *           dp[i][j] = min{dp[i-1][j-k*array[i]]+k} (0<=k)
 *                        = min{dp[i-1][j], min{dp[i-1][j-x*array[i]]+x}} (1<=x)
 *                        = min{dp[i-1][j], min{dp[i-1][j-array[i]-y*array[i]]+y+1}} (0<=y)
 *           又有 min{dp[i-1][j-array[i]-y*array[i]]+y} (0<=y) = dp[i][j-array[i]]，因此
 *           dp[i][j] = min{dp[i-1][j], dp[i][j-array[i]]+1}
 *           如果j-array[i]<0，即发生越界，说明array[i]太大，用一张都会超过j，令result[i][j] = dp[i-1][j]即可
 *     方案二：
 *        在方案一的基础上使用空间压缩方法，思想与com.wz.recursionanddp.MinPathSum的方案二类似。
 *        生成一个aim+1的一维数组，然后按行更新即可。之所以不选择按列更新，因为根据
 *        dp[i][j] = min{dp[i-1][j], dp[i][j-array[i]]+1}克制，位置(i,j)同时依赖(i-1,j)与(i,j-array[i])
 *     问题二：给定数组array，数组中所有值都为正数且不重复，每个仅代表一张面额的货币，给定整数aim，求组成aim的最少货币数
 *     eg. array = {2, 3, 5}，aim=10
 *        方案一生成的矩阵为 0 M 1 M 2 M 3 M 4 M 5
 *                        0 M 1 1 2 2 2 3 3 3 4
 *                        0 M 1 1 2 1 2 2 2 3 2
 *        方案二过程数据为 [0, M, 1, M, 2, M, 3, M, 4, M, 5]
 *                      [0, M, 1, 1, 2, 2, 2, 3, 3, 3, 4]
 *                      [0, M, 1, 1, 2, 1, 2, 2, 2, 3, 2]
 *     方案一：
 *        生成行数为N、列数为aim+1的动态规划表result，dp[i][j]代表在任意使用array[i][j]货币的情况下，组成j所需的最小货币张数，计算：
 *        1、dp[0...N][0]的值，即矩阵第一列的值，表示aim为0时需要的最小张数，不需要任何货币，全设为0即可
 *        2、dp[0][0...aim]的值，即矩阵第一行的值，表示只能使用一张array[0]货币时组成j所需的最小张数。比如array[0]=2，
 *           令result[0][2]=1，因为只能使用一张，其余设为整数的最大值，记为MAX
 *        3、剩余的位置依次从左到右，再从上到下计算。假设计算到位置result[i][j]，dp[i][j]的值可能来自下面的情况：
 *           不使用当前货币array[i]下的最小张数，即result[i-1][j]
 *           因为每个货币只能使用一张，考虑result[i-1][j-array[i]]的值，这个值代表任意使用array[0...i-1]货币情况下，
 *           组成j-array[i]所需的最小张数，从钱数为j-array[i]到钱数j，只要加上这张array[i]即可，即result[i-1][j-array[i]]+1
 *           所有情况下，最终取张数最小的：
 *           dp[i][j] = min{dp[i-1][j], dp[i-1][j-array[i]]+1}
 *           如果j-array[i]<0，即发生越界，说明array[i]太大，用一张都会超过j，令result[i][j] = dp[i-1][j]即可
 *     方案二：
 *        与问题一类似，在方案一的基础上使用空间压缩方法。生成一个aim+1的一维数组，然后按行更新即可。
 *     eg. array = {2, 3, 5}，aim=10
 *        方案一生成的矩阵为 0 M 1 M M M M M M M M
 *                        0 M 1 1 M 2 M M M M M
 *                        0 M 1 1 M 1 M 2 2 M 3
 *        方案二过程数据为 [0, M, 1, M, M, M, M, M, M, M, M]
 *                      [0, M, 1, 1, M, 2, M, M, M, M, M]
 *                      [0, M, 1, 1, M, 1, M, 2, 2, M, 3]
 * </p>
 * <p>
 *     问题一中，方案一时间复杂度为O(N*aim)，空间复杂度为O(N*aim)；方案二时间复杂度为O(N*aim)，空间复杂度为O(aim)
 *     问题二中，方案一时间复杂度为O(N*aim)，空间复杂度为O(N*aim)；方案二时间复杂度为O(N*aim)，空间复杂度为O(aim)
 * </p>
 *
 * @author wangzi
 */
public class CoinsMin {
    private static final int MAX = Integer.MAX_VALUE;

    /**
     * 经典动态规划解法
     */
    public static int minCoinsOne(int[] array, int aim) {
        if (array == null || array.length == 0 || aim < 0) {
            return -1;
        }

        // 动态规划矩阵
        int[][] dp = new int[array.length][aim + 1];
        for (int j = 1; j <= aim; j++) {
            dp[0][j] = MAX;
            // 第一行只能使用货币array[0]
            if (j - array[0] >= 0 && dp[0][j - array[0]] != MAX) {
                dp[0][j] = dp[0][j - array[0]] + 1;
            }
        }

        // 左边某个位置的值
        int left;
        for (int i = 1; i < array.length; i++) {
            for (int j = 1; j <= aim; j++) {
                left = MAX;
                if (j - array[i] >= 0 && dp[i][j - array[i]] != MAX) {
                    left = dp[i][j - array[i]] + 1;
                }
                dp[i][j] = Math.min(left, dp[i - 1][j]);
            }
        }

        return dp[array.length - 1][aim] != MAX ? dp[array.length - 1][aim] : -1;
    }

    /**
     * 动态规划基础上的空间压缩解法
     */
    public static int minCoinsTwo(int[] array, int aim) {
        if (array == null || array.length == 0 || aim < 0) {
            return -1;
        }

        int dp[] = new int[aim + 1];
        for (int j = 1; j <= aim; j++) {
            dp[j] = MAX;
            // 初始化只能使用货币array[0]
            if (j - array[0] >= 0 && dp[j - array[0]] != MAX) {
                dp[j] = dp[j - array[0]] + 1;
            }
        }

        // 左边某个位置的值
        int left;
        // 按行更新result
        for (int i = 1; i < array.length; i++) {
            for (int j = 1; j <= aim; j++) {
                left = MAX;
                if (j - array[i] >= 0 && dp[j - array[i]] != MAX) {
                    left = dp[j - array[i]] + 1;
                }
                dp[j] = Math.min(left, dp[j]);
            }
        }

        return dp[aim] != MAX ? dp[aim] : -1;
    }

    public static int uniMinCoinsOne(int[] array, int aim) {
        if (array == null || array.length == 0 || aim < 0) {
            return -1;
        }

        int[][] dp = new int[array.length][aim + 1];
        // 第一行只能使用一张array[0]，只有result[0][array[0]]=1，其他均为MAX
        for (int j = 1; j <= aim; j++) {
            dp[0][j] = MAX;
        }
        if (array[0] <= aim) {
            dp[0][array[0]] = 1;
        }

        // 左上角某个位置的值
        int leftUp;
        for (int i = 1; i < array.length; i++) {
            for (int j = 1; j <= aim; j++) {
                leftUp = MAX;
                if (j - array[i] >= 0 && dp[i - 1][j - array[i]] != MAX) {
                    leftUp = dp[i - 1][j - array[i]] + 1;
                }
                dp[i][j] = Math.min(leftUp, dp[i - 1][j]);
            }
        }

        return dp[array.length - 1][aim] != MAX ? dp[array.length - 1][aim] : -1;
    }

    public static int uniMinCoinsTwo(int[] array, int aim) {
        if (array == null || array.length == 0 || aim < 0) {
            return -1;
        }

        int[] dp = new int[aim + 1];
        // 初始化只能使用一张array[0]，只有result[array[0]]=1，其他均为MAX
        for (int j = 1; j <= aim; j++) {
            dp[j] = MAX;
        }
        if (array[0] <= aim) {
            dp[array[0]] = 1;
        }

        // 左上角某个位置的值
        int leftUp;
        for (int i = 1; i < array.length; i++) {
            // 每个货币只能使用一张，需要从右向左更新，不然会导致每个货币使用多张
            for (int j = aim; j > 0; j--) {
                leftUp = MAX;
                if (j - array[i] >= 0 && dp[j - array[i]] != MAX) {
                    leftUp = dp[j - array[i]] + 1;
                }
                dp[j] = Math.min(leftUp, dp[j]);
            }
        }

        return dp[aim] != MAX ? dp[aim] : -1;
    }

    public static void main(String[] args) {
        int[] array = {2, 3, 5};
        int aim = 10;
        System.out.println(minCoinsOne(array, aim));
        System.out.println(minCoinsTwo(array, aim));
        System.out.println(uniMinCoinsOne(array, aim));
        System.out.println(uniMinCoinsTwo(array, aim));
    }
}
