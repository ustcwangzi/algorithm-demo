/**
 * <p>Title: SubArrayMaxProductTest</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/4/7</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz;

import com.wz.arrayandmatrix.SubArrayMaxProduct;

import java.util.Arrays;

/**
 * <p>数组中的元素可正、可负、可0，求其中子数组的最大累乘积</p>
 *
 * @author wangzi
 */
public class SubArrayMaxProductTest {
    private static double solution(double[] array) {
        // min:以array[i]结尾的最小累乘积，min:以array[i]结尾的最大累乘积
        double max = array[0], min = array[0], result = array[0];
        double maxEnd, minEnd;
        // 以array[i]结尾的最大累乘积只能有三种可能：
        // 1:array[i-1]的最大累乘积*当前值(2,3)，2:array[i-1]的最小累乘积*当前值(-2,-3)，3:当前值(0.1,1)
        for (int i = 1; i < array.length; i++) {
            minEnd = min * array[i];
            maxEnd = max * array[i];
            min = Math.min(Math.min(minEnd, maxEnd), array[i]);
            max = Math.max(Math.max(minEnd, maxEnd), array[i]);
            result = Math.max(max, result);
        }
        return result;
    }

    public static void main(String[] args) {
        int times = 100;
        boolean result = true;
        for (int i = 0; i < times; i++) {
            double[] array = RandomUtils.genRandomDoubleArray();
            if (solution(array) != SubArrayMaxProduct.maxProduct(array)) {
                result = false;
                System.out.println("Error, array:" + Arrays.toString(array));
            }
        }
        if (result) {
            System.out.println("Past");
        }
    }
}
