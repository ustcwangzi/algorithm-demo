/**
 * <p>Title: BubbleSort</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/9/15</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.daily;

import java.util.Arrays;

/**
 * <p>冒泡排序</p>
 * <p>
 *     排序思想：
 *       自上而下对相邻的两个数依次进行比较和调整，让较大的数往下沉，较小的往上冒
 *     第一种方法：
 *       如果在某一趟的冒泡途中没有出现数据交换，就说明数据已有序，可终止循环，消除掉不必要的比较
 *     第二种方法：
 *       如果在某一趟的冒泡途中最后的交换出现在pos的位置，那么表示pos位置以后都已经排好序
 * </p>
 * @author wangzi
 */
public class BubbleSort {
    public static void sort1(int[] array) {
        if (array == null || array.length <= 1) {
            return;
        }

        int loopTimes = 0;
        boolean hasExchange = true;
        // 外层循环，控制排序趟数，某一趟没有进行数据交换，则说明已排序完成
        for (int i = 0; hasExchange && i < array.length - 1; i++) {
            hasExchange = false;
            // 内层循环，控制每一趟进行排序的元素数
            for (int j = 0; j < array.length - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    hasExchange = true;
                }
            }
            loopTimes++;
        }
        System.out.println("比较趟数：" + loopTimes);
    }

    public static void sort2(int[] array) {
        if (array == null || array.length <= 1) {
            return;
        }
        int loopTimes = 0, pos;
        // 外层循环，控制排序趟数，pos之后的数据已有序
        for (int i = 0; i < array.length - 1; i = array.length - pos) {
            // pos=0是必须的，不然没有出现数据交换的时候会出现死循环
            pos = 0;
            // 内层循环，控制每一趟进行排序的元素数
            for (int j = 0; j < array.length - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    // 从j+1开始的往后的数据已经排好序
                    pos = j + 1;
                }
            }
            loopTimes++;
        }
        System.out.println("比较趟数：" + loopTimes);
    }

    public static void main(String[] args) {
        int[] array1 = {50, 13, 55, 97, 27, 38, 49, 65};
        sort1(array1);
        System.out.println(Arrays.toString(array1));
        int[] array2 = {50, 13, 55, 97, 27, 38, 49, 65};
        sort2(array2);
        System.out.println(Arrays.toString(array2));
    }
}
