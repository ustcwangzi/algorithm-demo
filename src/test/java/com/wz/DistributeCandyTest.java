package com.wz;

import com.wz.others.DistributeCandy;

import java.util.Arrays;

public class DistributeCandyTest {
    private static int solution(int[] array) {
        int[] candy = new int[array.length];
        for (int i = 0; i < candy.length; i++) {
            candy[i] = 1;
        }

        for (int i = 0; i < array.length - 1; i++) {
            if (array[i + 1] > array[i]) {
                candy[i + 1] = candy[i] + 1;
            }
        }

        for (int i = array.length - 1; i > 0; i--) {
            if (array[i - 1] > array[i]) {
                candy[i - 1] = Math.max(candy[i - 1], candy[i] + 1);
            }
        }
        return Arrays.stream(candy).sum();
    }

    public static void main(String[] args) {
        int times = 100;
        boolean result = true;
        for (int i = 0; i < times; i++) {
            int[] array = RandomUtils.genRandomArray(i + 1);
            if (solution(array) != DistributeCandy.candyOne(array)) {
                result = false;
                System.out.println("Error, array:" + Arrays.toString(array));
            }
        }
        if (result) {
            System.out.println("Past");
        }
    }
}
