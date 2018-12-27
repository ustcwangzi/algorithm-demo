/**
 * <p>Title: MinLengthForSort</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/12/27</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.arrayandmatrix;

/**
 * <p>需要排序的最短子数组长度</p>
 * <p>
 *     给定一个无序数组array，求出需要排序的最短子数组长度。
 *     例如：
 *        array=[1, 5, 3, 4, 2, 6, 7]，返回4，因为只有[5, 3, 4, 2]需要排序。
 *     解决方案：
 *        从右到左遍历array，遍历过程中记录右侧出现的最小值min，假设当前遍历到array[i]，如果array[i]>min，说明要整体有序，
 *        min值必然要移到array[i]的左边，用noMinIndex记录最左边出现这种情况的位置。如果遍历完成后noMinIndex依然为-1，
 *        说明从右到左始终不升序，原数组就是有序的，直接返回0。
 *        从左到右遍历array，遍历过程中记录左侧出现的最大值max，假设当前遍历到array[i]，如果array[i]<max，说明要整体有序，
 *        max值必然要移到array[i]的右边，用noMaxIndex记录最右边出现这种情况的位置。
 *        遍历完成后，array[noMinIndex...noMaxIndex]就是真正需要排序的部分，返回它的长度即可。
 *        以array=[1, 5, 3, 4, 2, 6, 7]为例说明上述过程：
 *             array: 7  6  2  4  3  5  1
 *               min: 7  6  2  2  2  2  1
 *        noMinIndex:-1 -1 -1  3  2  1  1
 *        ----------------------------------
 *             array: 1  5  3  4  2  6  7
 *               max: 1  5  5  5  5  6  7
 *        noMaxIndex:-1 -1  2  3  4  4  4
 * </p>
 * <p>时间复杂度为O(N)，空间复杂度为O(1)</p>
 *
 * @author wangzi
 */
public class MinLengthForSort {
    public static int getMinLength(int[] array) {
        if (array == null || array.length < 2) {
            return 0;
        }
        int min = array[array.length - 1];
        int noMinIndex = -1;
        // 从右到左遍历，找到出现的最小值min，及大于最小值的位置noMinIndex
        for (int i = array.length - 2; i > -1; i--) {
            if (array[i] > min) {
                noMinIndex = i;
            } else {
                min = Math.min(min, array[i]);
            }
        }
        // 说从左到右始终不升序，即数组有序
        if (noMinIndex == -1) {
            return 0;
        }

        int max = array[0];
        int noMaxIndex = -1;
        // 从左到右遍历，找到出现的最大值max，及小于最大值的位置noMaxIndex
        for (int i = 1; i < array.length; i++) {
            if (array[i] < max) {
                noMaxIndex = i;
            } else {
                max = Math.max(max, array[i]);
            }
        }

        return noMaxIndex - noMinIndex + 1;
    }

    public static void main(String[] args) {
        int[] array = {1, 2, 4, 7, 10, 11, 7, 12, 6, 7, 16, 18, 19};
        System.out.println(getMinLength(array));
    }
}
