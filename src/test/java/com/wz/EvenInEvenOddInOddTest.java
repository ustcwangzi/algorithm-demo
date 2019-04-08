package com.wz;

import com.wz.arrayandmatrix.EvenInEvenOddInOdd;

import java.util.Arrays;

/**
 * <p>调整数组使奇数位置都是奇数或者偶数位置都是偶数</p>
 *
 * @author wangzi
 */
public class EvenInEvenOddInOddTest {
    private static int[] solution(int[] array) {
        // even:已调整的最左偶数下标，odd:已调整的最左奇数下标
        int even = 0, odd = 1;
        int end = array.length - 1;
        // 每次检查array[N-1]的奇偶，偶数则和even交换，奇数则和odd交换
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
        if (array[self] == array[other]) {
            return;
        }
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
