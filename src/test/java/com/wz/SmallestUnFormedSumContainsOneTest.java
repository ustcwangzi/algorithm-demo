/**
 * <p>Title: SmallestUnFormedSumContainsOneTest</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/3/23</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz;

import com.wz.others.SmallestUnFormedSum;

import java.util.Arrays;

/**
 * <p>包含1的正数数组的最小不可组成和</p>
 *
 * @author wangzi
 */
public class SmallestUnFormedSumContainsOneTest {
    private static int solution(int[] array) {
        Arrays.sort(array);

        // [0...range]内的数都能够被array的子集累加得到
        int range = 0;
        for (int cur : array) {
            // array是有序的，后面的累加和不可能再出现range+1
            if (cur > range + 1) {
                return range + 1;
            }
            range += cur;
        }
        return range + 1;
    }

    public static void main(String[] args) {
        int times = 100;
        boolean result = true;
        for (int i = 0; i < times; i++) {
            int[] array = RandomUtils.genRandomArray();
            array[0] = 1;
            if (solution(array) != SmallestUnFormedSum.unformedSumContainOne(array)) {
                result = false;
                System.out.println("Error, array:" + Arrays.toString(array));
            }
        }
        if (result) {
            System.out.println("Past");
        }
    }
}
