package com.wz;

import com.wz.arrayandmatrix.LongestSumSubArrayLength;

import java.util.Arrays;

public class LongestSumSubPositiveArrayLengthTest {
    private static int solution(int[] array, int k) {
        int left = 0, right = 0, result = 0;
        int sum = array[0];
        while (right < array.length) {
            if (sum == k) {
                result = Math.max(result, right - left + 1);
                sum -= array[left++];
            } else if (sum < k) {
                right++;
                if (right == array.length) {
                    break;
                }
                sum += array[right];
            } else {
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
