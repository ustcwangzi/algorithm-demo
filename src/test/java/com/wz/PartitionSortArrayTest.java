/**
 * <p>Title: PartitionSortArrayTest</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/4/5</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz;

import com.wz.arrayandmatrix.PartitionArray;

import java.util.Arrays;

/**
 * <p>使有序数组左半部分保持有序并且无重复</p>
 *
 * @author wangzi
 */
public class PartitionSortArrayTest {
    private static int[] solution(int[] array) {
        int left = 0, index = 1;
        while (index < array.length) {
            // array[0...left]有序无重复，不相等时将元素加入该区间
            if (array[index] != array[left]) {
                left++;
                swap(array, left, index);
            } else {
                index++;
            }
        }
        return array;
    }

    private static void swap(int[] array, int self, int other) {
        if (self == other) {
            return;
        }
        int tmp = array[self];
        array[self] = array[other];
        array[other] = tmp;
    }

    public static void main(String[] args) {
        int[] array = {1, 2, 2, 2, 3, 3, 4, 5, 6, 6, 7, 7, 8, 8, 8, 9};
        if (Arrays.equals(solution(array), PartitionArray.leftUnique(array))) {
            System.out.printf("Past");
        } else {
            System.out.printf("Error");
        }
    }
}
