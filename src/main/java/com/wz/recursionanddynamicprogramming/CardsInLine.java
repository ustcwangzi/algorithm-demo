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
 *
 * @author wangzi
 */
public class CardsInLine {
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

    public static int winTwo(int[] array) {
        if (array == null || array.length == 0) {
            return 0;
        }

        int[][] first = new int[array.length][array.length];
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
