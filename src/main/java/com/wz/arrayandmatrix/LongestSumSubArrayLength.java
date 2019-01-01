/**
 * <p>Title: LongestSumSubArrayLength</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/1/1</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.arrayandmatrix;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>无序数组中累加和为给定值的最长子数组长度</p>
 *
 * @author wangzi
 */
public class LongestSumSubArrayLength {
    /**
     * 无序正数数组中累加和为k的最长子数组长度
     */
    public static int getMaxLengthInPositiveArray(int[] array, int k) {
        if (array == null || array.length == 0 || k <= 0) {
            return 0;
        }

        // 标记子数组左右位置
        int left = 0, right = 0;
        // 当前子数组中元素之和
        int sum = array[0];
        int result = 0;
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

    /**
     * 无序数组中累加和为k的最长子数组长度
     */
    public static int getMaxLengthInArray(int[] array, int k) {
        if (array == null || array.length == 0) {
            return 0;
        }

        // 记录累加和key首次出现的位置value
        Map<Integer, Integer> map = new HashMap<>();
        // 以免漏掉从0开始的子数组
        map.put(0, -1);
        // array[0...i]的累加和
        int sum = 0;
        int result = 0;
        for (int i = 0; i < array.length; i++) {
            // s(i)为sum
            sum += array[i];
            // s(j)为sum-k，array[j+1...i]的累加=s(i)-s(j)=k
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
        int[] array1 = {1, 2, 1, 1, 1};
        System.out.println(getMaxLengthInPositiveArray(array1, 3));
        int[] array2 = {1, 2, 3, 3};
        System.out.println(getMaxLengthInArray(array2, 6));
    }
}
