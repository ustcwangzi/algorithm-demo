/**
 * <p>Title: LongestIntegratedLengthTest</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/4/14</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz;

import com.wz.arrayandmatrix.LongestIntegratedLength;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * <p></p>
 *
 * @author wangzi
 */
public class LongestIntegratedLengthTest {
    private static int solution(int[] array) {
        int result = 0;
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < array.length; i++) {
            int min = Integer.MAX_VALUE;
            int max = Integer.MIN_VALUE;
            for (int j = i; j < array.length; j++) {
                // 有重复元素时直接退出
                if (set.contains(array[j])) {
                    break;
                }
                set.add(array[j]);
                min = Math.min(min, array[j]);
                max = Math.max(max, array[j]);
                if (max - min == j - i) {
                    result = Math.max(result, j - i + 1);
                }
            }
            set.clear();
        }
        return result;
    }

    public static void main(String[] args) {
        int times = 100;
        boolean result = true;
        for (int i = 0; i < times; i++) {
            int[] array = RandomUtils.genRandomArray();
            if (solution(array) != LongestIntegratedLength.getMaxLengthTwo(array)) {
                result = false;
                System.out.println("Error, array:" + Arrays.toString(array));
            }
        }
        if (result) {
            System.out.println("Past");
        }
    }
}
