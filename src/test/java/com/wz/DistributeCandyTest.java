package com.wz;

import com.wz.others.DistributeCandy;

import java.util.Arrays;

/**
 * <p>根据得分情况粉糖果，相邻孩子得分高的糖果多、得分一样糖果数一样，求最少糖果数</p>
 *
 * @author wangzi
 */
public class DistributeCandyTest {
    private static int solution(int[] array) {
        int[] candy = new int[array.length];
        for (int i = 0; i < candy.length; i++) {
            candy[i] = 1;
        }

        // 正向遍历，右边比左边得分高时，糖果加1；得分一致，糖果一样
        for (int i = 0; i < array.length - 1; i++) {
            if (array[i + 1] > array[i]) {
                candy[i + 1] = candy[i] + 1;
            } else if (array[i + 1] == array[i]) {
                candy[i + 1] = candy[i];
            }
        }

        // 逆向遍历，左边比右边得分高时，糖果加1；得分一致，糖果一样
        for (int i = array.length - 1; i > 0; i--) {
            if (array[i - 1] > array[i]) {
                candy[i - 1] = Math.max(candy[i - 1], candy[i] + 1);
            } else if (array[i - 1] == array[i]) {
                candy[i - 1] = candy[i];
            }
        }
        return Arrays.stream(candy).sum();
    }

    public static void main(String[] args) {
        int times = 100;
        boolean result = true;
        for (int i = 0; i < times; i++) {
            int[] array = RandomUtils.genRandomArray();
            if (solution(array) != DistributeCandy.candyWithEqualOne(array)) {
                result = false;
                System.out.println("Error, array:" + Arrays.toString(array));
            }
        }
        if (result) {
            System.out.println("Past");
        }
    }
}
