/**
 * <p>Title: SmallestUnFormedSum</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/1/24</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.others;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>正数数组的最小不可组成和</p>
 * <p>
 *     给定一个正整数数组array，其中所有的值都为整数，以下是最小不接组成和的概念：
 *     1、把array每个子集的所有元素加起来出现很多值，其中最小的记为min,最大的记为max；
 *     2、在区间[min,max]上，如果有数不可以被array某个子集相加得到，那么其中最小的那个数就是最小不可组成和；
 *     3、如果[min,max]所有的数都可以相加得到，那么max+1就是array的最小不可组成和。
 *     给定一个正整数数组array，求出array的最小不可组成和。
 *     另，若已知array中一定的包含1，求出最小不可组成和。
 *     例如：
 *        array=[3,2,5]，min为2，max为10，在[2,10]上4、6、9不能通过子集相加得到，最小不可组成和为4；
 *        array=[1,2,4]，min为1，max为7，在[1,7]上任何数都能通过子集相加得到，最小不可组成和为8。
 *     解决方案一：
 *        暴力递归。计算出每个子集的累加和，存到set中，然后从min到max范围开始检查，看哪个正数不在set中，第一个在set中的数就是结果。
 *        计算每个子集的累加和采用的是递归，0～i位置上已经完成决策，和为sum，决定i位置上的元素要或着不要，
 *        每次面临两个选择，向下不断分支(类似二叉树)，最终决策完成。
 *     解决方案二：
 *        动态规划。array中所有数的相加和即是该数组的最大累加和max，所有array子集的累加和必然在[0, max]区间上。
 *        生成长度为max+1的dp数组，dp[i]==true表示i这个累加和可以被array的子集相加得到，否则不能。
 *        如果array[0...i]成的子集可以累加出k，那么array[0...i+1]的子集中必然可以累加出k+arr[i+1]。
 *     一定包含1的情况：
 *        1、将array进行排序，排序后必然有array[0]==1；
 *        2、从左往右计算每个位置i的range。range表示当计算到位置arr[i]时，[1,range]上所有数都可以被array[0...i-1]的某一个子集累加出来。
 *           初始时range=0；
 *        3、如果arr[i]>range+1，因为array是有序的，后续不会再出现range+1，即array[0...i]上，range+1这个数一定不能累加出来，
 *           返回range+1即可。
 *           如果array[i]<=range+1，说明[1,range+array[i]]区间上所有的正数都可以被array[0...i]上的某一个子集累加出来，
 *           所以令range+=arr[i]，然后继续计算下一个位置。
 *        例如array=[3,8,1,2]，排序后为[1,2,3,8]，开始时range=0；
 *           计算到1时，1<=0+1，range更新为0+1=1；
 *           计算到2时，2<=1+1，range更新为1+2=3；
 *           计算到3时，3<=3+1，range更新为3+3=6；
 *           计算到8时，8>6+1，说明7不能通过子集累加得到，返回7。
 * </p>
 * <p>
 *     暴力递归中子集的个数为2^N，因此时间复杂度为O(2^N)，递归N层，因此空间复杂度为O(N)
 *     动态规划时间复杂度为O(N*max)，空间复杂度为O(N)
 *     包含1的情况下，时间复杂度为O(N*logN)，空间复杂度为O(1)
 * </p>
 *
 * @author wangzi
 */
public class SmallestUnFormedSum {

    /**
     * 暴力递归
     */
    public static int unformedSumOne(int[] array) {
        if (array == null || array.length == 0) {
            return 1;
        }

        int min = Integer.MAX_VALUE;
        int max = 0;
        for (int cur : array) {
            min = Math.min(cur, min);
            max += cur;
        }

        Set<Integer> set = new HashSet<>();
        // 计算所有子集的和
        process(0, 0, array, set);

        for (int num = min + 1; num < max; num++) {
            if (!set.contains(num)) {
                return num;
            }
        }

        return max + 1;
    }

    /**
     * 计算所有子集的累加和
     * 0～i位置上已经完成决策，和为sum，决定i位置上的元素要或者不要
     */
    private static void process(int i, int sum, int[] array, Set<Integer> set) {
        if (i == array.length) {
            set.add(sum);
            return;
        }
        // 不包含当前数array[i]的情况
        process(i + 1, sum, array, set);
        // 包含当前数array[i]的情况
        process(i + 1, sum + array[i], array, set);
    }

    /**
     * 动态规划
     */
    public static int unformedSumTwo(int[] array) {
        if (array == null || array.length == 0) {
            return 1;
        }

        int min = Integer.MAX_VALUE;
        int max = 0;
        for (int cur : array) {
            min = Math.min(cur, min);
            max += cur;
        }

        // dp[j]代表j这个累加和能否array的子集相加得到
        boolean[] dp = new boolean[max + 1];
        dp[0] = true;
        // 如果array[0...i]组成的子集可以累加出k，那么array[0...i+1]组成的子集必然可以累加出k+array[i+1]
        for (int cur : array) {
            for (int j = max; j >= cur; j--) {
                dp[j] = dp[j - cur] || dp[j];
            }
        }

        for (int num = min + 1; num < max; num++) {
            if (!dp[num]) {
                return num;
            }
        }
        return max + 1;
    }

    /**
     * array中一定包含1的情况
     */
    public static int unformedSumContainOne(int[] array) {
        if (array == null || array.length == 0) {
            return 1;
        }

        // 将array排序，必然有array[0]==1
        Arrays.sort(array);

        // [1,range]区间内得所有正数都可以被array[0...i-1]的某个子集相加表示
        int range = 0;
        for (int cur : array) {
            // array是有序的，array[i]往后的数都不会再出现range+1
            if (cur > range + 1) {
                return range + 1;
            }
            range += cur;
        }

        return range + 1;
    }

    public static void main(String[] args) {
        int[] array = {3, 2, 5};
        System.out.println(unformedSumOne(array));
        System.out.println(unformedSumTwo(array));

        array = new int[]{1, 2, 4};
        System.out.println(unformedSumOne(array));
        System.out.println(unformedSumTwo(array));
        System.out.println(unformedSumContainOne(array));
    }
}
