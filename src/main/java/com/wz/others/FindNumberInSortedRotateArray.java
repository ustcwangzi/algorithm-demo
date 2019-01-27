/**
 * <p>Title: FindNumberInSortedRotateArray</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/1/27</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.others;

/**
 * <p>在有序旋转数组中找到一个数</p>
 *
 * @author wangzi
 */
public class FindNumberInSortedRotateArray {
    public static boolean isContains(int[] array, int number) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("array is empty");
        }
        int low = 0, mid, high = array.length - 1;
        while (low <= high) {
            mid = (low + high) / 2;
            if (array[mid] == number) {
                return true;
            }
            if (array[low] == array[mid] && array[mid] == array[high]) {
                while (low != mid && array[low] == array[mid]) {
                    low++;
                }
                // array[low...mid]都是同一个值，number一定在array[mid...high]
                if (low == mid) {
                    low = mid + 1;
                    continue;
                }
            }
            if (array[low] != array[mid]) {
                // array[low...mid]有序
                if (array[mid] > array[low]) {
                    if (number >= array[low] && number < array[mid]) {
                        high = mid - 1;
                    } else {
                        low = mid + 1;
                    }
                } else {
                    // array[mid...high]有序
                    if (number > array[mid] && number <= array[high]) {
                        low = mid + 1;
                    } else {
                        high = mid - 1;
                    }
                }
            } else {
                // array[mid] != array[high]
                // array[mid...high]有序
                if (array[mid] < array[high]) {
                    if (number > array[mid] && number <= array[high]) {
                        low = mid + 1;
                    } else {
                        high = mid - 1;
                    }
                } else {
                    // array[low...mid]有序
                    if (number >= array[low] && number < array[mid]) {
                        high = mid - 1;
                    } else {
                        low = mid + 1;
                    }
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        int[] array = {4, 5, 6, 7, 1, 2, 3};
        System.out.println(isContains(array, 2));

        array = new int[]{4, 4, 4, 4, 4, 1, 2, 3};
        System.out.println(isContains(array, 2));

        array = new int[]{4, 1, 2, 3, 4, 4, 4, 4, 4};
        System.out.println(isContains(array, 2));

        array = new int[]{1, 2, 3, 1, 1, 1, 1};
        System.out.println(isContains(array, 2));
    }
}
