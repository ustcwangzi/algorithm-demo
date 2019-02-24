/**
 * <p>Title: RandomUtils</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/2/24</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz;

/**
 * <p></p>
 *
 * @author wangzi
 */
public class RandomUtils {
    public static int[][] genRandomArray(int m, int n) {
        int[][] array = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                array[i][j] = (int) (Math.random() * 10) + 1;
            }
        }
        return array;
    }

    public static int[] genRandomArray(int m) {
        int[] array = new int[m];
        for (int i = 0; i != m; i++) {
            array[i] = (int) (Math.random() * 10) + 1;
        }
        return array;
    }
}
