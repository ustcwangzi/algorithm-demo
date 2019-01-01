/**
 * <p>Title: LongestSumSubArrayLength</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/1/1</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.arrayandmatrix;

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

    public static void main(String[] args) {
        int[] array1 = {1, 2, 1, 1, 1};
        System.out.println(getMaxLengthInPositiveArray(array1, 3));
    }
}
