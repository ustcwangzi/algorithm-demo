/**
 * <p>Title: SubArrayMaxSum</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/1/5</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.arrayandmatrix;

/**
 * <p>子数组的最大累加和</p>
 * <p>
 *     给定一个数组array，获取子数组的最大累加和。
 *     例如，array=[1,-2,3,5,-2,6,-1]，所有子数组中，[3,5,-2,6]可得到最大累加和12，返回12。
 *     解决方案：
 *        从左到右遍历array，使用sum记录每一步的累加和，遍历到正数时sum增加，遍历到负数时sum减少，
 *        当sum<0时，说明累加到当前数出现了小于0的结果，那么累加的这部分肯定不能作为产生最大累加和的子数组的左侧部分，
 *        此时令sum=0，表示从下一个数开始累加。当sum>=0时，每一次的累加都可能是最大的累加和，使用max记录sum出现的最大值。
 *        即sum累加成负数就置为0重新累加，max记录sum的最大值即可。
 *        以array=[1,-2,3,5,-2,6,-1]为例说明以上过程：
 *        1、遍历到1，sum=sum+1=1，max更新成1；
 *        2、遍历到-2，sum=sum-2=-1，出现负的累加和，令sum=0，max不变；
 *        3、遍历到3，sum=sum+3=3，max更新成3；
 *        4、遍历到5，sum=sum+5=8，max更新成8；
 *        5、遍历到-2，sum=sum-2=6，虽然累加了负数，但是sum依然大于0，max不变；
 *        6、遍历到6，sum=sum+6=12，max更新成12；
 *        7、遍历到-1，sum=sum-1=11，max不变。
 *        返回12。
 * </p>
 * <p>
 *     时间复杂度为O(N)，空间复杂度为O(1)
 * </p>
 *
 * @author wangzi
 */
public class SubArrayMaxSum {

    public static int maxSum(int[] array) {
        if (array == null || array.length == 0) {
            return 0;
        }
        int max = Integer.MIN_VALUE;
        int sum = 0;
        for (int cur : array) {
            sum += cur;
            max = Math.max(max, sum);
            sum = sum < 0 ? 0 : sum;
        }
        return max;
    }

    public static void main(String[] args) {
        int[] array = {-2, -3, -5, 40, -10, -10, 100, 1};
        System.out.println(maxSum(array));

        array = new int[]{-2, -3, -5, 0, 1, 2, -1};
        System.out.println(maxSum(array));

        array = new int[]{-2, -3, -5, -1};
        System.out.println(maxSum(array));
    }
}
