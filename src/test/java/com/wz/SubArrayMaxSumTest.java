/**
 * <p>Title: SubArrayMaxSumTest</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/4/7</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz;

import com.wz.arrayandmatrix.SubArrayMaxSum;

import java.util.Arrays;

/**
 * <p>数组中的元素可正、可负、可0，求其中子数组的最大累加和</p>
 *
 * @author wangzi
 */
public class SubArrayMaxSumTest {
    private static int solution(int[] array) {
        int max = Integer.MIN_VALUE;
        int sum = 0;
        for (int cur : array) {
            sum += cur;
            max = Math.max(max, sum);
            // sum为负时不能作为产生最大累加和的基础，置0丢弃这部分
            sum = sum > 0 ? sum : 0;
        }
        return max;
    }

    public static void main(String[] args) {
        int times = 100;
        boolean result = true;
        for (int i = 0; i < times; i++) {
            int[] array = RandomUtils.genRandomArrayWithMinus();
            if (solution(array) != SubArrayMaxSum.maxSum(array)) {
                result = false;
                System.out.println("Error, array:" + Arrays.toString(array));
            }
        }
        if (result) {
            System.out.println("Past");
        }
    }
}
