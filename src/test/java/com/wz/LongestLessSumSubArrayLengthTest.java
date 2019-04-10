package com.wz;

import com.wz.arrayandmatrix.LongestLessSumSubArrayLength;

import java.util.Arrays;

public class LongestLessSumSubArrayLengthTest {
    private static int solution(int[] array, int k) {
        int[] max = new int[array.length + 1];
        max[0] = 0;
        int sum = 0;
        for (int i = 0; i < array.length; i++) {
            sum += array[i];
            max[i + 1] = Math.max(sum, max[i]);
        }

        sum = 0;
        int index, length, result = 0;
        for (int i = 0; i < array.length; i++) {
            sum += array[i];
            index = firstBiggerOrEqualIndex(max, sum - k);
            length = index == -1 ? 0 : i - index + 1;
            result = Math.max(result, length);
        }
        return result;
    }

    /**
     * 二分查找大于等于num第一次出现的位置
     */
    private static int firstBiggerOrEqualIndex(int[] array, int num) {
        int left = 0, mid, right = array.length - 1;
        int result = -1;
        while (left <= right) {
            mid = (left + right) / 2;
            if (array[mid] >= num) {
                result = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int times = 100;
        boolean result = true;
        for (int i = 0; i < times; i++) {
            int[] array = RandomUtils.genRandomArrayWithMinus();
            int k = RandomUtils.genRandomNumber();
            if (solution(array, k) != LongestLessSumSubArrayLength.getMaxLength(array, k)) {
                result = false;
                System.out.println("Error, array:" + Arrays.toString(array) + ", k:" + k);
            }
        }
        if (result) {
            System.out.println("Past");
        }
    }
}
