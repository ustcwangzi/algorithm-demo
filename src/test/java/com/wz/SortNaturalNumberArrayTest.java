package com.wz;

import com.wz.arrayandmatrix.SortNaturalNumberArray;

import java.util.Arrays;

/**
 * <p>对只包含1～N的数组进行排序</p>
 *
 * @author wangzi
 */
public class SortNaturalNumberArrayTest {
    private static int[] solution(int[] array) {
        for (int i = 0; i < array.length; i++) {
            // 发现值与位置不对应，立马调整
            while (array[i] != i + 1) {
                // array[i]的值应该是i+1，反过来值array[i]应该放在array[i]-1处
                swap(array, array[i] - 1, i);
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
            int[] array = RandomUtils.genShuffleArray();
            if (!Arrays.equals(solution(array), SortNaturalNumberArray.sortOne(array))
                    || !Arrays.equals(solution(array), SortNaturalNumberArray.sortTow(array))) {
                result = false;
                System.out.println("Error, array:" + Arrays.toString(array));
            }
        }
        if (result) {
            System.out.println("Past");
        }
    }
}
