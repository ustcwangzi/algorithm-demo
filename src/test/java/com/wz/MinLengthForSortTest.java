package com.wz;

import com.wz.arrayandmatrix.MinLengthForSort;

import java.util.Arrays;

/**
 * <p>无需数组中需要排序的最短子数组长度</p>
 *
 * @author wangzi
 */
public class MinLengthForSortTest {
    private static int solution(int[] array) {
        int leftIndex = -1;
        int min = array[array.length - 1];
        // 从右到左遍历，找到出现的最小值min，及大于最小值的最左位置leftIndex
        for (int i = array.length - 2; i > -1; i--) {
            if (array[i] > min) {
                leftIndex = i;
            } else {
                min = Math.min(min, array[i]);
            }
        }
        if (leftIndex == -1) {
            return 0;
        }

        int rightIndex = -1;
        int max = array[0];
        // 从左到右遍历，找到出现的最大值max，及小于最大值的最右位置rightIndex
        for (int i = 1; i < array.length; i++) {
            if (array[i] < max) {
                rightIndex = i;
            } else {
                max = Math.max(max, array[i]);
            }
        }

        return rightIndex - leftIndex + 1;
    }

    public static void main(String[] args) {
        int times = 100;
        boolean result = true;
        for (int i = 0; i < times; i++) {
            int[] array = RandomUtils.genRandomArrayWithMinus();
            if (solution(array) != MinLengthForSort.getMinLength(array)) {
                result = false;
                System.out.println("Error, array:" + Arrays.toString(array));
            }
        }
        if (result) {
            System.out.println("Past");
        }
    }
}
