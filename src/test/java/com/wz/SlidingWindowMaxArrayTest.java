package com.wz;

import com.wz.stackandqueue.SlidingWindowMaxArray;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Random;

public class SlidingWindowMaxArrayTest {
    private static int[] solution(int[] array, int size) {
        if (array == null || size <= 1 || array.length < size) {
            return array;
        }

        Deque<Integer> deque = new LinkedList<>();
        int[] result = new int[array.length - size + 1];
        int index = 0;
        for (int i = 0; i < array.length; i++) {
            while (!deque.isEmpty() && array[deque.peekLast()] <= array[i]) {
                deque.pollLast();
            }
            deque.addLast(i);
            if (deque.peekFirst() == i - size) {
                deque.pollFirst();
            }
            if (i >= size - 1) {
                result[index++] = array[deque.peekFirst()];
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int times = 100;
        boolean result = true;
        for (int i = 0; i < times; i++) {
            int[] array = RandomUtils.genRandomArray();
            int size = new Random().nextInt(5) + 1;
            if (!Arrays.equals(solution(array, size), SlidingWindowMaxArray.getMaxWindow(array, size))) {
                result = false;
                System.out.println("Error, array:" + Arrays.toString(array));
            }
        }
        if (result) {
            System.out.println("Past");
        }
    }
}
