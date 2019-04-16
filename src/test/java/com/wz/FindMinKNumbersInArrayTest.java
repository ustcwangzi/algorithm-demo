package com.wz;

import com.wz.arrayandmatrix.FindMinKNumbersInArray;

import java.util.Arrays;
import java.util.Random;

/**
 * <p>无数数组中找到最小的k个数</p>
 *
 * @author wangzi
 */
public class FindMinKNumbersInArrayTest {
    private static int[] solution(int[] array, int k) {
        int[] heap = new int[k];
        for (int i = 0; i < k; i++) {
            // 初始化大根堆
            heapInsert(heap, array[i], i);
        }

        for (int i = k; i < array.length; i++) {
            // 当前元素小于堆顶元素，加入堆，然后进行堆调整
            if (array[i] < heap[0]) {
                heap[0] = array[i];
                heapify(heap, 0);
            }
        }
        return heap;
    }

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
     * 从index开始对堆进行调整
     */
    private static void heapify(int[] heap, int index) {
        int heapSize = heap.length;
        int left = index * 2 + 1;
        int right = index * 2 + 2;
        int largestIndex = index;
        while (left < heapSize) {
            // 获取父节点和子节点中的最大值对应的索引
            if (heap[left] > heap[index]) {
                largestIndex = left;
            }
            if (right < heapSize && heap[right] > heap[largestIndex]) {
                largestIndex = right;
            }
            // 如果最大值对应的索引还是index，说明不需要调整
            if (largestIndex == index) {
                break;
            }
            swap(heap, index, largestIndex);
            // index变化到子节点，继续向下调整
            index = largestIndex;
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
        int times = 100;
        boolean result = true;
        for (int i = 0; i < times; i++) {
            int[] array = RandomUtils.genRandomArray();
            int k = new Random().nextInt(10) + 1;
            int[] resultA = solution(array, k);
            int[] resultB = FindMinKNumbersInArray.getMinKNumbersByBFPRT(array, k);
            Arrays.sort(resultA);
            Arrays.sort(resultB);
            if (!Arrays.equals(resultA, resultB)) {
                result = false;
                System.out.println("Error, array:" + Arrays.toString(array));
            }
        }
        if (result) {
            System.out.println("Past");
        }
    }
}
