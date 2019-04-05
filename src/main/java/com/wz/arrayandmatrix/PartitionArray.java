/**
 * <p>Title: PartitionArray</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/1/12</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.arrayandmatrix;

import java.util.Arrays;

/**
 * <p>数组的部分调整</p>
 * <p>
 *     问题一：
 *        给定一个有序数组array，调整array使得这个数组的左半部分变成无重复升序的，右半部分不作要求。
 *     问题二：
 *        给定一个数组array，其中只含有0、1、2三种值，请对array进行排序。
 *        另一种表述：数组中只含有红球、蓝球和黄球，请实现红球放在数组的左边，蓝球放在中间，黄球放在右边。
 *        另一种表述：给定一个值k，实现比k小的数放在数组的左边，等于k的数放在中间，比k大的数放在右边。
 *     问题一解答：
 *        1、生成变量uniqueIndex，array[0...uniqueIndex]上都是无重复且升序的，即uniqueIndex是这个区域最右的位置，
 *           初始时uniqueIndex=0，这个区域记为A
 *        2、利用index从左到右遍历数组，在array[uniqueIndex+1...index]上不保证无重复且升序，index是这个区域最右的位置，
 *           初始时index=1，这个区域记为B
 *        3、index向右移动，因为数组是有序的，如果array[index]!=array[uniqueIndex]，说明当前数array[index]应该加入区域A，
 *           交换array[uniqueIndex+1]与array[index]，此时区域A增加了一个数；如果array[index]==array[uniqueIndex]，
 *           说明当前数array[index]的值在之前已经加入到A区域了，不需要再次加入
 *        4、重复步骤三，直到数组遍历完。
 *     问题二解答：
 *        1、生成变量left，array[0...left]都是0，left是这个区域最右的位置，记为左区，初始时left=-1
 *        2、利用index从左到右遍数组，array[left+1...index]都是1，index是这个区域最右的位置，记为中区，初始时index=0
 *        3、生成变量right，array[right...N-1]上都是2，right是这个区域最左的位置，记为右区，初始时right=N
 *        4、index表示遍历到array的一个位置
 *        4.1、如果array[index]==1，这个值应该加入到中区，index++；
 *        4.2、如果array[index]==0，这个值应该加入到左区，array[left+1]是中区最左位置的数，将array[index]与array[left+1]交换后，
 *             左区就扩大了，index++；
 *        4.3、如果array[index]==2，这个值应该加入到右区，array[right-1]右区最左位置的数，将array[index]与array[right-1]交换后，
 *             右区就扩大了，此时array[index]的值未知，因此index不变。
 *        5、重复步骤四，直到index==right过程停止。
 *        遍历中的每一步，要么index增加，要么right减少，如果index==right过程停止。
 * </p>
 * <p>
 *     问题一时间复杂度为O(N)，空间复杂度为O(1)
 *     问题二时间复杂度为O(N)，空间复杂度为O(1)
 * </p>
 *
 * @author wangzi
 */
public class PartitionArray {

    public static int[] leftUnique(int[] array) {
        if (array == null || array.length < 2) {
            return array;
        }
        // array[0...uniqueIndex]无重复升序
        int uniqueIndex = 0;
        int index = 1;
        while (index < array.length) {
            // array[index]加入左边的无重复升序区域
            if (array[index++] != array[uniqueIndex]) {
                swap(array, ++uniqueIndex, index - 1);
            }
        }
        return array;
    }

    public static int[] partitionSort(int[] array) {
        if (array == null || array.length < 2) {
            return array;
        }

        int left = -1, index = 0, right = array.length;
        while (index < right) {
            if (array[index] == 0) {
                // 加入左区，index右移
                swap(array, ++left, index++);
            } else if (array[index] == 2) {
                // 加入右区，index不变
                swap(array, index, --right);
            } else {
                // 加入中区
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
        int[] array1 = {1, 2, 2, 2, 3, 3, 4, 5, 6, 6, 7, 7, 8, 8, 8, 9};
        System.out.println(Arrays.toString(leftUnique(array1)));
        int[] array2 = {2, 1, 2, 0, 1, 1, 2, 2, 0};
        System.out.println(Arrays.toString(partitionSort(array2)));
    }
}
