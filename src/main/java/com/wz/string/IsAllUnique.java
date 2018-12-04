/**
 * <p>Title: IsAllUnique</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/12/4</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.string;

import java.util.Arrays;

/**
 * <p>判断字符数组中是否所有的字符都只出现过一次</p>
 *
 * @author wangzi
 */
public class IsAllUnique {
    public static boolean isUniqueOne(char[] chars) {
        if (chars == null || chars.length == 1) {
            return true;
        }

        boolean[] map = new boolean[256];
        for (char ch : chars) {
            if (map[ch]) {
                return false;
            }
            map[ch] = true;
        }
        return true;
    }

    public static boolean isUniqueTwo(char[] chars) {
        if (chars == null || chars.length == 1) {
            return true;
        }

        heapSort(chars);
        for (int i = 1; i < chars.length; i++) {
            if (chars[i] == chars[i - 1]) {
                return false;
            }
        }
        return true;
    }

    private static void heapSort(char[] chars) {
        for (int i = 0; i < chars.length; i++) {
            heapInsert(chars, i);
        }

        for (int i = chars.length - 1; i > 0; i--) {
            // 交换chars[0]与chars[i]，即将当前最大的元素换到后面，实现排序
            swap(chars, 0, i);
            System.out.println(Arrays.toString(chars));
            // 交换后，将0到size恢复成大根堆
            maxHeapify(chars, 0, i);
            System.out.println(Arrays.toString(chars));
        }

    }

    /**
     * 建立大根堆
     */
    private static void heapInsert(char[] chars, int i) {
        int parent;
        while (i != 0) {
            parent = (i - 1) / 2;
            if (chars[parent] < chars[i]) {
                swap(chars, parent, i);
                i = parent;
            } else {
                break;
            }
        }
    }

    /**
     * 从i到size进行堆调整，将i到size恢复成大根堆
     */
    private static void maxHeapify(char[] chars, int i, int size) {
        int left = i * 2 + 1;
        int right = i * 2 + 2;
        int largest = i;
        while (left < size) {
            // 子节点中找较大的
            if (chars[left] > chars[i]) {
                largest = left;
            }
            if (right < size && chars[right] > chars[largest]) {
                largest = right;
            }
            if (largest != i) {
                swap(chars, largest, i);
            } else {
                break;
            }
            i = largest;
            left = i * 2 + 1;
            right = i * 2 + 2;
        }
    }

    private static void swap(char[] chars, int index1, int index2) {
        char tmp = chars[index1];
        chars[index1] = chars[index2];
        chars[index2] = tmp;
    }

    public static void main(String[] args) {
        char[] chars = {'1', '2', '3', '4', '5', '6'};
        System.out.println(isUniqueOne(chars));
        System.out.println(isUniqueTwo(chars));
    }
}
