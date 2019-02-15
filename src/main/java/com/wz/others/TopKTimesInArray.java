/**
 * <p>Title: TopKTimesInArray</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/2/15</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.others;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * <p>数组中出现次数的TopK</p>
 *
 * @author wangzi
 */
public class TopKTimesInArray {

    private static class HeapNode {
        public String value;
        public int times;

        public HeapNode(String value, int times) {
            this.value = value;
            this.times = times;
        }
    }

    public static void printTopK(String[] array, int k) {
        if (array == null || array.length == 0 || k < 1) {
            return;
        }

        // 词频
        Map<String, Integer> map = new HashMap<>();
        for (String cur : array) {
            if (map.containsKey(cur)) {
                map.put(cur, map.get(cur) + 1);
            } else {
                map.put(cur, 1);
            }
        }

        // 小根堆，用以选出TopK
        Queue<HeapNode> heap = new PriorityQueue<>(k, (HeapNode o1, HeapNode o2) -> (Integer.compare(o1.times, o2.times)));
        // 遍历词频表，决定每个元素是否进堆
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            HeapNode node = new HeapNode(entry.getKey(), entry.getValue());
            if (heap.size() != k) {
                heap.add(node);
            } else if (heap.peek().times < node.times) {
                heap.poll();
                heap.add(node);
            }
        }

        // 把小根堆的所有元素按词频放入result中
        HeapNode[] result = new HeapNode[k];
        int index = k;
        while (!heap.isEmpty()) {
            result[--index] = heap.poll();
        }

        for (HeapNode node : result) {
            System.out.print("No." + (++index) + ": ");
            System.out.print(node.value + ", times: ");
            System.out.println(node.times);
        }
    }

    public static void main(String[] args) {
        String[] array = {"A", "B", "C", "D", "E", "A", "C", "B", "C", "B", "A", "A"};
        printTopK(array, 2);
    }
}
