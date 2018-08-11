/**
 * <p>Title: MSD</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/8/11</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.sort;

import com.wz.utils.SortUtils;

/**
 * <p>高位优先字符串排序(Most Significant Digit)</p>
 * <p>
 *     高位优先字符串排序不要求待排序的字符串长度一致，从高位向低位进行排序
 *     和低位优先字符串排序相比，不同点在于：
 *     1、count[]依然用来记录每个键出现的频率，但是长度为 RADIX+2
 *        RADIX个位置用来存放对应字符出现的次数
 *        一个位置用来表示字符串的结尾(count[1])
 *        一个位置用于后面的转换索引(count[0]，这一点与低位排序相同)
 *     2、需要自己实现一个charAt()方法，因为字符串长度不固定
 *     3、每一次的循环只对[low，high]的部分进行排序，回写时需要写到对应的位置
 * </p>
 * <p>高位优先的字符串排序是稳定排序</p>
 * <p>
 *     对基于R个字符的字母表的N个字符串进行排序，
 *     高位优先的字符串排序所需空间与 WR 成正比，W为最长的字符串长度
 *     大概需要访问 8N+3R ～ 7wN+3wR 次数组，w为字符串平均长度
 * </p>
 *
 * @author wangzi
 */
public class MSD {
    /**
     * 字符串基数，即所有字符串中不同字符的个数
     * 256为8位的ASCII码
     */
    private static final int RADIX = 256;

    public static void sort(String[] arr) {
        int length = arr.length;
        String[] auxArr = new String[length];
        sort(arr, 0, length - 1, 0, auxArr);
    }

    /**
     * 以第d个字符为键索对 arr[low] 至 arr[high] 排序
     */
    private static void sort(String[] arr, int low, int high, int d, String[] auxArr) {
        // 计算频率，count[n + 2]中统计键值为n的字符串的个数
        int[] count = new int[RADIX + 2];
        for (int i = low; i <= high; i++) {
            count[charAt(arr[i], d) + 2]++;
        }
        // 将频率转换为索引，变换后count[n+1]正是下一个键值为n的字符串的索引下标
        for (int r = 0; r < RADIX + 1; r++) {
            count[r + 1] += count[r];
        }
        // 元素分类
        for (int i = low; i <= high; i++) {
            auxArr[count[charAt(arr[i], d) + 1]++] = arr[i];
        }
        // 回写
        for (int i = low; i <= high; i++) {
            arr[i] = auxArr[i - low];
        }

        // 递归的以每个字符为键进行排序
        for (int r = 0; r < RADIX; r++) {
            sort(arr, low + count[r], low + count[r + 1] - 1, d + 1, auxArr);
        }
    }

    private static int charAt(String str, int d) {
        assert d >= 0 && d <= str.length();
        if (d == str.length()) {
            return -1;
        }
        return str.charAt(d);
    }

    public static void main(String[] args) {
        String[] arr = SortUtils.initStrArray();
        sort(arr);
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
    }
}
