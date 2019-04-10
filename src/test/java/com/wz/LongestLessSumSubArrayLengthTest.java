package com.wz;

import com.wz.arrayandmatrix.LongestLessSumSubArrayLength;

import java.util.Arrays;

/**
 * <p>数组中的元素可正、可负、可0，求其中累加和小于等于给定值的最长子数组长度</p>
 *
 * @author wangzi
 */
public class LongestLessSumSubArrayLengthTest {
    private static int solution(int[] array, int k) {
        // array[0...i-1]的最大累加和
        int[] max = new int[array.length + 1];
        // 第一个为0表示当没有任何一个数时的累加和为0
        max[0] = 0;
        int sum = 0;
        for (int i = 0; i < array.length; i++) {
            sum += array[i];
            max[i + 1] = Math.max(sum, max[i]);
        }

        sum = 0;
        int index, length, result = 0;
        // sum[j]表示array[0...j]的累加和，sum[i]表示array[0...i]的累加和
        // 对于每个位置j，需要找到第一次累加和大于等于sum[j]-k的位置，如果找到了这个位置，
        // 那么就有array[i+1...j]的累加和小于等于k，也就是以位置j结尾的累加和小于等于k的最长子序列
        for (int i = 0; i < array.length; i++) {
            sum += array[i];
            // 找到第一个大于等于sum-k的位置，那么该位置之后到i的累加和必然小于等于k
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
