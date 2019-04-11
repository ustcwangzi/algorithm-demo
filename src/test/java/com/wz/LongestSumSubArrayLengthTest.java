package com.wz;

import com.wz.arrayandmatrix.LongestSumSubArrayLength;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>数组中的元素可正、可负、可0，求其中累加和等于给定值的最长子数组长度</p>
 *
 * @author wangzi
 */
public class LongestSumSubArrayLengthTest {
    private static int solution(int[] array, int k) {
        int sum = 0, result = 0;
        // 记录累加和首次出现的位置
        Map<Integer, Integer> map = new HashMap<>(array.length + 1);
        map.put(0, -1);
        for (int i = 0; i < array.length; i++) {
            // array[0...i]累加和s(i)为sum
            sum += array[i];
            // array[0...j]累加和s(j)为sum-k，则array[j+1...i]的累加=s(i)-s(j)=k
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
