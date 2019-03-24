/**
 * <p>Title: MaxAbsoluteBetweenLeftAndRightTest</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/3/24</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz;

import com.wz.others.MaxAbsoluteBetweenLeftAndRight;

import java.util.Arrays;

/**
 * <p>数组划分中leftMax与rightMax之差的最大值</p>
 *
 * @author wangzi
 */
public class MaxAbsoluteBetweenLeftAndRightTest {
    private static int solution(int[] array) {
        int max = Integer.MIN_VALUE;
        for (int cur : array) {
            max = Math.max(max, cur);
        }

        // max作为左部分最大值时，右部分只含有array[N-1]是最小的情况
        // max作为右部分最大值时，左部分只含有array[0]是最小的情况
        return max - Math.min(array[0], array[array.length - 1]);
    }

    public static void main(String[] args) {
        int times = 100;
        boolean result = true;
        for (int i = 0; i < times; i++) {
            int[] array = RandomUtils.genRandomArray();
            if (solution(array) != MaxAbsoluteBetweenLeftAndRight.maxAbsoluteThree(array)) {
                result = false;
                System.out.println("Error, array:" + Arrays.toString(array));
            }
        }
        if (result) {
            System.out.println("Past");
        }
    }
}
