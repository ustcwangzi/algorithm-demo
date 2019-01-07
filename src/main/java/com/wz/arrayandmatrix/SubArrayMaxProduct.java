/**
 * <p>Title: SubArrayMaxProduct</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/1/8</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.arrayandmatrix;

/**
 * <p>子数组的最大累乘积</p>
 *
 * @author wangzi
 */
public class SubArrayMaxProduct {
    public static double maxProduct(double[] array) {
        if (array == null || array.length == 0) {
            return 0;
        }

        double max = array[0], min = array[0], result = array[0];
        double maxEnd, minEnd;
        for (int i = 1; i < array.length; i++) {
            // 以array[i-1]结尾的最大乘积 * array[i]
            maxEnd = max * array[i];
            // 以array[i-1]结尾的最小乘积 * array[i]
            minEnd = min * array[i];
            // 以array[i]结尾的最大乘积
            max = Math.max(Math.max(maxEnd, minEnd), array[i]);
            // 以array[i]结尾的最小乘积
            min = Math.min(Math.min(maxEnd, minEnd), array[i]);
            // 结果更新
            result = Math.max(result, max);
        }
        return result;
    }

    public static void main(String[] args) {
        double[] array = {-2.5, 4, 0, 3, 0.5, 8, -1};
        System.out.println(maxProduct(array));
    }
}
