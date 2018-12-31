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
 * <p>
 *     给定有序数组array和整数k，获取array中所有相加和为k的不降序二元组和三元组。
 *     比如，array=[-8,-4,-3,0,1,2,4,5,8,9]，k=10
 *        二元组为(1,9)，(2,8)；三元组为(-4,5,9)，(-3,4,9)，(-3,5,8)，(0,1,9)，(0,2,8)，(1,4,5)
 *     获取二元组：
 *        用左指针和右指针不断向中间压缩的方式获取二元组：
 *        1、设置left=0，right=array.length-1
 *        2、比较array[left]+array[right]的结果sum与k的大小
 *        2.1、sum==k，输出array[left],array[right]，left++，right--
 *        2.2、sum>k，right--
 *        2.3、sum<k，left++
 *        3、如果left<right，则重复步骤二，否则结束。
 *        在步骤2.1中，增加检查array[left]是否与array[left-1]相等，若相等则跳过，保证结果不重复。
 *     获取三元组：
 *        与获取二元组的方式类似。例如，array=[-8,-4,-3,0,1,2,4,5,8,9]，k=10。
 *        当三元组第一个数值为-8时，寻找-8后面的子数组中所有相加为18的不重复而元素；
 *        当三元组第一个数值为-4时，寻找-4后面的子数组中所有相加为14的不重复而元素；
 *        当三元组第一个数值为-3时，寻找-3后面的子数组中所有相加为13的不重复而元素。
 *        以此类推。
 *        在选择三元素第一个值时，检查是否与它的前一个值相等，若相等则跳过，保证结果不重复。
 * </p>
 * <p>
 *     获取二元组时间复杂度为O(N)，获取三元组时间复杂度为O(N*N)
 * </p>
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
