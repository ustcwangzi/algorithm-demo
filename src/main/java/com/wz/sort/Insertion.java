/**
 * <p>Title: Insertion</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/5/1</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.sort;

import com.wz.utils.SortUtils;

/**
 * <p>插入排序</p>
 * <p>
 *     第一趟、对下标1处的元素进行排序，保证数组[0,1]上的元素有序；
 *     第二趟、对下标2处的元素进行排序，保证数组[0,2]上的元素有序；
 *     。。。。。。
 *     第N-1趟对下标 N-1 处的元素进行排序，保证数组[0,N-1]上的元素有序，也就是整个数组有序了
 * </p>
 * <p>长度为N的数组，平均需要N*N/4次比较以及N*N/4次移动</p>
 * <p>最坏情况需要N*N/2次比较以及N*N/2次移动</p>
 * <p>最好情况需要N-1次比较以及0次移动</p>
 *
 * @author wangzi
 */
public class Insertion {
    public static void sort(Comparable[] arr){
        int length = arr.length;
        for (int i = 1; i < length; i++){
            Comparable temp = arr[i];  //[0,i-1]已经有序
            int j;  // 找到temp的位置
            for (j = i; j > 0 && SortUtils.less(temp, arr[j-1]); j--){
                arr[j] = arr[j-1];  // 元素后移
            }
            arr[j] = temp;
        }
    }

    public static void main(String[] args) {
        SortUtils.show(SortUtils.arr);
        sort(SortUtils.arr);
        assert SortUtils.isSorted(SortUtils.arr);
        SortUtils.show(SortUtils.arr);
    }
}
