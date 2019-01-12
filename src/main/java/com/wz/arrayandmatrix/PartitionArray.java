/**
 * <p>Title: PartitionArray</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/1/12</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.arrayandmatrix;

import java.util.Arrays;

/**
 * <p>数组的部分调整</p>
 *
 * @author wangzi
 */
public class PartitionArray {

    public static void leftUnique(int[] array) {
        if (array == null || array.length < 2) {
            return;
        }
        // array[0...uniqueIndex]无重复升序
        int uniqueIndex = 0;
        int index = 1;
        while (index < array.length) {
            // array[index]加入左边的无重复升序区域
            if (array[index++] != array[uniqueIndex]) {
                swap(array, ++uniqueIndex, index - 1);
            }
        }
    }

    public static void partitionSort(int[] array) {
        if (array == null || array.length < 2) {
            return;
        }

        int left = -1, index = 0, right = array.length;
        while (index < right) {
            if (array[index] == 0) {
                // 加入左区，index右移
                swap(array, ++left, index++);
            } else if (array[index] == 2) {
                // 加入右区，index不变
                swap(array, index, --right);
            } else {
                // 加入中区
                index++;
            }
        }
    }

    private static void swap(int[] array, int self, int other) {
        int tmp = array[self];
        array[self] = array[other];
        array[other] = tmp;
    }

    public static void main(String[] args) {
        int[] array1 = {1, 2, 2, 2, 3, 3, 4, 5, 6, 6, 7, 7, 8, 8, 8, 9};
        leftUnique(array1);
        System.out.println(Arrays.toString(array1));
        int[] array2 = {2, 1, 2, 0, 1, 1, 2, 2, 0};
        partitionSort(array2);
        System.out.println(Arrays.toString(array2));
    }
}
