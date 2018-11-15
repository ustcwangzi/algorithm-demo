/**
 * <p>Title: CoinsWay</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/11/12</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.recursionanddynamicprogramming;

/**
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
        int[][] result = new int[array.length][aim + 1];
        // 第一列，aim为0，即不使用任何货币
        for (int i = 0; i < array.length; i++) {
            result[i][0] = 1;
        }
        // 第一行只能使用货币array[0]
        for (int j = 1; array[0] * j <= aim; j++) {
            result[0][array[0] * j] = 1;
        }

        int num;
        for (int i = 1; i < array.length; i++) {
            for (int j = 1; j <= aim; j++) {
                num = 0;
                for (int k = 0; j - array[i] * k >= 0; k++) {
                    num += result[i - 1][j - array[i] * k];
                }
                result[i][j] = num;
            }
        }
        return result[array.length - 1][aim];
    }

    /**
     * 优化的动态规划求解
     */
    public static int coinsWayFour(int[] array, int aim) {
        if (array == null || array.length == 0 || aim < 0) {
            return 0;
        }
        int[][] result = new int[array.length][aim + 1];
        // 第一列，aim为0，即不使用任何货币
        for (int i = 0; i < array.length; i++) {
            result[i][0] = 1;
        }
        // 第一行只能使用货币array[0]
        for (int j = 1; array[0] * j <= aim; j++) {
            result[0][array[0] * j] = 1;
        }

        for (int i = 1; i < array.length; i++) {
            for (int j = 1; j <= aim; j++) {
                result[i][j] = result[i - 1][j];
                result[i][j] += j - array[i] >= 0 ? result[i][j - array[i]] : 0;
            }
        }
        return result[array.length - 1][aim];
    }

    /**
     * 动态规划基础上的空间压缩解法
     */
    public static int coinsWayFive(int[] array, int aim) {
        if (array == null || array.length == 0 || aim < 0) {
            return 0;
        }

        int[] result = new int[aim + 1];
        // 初始化时只能使用货币array[0]
        for (int j = 0; array[0] * j <= aim; j++) {
            result[array[0] * j] = 1;
        }
        // 按行更新result
        for (int i = 1; i < array.length; i++) {
            for (int j = 1; j <= aim; j++) {
                result[j] += j - array[i] >= 0 ? result[j - array[i]] : 0;
            }
        }
        return result[aim];
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
