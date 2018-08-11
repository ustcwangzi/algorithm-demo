/**
 * <p>Title: LSD</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/8/11</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.strsort;

import com.wz.utils.StrSortUtils;

/**
 * <p>低位优先字符串排序(Least Significant Digit)</p>
 * <p>
 *     低位优先字符串排序要求待排序的字符串长度一致，从低位向高位依次进行排序
 *     第一步：计算每个键出现的频率，存储在count[]中，使用"键+1"作为下标，原因稍后解释
 *          eg：现在有4个小组，记为1~4组，分别有3,5,6,6个人，count[]为{0,0,3,5,6,6}
 *     第二步：将频率转换为索引，即使用count[]来计算每个键在排序结果中的起始索引位置
 *          eg：因为第一组有3人、第二组有5问，因此第三组起始位置为8，转换索引后count[]={0,0,3,8,14,20}
 *     第三步：元素分类，将数组中的元素移动到临时数组中进行排序，每个元素在临时数组中的位置由它的键所对应的count[]值决定
 *          eg：分类时，第i组的起始下标刚好是count[i]，如第一组起始为0，第二组起始为3
 *     第四步：回写，将临时数组中的与元素写回到原数组中
 * </p>
 * <p>低位优先的字符串排序是稳定排序</p>
 * <p>
 *     对基于R个字符的字母表的N个长度为W的字符串为键的元素，
 *     低位优先的字符串排序是稳定排序需要使用的额外空间为 N+R 成正比
 *     大概需要访问 7WN+3WR 次数组
 * </p>
 *
 * @author wangzi
 */
public class LSD {
    /**
     * 字符串基数，即所有字符串中不同字符的个数
     * 256为8位的ASCII码
     */
    private static int RADIX = 256;

    /**
     * 通过前prefix个字符将arr[]排序
     */
    public static void sort(String[] arr, int prefix) {
        int length = arr.length;
        String[] auxArr = new String[length];

        // 对第d个字符使用键索引计数法排序
        for (int d = prefix - 1; d >= 0; d--) {
            // 计算频率
            int[] count = new int[RADIX + 1];
            for (int i = 0; i < length; i++) {
                count[arr[i].charAt(d) + 1]++;
            }
            // 将频率转换为索引
            for (int r = 0; r < RADIX; r++) {
                count[r + 1] += count[r];
            }
            // 将元素分类
            for (int i = 0; i < length; i++) {
                auxArr[count[arr[i].charAt(d)]++] = arr[i];
            }
            // 回写
            for (int i = 0; i < length; i++) {
                arr[i] = auxArr[i];
            }
        }
    }

    public static void main(String[] args) {
        String[] arr = StrSortUtils.initArray();
        int length = arr.length;
        int prefix = arr[0].length();
        for (int i = 1; i < length; i++) {
            assert arr[i].length() == prefix : "Strings must have fixed length";
        }

        sort(arr, prefix);
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
    }
}
