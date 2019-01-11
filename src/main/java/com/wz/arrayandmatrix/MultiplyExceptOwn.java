/**
 * <p>Title: MultiplyExceptOwn</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/1/11</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.arrayandmatrix;

import java.util.Arrays;

/**
 * <p>不包含本位置值的累乘结果数组</p>
 *
 * @author wangzi
 */
public class MultiplyExceptOwn {
    public static int[] multiplyOne(int[] array) {
        if (array == null || array.length < 2) {
            return null;
        }
        // 0的个数
        int zeroCount = 0;
        // 所有元素的累乘
        int all = 1;
        for (int cur : array) {
            if (cur != 0) {
                all *= cur;
            } else {
                zeroCount++;
            }
        }

        int[] result = new int[array.length];
        // 不存在0时，result[i]=all/array[i]
        if (zeroCount == 0) {
            for (int i = 0; i < array.length; i++) {
                result[i] = all / array[i];
            }
        }
        // 只有一个0时，值为0的那个位置结果为all，其他均为0
        if (zeroCount == 1) {
            for (int i = 0; i < array.length; i++) {
                if (array[i] == 0) {
                    result[i] = all;
                }
            }
        }
        return result;
    }

    public static int[] multiplyTwo(int[] array) {
        if (array == null || array.length < 2) {
            return null;
        }

        int[] result = new int[array.length];
        result[0] = array[0];
        for (int i = 1; i < array.length; i++) {
            // 此时result[i]为array[0]到array[i]的累乘
            result[i] = result[i - 1] * array[i];
        }

        // tmp用以记录从array[N-1]到array[i+1]的累乘
        int tmp = 1;
        for (int i = array.length - 1; i > 0; i--) {
            // 此时result[i]就是array[0...i]的累乘 * array[i+1...N-1]的累乘
            result[i] = result[i - 1] * tmp;
            tmp *= array[i];
        }
        result[0] = tmp;

        return result;
    }

    public static void main(String[] args) {
        int[] array = {1, 2, 3, 4};
        System.out.println(Arrays.toString(multiplyOne(array)));
        System.out.println(Arrays.toString(multiplyTwo(array)));
    }
}
