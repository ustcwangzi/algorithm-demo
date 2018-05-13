/**
 * <p>Title: Merge</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/5/13</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.sort;

import com.wz.utils.SortUtils;

/**
 * <p>归并排序</p>
 * <p>
 *     将数组分成二组A，B，如果这二组组内的数据都是有序的，那么就可以很方便的将这二组数据进行排序。
 *     如何让这二组组内数据有序了？
 *     可以将A，B组各自再分成二组。依次类推，当分出来的小组只有一个数据时，
 *     可以认为这个小组组内已经达到了有序，然后再合并相邻的二个小组就可以了。
 *     这样通过先递归的分解数列，再合并数列就完成了归并排序。
 * </p>
 *
 * @author wangzi
 */
public class Merge {
    /**
     * 辅助空间
     */
    private static Comparable[] aux;

    public static void sort(Comparable[] arr){
        aux = new Comparable[arr.length];
        sort(arr, 0, arr.length - 1);
    }

    /**
     * 排序arr[low ... high]
     */
    private static void sort(Comparable[] arr, int low, int high){
        if (high <= low){
            return;
        }
        int mid = low + (high-low)/2;
        sort(arr, low, mid);
        sort(arr, mid+1, high);
        merge(arr, low, mid, high);
    }

    /**
     * 将两个有序数组 arr[low ... mid] 与 arr[mid+1, high]进行归并
     */
    private static void merge(Comparable[] arr, int low, int mid, int high){
        assert SortUtils.isSorted(arr, low, mid);
        assert SortUtils.isSorted(arr, mid+1, high);

        for (int k = low; k <= high; k++){
            aux[k] = arr[k];
        }

        int i = low, j = mid + 1;
        for (int k = low; k <= high; k++){
            if (i > mid){
                // 左边用尽，取右边
                arr[k] = aux[j++];
            }else if (j > high){
                // 右边用尽，取左边
                arr[k] = aux[i++];
            }else if (SortUtils.less(aux[j], aux[i])){
                // 右边元素较小，取右边
                arr[k] = aux[j++];
            }else {
                // 左边元素较小，取左边
                arr[k] = aux[i++];
            }
        }
    }

    public static void main(String[] args) {
        SortUtils.show(SortUtils.arr);
        sort(SortUtils.arr);
        assert SortUtils.isSorted(SortUtils.arr);
        SortUtils.show(SortUtils.arr);
    }
}
