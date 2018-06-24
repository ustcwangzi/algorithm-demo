/**
 * <p>Title: Quick</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/5/13</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.sort;

import com.wz.utils.SortUtils;

/**
 * <p>快速排序</p>
 * <p>
 *     分治策略
 *     1、从数列中取出一个数作为基准数
 *     2、分区，即将比这个数大的数放在它的右边，其余的数放在它的左边
 *     3、再对左右区间重复步骤2，直到各个区间只有一个数
 * </p>
 * <p>不稳定，原地排序，时间复杂度N*logN，空间复杂度logN，受基准选择的影响</p>
 *
 * @author wangzi
 */
public class Quick {
    public static void sort(Comparable[] arr){
        sort(arr, 0, arr.length - 1);
    }

    private static void sort(Comparable[] arr, int low, int high){
        if (low >= high){
            return;
        }
        int j = partition(arr, low, arr.length - 1);
        sort(arr, low, j - 1);
        sort(arr, j + 1, high);
    }

    private static int partition(Comparable[] arr, int low, int high){
        // 待排序的第一个元素作为基准元素
        Comparable temp = arr[low];
        while (low < high){
            // 从右往左扫描，找到比基准小的元素，将其放到左边
            while (low < high && SortUtils.less(temp, arr[high])){
                high--;
            }
            arr[low] = arr[high];

            // 从左往右扫描，找到比基准大的元素，将其放到右边
            while (low < high && SortUtils.less(arr[low], temp)){
                low++;
            }
            arr[high] = arr[low];
        }
        // 放入基准元素
        arr[high] = temp;
        return high;
    }

    public static void main(String[] args) {
        SortUtils.show(SortUtils.arr);
        sort(SortUtils.arr);
        assert SortUtils.isSorted(SortUtils.arr);
        SortUtils.show(SortUtils.arr);
    }
}
