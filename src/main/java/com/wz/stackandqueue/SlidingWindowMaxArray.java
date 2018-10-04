/**
 * <p>Title: SlidingWindowMaxArray</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/10/3</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.stackandqueue;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;

/**
 * <p>生成窗口最大值数组</p>
 * <p>
 *     大小为window的窗口从数组array的最左侧滑动到最右侧，获取窗口每次的最大值
 *     最简单的做法是维护一个最大值数组，遍历时逐个比较，但复杂度为O(N*W)
 *     另一个复杂度为O(N)的实现是：
 *        使用双端队列deque存放数组下标，当前遍历到array[i]，deque的放入规则为：
 *          1、deque为空，直接放入i
 *          2、deque不空，队尾下标为j，
 *          2.1、若array[j]>array[i]，将i放入队尾
 *          2.2、若array[j]<=array[i]，将j弹出，继续deque的放入规则
 *        deque的弹出规则为：
 *          若deque队头下标等于i-window，说明当前队头下标已过期，弹出队头
 *        保证队首所代表的值是当前窗口的最大值
 *      根据以上规则，假设当前数组为{4,3,5,3,3,6}，整个过程为：
 *          arr[0]=4,deque={0} ->
 *          arr[1]=3,3<4,deque={0,1} ->
 *          arr[2]=5,5>3,deque={0},5>4,deque={},deque={2} ->
 *          arr[3]=3,3<5,deque={2,3} ->
 *          arr[4]=3,3==3,deque={2},deque={2,4} ->
 *          arr[5]=6,6>3,deque={2},6>5,deque={},deque={5}
 * </p>
 *
 * @author wangzi
 */
public class SlidingWindowMaxArray {

    public static int[] getMaxWindow(int[] array, int window) {
        if (array == null || window < 1 || array.length < window) {
            return new int[]{};
        }

        // 存放数组array的下标
        Deque<Integer> deque = new LinkedList<>();
        int[] resultArray = new int[array.length - window + 1];
        int index = 0;
        for (int i = 0; i < array.length; i++) {
            // 保证新进的值小于队尾，队首代表的值一直是当前最大值
            while (!deque.isEmpty() && array[deque.peekLast()] <= array[i]) {
                deque.pollLast();
            }
            deque.addLast(i);
            // 队首下标等于i-window，说明当前队首下标已过期，弹出即可
            if (deque.peekFirst() == i - window) {
                deque.pollFirst();
            }
            // 队首对应的值，就是当前窗口最大值
            if (i >= window - 1) {
                resultArray[index++] = array[deque.peekFirst()];
            }
        }
        return resultArray;
    }

    public static void main(String[] args) {
        int[] arr = {4, 3, 5, 4, 3, 3, 6, 7};
        int[] result = getMaxWindow(arr, 3);
        Arrays.stream(result).forEach(System.out::println);
    }
}
