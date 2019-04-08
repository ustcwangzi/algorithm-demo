package com.wz;

import com.wz.arrayandmatrix.EvenInEvenOddInOdd;

import java.util.Arrays;

public class EvenInEvenOddInOddTest {
    private static int[] solution(int[] array) {
        int even = 0, odd = 1;
        int end = array.length - 1;
        while (even <= end && odd <= end) {
            if ((array[end] & 1) == 0) {
                swap(array, even, end);
                even += 2;
            } else {
                swap(array, odd, end);
                odd += 2;
            }
        }
        return array;
    }

    private static void swap(int[] array, int self, int other) {
        int tmp = array[self];
        array[self] = array[other];
        array[other] = tmp;
    }

    public static void main(String[] args) {
        int times = 100;
        boolean result = true;
        for (int i = 0; i < times; i++) {
            int[] array = RandomUtils.genRandomArray();
            if (!Arrays.equals(solution(array), EvenInEvenOddInOdd.modify(array))) {
                result = false;
                System.out.println("Error, array:" + Arrays.toString(array));
            }
        }
        if (result) {
            System.out.println("Past");
        }
    }
}
