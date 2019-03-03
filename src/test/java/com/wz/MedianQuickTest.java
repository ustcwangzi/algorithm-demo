/**
 * <p>Title: MedianQuickTest</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/3/3</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz;

import com.wz.others.MedianQuick;

import java.util.PriorityQueue;
import java.util.Queue;

/**
 * <p>假设有足够的空间来保存所有的数，快速找到所有数的中位数</p>
 *
 * @author wangzi
 */
public class MedianQuickTest {
    /**
     * 小根堆，存较大的一半
     */
    private Queue<Integer> minHeap;
    /**
     * 大跟堆，存较小的一半
     */
    private Queue<Integer> maxHeap;

    public MedianQuickTest() {
        minHeap = new PriorityQueue<>();
        maxHeap = new PriorityQueue<>((Integer o1, Integer o2) -> (o2.compareTo(o1)));
    }

    private void add(int number) {
        if (maxHeap.isEmpty()) {
            maxHeap.add(number);
            return;
        }

        if (maxHeap.peek() >= number) {
            maxHeap.add(number);
        } else {
            if (minHeap.isEmpty()) {
                minHeap.add(number);
                return;
            }
            if (minHeap.peek() <= number) {
                minHeap.add(number);
            } else {
                maxHeap.add(number);
            }
        }
        modifySize();
    }

    private Integer solution() {
        if (maxHeap.isEmpty() || minHeap.isEmpty()) {
            if (maxHeap.isEmpty() && minHeap.isEmpty()) {
                return null;
            }
            return maxHeap.isEmpty() ? minHeap.peek() : maxHeap.peek();
        }
        int maxHeapSize = maxHeap.size();
        int minHeapSize = minHeap.size();
        Integer maxHead = maxHeap.peek();
        Integer minHead = minHeap.peek();
        // 偶数个元素
        if (((maxHeapSize + minHeapSize) & 1) == 0) {
            return (maxHead + minHead) / 2;
        } else {
            return maxHeapSize > minHeapSize ? maxHead : minHead;
        }
    }

    private void modifySize() {
        if (maxHeap.size() == minHeap.size() + 2) {
            minHeap.add(maxHeap.poll());
        }
        if (minHeap.size() == maxHeap.size() + 2) {
            maxHeap.add(minHeap.poll());
        }
    }

    public static void main(String[] args) {
        MedianQuickTest medianQuickTest = new MedianQuickTest();
        MedianQuick medianQuick = new MedianQuick();

        int times = 100;
        boolean result = true;
        for (int i = 0; i < times; i++) {
            int number = RandomUtils.genRandomNumber();
            medianQuickTest.add(number);
            medianQuick.addNumber(number);
            if (!medianQuickTest.solution().equals(medianQuick.getMedian())) {
                result = false;
                System.out.println("Error");
            }
        }
        if (result) {
            System.out.println("Past");
        }
    }
}
