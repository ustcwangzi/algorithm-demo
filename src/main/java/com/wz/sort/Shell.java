/**
 * <p>Title: Shell</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/5/1</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.sort;

import com.wz.utils.SortUtils;

/**
 * <p>希尔排序</p>
 * <p>
 *     将数组分割为若干个子序列，子序列不是逐段分割的，而是相隔特定的增量的子序列
 *     对各个子序列进行插入排序，然后再选择一个更小的增量
 *     再将数组分割为多个子序列进行排序......最后选择增量为1
 * </p>
 * <p>不稳定，原地排序，时间复杂度N*logN～N^6/5，空间复杂度1，受输入顺序的影响</p>
 *
 * @author wangzi
 */
public class Shell {
    public static void sort(Comparable[] arr){
        int length = arr.length;
        for (int gap = length/2; gap > 0; gap /= 2){ // 增量减少
            // 从gap个元素开始，逐渐对其所在组进行直接插入排序
            for (int i = gap; i < length; i++){
                Comparable temp = arr[i];
                int j;  // 找到temp的位置
                for (j = i; j-gap >= 0 && SortUtils.less(temp, arr[j-gap]); j-=gap){
                    arr[j] = arr[j-gap];  // 元素后移
                }
                arr[j] = temp;
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
