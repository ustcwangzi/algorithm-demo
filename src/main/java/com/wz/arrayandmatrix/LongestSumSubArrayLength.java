/**
 * <p>Title: LongestSumSubArrayLength</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/1/1</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.arrayandmatrix;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>无序数组中累加和为给定值的最长子数组长度</p>
 * <p>
 *     问题一：
 *        给定一个无序数组array，每个值均为正数，再给定一个正数k，求出array的所有子数组中元素之和为k的最长子数组长度。
 *     问题二：
 *        给定一个无序数组array，其中元素可正、可负、可0，再给定一个整数k，求出array的所有子数组中元素之和为k的最长子数组长度。
 *     问题三：
 *        给定一个无序数组array，其中元素可正、可负、可0，求出array的所有子数组中正数和负数个数相等的最长子数组长度。
 *     问题四：
 *        给定一个无序数组array，其中元素只有1和0，求出array的所有子数组中0和1的个数相等的最长子数组长度。
 *     问题一解答：
 *        1、用left和right标记子数组的左右位置，代表子数组array[left...right]，开始时left=0，right=0
 *        2、sum代表array[left...right]中元素之和，开始时sum=array[0]
 *        3、result记录累加和为k的所有子数组中最大子数组的长度，开始时result=0
 *        4、比较sum与k
 *        4.1、sum==k，说明array[left...right]的累加和为k，若array[left...right]的长度大于result，则更新result。
 *             此时因为数组中所有元素均为正数，那么从left开始，在right位置之后的子数组即array[left...i](i>right)，累加和一定大于k，
 *             所以，令left+1，表示接下来检查left之后的子数组，同时令sum-=array[left]。
 *        4.2、sum<k，说明array[left...right]需要假设right之后的元素，累加和才能达到k，所以令right+1，sum+=array[right]
 *        4.3、sum>k，说明right位置之后的子数组，即array[left...i](i>right)，累加和一定大于k，令left+1，sum-=array[left]
 *        5、如果right<array.length，重复步骤四，都在返回result。
 *     问题二解答：
 *     问题三解答：
 *     问题四解答：
 * </p>
 * <p>
 *     问题一时间复杂度为O(N)，空间复杂度为O(1)
 * </p>
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

    /**
     * 无序数组中累加和为k的最长子数组长度
     */
    public static int getMaxLengthInArray(int[] array, int k) {
        if (array == null || array.length == 0) {
            return 0;
        }

        // 记录累加和key首次出现的位置value
        Map<Integer, Integer> map = new HashMap<>();
        // 以免漏掉从0开始的子数组
        map.put(0, -1);
        // array[0...i]的累加和
        int sum = 0;
        int result = 0;
        for (int i = 0; i < array.length; i++) {
            // s(i)为sum
            sum += array[i];
            // s(j)为sum-k，array[j+1...i]的累加=s(i)-s(j)=k
            if (map.containsKey(sum - k)) {
                result = Math.max(i - map.get(sum - k), result);
            }
            if (!map.containsKey(sum)) {
                map.put(sum, i);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int[] array1 = {1, 2, 1, 1, 1};
        System.out.println(getMaxLengthInPositiveArray(array1, 3));
        int[] array2 = {1, 2, 3, 3};
        System.out.println(getMaxLengthInArray(array2, 6));
    }
}
