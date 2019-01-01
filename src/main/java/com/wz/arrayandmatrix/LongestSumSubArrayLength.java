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
 *        s(i)代表子数组array[0...i]所有元素的累加和，由此可以得到array[j...i]的累加和为s(i)-s(j-1)，
 *        因为s(i)=sum{array[0...i]}=sum{array[0...j-1]}+sum{array[j...i]}=s(j-1)+sum{array[j...i]}，
 *        所以sum{array[j...i]}=s(i)-s(j-1)。
 *        sum代表array[0...i]所以元素的累加和；result代表累加和为k的最长子数组长度；
 *        map的key为累加过程中出现的sum，value为sum最早出现的位置。
 *        从左到右遍历array，假设当前遍历到array[i]
 *        1、令sum+=array[i]，即目前所有元素的累加和s(i)，检查map中是否存在sum-k
 *        1.1、若sum-k存在，从map中去除sum-k对应的value，记为j，j代表从左到右的累加过程中第一次出现结果sum-k的位置，
 *             又sum{array[j...i]}=s(i)-s(j-1)，可得：sum{array[j+1...i]}=s(i)-s(j)=sum-(sum-k)=k，
 *             同时因为map中只记录每个累加值最早出现的位置，所以此时的array[j+1...i]是在必须以array[i]结尾的所有子数组中，
 *             累加和为k的最长子数组，如果其长度大于result，更新result
 *        1.2、若sum-k不存在，说明必须以array[i]结尾的情况下，没有累加和为k的子数组
 *        2、检查当前sum是否在map中，如不存在，将(sum,i)记录到map中。
 *        根据sum{array[j+1...i]}=s(i)-s(j)可知，如果从位置0开始累加，会导致j+1==1，也就是所有从0开始子数组都会被忽略，
 *        因此，应该从-1位置开始累加，即在遍历之前将(0,-1)存入map。
 *     问题三解答：
 *        与问题二的解答类似，只需要将数组中正数全部变成1，负数全部变成-1，0不变，然后求累加和为0的最长子数组长度即可。
 *     问题四解答：
 *        与问题二的解答类似，只需要将数组中0全部变成-1，1不变，然后求累加和为0的最长子数组长度即可。
 * </p>
 * <p>
 *     问题一时间复杂度为O(N)，空间复杂度为O(1)
 *     问题二时间复杂度为O(N)，空间复杂度为O(N)
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
