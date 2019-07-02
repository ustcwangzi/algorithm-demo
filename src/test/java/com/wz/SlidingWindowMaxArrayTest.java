package com.wz;

import com.wz.stackandqueue.SlidingWindowMaxArray;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Random;

/**
 * <p>生成窗口最大值数组</p>
 *
 * @author wangzi
 */
public class SlidingWindowMaxArrayTest {
    /**
     * 使用双端队列来维持当前的最大值，队首一直是当前窗口的最大值下标
     * 若当前元素大于等于队尾则弹出队尾，最后将当前元素入队；否则直接入队
     * 队首元素等于i-size，说明当前队首已过期，弹出队首
     */
    private static int[] solution(int[] array, int size) {
        if (array == null || size <= 1 || array.length < size) {
            return array;
        }

        Deque<Integer> deque = new LinkedList<>();
        int[] result = new int[array.length - size + 1];
        int index = 0;
        for (int i = 0; i < array.length; i++) {
            // 保证新进的值小于等于队尾
            while (!deque.isEmpty() && array[deque.peekLast()] <= array[i]) {
                deque.pollLast();
            }
            deque.addLast(i);

            // 队首已过期，弹出
            if (deque.peekFirst() == i - size) {
                deque.pollFirst();
            }
            // 记录当前窗口最大值，即队首对应的元素值
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
