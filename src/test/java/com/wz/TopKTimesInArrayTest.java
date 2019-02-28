package com.wz;

import com.wz.others.TopKTimesInArray;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class TopKTimesInArrayTest {

    private static class Node {
        public String value;
        public int times;

        public Node(String value, int times) {
            this.value = value;
            this.times = times;
        }
    }

    private static void solution(String[] array, int k) {
        if (array == null || array.length == 0) {
            return;
        }
        Map<String, Integer> map = new HashMap<>();
        for (String cur : array) {
            if (map.containsKey(cur)) {
                map.put(cur, map.get(cur) + 1);
            } else {
                map.put(cur, 1);
            }
        }

        Queue<Node> heap = new PriorityQueue<>(k, (Node o1, Node o2) -> (Integer.compare(o1.times, o2.times)));
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (heap.isEmpty() || heap.size() < k) {
                heap.add(new Node(entry.getKey(), entry.getValue()));
            } else if (heap.peek().times < entry.getValue()) {
                heap.poll();
                heap.add(new Node(entry.getKey(), entry.getValue()));
            }
        }

        Node[] result = new Node[k];
        int index = k;
        while (!heap.isEmpty()) {
            result[--index] = heap.poll();
        }

        for (Node node : result) {
            System.out.print("No." + (++index) + ": ");
            System.out.print(node.value + ", times: ");
            System.out.println(node.times);
        }
    }

    public static void main(String[] args) {
        String[] array = {"A", "B", "C", "D", "E", "A", "C", "B", "C", "B", "A", "A"};
        for (int i = 1; i < 6; i++) {
            solution(array, i);
            TopKTimesInArray.printTopK(array, i);
            System.out.println("------------------");
        }
    }
}
