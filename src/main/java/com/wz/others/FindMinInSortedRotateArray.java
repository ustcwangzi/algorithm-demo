/**
 * <p>Title: FindMinInSortedRotateArray</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/1/27</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.others;

/**
 * <p>在有序旋转数组中找到最小值</p>
 *
 * @author wangzi
 */
public class FindMinInSortedRotateArray {
    public static int findMin(int[] array) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("array is empty");
        }
        int low = 0, mid, high = array.length - 1;
        while (low < high) {
            if (low == high - 1) {
                break;
            }
            if (array[low] < array[high]) {
                // 没有经过旋转，断点就是array[low]
                return array[low];
            }
            mid = (low + high) / 2;
            if (array[low] > array[mid]) {
                //  断点在[low...mid]上
                high = mid;
                continue;
            }
            if (array[mid] > array[high]) {
                //  断点在[mid...high]上
                low = mid;
                continue;
            }
            while (low < mid) {
                if (array[low] == array[mid]) {
                    low++;
                } else if (array[low] < array[mid]) {
                    // 发现的降序必然是断点
                    return array[low];
                } else {
                    // 断点在[low...mid]上，继续二分
                    high = mid;
                    break;
                }
            }
        }
        return Math.min(array[low], array[high]);
    }

    public static void main(String[] args) {
        int[] array = {4, 5, 5, 5, 1, 2, 3};
        System.out.println(findMin(array));
    }
}
