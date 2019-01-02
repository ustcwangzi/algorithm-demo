/**
 * <p>Title: LongestLessSumSubArrayLength</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/1/2</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.arrayandmatrix;

/**
 * <p>无序数组中累加和小于等于给定值的最长子数组长度</p>
 *
 * @author wangzi
 */
public class LongestLessSumSubArrayLength {

    public static int getMaxLength(int[] array, int k) {
        int[] maxArray = new int[array.length + 1];
        maxArray[0] = 0;
        int sum = 0;
        for (int i = 0; i < array.length; i++) {
            sum += array[i];
            maxArray[i + 1] = Math.max(sum, maxArray[i]);
        }
        sum = 0;
        int pre, length;
        int result = 0;
        for (int i = 0; i < array.length; i++) {
            sum += array[i];
            // 大于等于(sum-k)第一次出现的位置
            pre = getLessIndex(maxArray, sum - k);
            // pre到i当前就是满足(小于等于k)的最长数组
            length = pre == -1 ? 0 : i - pre + 1;
            result = Math.max(result, length);
        }
        return result;
    }

    /**
     * 二分查找大于等于num第一次出现的位置
     */
    private static int getLessIndex(int[] array, int num) {
        int low = 0, mid = 0, high = array.length - 1;
        int result = -1;
        while (low <= high) {
            mid = (low + high) / 2;
            if (array[mid] >= num) {
                result = mid;
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int[] array = {3, -2, -4, 0, 6};
        System.out.println(getMaxLength(array, -2));
    }
}
