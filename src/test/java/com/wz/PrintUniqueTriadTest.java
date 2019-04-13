/**
 * <p>Title: PrintUniqueTriadTest</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/4/13</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz;

import com.wz.arrayandmatrix.PrintUniquePairAndTriad;

/**
 * <p>有序数组中相加和为给定值的不重复三元组</p>
 *
 * @author wangzi
 */
public class PrintUniqueTriadTest {
    private static void solution(int[] array, int k) {
        for (int i = 0; i < array.length - 2; i++) {
            if (i == 0 || array[i] != array[i - 1]) {
                // 寻找i后面和为 k-array[i] 的二元组
                solution(array, i, i + 1, array.length - 1, k - array[i]);
            }
        }
    }

    /**
     * 寻找array[left...right]和为k的二元组
     */
    private static void solution(int[] array, int cur, int left, int right, int k) {
        while (left < right) {
            int sum = array[left] + array[right];
            if (sum < k) {
                left++;
            } else if (sum > k) {
                right--;
            } else {
                if (left == cur + 1 || array[left - 1] != array[left]) {
                    System.out.println(array[cur] + ", " + array[left] + ", " + array[right]);
                }
                left++;
                right--;
            }
        }
    }

    public static void main(String[] args) {
        int[] array = {-8, -4, -3, 0, 1, 2, 4, 5, 8, 9};
        for (int i = 0; i < 20; i++) {
            solution(array, i);
            System.out.println("--------");
            PrintUniquePairAndTriad.printUniqueTriad(array, i);
            System.out.println("========");
        }
    }
}
