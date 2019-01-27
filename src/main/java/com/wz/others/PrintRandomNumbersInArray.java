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
 * <p>
 *     给定一个长度N且没有重复元素的数组array和一个整数M，请等概率随机打印array中的M个数。
 *     要求：
 *        1、相同的数不要重复打印
 *        2、时间复杂度为O(M)，空间复杂度为O(1)
 *        3、可以改变array数组。
 *     解决方案：
 *        如果没有空间复杂度要求，可以用哈希表记录每个数是否已经被打印过，就可做到不重复打印。
 *        解决问题的关键点在于可以改变数组，具体过程如下：
 *        1、在[0,N-1]中随机得到一个位置a，然后打印array[a]
 *        2、交换array[a]与array[N-1]
 *        3、在[0,N-2]中随机得到一个位置b，然后打印array[b]，因为打印过的array[a]已经被换到N-1位置，所以这次不可能再次出现
 *        4、交换array[b]与array[N-2]
 *        5、在[0,N-3]中随机得到一个位置c，然后打印array[c]，因为打印过的array[a]和array[b]已经被换到N-1和N-2位置，
 *           所以这次不可能再次出现
 *        6、以此类推，直到打印M个数。
 *        总之，就是把随机选出来的数打印出来，然后将该数交换到当前范围中的最后位置，再把范围缩小，使得下次不会再选中。
 * </p>
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
