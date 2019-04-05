/**
 * <p>Title: PartitionArrayTest</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/4/5</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz;

import com.wz.arrayandmatrix.PartitionArray;

import java.util.Arrays;
import java.util.Random;

/**
 * <p>将只包含0,1,2的数组划分为左区间全是0，中间全是1，右区间全是2</p>
 *
 * @author wangzi
 */
public class PartitionArrayTest {
    private static int[] solution(int[] array) {
        int left = -1, index = 0, right = array.length;
        while (index < right) {
            if (array[index] == 0) {
                // array[0...left]全是0，为0时加入该区间，index右移
                swap(array, index++, ++left);
            } else if (array[index] == 2) {
                // array[right..N-1]全是2，为2时加入该区间，right左移
                swap(array, index, --right);
            } else {
                // array[left+1...index]全是1，为1时加入该区间，index右移
                index++;
            }
        }
        return array;
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
        int times = 100;
        boolean result = true;
        for (int i = 0; i < times; i++) {
            int[] array = new int[times];
            for (int j = 0; j < array.length; j++) {
                array[j] = new Random().nextInt(2) + 1;
            }
            if (!Arrays.equals(solution(array), PartitionArray.partitionSort(array))) {
                result = false;
                System.out.println("Error, array:" + Arrays.toString(array));
            }
        }
        if (result) {
            System.out.println("Past");
        }
    }
}
