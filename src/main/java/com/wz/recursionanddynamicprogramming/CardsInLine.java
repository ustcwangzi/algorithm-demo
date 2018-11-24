/**
 * <p>Title: CardsInLine</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/11/24</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.recursionanddynamicprogramming;

/**
 * <p>排成一条线的纸牌博弈问题</p>
 * <p>
 *     给定一个整数数组array，代表数值不同的纸牌排成一条线，玩家A和B依次拿走每张纸牌，每位玩家每次只能拿走最左或最右的纸牌，
 *     数值相加为其分数，玩家A和B都绝顶聪明，求出最后获胜者的分数。
 *     例如，array=[1,2,100,4]，开始时A只能拿走1或4，如果拿走4会导致B拿走100，所以A不会拿走4，会拿走1；接下来无论B拿走2还是4，
 *          A都能拿走100，因此A会获胜，分数为101。
 *          array=[1,100,2]，无论A拿走1或2，B都能拿走100，因此B会获胜，分数为100。
 *     方案一：
 *        暴力递归。
 *        定义函数first(i,j)，表示array[i...j]被先拿，最终能获取什么分数。
 *        定义函数second(i,j)，表示array[i...j]被后拿，最终能获取什么分数。
 *        分析first(i,j)：
 *           1、如果i==j，即只剩下一张纸牌，当然会被先拿的人拿走，返回array[i]
 *           2、如果i!=j，当前玩家有两种选择：
 *           2.1、拿走array[i]，剩下array[i+1...j]，对当前玩家来说，面对array[i+1...j]时，他是后拿的人，后续得分为second(i+1,j)
 *           2.2、拿走array[j]，剩下array[i...j-1]，对当前玩家来说，面对array[i...j-1]时，他是后拿的人，后续得分为second(i,j-1)
 *           当前玩家必然会在两种决策中选择最优的，即 max{array[i]+second(i+1,j), array[j]+second(i,j-1)}
 *        分析second(i,j)：
 *           1、如果i==j，即只剩下一张纸牌，当然会被先拿的人拿走，返回array[i]
 *           2、如果i!=j，对手会先拿牌，而且对手会将最差的情况留给自己，即 min{first(i+1,j), first(i,j-1)}
 *     方案二：
 *        动态规划。
 *        生成两个N*N的矩阵first和second，first[i][j]代表函数first(i,j)的返回值，second[i][j]代表second(i,j)的返回值。
 *        然后规定好两个矩阵的计算方向即可。
 * </p>
 * <p>
 *     方案一中，递归函数共有N层，而且是first和second交替出现，first(i,j)有second(i+1,j)和second(i,j-1)两个递归分支，
 *          second(i,j)有first(i+1,j)和first(i,j-1)两个递归分支，整体时间复杂度为O(2^N)，空间复杂度为O(N)。
 *     方案二时间复杂度为O(N*N)，空间复杂度为O(N*N)。
 * </p>
 *
 * @author wangzi
 */
public class CardsInLine {
    /**
     * 暴力递归
     */
    public static int winOne(int[] array) {
        if (array == null || array.length == 0) {
            return 0;
        }
        return Math.max(first(array, 0, array.length - 1), second(array, 0, array.length - 1));
    }

    /**
     * array[i...j]被先拿，能获得的分数
     */
    public static int first(int[] array, int i, int j) {
        if (i == j) {
            return array[i];
        }

        return Math.max(array[i] + second(array, i + 1, j), array[j] + second(array, i, j - 1));
    }

    /**
     * array[i...j]被后拿，能获得的分数
     */
    public static int second(int[] array, int i, int j) {
        if (i == j) {
            return 0;
        }
        return Math.min(first(array, i + 1, j), first(array, i, j - 1));
    }

    /**
     * 动态规划
     */
    public static int winTwo(int[] array) {
        if (array == null || array.length == 0) {
            return 0;
        }

        // 先拿
        int[][] first = new int[array.length][array.length];
        // 后拿
        int[][] second = new int[array.length][array.length];
        for (int j = 0; j < array.length; j++) {
            first[j][j] = array[j];
            for (int i = j - 1; i >= 0; i--) {
                first[i][j] = Math.max(array[i] + second[i + 1][j], array[j] + second[i][j - 1]);
                second[i][j] = Math.min(first[i + 1][j], first[i][j - 1]);
            }
        }

        return Math.max(first[0][array.length - 1], second[0][array.length - 1]);
    }

    public static void main(String[] args) {
        int[] array = {1, 9, 1};
        System.out.println(winOne(array));
        System.out.println(winTwo(array));
    }
}
