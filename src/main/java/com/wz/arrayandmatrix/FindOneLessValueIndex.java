/**
 * <p>Title: SubMatrixMaxSum</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/1/7</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.arrayandmatrix;

/**
 * <p>在数组中找到一个局部最小的位置</p>
 *
 * @author wangzi
 */
public class FindOneLessValueIndex {
    public static int getLessIndex(int[] array) {
        if (array == null || array.length == 0) {
            return -1;
        }
        if (array.length == 1 || array[0] < array[1]) {
            return 0;
        }
        if (array[array.length - 1] < array[array.length - 2]) {
            return array.length - 1;
        }

        int left = 1, right = array.length - 2, mid;
        // 可以确定mid的两侧肯定存在要找的内容，因此可使用二分法查找
        while (left < right) {
            mid = (left + right) / 2;
            if (array[mid] > array[mid - 1]) {
                right = mid - 1;
            } else if (array[mid] > array[mid + 1]) {
                left = mid + 1;
            } else {
                return mid;
            }
        }
        return left;
    }
}
