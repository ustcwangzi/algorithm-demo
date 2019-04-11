package com.wz;

import com.wz.arrayandmatrix.LongestSumSubArrayLength;

import java.util.Arrays;

/**
 * <p>正数数组中累加和为给定值的最长子数组长度</p>
 *
 * @author wangzi
 */
public class LongestSumSubPositiveArrayLengthTest {
    private static int solution(int[] array, int k) {
        int left = 0, right = 0, result = 0;
        // array[left...right]累加和
        int sum = array[0];
        while (right < array.length) {
            if (sum == k) {
                // 当前和为k，要想再次为k，只有减少范围，即减去array[left]
                result = Math.max(result, right - left + 1);
                sum -= array[left++];
            } else if (sum < k) {
                // 当前和小于k，需要继续向右，即加上array[right]
                right++;
                if (right == array.length) {
                    break;
                }
                sum += array[right];
            } else {
                // 当前和大于k，需要减少范围，即减去array[left]
                sum -= array[left++];
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int times = 100;
        boolean result = true;
        for (int i = 0; i < times; i++) {
            int[] array = RandomUtils.genRandomArray();
            int k = RandomUtils.genRandomNumber() + 1;
            if (solution(array, k) != LongestSumSubArrayLength.getMaxLengthInPositiveArray(array, k)) {
                result = false;
                System.out.println("Error, array:" + Arrays.toString(array) + ", k:" + k);
            }
        }
        if (result) {
            System.out.println("Past");
        }
    }
}
