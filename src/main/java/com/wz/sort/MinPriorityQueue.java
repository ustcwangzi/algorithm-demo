/**
 * <p>Title: MinPriorityQueue</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/8/4</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.sort;

import com.wz.utils.SortUtils;

/**
 * <p>优先队列</p>
 *
 * @author wangzi
 */
public class MinPriorityQueue<T extends Comparable<T>> {
    private T[] pQueue;
    private int n;

    public MinPriorityQueue(int initCapacity) {
        if (initCapacity < 0) {
            throw new IllegalArgumentException();
        }
        pQueue = (T[]) new Comparable[initCapacity + 1];
    }

    public MinPriorityQueue() {
        this(10);
    }

    public MinPriorityQueue(T[] keys) {
        n = keys.length;
        pQueue = (T[]) new Comparable[n + 1];
        for (int i = 0; i < n; i++) {
            pQueue[i + 1] = keys[i];
        }
        for (int k = n / 2; k >= 1; k--) {
            sink(k);
        }
        assert isMinHeap();
    }

    private boolean isMinHeap() {
        return isMinHeap(1);
    }

    private boolean isMinHeap(int k) {
        if (k > n) {
            return true;
        }
        int left = 2 * k;
        int right = 2 * k + 1;
        if (left <= n && SortUtils.greater(pQueue[k], pQueue[left])) {
            return false;
        }
        if (right <= n && SortUtils.greater(pQueue[k], pQueue[right])) {
            return false;
        }
        return isMinHeap(left) && isMinHeap(right);
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
        assert isMinHeap();
    }

    public T getMin() {
        if (isEmpty()) {
            return null;
        }
        return pQueue[1];
    }

    public T delMin() {
        if (isEmpty()) {
            return null;
        }
        T min = pQueue[1];
        SortUtils.exch(pQueue, 1, n--);
        sink(1);
        if ((n > 0) && (n == (pQueue.length - 1) / 4)) {
            resize(pQueue.length / 2);
        }
        assert isMinHeap();
        return min;
    }

    /**
     * 位置k的元素上浮，由下至上调整堆
     */
    private void swim(int k) {
        while (k > 1 && SortUtils.greater(pQueue[k / 2], pQueue[k])) {
            SortUtils.exch(pQueue, k / 2, k);
            k = k / 2;
        }
    }

    /**
     * 位置k的元素下沉，由上至下调整堆
     */
    private void sink(int k) {
        while (2 * k <= n) {
            int j = 2 * k;
            if (j < n && j + 1 < n && SortUtils.greater(pQueue[j], pQueue[j + 1])) {
                j++;
            }
            if (SortUtils.greater(pQueue[k], pQueue[j])) {
                SortUtils.exch(pQueue, k, j);
                k = j;
            } else {
                break;
            }
        }
    }

    public static void main(String[] args) {
        MinPriorityQueue<Integer> priorityQueue = new MinPriorityQueue<>(5);
        priorityQueue.insert(9);
        priorityQueue.insert(1);
        priorityQueue.insert(2);
        priorityQueue.insert(8);
        priorityQueue.insert(6);
        System.out.println(priorityQueue.getMin());
        assert priorityQueue.isMinHeap();
    }

}
