package com.wz;

import com.wz.stackandqueue.AllLessNumSubArray;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Random;

/**
 * <p>最大最小值之差小于等于num的子数组数量</p>
 *
 * @author wangzi
 */
public class AllLessNumSubArrayTest {

    /**
     * 使用两个双端队列用来维护子数组arr[i...j]的最小值和最大值，队头表示的就是子数组arr[i...j]的最小（大）值。
     * 生成两个整型变量i和j，用于表示子数组的范围，即arr[i...j]。
     * 令j不断向右移动，表示arr[i...j]一直向右扩张，并不断更新最大最小队列，一旦出现arr[i...j]不满足条件的情况，扩张停止
     * 此时，array[i...j-1]满足条件，即以array[i]开头的满足条件的子数组数量为j-i
     * 然后令i向右移动，表示开始考虑以array[i+1]开头的满足条件的子数组数量
     */
    private static int solution(int[] array, int num) {
        if (array == null || array.length == 0) {
            return 0;
        }

        // 队首为array[i...j]的最小值
        Deque<Integer> minQueue = new LinkedList<>();
        // 队首为array[i...j]的最大值
        Deque<Integer> maxQueue = new LinkedList<>();
        int i = 0, j = 0, result = 0;
        while (i < array.length) {
            while (j < array.length) {
                // 维持最小队列
                while (!minQueue.isEmpty() && array[minQueue.peekLast()] >= array[j]) {
                    minQueue.pollLast();
                }
                minQueue.addLast(j);
                // 维持最大队列
                while (!maxQueue.isEmpty() && array[maxQueue.peekLast()] <= array[j]) {
                    maxQueue.pollLast();
                }
                maxQueue.addLast(j);

                // 出现不满足条件的元素，不用再向后查找
                if (array[maxQueue.peekFirst()] - array[minQueue.peekFirst()] > num) {
                    break;
                }
                j++;
            }
            // 下一次循环从i+1开始
            if (minQueue.peekFirst() == i) {
                minQueue.pollFirst();
            }
            if (maxQueue.peekFirst() == i) {
                maxQueue.pollFirst();
            }
            // array[j]不满足条件，array[i...j-1]满足条件，长度为j-i
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
