/**
 * <p>Title: SmallSum</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/1/3</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.arrayandmatrix;

/**
 * <p>计算数组的小和</p>
 *
 * @author wangzi
 */
public class SmallSum {

    public static int getSmallSum(int[] array) {
        if (array == null || array.length == 0) {
            return 0;
        }
        return process(array, 0, array.length - 1);
    }

    private static int process(int[] array, int left, int right) {
        if (left == right) {
            return 0;
        }
        int mid = (left + right) / 2;
        return process(array, left, mid) + process(array, mid + 1, right) + merge(array, left, mid, right);
    }

    private static int merge(int[] array, int left, int mid, int right) {
        int[] tmpArray = new int[right - left + 1];
        int smallSum = 0;
        int cur = 0;
        int i = left, j = mid + 1;
        while (i <= mid && j <= right) {
            if (array[i] <= array[j]) {
                smallSum += array[i] * (right - j + 1);
                tmpArray[cur++] = array[i++];
            } else {
                tmpArray[cur++] = array[j++];
            }
        }

        for (; j <= right || i <= mid; j++, i++) {
            tmpArray[cur++] = i > mid ? array[j] : array[i];
        }
        for (int value : tmpArray) {
            array[left++] = value;
        }
        return smallSum;
    }

    public static void main(String[] args) {
        int[] array = {3, 1, 2, 4, 6, 2, 7, 8, 1};
        System.out.println(getSmallSum(array));
    }
}
