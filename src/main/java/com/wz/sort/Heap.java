/**
 * <p>Title: Heap</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/6/18</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.sort;

import com.wz.utils.SortUtils;

/**
 * <p>堆排序</p>
 * <p>
 *     从右至左使用sink()函数构造子堆
 *     如果一个节点的两个子节点都已经是堆了，那么在该节点调用sink()可以将它们变成一个堆
 *     使用这种方法会递归建立起整个堆，只需要扫描数组中的一半元素，因为可以跳过大小为1的子堆
 *     第一阶段结束，构造一个大根堆
 *     然后，在位置1上不断交换元素，将位置1上的元素放到数组后面，同时执行sink()继续构造子堆
 *     第二阶段中，每一次循环结束，都会将位置1的元素放到数组后面，同时剩余数组中的最大元素放置位置1
 *     第二阶段结束后，将得到一个从小到大排序好的数组
 * </p>
 * <p>使用下沉方法由 N 个元素构造堆只需要少于 2N 次比较以及少于 N 次交换</p>
 * <p>不稳定，原地排序，时间复杂度N*logN，空间复杂度1</p>
 *
 * @author wangzi
 */
public class Heap {
    public static void sort(Comparable[] arr){
        // 位置0不使用
        int length = arr.length - 1;
        // 扫描前半部分元素，建立子堆
        for (int k = length/2; k >= 1; k--){
            sink(arr, k, length);
        }
        // 不断将最大的元素(位于位置1)放到后面，并保持堆有序
        while (length > 1){
            SortUtils.exch(arr, 1, length--);
            sink(arr, 1, length);
        }
    }

    /**
     * 位置k的元素下沉，由上至下调整堆
     */
    private static void sink(Comparable[] arr, int k, int length){
        // 存在子节点
        while (2*k <= length){
            int j = 2*k;
            // 存在右孩子时，找出左右孩子中较大的一个
            if (j < length  && j+1 < length && SortUtils.less(arr[j],arr[j+1])){
                j++;
            }
            if (SortUtils.less(arr[k], arr[j])){
                SortUtils.exch(arr, j, k);
                k = j;
            }else {
                break;
            }
        }
    }

    public static void main(String[] args) {
        // 只使用[1...N]，位置0不使用
        Integer[] arr = {null, 36, 8, 67, 98, 32, 21};
        sort(arr);
        SortUtils.show(arr);
    }

}
