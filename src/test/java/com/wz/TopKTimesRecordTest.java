package com.wz;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>输出词频前K的字符，有输入功能</p>
 *
 * @author wangzi
 */
public class TopKTimesRecordTest {
    private static class Node {
        public String value;
        public int times;

        public Node(String value, int times) {
            this.value = value;
            this.times = times;
        }
    }

    private static class TopKTimesRecord {
        private Node[] heap;
        private int index;
        private Map<Node, Integer> nodeIndexMap;
        private Map<String, Node> valueNodeMap;

        private TopKTimesRecord(int size) {
            this.heap = new Node[size];
            this.index = 0;
            this.nodeIndexMap = new HashMap<>();
            this.valueNodeMap = new HashMap<>();
        }

        private void add(String value) {
            Node curNode;
            int preIndex = -1;
            // 之前出现过
            if (valueNodeMap.containsKey(value)) {
                curNode = valueNodeMap.get(value);
                curNode.times++;
                preIndex = nodeIndexMap.get(curNode);
            } else {
                curNode = new Node(value, 1);
                valueNodeMap.put(value, curNode);
                nodeIndexMap.put(curNode, preIndex);
            }

            // 之前未出现过
            if (preIndex == -1) {
                // 堆已满
                if (index == heap.length) {
                    // 检查是否需要进堆
                    if (heap[0].times < curNode.times) {
                        nodeIndexMap.put(heap[0], -1);
                        nodeIndexMap.put(curNode, 0);
                        heap[0] = curNode;
                        heapify(0, index);
                    }
                } else {
                    // 直接进堆
                    nodeIndexMap.put(curNode, index);
                    heap[index] = curNode;
                    heapInsert(index++);
                }
            } else {
                // 直接进行堆调整
                heapify(preIndex, index);
            }
        }

        private void heapInsert(int index) {
            while (index > 0) {
                int parent = (index - 1) / 2;
                if (heap[parent].times > heap[index].times) {
                    swap(index, parent);
                    index = parent;
                } else {
                    break;
                }
            }
        }

        private void printTopK() {
            for (int i = 0; i != heap.length; i++) {
                if (heap[i] == null) {
                    break;
                }
                System.out.print("value: " + heap[i].value);
                System.out.println(", times: " + heap[i].times);
            }
        }

        private void heapify(int index, int heapSize) {
            int left = index * 2 + 1;
            int right = index * 2 + 2;
            int smallest = index;
            while (left < heapSize) {
                if (heap[left].times < heap[smallest].times) {
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

        private void swap(int self, int other) {
            nodeIndexMap.put(heap[self], other);
            nodeIndexMap.put(heap[other], self);
            Node tmp = heap[self];
            heap[self] = heap[other];
            heap[other] = tmp;
        }
    }

    public static void main(String[] args) {
        String[] array = {"A", "B", "C", "D", "E", "A", "C", "B", "C", "B", "A", "A"};
        com.wz.others.TopKTimesRecord.TopKRecord record = new com.wz.others.TopKTimesRecord.TopKRecord(2);
        TopKTimesRecord curRecord = new TopKTimesRecord(2);
        for (String cur : array) {
            record.add(cur);
            curRecord.add(cur);
            record.printTopK();
            curRecord.printTopK();
            System.out.println("------------------");
        }
    }
}
