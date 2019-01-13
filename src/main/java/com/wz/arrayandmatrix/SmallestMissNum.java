/**
 * <p>Title: SmallestMissNum</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/1/13</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.arrayandmatrix;

/**
 * <p>数组中未出现的最小正整数</p>
 *
 * @author wangzi
 */
public class SmallestMissNum {

    public static int missNum(int[] array) {
        if (array == null || array.length == 0) {
            return 1;
        }
        // array已经包含的正整数范围是array[1...left]
        int left = 0;
        // 在后续出现最优情况下，array可能包含的正整数范围是array[1...right]
        int right = array.length;
        while (left < right) {
            if (array[left] == left + 1) {
                // array包含的正整数范围扩展到array[1...left+1]
                left++;
            } else if (array[left] <= left || array[left] > right || array[array[left] - 1] == array[left]) {
                // array[left+1...right]上的数少了一个，将array最后位置的数放在left位置上，下一步检查这个数
                array[left] = array[--right];
            } else {
                // 发现了array[left+1...right]上的数，并且未重复，交换left与array[left]-1位置上的数
                swap(array, left, array[left] - 1);
            }
        }
        return left + 1;
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
        int[] array = {-1, 0, 2, 1, 3, 5};
        ;
        System.out.println(missNum(array));
    }
}
