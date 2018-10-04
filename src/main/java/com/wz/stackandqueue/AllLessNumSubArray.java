/**
 * <p>Title: AllLessNumSubArray</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/10/4</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.stackandqueue;

import java.util.Deque;
import java.util.LinkedList;

/**
 * <p>最大最小值之差小于等于num的子数组数量</p>
 * <p>
 *     给定数组array[]和整数num，计算有多少子数组满足max(array[i...j])-min(array[i...j]) <= num
 *     普通解法可先生成所有子数组，然后遍历找到最大最小值，再看一下哪些满足条件，复杂度较高
 *     分析问题可得到两个结论：
 *          1、如果子数组array[i...j]满足条件，那么array[i...j]中的每个子数组都满足条件
 *          2、如果子数组array[i...j]不满足条件，所有包含array[i...j]的子数组都不满足条件
 *     可使用两个双端队列来维护子数组的最大最小值，过程如下：
 *          1、生成两个双端队列minQueue、maxQueue维持动态窗口的最大最小值
 *          2、j不断右移，并同时更新minQueue和maxQueue，保证minQueue队首永远是最小值、保证maxQueue队首永远是最大值
 *             一旦出现不满足条件的，j右移过程停止，此时array[i...j-1]均满足条件，
 *             即以array[i]为第一个元素的子数组，满足条件的数量为j-i个
 *          3、i右移一个位置，重复步骤二，求出以array[i+1]为第一个元素的子数组，满足条件的数量
 *          4、根据步骤二和步骤三，依次求出以array[0]、array[1]...array[N-1]为第一个元素的子数组，满足条件的数量
 *      上述过程，所有下标最多进minQueue和maxQueue一次、出minQueue和maxQueue一次，
 *      i和j不断增加，从不减小，所以整个过程的时间复杂度为O(N)
 * </p>
 *
 * @author wangzi
 */
public class AllLessNumSubArray {

    public static int getCount(int[] array, int num) {
        if (array == null || array.length == 0) {
            return 0;
        }
        Deque<Integer> minQueue = new LinkedList<>();
        Deque<Integer> maxQueue = new LinkedList<>();
        int i = 0, j = 0, result = 0;
        while (i < array.length) {
            while (j < array.length) {
                // 新加入的值比队尾更小时，弹出队尾，保证队首永远是最小值
                while (!minQueue.isEmpty() && array[minQueue.peekLast()] >= array[j]) {
                    minQueue.pollLast();
                }
                minQueue.addLast(j);

                // 新加入的值比队尾更大时，弹出队尾，保证队首永远是最大值
                while (!maxQueue.isEmpty() && array[maxQueue.peekLast()] <= array[j]) {
                    maxQueue.pollLast();
                }
                maxQueue.addLast(j);

                if (array[maxQueue.getFirst()] - array[minQueue.getFirst()] > num) {
                    break;
                }
                j++;
            }

            // 下一轮为i+1作为子数组第一个元素
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
        int[] array = {1, 5, 6, 2, 3, 9, 6, 7, 8};
        System.out.println(getCount(array, 3));
    }
}
