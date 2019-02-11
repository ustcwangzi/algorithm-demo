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
 * <p>
 *     有一个源源不断地吐出整数的数据流，假设你有足够的空间来保存吐出的数。请设计一个结构可以随时取得之前吐出所有数的中位数。
 *     要求：
 *        1、新数加入的过程，其时间复杂度为O(logN)；
 *        2、取得已经吐出的N个数整体的中位数的过程，时间复杂度O(1)。
 *     解决方案：
 *        使用两个堆结构，一个大根堆，一个小根堆。将接收的所有数的较小的一半放入大根堆，将接收的较大的一半放入小根堆。
 *        如果接收的个数为奇数，中位数就是小根堆和大根堆中元素数量多的那个堆的堆顶，比如吐出的数是6,1,3,0,9,8,7，
 *        小根堆中存放6,7,8,9，大根堆存放0,1,3，小根堆的元素个数多，它的堆顶就是该序列的中位数，即6；
 *        如果接收的个数是偶数，中位数就是两个堆顶相加除以2。
 *        每次接收到一个数，都要正确选择放入哪个堆，小于小根堆堆顶的都放入大根堆，否则放入小根堆。
 *        如果出现一个堆的元素个数比另一个堆的元素多两个的情况，将前者的堆顶弹出添加到后者中，并重新调整两个堆。
 *        总之要始终保持两个堆元素个数的差值不大于１。
 *        总结如下：
 *        1、大根堆每时每刻都是较小的一半元素，堆顶为该堆的最大值；
 *        2、小根堆每时每刻都是较大的一半元素，堆顶为该堆的最小值；
 *        3、新加入元素根据与两个堆的堆顶元素的大小关系，选择放进哪个堆中；
 *        4、当任何一个堆的size比另一个堆的size大2时，需要进行堆调整，过程为：
 *        4.1、若大根堆的size比小根堆大2，从大根堆弹出堆顶元素，放入小根堆；
 *        4.2、若小根堆的size比大根堆大2，从小根堆弹出堆顶元素，放入大根堆。
 *        另外，可借助PriorityQueue来实现小根堆和大根堆：
 *        对于PriorityQueue，它容量没有界限，且默认排序是自然排序，队头元素是最小元素，故可以直接拿来作为小根堆使用。
 *        注意：默认的PriorityQueue并非保证了整个队列都是有序的，只是保证了队头是最小的。
 *        对于大根堆，就要借助于comparator比较器，来实现大根堆。
 * </p>
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
