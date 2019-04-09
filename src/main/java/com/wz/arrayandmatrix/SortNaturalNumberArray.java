/**
 * <p>Title: SortNaturalNumberArray</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/1/4</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.arrayandmatrix;

import java.util.Arrays;

/**
 * <p>自然数数组的排序</p>
 * <p>
 *     给定一个长度为N的整型数组array，其中有N个互不相等的自然数1～N，对array进行排序，但不要对0～N-1位置进行直接赋值替换成1～N。
 *     array排序完成后，下标从0到N-1位置上依次放着1～N，即array[index]=index+1。
 *     解决方案一：
 *        1、从左到右遍历array，假设当前遍历到位置i
 *        2、如果array[i]==i+1，说明当前位置不需要调整，继续遍历下一个位置
 *        3、如果array[i]!=i+1，说明此时i位置的数array[i]不应该放在i位置上，接下来进行跳的过程
 *           以[1,2,5,3,4]为例来说明，假设遍历到位置2，5应该放在位置4上，把5放过去，数组变成[1,2,5,3,5]；
 *           同时4被替换下来，而4应该放在位置3上，把4放过去，数组变成[1,2,5,4,5]；
 *           同时3被替换下来，而3应该放在位置2上，把3放过去，数组变成[1,2,3,4,5]；
 *           又回到位置2，发现此时array[i]==i+1，继续遍历下一个位置。
 *     解决方案二：
 *        1、从左到右遍历array，假设当前遍历到位置i
 *        2、如果array[i]==i+1，说明当前位置不需要调整，继续遍历下一个位置
 *        3、如果array[i]!=i+1，说明此时i位置的数array[i]不应该放在i位置上，接下来将在i位置上进行交换过程
 *           以[1,2,5,3,4]为例来说明，假设遍历到位置2，5应该放在位置4上，将位置4上的数4和5进行交换，数组变成[1,2,4,3,5]；
 *           但此时array[2]!=3，4应该放在位置3上，将位置3上的数3和4进行交换，数组变成[1,2,3,4,5]；此时array[2]==3，遍历下一个位置。
 * </p>
 * <p>
 *     时间复杂度为O(N)，空间复杂度为O(1)
 * </p>
 *
 * @author wangzi
 */
public class SortNaturalNumberArray {

    public static int[] sortOne(int[] array) {
        if (array == null || array.length < 2) {
            return array;
        }

        int tmp, next;
        int[] result = Arrays.copyOf(array, array.length);
        for (int i = 0; i < result.length; i++) {
            tmp = result[i];
            // 进行跳的过程，跳一圈回到原位置后，i位置上数据正确
            while (result[i] != i + 1) {
                // 被替换下来的数
                next = result[tmp - 1];
                // tmp放在正确的位置上
                result[tmp - 1] = tmp;
                tmp = next;
            }
        }
        return result;
    }

    public static int[] sortTow(int[] array) {
        if (array == null || array.length < 2) {
            return array;
        }

        int tmp;
        int[] result = Arrays.copyOf(array, array.length);
        for (int i = 0; i < result.length; i++) {
            // i位置上直接进行数据交换
            while (result[i] != i + 1) {
                tmp = result[result[i] - 1];
                result[result[i] - 1] = result[i];
                result[i] = tmp;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int[] array = {8, 2, 1, 6, 9, 3, 7, 5, 4};
        System.out.println(Arrays.toString(sortOne(array)));

        array = new int[]{8, 2, 1, 6, 9, 3, 7, 5, 4};
        System.out.println(Arrays.toString(sortTow(array)));
    }
}
