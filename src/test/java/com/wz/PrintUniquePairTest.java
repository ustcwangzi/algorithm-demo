/**
 * <p>Title: PrintUniquePairTest</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/4/13</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz;

import com.wz.arrayandmatrix.PrintUniquePairAndTriad;

/**
 * <p>有序数组中相加和为给定值的不重复二元组</p>
 *
 * @author wangzi
 */
public class PrintUniquePairTest {
    private static void solution(int[] array, int k) {
        // 有序数组，因此只需要不停移动left和right即可
        int left = 0, right = array.length - 1;
        while (left < right) {
            int sum = array[left] + array[right];
            if (sum < k) {
                left++;
            } else if (sum > k) {
                right--;
            } else {
                // 保证结果不重复
                if (left == 0 || array[left] != array[left - 1]) {
                    System.out.println(array[left] + ", " + array[right]);
                }
                left++;
                right--;
            }
        }
    }

    public static void main(String[] args) {
        int[] array = {-8, -4, -3, 0, 1, 2, 4, 5, 8, 9};
        for (int i = 0; i < 13; i++) {
            solution(array, i);
            System.out.println("-----");
            PrintUniquePairAndTriad.printUniquePair(array, i);
            System.out.println("=====");
        }
    }
}
