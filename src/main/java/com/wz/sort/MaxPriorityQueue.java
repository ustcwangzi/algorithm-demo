/**
 * <p>Title: MaxPriorityQueue</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/6/17</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.sort;

import com.wz.utils.SortUtils;

/**
 * <p>基于堆的优先队列</p>
 * <p>
 *     优先队列由一个基于堆的完全二叉树表示，存储在数组pQueue[1...n]中，pQueue[0]没有使用
 *     在堆中，位置 K 的节点的父节点位置为 K/2，而它的两个子节点位置为 2K 和 2K+1
 *     元素新增时，将其添加到数组最后，然后由下至上交换 K 和 K/2，直至 K 不大于它的父节点 k/2
 *     删除最大元素时，将其与最后一个节点交换，然后由上至下交换 K 和 max(2K, 2K+1)，直至 K 不小于它的两个子节点
 * </p>
 *
 * @author wangzi
 */
public class MaxPriorityQueue<T extends Comparable<T>> {
    /**
     * 基于堆的完全二叉树
     */
    private T[] pQueue;
    /**
     * 存储在[1...n]中
     */
    private int n = 0;

    public MaxPriorityQueue(int initCapacity) {
        if (initCapacity < 0) {
            throw new IllegalArgumentException();
        }
        pQueue = (T[]) new Comparable[initCapacity + 1];
    }

    public MaxPriorityQueue() {
        this(10);
    }

    public MaxPriorityQueue(T[] keys) {
        n = keys.length;
        pQueue = (T[]) new Comparable[n + 1];
        for (int i = 0; i < n; i++) {
            pQueue[i + 1] = keys[i];
        }
        for (int k = n / 2; k >= 1; k--) {
            sink(k);
        }
        assert isMaxHeap();
    }

    private boolean isMaxHeap() {
        return isMaxHeap(1);
    }

    private boolean isMaxHeap(int k) {
        if (k > n) {
            return true;
        }
        int left = 2 * k;
        int right = 2 * k + 1;
        if (left <= n && SortUtils.less(pQueue[k], pQueue[left])) {
            return false;
        }
        if (right <= n && SortUtils.less(pQueue[k], pQueue[right])) {
            return false;
        }
        return isMaxHeap(left) && isMaxHeap(right);
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    private void resize(int capacity) {
        assert capacity > n;
        T[] temp = (T[]) new Comparable[capacity];
        for (int i = 1; i <= n; i++) {
            temp[i] = pQueue[i];
        }
        pQueue = temp;
    }


    public void insert(T value) {
        if (n == pQueue.length - 1) {
            resize(2 * pQueue.length);
        }
        pQueue[++n] = value;
        swim(n);
        assert isMaxHeap();
    }

    public T getMax() {
        if (isEmpty()) {
            return null;
        }
        return pQueue[1];
    }

    public T delMax() {
        if (isEmpty()) {
            return null;
        }
        T max = pQueue[1];
        SortUtils.exch(pQueue, 1, n--);
        sink(1);
        if ((n > 0) && (n == (pQueue.length - 1) / 4)) {
            resize(pQueue.length / 2);
        }
        assert isMaxHeap();
        return max;
    }

    /**
     * 位置k的元素上浮，由下至上调整堆
     * 即：若当前元素大于父节点，交换
     */
    private void swim(int k) {
        while (k > 1 && SortUtils.less(pQueue[k / 2], pQueue[k])) {
            SortUtils.exch(pQueue, k / 2, k);
            k = k / 2;
        }
    }

    /**
     * 位置k的元素下沉，由上至下调整堆
     * 即：当前元素与子节点中较小的一个进行交换
     */
    private void sink(int k) {
        while (2 * k <= n) {
            int j = 2 * k;
            // 存在右孩子时，找出左右孩子中较大的一个
            if (j < n && j + 1 < n && SortUtils.less(pQueue[j], pQueue[j + 1])) {
                j++;
            }
            // 如果孩子中较大的一个大于当前位置k的元素，交换
            if (SortUtils.less(pQueue[k], pQueue[j])) {
                SortUtils.exch(pQueue, k, j);
                k = j;
            } else {
                break;
            }
        }
    }

    public static void main(String[] args) {
        MaxPriorityQueue<Integer> priorityQueue = new MaxPriorityQueue<>(5);
        priorityQueue.insert(1);
        priorityQueue.insert(2);
        priorityQueue.insert(9);
        priorityQueue.insert(8);
        priorityQueue.insert(6);
        assert priorityQueue.isMaxHeap();
    }
}
