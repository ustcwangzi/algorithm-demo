/**
 * <p>Title: Selection</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/5/1</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.sort;

import com.wz.utils.SortUtils;

/**
 * <p>选择排序</p>
 * <p>
 *     1、找出数组中最小的那个元素，将它和数组的第一个元素交换位置
 *     2、在剩下的元素中找出最小的元素，将它和数组的第二个元素交换位置
 *     3、依此类推，直到将整个数组排序
 * </p>
 * <p>长度为N的数组，大约需要N(N-1)2次比较和N次交换</p>
 *
 * @author wangzi
 */
public class Selection {
    public static void sort(Comparable[] arr){
        int length = arr.length;
        for (int i = 0; i < length; i++){
            int min = i; //最小元素的索引
            for (int j = i+1; j < length; j++){
                if (SortUtils.less(arr[j], arr[min])){
                    min = j;
                }
            }
            SortUtils.exch(arr, i, min);
        }
    }

    public static void main(String[] args) {
        String[] arr = new String[]{"a", "n", "i", "p", "z"};
        sort(arr);
        assert SortUtils.isSorted(arr);
        SortUtils.show(arr);
    }
}
