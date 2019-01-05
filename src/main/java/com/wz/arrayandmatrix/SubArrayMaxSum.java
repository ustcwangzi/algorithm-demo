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
