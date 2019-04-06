/**
 * <p>Title: MultiplyExceptOwnTest</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/4/6</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz;

import com.wz.arrayandmatrix.MultiplyExceptOwn;

import java.util.Arrays;

/**
 * <p>数组中不包含自身的累乘结果</p>
 *
 * @author wangzi
 */
public class MultiplyExceptOwnTest {
    private static int[] solution(int[] array) {
        // 0的个数
        int zeroCount = 0;
        // 累乘结果
        int all = 1;
        for (int cur : array) {
            if (cur == 0) {
                zeroCount++;
            } else {
                all *= cur;
            }
        }
        int[] result = new int[array.length];
        // 没有0，直接使用all/cur
        if (zeroCount == 0) {
            for (int i = 0; i < array.length; i++) {
                result[i] = all / array[i];
            }
        }
        // 只有一个0，为0的那个为all，其他都是0
        if (zeroCount == 1) {
            for (int i = 0; i < array.length; i++) {
                if (array[i] == 0) {
                    result[i] = all;
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int times = 100;
        boolean result = true;
        for (int i = 0; i < times; i++) {
            int[] array = RandomUtils.genRandomArray();
            if (!Arrays.equals(solution(array), MultiplyExceptOwn.multiplyOne(array))) {
                result = false;
                System.out.println("Error, array:" + Arrays.toString(array));
            }
        }
        if (result) {
            System.out.println("Past");
        }
    }
}
