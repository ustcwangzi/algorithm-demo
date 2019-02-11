/**
 * <p>Title: MedianQuick</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/2/11</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.others;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * <p>快速找到中位数</p>
 *
 * @author wangzi
 */
public class MedianQuick {
    /**
     * 小根堆
     */
    private Queue<Integer> minHeap;
    /**
     * 大根堆
     */
    private Queue<Integer> maxHeap;

    public MedianQuick() {
        // 借助PriorityQueue可以实现小根堆和大根堆
        // PriorityQueue容量没有界限，且默认排序是自然排序，队头元素是最小元素，可以作为小根堆使用
        minHeap = new PriorityQueue<>();
        // 对于大根堆，就要借助于comparator比较器，来实现大根堆
        maxHeap = new PriorityQueue<>((Integer o1, Integer o2) -> (o2.compareTo(o1)));
    }

    public void addNumber(int number) {
        if (this.maxHeap.isEmpty()) {
            this.maxHeap.add(number);
            return;
        }
        if (this.maxHeap.peek() >= number) {
            this.maxHeap.add(number);
        } else {
            if (this.minHeap.isEmpty()) {
                this.minHeap.add(number);
                return;
            }
            if (this.minHeap.peek() > number) {
                this.maxHeap.add(number);
            } else {
                this.minHeap.add(number);
            }
        }

        this.modifyTwoHeapsSize();
    }

    public Integer getMedian() {
        int maxHeapSize = this.maxHeap.size();
        int minHeapSize = this.minHeap.size();
        if (maxHeapSize + minHeapSize == 0) {
            return null;
        }
        Integer maxHeapHead = this.maxHeap.peek();
        Integer minHeapHead = this.minHeap.peek();
        if (maxHeapHead == null || minHeapHead == null) {
            return maxHeapHead == null ? minHeapHead : maxHeapHead;
        }

        // 偶数个元素时，中位数为中间两个元素之和再除以2
        // 奇数个元素时，中位数为size较大的那个堆的堆顶元素
        if (((maxHeapSize + minHeapSize) & 1) == 0) {
            return (maxHeapHead + minHeapHead) / 2;
        } else if (maxHeapSize > minHeapSize) {
            return maxHeapHead;
        } else {
            return minHeapHead;
        }
    }

    /**
     * 当两个堆的size相差为2时，需要进行元素直接的调整
     */
    private void modifyTwoHeapsSize() {
        if (this.maxHeap.size() == this.minHeap.size() + 2) {
            this.minHeap.add(this.maxHeap.poll());
        }
        if (this.minHeap.size() == this.maxHeap.size() + 2) {
            this.maxHeap.add(this.minHeap.poll());
        }
    }

    private static int[] getRandomArray(int maxSize, int maxValue) {
        int[] result = new int[(int) (Math.random() * maxSize) + 1];
        for (int i = 0; i < result.length; i++) {
            result[i] = (int) (Math.random() * maxValue);
        }
        return result;
    }

    private static int getMedianOfArray(int[] array) {
        int[] tmpArray = Arrays.copyOf(array, array.length);
        Arrays.sort(tmpArray);
        int mid = (tmpArray.length - 1) / 2;
        if ((tmpArray.length & 1) == 0) {
            return (tmpArray[mid] + tmpArray[mid + 1]) / 2;
        } else {
            return tmpArray[mid];
        }
    }

    public static void main(String[] args) {
        boolean error = false;
        int times = 200000;
        for (int i = 0; i != times; i++) {
            int len = 30;
            int maxValue = 1000;
            int[] array = getRandomArray(len, maxValue);
            MedianQuick medianQuick = new MedianQuick();
            for (int j = 0; j != array.length; j++) {
                medianQuick.addNumber(array[j]);
            }
            if (medianQuick.getMedian() != getMedianOfArray(array)) {
                error = true;
                System.out.println(Arrays.toString(array));
                break;
            }
        }
        System.out.println(error ? "Error!" : "Past");
    }
}
