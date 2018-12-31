/**
 * <p>Title: PrintUniquePairAndTriad</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/12/31</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.arrayandmatrix;

/**
 * <p>获取有序数组中相加和为给定值的不重复二元组和三元组</p>
 *
 * @author wangzi
 */
public class PrintUniquePairAndTriad {
    public static void printUniquePair(int[] array, int k) {
        if (array == null || array.length < 2) {
            return;
        }

        int left = 0, right = array.length - 1;
        while (left < right) {
            if (array[left] + array[right] < k) {
                left++;
            } else if (array[left] + array[right] > k) {
                right--;
            } else {
                if (left == 0 || array[left - 1] != array[left]) {
                    System.out.println(array[left] + ", " + array[right]);
                }
                left++;
                right--;
            }
        }
    }

    public static void printUniqueTriad(int[] array, int k) {
        if (array == null || array.length < 2) {
            return;
        }

        for (int i = 0; i < array.length - 2; i++) {
            if (i == 0 || array[i] != array[i - 1]) {
                // 寻找i后面的相加和为(k-array[i])的二元组
                printUniqueTriad(array, i, i + 1, array.length - 1, k - array[i]);
            }
        }
    }

    private static void printUniqueTriad(int[] array, int pre, int left, int right, int k) {
        while (left < right) {
            if (array[left] + array[right] < k) {
                left++;
            } else if (array[left] + array[right] > k) {
                right--;
            } else {
                if (left == pre + 1 || array[left - 1] != array[left]) {
                    System.out.println(array[pre] + ", " + array[left] + ", " + array[right]);
                }
                left++;
                right--;
            }
        }
    }

    public static void main(String[] args) {
        int[] array = {-8, -4, -3, 0, 1, 2, 4, 5, 8, 9};
        printUniquePair(array, 10);
        System.out.println("========");
        printUniqueTriad(array, 10);
    }
}
