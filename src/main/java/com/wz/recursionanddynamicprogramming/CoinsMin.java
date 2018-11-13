/**
 * <p>Title: CoinsMin</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/11/12</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.recursionanddynamicprogramming;

/**
 * <p>换钱的最小货币数</p>
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

        int[][] result = new int[array.length][aim + 1];
        for (int j = 1; j <= aim; j++) {
            result[0][j] = MAX;
            // 第一行只能使用货币array[0]
            if (j - array[0] >= 0 && result[0][j - array[0]] != MAX) {
                result[0][j] = result[0][j - array[0]] + 1;
            }
        }

        int left;
        for (int i = 1; i < array.length; i++) {
            for (int j = 1; j <= aim; j++) {
                left = MAX;
                if (j - array[i] >= 0 && result[i][j - array[i]] != MAX) {
                    left = result[i][j - array[i]] + 1;
                }
                result[i][j] = Math.min(left, result[i - 1][j]);
            }
        }

        return result[array.length - 1][aim] != MAX ? result[array.length - 1][aim] : -1;
    }

    /**
     * 动态规划基础上的空间压缩解法
     */
    public static int minCoinsTwo(int[] array, int aim) {
        if (array == null || array.length == 0 || aim < 0) {
            return -1;
        }

        int result[] = new int[aim + 1];
        for (int j = 1; j <= aim; j++) {
            result[j] = MAX;
            // 初始化只能使用货币array[0]
            if (j - array[0] >= 0 && result[j - array[0]] != MAX) {
                result[j] = result[j - array[0]] + 1;
            }
        }

        int left;
        // 按行更新result
        for (int i = 1; i < array.length; i++) {
            for (int j = 1; j <= aim; j++) {
                left = MAX;
                if (j - array[i] >= 0 && result[j - array[i]] != MAX) {
                    left = result[j - array[i]] + 1;
                }
                result[j] = Math.min(left, result[j]);
            }
        }
        return result[aim] != MAX ? result[aim] : -1;
    }

    public static void main(String[] args) {
        int[] array = {2, 3, 5};
        int aim = 10;
        System.out.println(minCoinsOne(array, aim));
        System.out.println(minCoinsTwo(array, aim));
    }
}
