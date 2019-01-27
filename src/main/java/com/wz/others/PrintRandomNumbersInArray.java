/**
 * <p>Title: PrintRandomNumbersInArray</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/1/27</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.others;

import java.util.Random;

/**
 * <p>从N个数中等概率随机打印M个数</p>
 *
 * @author wangzi
 */
public class PrintRandomNumbersInArray {
    public static void printRandom(int[] array, int m) {
        if (array == null || array.length == 0 || m < 0) {
            return;
        }
        m = Math.min(array.length, m);
        int count = 0;
        int index;
        while (count < m) {
            // 在[0,length-count)中等概率随机获取一个位置
            index = new Random().nextInt(array.length - count);
            System.out.println(array[index]);
            count++;
            // 与"当前最后一个元素"交换，保证下次不会再被选中
            swap(array, array.length - count, index);
        }
    }

    private static void swap(int[] array, int self, int other) {
        if (self == other) {
            return;
        }
        int tmp = array[self];
        array[self] = array[other];
        array[other] = tmp;
    }

    public static void main(String[] args) {
        int[] array = {6, 2, 3, 5, 1, 4};
        printRandom(array, 3);
    }
}
