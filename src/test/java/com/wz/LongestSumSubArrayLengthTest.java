package com.wz;

import com.wz.arrayandmatrix.LongestSumSubArrayLength;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class LongestSumSubArrayLengthTest {
    private static int solution(int[] array, int k) {
        int sum = 0, result = 0;
        Map<Integer, Integer> map = new HashMap<>(array.length + 1);
        map.put(0, -1);
        for (int i = 0; i < array.length; i++) {
            sum += array[i];
            if (map.containsKey(sum - k)) {
                result = Math.max(i - map.get(sum - k), result);
            }
            if (!map.containsKey(sum)) {
                map.put(sum, i);
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
            if (solution(array, k) != LongestSumSubArrayLength.getMaxLengthInArray(array, k)) {
                result = false;
                System.out.println("Error, array:" + Arrays.toString(array) + ", k:" + k);
            }
        }
        if (result) {
            System.out.println("Past");
        }
    }
}
