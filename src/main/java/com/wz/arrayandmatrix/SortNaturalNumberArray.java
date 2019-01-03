/**
 * <p>Title: SortNaturalNumberArray</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/1/4</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.arrayandmatrix;

import java.util.Arrays;

/**
 * <p>自然数数组的排序</p>
 *
 * @author wangzi
 */
public class SortNaturalNumberArray {

    public static void sortOne(int[] array) {
        if (array == null || array.length < 2) {
            return;
        }

        int tmp, next;
        for (int i = 0; i < array.length; i++) {
            tmp = array[i];
            // 进行跳的过程，跳一圈回到原位置后，i位置上数据正确
            while (array[i] != i + 1) {
                next = array[tmp - 1];
                array[tmp - 1] = tmp;
                tmp = next;
            }
        }
    }

    public static void sortTow(int[] array) {
        if (array == null || array.length < 2) {
            return;
        }

        int tmp;
        for (int i = 0; i < array.length; i++) {
            // i位置上直接进行数据交换
            while (array[i] != i + 1) {
                tmp = array[array[i] - 1];
                array[array[i] - 1] = array[i];
                array[i] = tmp;
            }
        }
    }

    public static void main(String[] args) {
        int[] array = {8, 2, 1, 6, 9, 3, 7, 5, 4};
        sortOne(array);
        System.out.println(Arrays.toString(array));

        array = new int[]{8, 2, 1, 6, 9, 3, 7, 5, 4};
        sortTow(array);
        System.out.println(Arrays.toString(array));
    }
}
