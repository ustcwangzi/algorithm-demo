/**
 * <p>Title: QuickThreeWay</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/8/11</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.sort;

import com.wz.utils.SortUtils;

/**
 * <p>三向快速排序</p>
 * <p>
 *     与快速排序相比，增加了一个等于切分元素的区域
 *     将数组切分成三部分们分别对应于小于、等于、大于切分元素的数组元素
 *     维护一个指针 lt 使得arr[low...lt-1]中的元素都小于 v
 *     一个指针 gt 使得arr[gt+1...high]中的元素都大于 v
 *     一个指针 i 使得arr[lt...i-1]中的元素都等于 v，arr[i...gt]中的元素都还为确定
 *     初识时，i和low相等，使i递增，对于arr[i]：
 *     arr[i]小于v，将arr[lt]和arr[i]交换，将lt和i加一
 *     arr[i]大于v，将arr[gt]和arr[i]交换，将gt减一
 *     arr[i]等于v，将i加一
 * </p>
 *
 * @author wangzi
 */
public class QuickThreeWay {
    public static void sort(Comparable[] arr) {
        sort(arr, 0, arr.length - 1);
    }

    private static void sort(Comparable[] arr, int low, int high) {
        if (high <= low) {
            return;
        }
        int lt = low, gt = high;
        Comparable v = arr[low];
        int i = low;
        while (i <= gt) {
            int cmp = arr[i].compareTo(v);
            if (cmp < 0) {
                SortUtils.exch(arr, lt++, i++);
            } else if (cmp > 0) {
                SortUtils.exch(arr, i, gt--);
            } else {
                i++;
            }
        }
        sort(arr, low, lt - 1);
        sort(arr, gt + 1, high);
    }

    public static void main(String[] args) {
        SortUtils.show(SortUtils.arr);
        sort(SortUtils.arr);
        assert SortUtils.isSorted(SortUtils.arr);
        SortUtils.show(SortUtils.arr);
    }
}
