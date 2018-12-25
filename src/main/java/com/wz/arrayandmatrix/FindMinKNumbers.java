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
 * <p>
 *     给定一个无序数组array，找到其中最小的k个数。
 *     可以直接对array进行排序，该方法时间复杂度不好，此处不再详述。
 *     方案一：
 *        使用大根堆。一直维护一个有k个元素的大根堆，代表目前选出的k个最小的数，那么堆里的k个元素中堆顶元素的最小的k个数里最大的那个。
 *        接下来遍历整个数组，遍历过程中判断当前数是否比堆顶元素小。如果是，用它替换堆顶元素，然后调整堆，使堆恢复成大根堆。
 *        如果不是，则不进行操作，继续遍历下一个数。在遍历完成后，堆中的k个数就是整个数组中最小的k个数。
 * </p>
 * <p>
 *     方案一时间复杂度为O(N*logK)
 * </p>
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

    public static int[] getMinKNumbersByBFPRT(int[] array, int k) {
        if (array == null || array.length == 0 || k < 1 || k > array.length) {
            return array;
        }
        int minKth = getMinKthByBFPRT(array, k);
        int[] res = new int[k];
        int index = 0;
        for (int i = 0; i != array.length; i++) {
            if (array[i] < minKth) {
                res[index++] = array[i];
            }
        }
        for (; index != res.length; index++) {
            res[index] = minKth;
        }
        return res;
    }

    private static int getMinKthByBFPRT(int[] array, int k) {
        int[] copy = new int[array.length];
        System.arraycopy(array, 0, copy, 0, array.length);
        return select(copy, 0, copy.length - 1, k - 1);
    }

    private static int select(int[] array, int begin, int end, int i) {
        if (begin == end) {
            return array[begin];
        }
        int pivot = medianOfMedians(array, begin, end);
        int[] pivotRange = partition(array, begin, end, pivot);
        if (i >= pivotRange[0] && i <= pivotRange[1]) {
            return array[i];
        } else if (i < pivotRange[0]) {
            return select(array, begin, pivotRange[0] - 1, i);
        } else {
            return select(array, pivotRange[1] + 1, end, i);
        }
    }

    private static int medianOfMedians(int[] array, int begin, int end) {
        int num = end - begin + 1;
        int offset = num % 5 == 0 ? 0 : 1;
        int[] medianArray = new int[num / 5 + offset];
        for (int i = 0; i < medianArray.length; i++) {
            int beginI = begin + i * 5;
            int endI = beginI + 4;
            medianArray[i] = getMedian(array, beginI, Math.min(end, endI));
        }
        return select(medianArray, 0, medianArray.length - 1, medianArray.length / 2);
    }

    private static int[] partition(int[] array, int begin, int end, int pivotValue) {
        int small = begin - 1;
        int cur = begin;
        int big = end + 1;
        while (cur != big) {
            if (array[cur] < pivotValue) {
                swap(array, ++small, cur++);
            } else if (array[cur] > pivotValue) {
                swap(array, cur, --big);
            } else {
                cur++;
            }
        }
        int[] range = new int[2];
        range[0] = small + 1;
        range[1] = big - 1;
        return range;
    }

    private static int getMedian(int[] array, int begin, int end) {
        insertionSort(array, begin, end);
        int sum = end + begin;
        int mid = (sum / 2) + (sum % 2);
        return array[mid];
    }

    private static void insertionSort(int[] array, int begin, int end) {
        for (int i = begin + 1; i != end + 1; i++) {
            for (int j = i; j != begin; j--) {
                if (array[j - 1] > array[j]) {
                    swap(array, j - 1, j);
                } else {
                    break;
                }
            }
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
        System.out.println(Arrays.toString(getMinKNumbersByBFPRT(array, 10)));
    }
}
