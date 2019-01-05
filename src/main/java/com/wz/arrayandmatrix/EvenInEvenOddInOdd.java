/**
 * <p>Title: EvenInEvenOddInOdd</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/1/5</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.arrayandmatrix;

import java.util.Arrays;

/**
 * <p>奇数位置都是奇数或者偶数位置都是偶数</p>
 *
 * @author wangzi
 */
public class EvenInEvenOddInOdd {

    public static void modify(int[] array) {
        if (array == null || array.length < 2) {
            return;
        }
        int even = 0, odd = 1;
        int end = array.length - 1;
        while (even <= end && odd <= end) {
            if ((array[end] & 1) == 0) {
                // array[end]是偶数，与even交换
                swap(array, end, even);
                even += 2;
            } else {
                // array[end]是奇数，与odd交换
                swap(array, end, odd);
                odd += 2;
            }
        }
    }

    private static void swap(int[] array, int self, int other) {
        int tmp = array[self];
        array[self] = array[other];
        array[other] = tmp;
    }

    public static void main(String[] args) {
        int[] array = {1, 8, 3, 2, 4, 6};
        modify(array);
        System.out.println(Arrays.toString(array));
    }
}
