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
 * <p>
 *     给定String类型的数组array，再给定整数k，请按照出现次数的顺序打印前k名的字符串。
 *     解决方案：
 *        使用一个哈希表记录每种字符串出现的次数，遍历一遍哈希表，根据该哈希表构建一个大小为k的小根堆，该小根堆以词频作为衡量标准，
 *        如果新加入的元素词频大于小根堆的堆顶元素词频，则弹出堆顶元素，加入新元素，最后，小根堆中的字符串就是出现次数前TopK的字符串。
 * </p>
 * <p>
 *     时间复杂度为O(N*logk)
 * </p>
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
