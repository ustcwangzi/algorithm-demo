package com.wz;

import com.wz.stackandqueue.AllLessNumSubArray;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Random;

public class AllLessNumSubArrayTest {

    private static int solution(int[] array, int num) {
        if (array == null || array.length == 0) {
            return 0;
        }

        Deque<Integer> minQueue = new LinkedList<>();
        Deque<Integer> maxQueue = new LinkedList<>();
        int i = 0, j = 0, result = 0;
        while (i < array.length) {
            while (j < array.length) {
                while (!minQueue.isEmpty() && array[minQueue.peekLast()] >= array[j]) {
                    minQueue.pollLast();
                }
                minQueue.addLast(j);
                while (!maxQueue.isEmpty() && array[maxQueue.peekLast()] <= array[j]) {
                    maxQueue.pollLast();
                }
                maxQueue.addLast(j);

                if (array[maxQueue.peekFirst()] - array[minQueue.peekFirst()] > num) {
                    break;
                }
                j++;
            }
            if (minQueue.peekFirst() == i) {
                minQueue.pollFirst();
            }
            if (maxQueue.peekFirst() == i) {
                maxQueue.pollFirst();
            }
            result += j - i;
            i++;
        }
        return result;
    }

    public static void main(String[] args) {
        int times = 100;
        boolean result = true;
        for (int i = 0; i < times; i++) {
            int[] array = RandomUtils.genRandomArray();
            int num = new Random().nextInt(5) + 1;
            if (solution(array, num) != AllLessNumSubArray.getCount(array, num)) {
                result = false;
                System.out.println("Error, array:" + Arrays.toString(array));
            }
        }
        if (result) {
            System.out.println("Past");
        }
    }
}
