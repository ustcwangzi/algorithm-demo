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
 * <p>
 *     给定一个字符数组chars[]，判断chars中是否所有的字符都只出现过一次。
 *     例如，chars=['a','b','c']，返回true；chars=['1','2','1']，返回false。
 *     要求一：实现时间复杂度为O(N)的方法
 *     要求二：保证空间复杂度为O(1)的前提下，实现时间复杂度尽量低的方法
 *     方案一：
 *        遍历chars，用map记录每种字符出现的情况，这样就可以在遍历时发现字符是否有重复，map可使用固定长度的数组也可以使用哈希表。
 *     方案二：
 *        将chars排序，排序后相同的字符会在一起，然后判断有没有重复字符就变得很容易了，因此关键在于选择何种排序算法。
 *        首先，任何时间复杂度为O(N)的排序算法做不到空间复杂度为O(1)，因为这些排序算法不是基于比较，而是要先"装下"，再按顺序"倒出"。
 *        在看时间复杂度为O(N*logN)的排序算法，常见的有归并排序(需要辅助数组)、快速排序(空间复杂度为O(logN))、希尔排序(时间复杂度不固定，
 *        最坏情况下达到O(N*N))、堆排序。只有堆排序能够做到空间复杂度为O(1)，并且时间复杂度稳定保持O(N*logN)。
 *        注意，不能使用递归，因为递归函数需要使用栈空间，这样空间复杂度就增加至O(logN)。
 *        1、将数组中的字符依次插入堆中，建立大根堆
 *        2、交换第一个元素与最后一个元素(当前需要调整的最后一个)，即将最大的元素放到最后的位置上，此时堆的结构发生了变化，
 *           除最后一个元素外，将堆重新调整成大根堆
 *        3、重复步骤二。
 *
 *        以[1, 2, 3, 4, 5, 6]为例说明以上步骤：
 *        建立大根堆：[6, 4, 5, 1, 3, 2]
 *              交换元素               堆调整
 *        [2, 4, 5, 1, 3, 6]    [5, 4, 2, 1, 3, 6]
 *        [3, 4, 2, 1, 5, 6]    [4, 3, 2, 1, 5, 6]
 *        [1, 3, 2, 4, 5, 6]    [3, 1, 2, 4, 5, 6]
 *        [2, 1, 3, 4, 5, 6]    [2, 1, 3, 4, 5, 6]
 *        [1, 2, 3, 4, 5, 6]    [1, 2, 3, 4, 5, 6]
 * </p>
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
