/**
 * <p>Title: FindMinKNumbers</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/12/25</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.arrayandmatrix;

import java.util.Arrays;

/**
 * <p>找到无序数组中最小的k个数</p>
 *
 * @author wangzi
 */
public class FindMinKNumbers {

    public static int[] getMinKNumbersByHeap(int[] array, int k) {
        if (array == null || array.length == 0 || k < 1 || k > array.length) {
            return array;
        }
        int[] heap = new int[k];
        for (int i = 0; i < k; i++) {
            heapInsert(heap, array[i], i);
        }
        // 维护k个数的大根堆，堆中代表目前选出的k个最小的数，即堆顶元素是这最小的k个数里最大的那个
        for (int i = k; i < array.length; i++) {
            if (array[i] < heap[0]) {
                heap[0] = array[i];
                maxHeapify(heap, 0);
            }
        }
        return heap;
    }

    /**
     * 建立大根堆
     */
    private static void heapInsert(int[] heap, int value, int index) {
        heap[index] = value;
        while (index != 0) {
            int parent = (index - 1) / 2;
            if (heap[parent] < heap[index]) {
                swap(heap, parent, index);
                index = parent;
            } else {
                break;
            }
        }
    }

    /**
     * 从i开始调整大根堆
     */
    private static void maxHeapify(int[] heap, int index) {
        int heapSize = heap.length;
        int left = index * 2 + 1;
        int right = index * 2 + 2;
        int largest = index;
        while (left < heapSize) {
            // 子节点中找较大的
            if (heap[left] > heap[index]) {
                largest = left;
            }
            if (right < heapSize && heap[right] > heap[largest]) {
                largest = right;
            }
            if (largest != index) {
                swap(heap, largest, index);
            } else {
                break;
            }
            index = largest;
            left = index * 2 + 1;
            right = index * 2 + 2;
        }
    }

    private static void swap(int[] array, int self, int other) {
        int tmp = array[self];
        array[self] = array[other];
        array[other] = tmp;
    }

    public static void main(String[] args) {
        int[] array = {6, 9, 1, 3, 1, 2, 2, 5, 6, 1, 3, 5, 9, 7, 2, 5, 6, 1, 9};
        System.out.println(Arrays.toString(getMinKNumbersByHeap(array, 10)));
    }
}
