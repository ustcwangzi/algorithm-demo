/**
 * <p>Title: TopKTimesRecord</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/2/16</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.others;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>出现次数的TopK结构</p>
 *
 * @author wangzi
 */
public class TopKTimesRecord {

    public static class Node {
        public String value;
        public int times;

        public Node(String value, int times) {
            this.value = value;
            this.times = times;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            Node node = (Node) o;

            return value != null ? value.equals(node.value) : node.value == null;
        }

        @Override
        public int hashCode() {
            return value != null ? value.hashCode() : 0;
        }
    }

    public static class TopKRecord {
        private Node[] heap;
        private int index;
        private Map<String, Node> valueNodeMap;
        private Map<Node, Integer> nodeIndexMap;

        public TopKRecord(int size) {
            this.heap = new Node[size];
            this.index = 0;
            this.valueNodeMap = new HashMap<>();
            this.nodeIndexMap = new HashMap<>();
        }

        public void add(String value) {
            Node curNode;
            int preIndex = -1;
            if (!valueNodeMap.containsKey(value)) {
                // 之前未出现过
                curNode = new Node(value, 1);
                valueNodeMap.put(value, curNode);
                nodeIndexMap.put(curNode, -1);
            } else {
                // 之前出现过
                curNode = valueNodeMap.get(value);
                curNode.times++;
                preIndex = nodeIndexMap.get(curNode);
            }

            if (preIndex == -1) {
                // 堆已满
                if (index == heap.length) {
                    // 比较新加入元素的次数与堆顶元素的次数
                    if (heap[0].times < curNode.times) {
                        nodeIndexMap.put(heap[0], -1);
                        nodeIndexMap.put(curNode, 0);
                        heap[0] = curNode;
                        heapify(0, index);
                    }
                } else {
                    nodeIndexMap.put(curNode, index);
                    heap[index] = curNode;
                    heapInsert(index++);
                }
            } else {
                heapify(preIndex, index);
            }
        }

        public void printTopK() {
            for (int i = 0; i != heap.length; i++) {
                if (heap[i] == null) {
                    break;
                }
                System.out.print("value: " + heap[i].value);
                System.out.println(", times: " + heap[i].times);
            }
        }

        private void heapInsert(int index) {
            while (index != 0) {
                int parent = (index - 1) / 2;
                if (heap[index].times < heap[parent].times) {
                    swap(parent, index);
                    index = parent;
                } else {
                    break;
                }
            }
        }

        /**
         * 从index开始进行堆调整
         */
        private void heapify(int index, int heapSize) {
            int left = index * 2 + 1;
            int right = index * 2 + 2;
            int smallest = index;
            while (left < heapSize) {
                if (heap[left].times < heap[index].times) {
                    smallest = left;
                }
                if (right < heapSize && heap[right].times < heap[smallest].times) {
                    smallest = right;
                }
                if (smallest != index) {
                    swap(smallest, index);
                } else {
                    break;
                }
                index = smallest;
                left = index * 2 + 1;
                right = index * 2 + 2;
            }
        }

        /**
         * 节点位置调整
         */
        private void swap(int self, int other) {
            if (self == other) {
                return;
            }
            nodeIndexMap.put(heap[self], other);
            nodeIndexMap.put(heap[other], self);
            Node tmp = heap[self];
            heap[self] = heap[other];
            heap[other] = tmp;
        }
    }

    public static void main(String[] args) {
        String[] array = {"A", "B", "C", "D", "E", "A", "C", "B", "C", "B", "A", "A"};
        TopKRecord record = new TopKRecord(2);
        for (String cur : array) {
            record.add(cur);
            record.printTopK();
            System.out.println("------------------");
        }
    }
}
