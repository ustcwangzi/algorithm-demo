/**
 * <p>Title: BinarySearch</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/9/2</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.grokking;

/**
 * <p>二分查找</p>
 * <p>二分查找运行时间为O(log n)</p>
 *
 * @author wangzi
 */
public class BinarySearch {
    private static int binarySearch(int[] array, int target) {
        int low = 0;
        int high = array.length - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (array[mid] == target) {
                return mid;
            }
            if (array[mid] > target) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] array = {1, 3, 5, 7, 9};
        System.out.println(binarySearch(array, 9));
        System.out.println(binarySearch(array, 0));
    }
}
