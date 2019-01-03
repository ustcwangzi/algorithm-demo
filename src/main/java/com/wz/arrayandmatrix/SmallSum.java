/**
 * <p>Title: SmallSum</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/1/3</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.arrayandmatrix;

/**
 * <p>计算数组的小和</p>
 * <p>
 *     数组小和的定义：例如，数组array=[1,3,5,2,4,6]。
 *     在array[0]的左边小于或等于array[0]的数的和为0；在array[1]的左边小于或等于array[1]的数的和为1；
 *     在array[2]的左边小于或等于array[2]的数的和为1+3=4；在array[3]的左边小于或等于array[3]的数的和为1；
 *     在array[4]的左边小于或等于array[4]的数的和为1+3+2=6；在array[5]的左边小于或等于array[5]的数的和为：1+3+5+2+4=15；
 *     所以array的小和为0+1+4+1+6+15=27。现给定一个数组array，实现函数返回array的小和。
 *     解决方案：
 *        可直接利用二重循环暴力求解，时间复杂度为O(N*N)，此处不再详述。
 *        这里利用归并排序，在队友徐子数组进行merge时，累加数组小和，具体过程如下：
 *        1、假设left[]与right[]已组内有序，现需要对这两个数组进行合并，并设假当前正在对left[i]与right[j]进行比较
 *        2、如果left[i]<=right[j]，那么产生小和，假设right[]数组中从j到结束的元素个数为m，则产生的小和为left[i]*m
 *        3、如果left[i]>right[j]，不产生任何小和
 *        4、整个归并排序过程没有任何变化，只是利用步骤1～步骤3计算小和，即组间合并的过程中累加所产生的小和，最后的累加和就是结果。
 *        以array=[1,3,5,2,4,6]为例，说明以上过程：
 *        1、归并排序过程中会进行拆组再合并的过程。[1,3,5,2,4,6]拆成[1,3,5]和[2,4,6]，[1,3,5]拆成[1,3]和[5]，
 *           [2,4,6]拆成[2,4]和[6]，[1,3]拆成[1]和[3]，[2,4]拆成[2]和[4]，如resources/SamllSumSplit.png
 *        2、[1]与[3]合并，1和3比较，1小，从3开始到最后只有一个数，所以产生小和为1*1=1，合并为[1,3]
 *        3、[1,3]与[5]合并，1和5比较，1小，从5开始到最后只有一个数，所以产生小和为1*1=1，同理，3和5比较，产生小和为3*1=3，合并为[1,3,5]
 *        4、[2]与[4]合并，2和4比较，2小，从4开始到最后只有一个数，所以产生小和为2*1=2，合并为[2,4]
 *        5、[2,4]与[6]合并，与步骤三同理，产生小和为2*1+4*1=6，合并为[2,4,6]
 *        6、[1,3,5]与[2,4,6]合并，1和2比较，1小，从2开始到最后共用三个数，所以产生小和为1*3=3；3和2比较，3大，不产生小和；
 *           3和4比较，3小，从4开始到最后共用两个数，所以产生小和为3*2=6；5和4比较，5大，不产生小和；5和6比较，5小，
 *           从6开始到最后只有一个数，所以产生小和为5*1=5，合并为[1,3,5,2,4,6]
 *        7、归并过程结束，总的小和为1+1+3+2+6+3+6+5=27。合并的全部如resources/SmallSumMerge.png。
 * </p>
 * <p>
 *     时间复杂度为O(N*logN)，空间复杂度为O(N)
 * </p>
 *
 * @author wangzi
 */
public class SmallSum {

    public static int getSmallSum(int[] array) {
        if (array == null || array.length == 0) {
            return 0;
        }
        return mergeSort(array, 0, array.length - 1);
    }

    /**
     * 归并排序中计算小和
     */
    private static int mergeSort(int[] array, int left, int right) {
        if (left == right) {
            return 0;
        }
        int mid = (left + right) / 2;
        return mergeSort(array, left, mid) + mergeSort(array, mid + 1, right) + merge(array, left, mid, right);
    }

    /**
     * 合并两个已排好序的数组array[left...mid]和array[mid+1...right]
     */
    private static int merge(int[] array, int left, int mid, int right) {
        // 辅助数组
        int[] tmp = new int[right - left + 1];
        int index = 0;
        // 小和
        int smallSum = 0;
        int i = left, j = mid + 1;
        while (i <= mid && j <= right) {
            if (array[i] <= array[j]) {
                // 当前数组array[left...mid]的元素小于等于后一个数组array[mid+1...right]的元素时，累加小和
                smallSum += array[i] * (right - j + 1);
                tmp[index++] = array[i++];
            } else {
                tmp[index++] = array[j++];
            }
        }

        // 复制剩余元素
        while (i <= mid) {
            tmp[index++] = array[i++];
        }
        while (j <= right) {
            tmp[index++] = array[j++];
        }

        // 合并后复制到原数组
        for (int cur : tmp) {
            array[left++] = cur;
        }
        return smallSum;
    }

    public static void main(String[] args) {
        int[] array = {3, 1, 2, 4, 6, 2, 7, 8, 1};
        System.out.println(getSmallSum(array));
    }
}
