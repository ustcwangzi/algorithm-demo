/**
 * <p>Title: CoinsWay</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/11/12</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.recursionanddynamicprogramming;

/**
 * <p>换钱的方法数</p>
 * <p>
 *     给定数组array，数组中所有值都为正数且不重复，每个代表一种面额的货币，每种货币可使用任意张，给定整数aim，求组成aim的方法数
 *     方案一：
 *        暴力递归。如果array=[5,10,25,1]，aim=1000，过程如下：
 *        1、用0张5，让[10,25,1]组成剩下的1000，方法数为result1
 *        2、用1张5，让[10,25,1]组成剩下的995，方法数为result2
 *        3、用2张5，让[10,25,1]组成剩下的990，方法数为result3
 *          ... ...
 *        201、用0200张5，让[10,25,1]组成剩下的0，方法数为result201
 *        那么，result1+result2+...+result201就是总的方法数，据此定义coinsWayOne(array, index, aim)，
 *        含义是用array[index...N-1]的货币组成aim，返回总的方法数
 *     方案二：
 *        给予暴力递归的记忆搜索方法。coinsWayOne(array, index, aim)中存在大量重复的计算，比如上面的例子中，
 *        使用0张5和1张10的情况下，后续是求[25,1]组成990的方法总数，使用2张5和0张10的情况下，后续还是求[25,1]组成990的方法总数。
 *        类似的重复计算在递归过程中大量发生，记忆搜索方法在每计算完一个递归过程，将结果记录到map中，下次遇到同样的过程，先查找map中是否存在，
 *        若存在直接使用。map[i][j]表示coinsWayOne(array, i, j)的返回值，为0表示未计算过，为-1表示计算过但返回值为0。
 *     方案三：
 *        动态规划方法。生成N行、aim+1列的矩阵dp，dp[i][j]含义是使用array[0...i]的情况下，组成j有多少中方法，求法如下：
 *        1、矩阵第一列表示组成0的方法数，很明显是一种，即不使用任何货币，所以dp第一列统一设置为1
 *        2、矩阵第一行表示只使用array[0]的情况下，组成j的方法数，比如array[0]=5时，能组成的只有0,5,10,...，所以，
 *           令dp[0][k*array[0]]=1 (0<=k*array[0]<=aim, k为非负整数)
 *        3、除第一行和第一列的其他位置，记为(i,j)，dp[i][j]是一下值的累加：
 *           3.0、完全不使用array[i]货币，只使用array[0...i-1]货币时，方法数为dp[i-1][j]
 *           3.0、使用1张array[i]货币，剩下的使用array[0...i-1]货币组成时，方法数为dp[i-1][j-array[i]]
 *           3.0、使用2张array[i]货币，剩下的使用array[0...i-1]货币组成时，方法数为dp[i-1][j-2*array[i]]
 *               ... ...
 *           3.0、使用k张array[i]货币，剩下的使用array[0...i-1]货币组成时，方法数为dp[i-1][j-k*array[i]]
 *               j-k*array[i]>=0，k为非负整数
 *        4、最后，dp[N-1][aim]就是最终结果
 *     方案四：
 *        优化后动态规划方法。方案三中求dp[i][j]的步骤三可知：
 *        dp[i][j] = dp[i-1][j] + dp[i-1][j-array[i]] + dp[i-1][j-2*array[i]] +..+ dp[i-1][j-k*array[i]]
 *        其中，dp[i-1][j-array[i]] + dp[i-1][j-2*array[i]] +..+ dp[i-1][j-k*array[i]] (j-k*array[i]>=0) =
 *        dp[i-1][y] + dp[i-1][y-array[i]] +..+ dp[i-1][y-h*array[i]] (y>=0, y-h*array[i]>=0) =
 *        dp[i][y] = dp[i][j-array[i]]
 *        即：dp[i][j] = dp[i-1][j] + dp[i][j-array[i]] (j-array[i]>=0)
 *        省去了枚举的过程，降低了方案三的时间复杂度
 *     方案五：
 *        动态规划基础上的空间压缩。思想与com.wz.recursionanddynamicprogramming.MinPathSum的方案二类似。
 *        生成一个aim+1的一维数组，然后按行更新即可。
 * </p>
 * <p>
 *     方案一时间复杂度在最坏的情况下为O(aim^N)，方案二和方案三的时间复杂度为O(N*aim^2)，因为本质上记忆搜索方法等价于动态规划方法
 *     方案四时间复杂度为O(N*aim)、空间复杂度为O(N*aim)，方案五时间复杂度为O(N*aim)、空间复杂度为O(aim)
 * </p>
 *
 * @author wangzi
 */
public class CoinsWay {
    /**
     * 计算过，返回值为0
     */
    private static final int ZERO_RESULT = -1;

    /**
     * 暴力递归求解
     */
    public static int coinsWayOne(int[] array, int aim) {
        if (array == null || array.length == 0 || aim < 0) {
            return 0;
        }
        return coinsWayOne(array, 0, aim);
    }

    /**
     * 使用array[index...N-1]这些面值的货币组成aim，返回总的方法数
     */
    private static int coinsWayOne(int[] array, int index, int aim) {
        int result = 0;
        // 因递归中有index+1,所以最大为array.length
        if (index == array.length) {
            // aim=0，只有一种方法，即所有面值都不用
            result = aim == 0 ? 1 : 0;
        } else {
            for (int i = 0; array[index] * i <= aim; i++) {
                // 对于array[2, 3, 5], aim=10
                // 用0张2，让[3, 5]组成剩下的10，方法result1
                // 用1张2，让[3, 5]组成剩下的8，方法result2
                // ... ...
                // 用5张2，让[3, 5]组成剩下的0，方法result6
                // 最终，result=result1+result2+...+result6
                result += coinsWayOne(array, index + 1, aim - array[index] * i);
            }
        }
        return result;
    }

    /**
     * 记忆搜索方法
     * 在暴力递归的基础上使用map记录递归过程中的结果
     */
    public static int coinsWayTwo(int[] array, int aim) {
        if (array == null || array.length == 0 || aim < 0) {
            return 0;
        }
        int[][] map = new int[array.length + 1][aim + 1];
        return coinsWayTwo(array, 0, aim, map);
    }

    /**
     * 使用array[index...N-1]这些面值的货币组成aim，返回总的方法数
     * 使用map将递归过程中的结果进行记录，下次再遇到同样的递归过程，就直接使用表中的数据
     */
    private static int coinsWayTwo(int[] array, int index, int aim, int[][] map) {
        int result = 0;
        if (index == array.length) {
            result = aim == 0 ? 1 : 0;
        } else {
            int mapValue = 0;
            for (int i = 0; array[index] * i <= aim; i++) {
                mapValue = map[index + 1][aim - array[index] * i];
                if (mapValue != 0) {
                    result += mapValue == -1 ? 0 : mapValue;
                } else {
                    result += coinsWayTwo(array, index + 1, aim - array[index] * i, map);
                }
            }
        }
        map[index][aim] = result == 0 ? ZERO_RESULT : result;
        return result;
    }

    /**
     * 动态规划求解
     */
    public static int coinsWayThree(int[] array, int aim) {
        if (array == null || array.length == 0 || aim < 0) {
            return 0;
        }

        // 动态规划矩阵
        int[][] dp = new int[array.length][aim + 1];
        // 第一列，aim为0，即不使用任何货币
        for (int i = 0; i < array.length; i++) {
            dp[i][0] = 1;
        }
        // 第一行只能使用货币array[0]
        for (int j = 1; array[0] * j <= aim; j++) {
            dp[0][array[0] * j] = 1;
        }

        int num;
        for (int i = 1; i < array.length; i++) {
            for (int j = 1; j <= aim; j++) {
                num = 0;
                for (int k = 0; j - array[i] * k >= 0; k++) {
                    num += dp[i - 1][j - array[i] * k];
                }
                dp[i][j] = num;
            }
        }
        return dp[array.length - 1][aim];
    }

    /**
     * 优化的动态规划求解
     */
    public static int coinsWayFour(int[] array, int aim) {
        if (array == null || array.length == 0 || aim < 0) {
            return 0;
        }

        // 动态规划矩阵
        int[][] dp = new int[array.length][aim + 1];
        // 第一列，aim为0，即不使用任何货币
        for (int i = 0; i < array.length; i++) {
            dp[i][0] = 1;
        }
        // 第一行只能使用货币array[0]
        for (int j = 1; array[0] * j <= aim; j++) {
            dp[0][array[0] * j] = 1;
        }

        for (int i = 1; i < array.length; i++) {
            for (int j = 1; j <= aim; j++) {
                dp[i][j] = dp[i - 1][j];
                dp[i][j] += j - array[i] >= 0 ? dp[i][j - array[i]] : 0;
            }
        }
        return dp[array.length - 1][aim];
    }

    /**
     * 动态规划基础上的空间压缩解法
     */
    public static int coinsWayFive(int[] array, int aim) {
        if (array == null || array.length == 0 || aim < 0) {
            return 0;
        }

        // 动态规划矩阵
        int[] dp = new int[aim + 1];
        // 初始化时只能使用货币array[0]
        for (int j = 0; array[0] * j <= aim; j++) {
            dp[array[0] * j] = 1;
        }
        // 按行更新dp
        for (int i = 1; i < array.length; i++) {
            for (int j = 1; j <= aim; j++) {
                dp[j] += j - array[i] >= 0 ? dp[j - array[i]] : 0;
            }
        }
        return dp[aim];
    }

    public static void main(String[] args) {
        int[] array = {2, 3, 5};
        int aim = 10;
        System.out.println(coinsWayOne(array, aim));
        System.out.println(coinsWayTwo(array, aim));
        System.out.println(coinsWayThree(array, aim));
        System.out.println(coinsWayFour(array, aim));
        System.out.println(coinsWayFive(array, aim));
    }
}
