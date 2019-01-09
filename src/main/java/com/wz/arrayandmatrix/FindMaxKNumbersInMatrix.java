/**
 * <p>Title: FindMaxKNumbersInMatrix</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/1/8</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.arrayandmatrix;

import java.util.Arrays;

/**
 * <p>找到N个数组中最大的K个数</p>
 *
 * @author wangzi
 */
public class FindMaxKNumbersInMatrix {

    private static class HeapNode {
        // 值
        public int value;
        // 来自哪个数组
        public int arrayNum;
        // 来自数组的哪个位置
        public int index;

        public HeapNode(int value, int arrayNum, int index) {
            this.value = value;
            this.arrayNum = arrayNum;
            this.index = index;
        }
    }

    public static int[] getMinKNumbers(int[][] matrix, int k) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0 || k <= 0) {
            return null;
        }

        int[] result = new int[k];
        // 以数组个数作为堆的大小
        int heapSize = matrix.length;
        HeapNode[] heap = new HeapNode[heapSize];
        // 使用每个数组的最大值建立大根堆
        for (int i = 0; i < heapSize; i++) {
            // 取数组最后一个值，即该数组的最大值
            int index = matrix[i].length - 1;
            heap[i] = new HeapNode(matrix[i][index], i, index);
            heapInsert(heap, i);
        }

        for (int i = 0; i < k && heapSize > 0; i++) {
            result[i] = heap[0].value;
            // 如果堆顶元素来自a数组i位置，且a数组中还有元素，就用a[i-1]替换堆顶元素；否则使用堆中最后一个替换堆顶元素，同时堆大小减1
            if (heap[0].index > 0) {
                heap[0].value = matrix[heap[0].arrayNum][--heap[0].index];
            } else {
                swap(heap, 0, --heapSize);
            }
            heapify(heap, 0, heapSize);
        }

        return result;
    }

    /**
     * 建立大根堆
     */
    private static void heapInsert(HeapNode[] heap, int index) {
        while (index > 0) {
            int parent = (index - 1) / 2;
            if (heap[parent].value < heap[index].value) {
                swap(heap, parent, index);
                index = parent;
            } else {
                break;
            }
        }
    }

    /**
     * 从index开始调整大根堆
     */
    private static void heapify(HeapNode[] heap, int index, int heapSize) {
        int left = index * 2 + 1;
        int right = index * 2 + 2;
        int largest = index;
        while (left < heapSize) {
            // 子节点中找较大的
            if (heap[left].value > heap[index].value) {
                largest = left;
            }
            if (right < heapSize && heap[right].value > heap[largest].value) {
                largest = right;
            }
            if (largest != index) {
                // 将子节点中最大的与index交换
                swap(heap, largest, index);
            } else {
                break;
            }
            // 继续向下调整
            index = largest;
            left = index * 2 + 1;
            right = index * 2 + 2;
        }
    }

    private static void swap(HeapNode[] heap, int self, int other) {
        HeapNode tmp = heap[self];
        heap[self] = heap[other];
        heap[other] = tmp;
    }

    public static void main(String[] args) {
        int[][] matrix = {{219, 405, 538, 845, 971}, {148, 558}, {52, 99, 348, 691}};
        System.out.println(Arrays.toString(getMinKNumbers(matrix, 5)));
    }
}
