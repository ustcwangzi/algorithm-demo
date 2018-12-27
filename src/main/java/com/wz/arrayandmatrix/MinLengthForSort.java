/**
 * <p>Title: MinLengthForSort</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/12/27</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.arrayandmatrix;

/**
 * <p>需要排序的最短子数组长度</p>
 *
 * @author wangzi
 */
public class MinLengthForSort {
    public static int getMinLength(int[] array) {
        if (array == null || array.length < 2) {
            return 0;
        }
        int min = array[array.length - 1];
        int noMinIndex = -1;
        for (int i = array.length - 2; i > -1; i--) {
            if (array[i] > min) {
                noMinIndex = i;
            } else {
                min = Math.min(min, array[i]);
            }
        }
        // 有序
        if (noMinIndex == -1) {
            return 0;
        }

        int max = array[0];
        int noMaxIndex = -1;
        for (int i = 1; i < array.length; i++) {
            if (array[i] < max) {
                noMaxIndex = i;
            } else {
                max = Math.max(max, array[i]);
            }
        }

        return noMaxIndex - noMinIndex + 1;
    }

    public static void main(String[] args) {
        int[] array = {1, 2, 4, 7, 10, 11, 7, 12, 6, 7, 16, 18, 19};
        System.out.println(getMinLength(array));
    }
}
