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
 *     优先队列由一个基于堆的完全二叉树表示，存储在数组pQueue[1...N]中，pQueue[0]没有使用
 *     在堆中，位置 K 的节点的父节点位置为 K/2，而它的两个子节点位置为 2K 和 2K+1
 *     元素新增时，将其添加到数组最后，然后由下至上交换 K 和 K/2，直至 K 不大于它的父节点 k/2
 *     删除最大元素时，将其与最后一个节点交换，然后由上至下交换 K 和 max(2K, 2K+1)，直至 K 不小于它的两个子节点
 * </p>
 *
 * @author wangzi
 */
public class MaxPriorityQueue<T extends Comparable<T>> {
    /** 基于堆的完全二叉树 */
    private T[] pQueue;
    /** 存储在[1...N]中 */
    private int N = 0;

    public MaxPriorityQueue(int maxLength) {
        pQueue = (T[]) new Comparable[maxLength + 1];
    }

    public boolean isEmpty(){
        return N == 0;
    }

    public int size(){
        return N;
    }

    public void insert(T value){
        pQueue[++N] = value;
        swim(N);
    }

    public T getMax(){
        if (isEmpty()){
            return null;
        }
        return pQueue[1];
    }

    public T delMax(){
        if (isEmpty()){
            return null;
        }
        T max = pQueue[1];
        SortUtils.exch(pQueue, 1, N--);
        sink(1);
        return max;
    }

    /**
     * 由下之上交换元素调整堆
     */
    private void swim(int k){
        while (k > 1 && SortUtils.less(pQueue[k/2], pQueue[k])){
            SortUtils.exch(pQueue,k/2, k);
            k = k/2;
        }
    }

    /**
     * 由上至下交换元素调整堆
     */
    private void sink(int k){
        while (2*k < N){
            int j = 2*k;
            // 找到k的两个孩子中较大的一个，与k进行交换
            // pQueue[j]是k的左孩子，并且k的右孩子大于左孩子
            if (j < N && j + 1 < N && SortUtils.less(pQueue[j], pQueue[j + 1])){
                j++;
            }
            if (SortUtils.less(pQueue[k], pQueue[j])){
                SortUtils.exch(pQueue, k, j);
                k = j;
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
        System.out.println(priorityQueue.delMax());
        System.out.println(priorityQueue.delMax());
        System.out.println(priorityQueue.delMax());
        System.out.println(priorityQueue.delMax());
        System.out.println(priorityQueue.delMax());
    }
}
