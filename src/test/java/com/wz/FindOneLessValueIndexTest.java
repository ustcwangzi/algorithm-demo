/**
 * <p>Title: FindOneLessValueIndexTest</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/4/7</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz;

import com.wz.arrayandmatrix.FindOneLessValueIndex;

import java.util.Arrays;

/**
 * <p>无序数组中找到局部最小值的下标</p>
 *
 * @author wangzi
 */
public class FindOneLessValueIndexTest {
    private static int solution(int[] array) {
        // array[0]局部最小
        if (array.length == 1 || array[0] < array[1]) {
            return 0;
        }
        // array[N-1]局部最小
        if (array[array.length - 1] < array[array.length - 2]) {
            return array.length - 1;
        }
        int left = 1, mid, right = array.length - 2;
        // 二分法查找局部最小，因为可以确定二分两侧的某一侧肯定存在要找的内容
        while (left < right) {
            mid = (left + right) / 2;
            if (array[mid] > array[mid - 1]) {
                right = mid - 1;
            } else if (array[mid] > array[mid + 1]) {
                left = mid + 1;
            } else {
                return mid;
            }
        }
        return left;
    }

    public static void main(String[] args) {
        int times = 100;
        boolean result = true;
        for (int i = 0; i < times; i++) {
            int[] array = RandomUtils.genRandomArray();
            if (solution(array) != FindOneLessValueIndex.getLessIndex(array)) {
                result = false;
                System.out.println("Error, array:" + Arrays.toString(array));
            }
        }
        if (result) {
            System.out.println("Past");
        }
    }
}
