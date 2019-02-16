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
 * <p>
 *     设计并实现TopKRecord结构，可以不断地向其中加入字符串，并且可以根据字符串出现的情况随时打印加入次数最多的前k个字符串，具体为：
 *     1、k在TopKRecord实例生成时指定，并且不再变化（k是构造函数的参数）；
 *     2、含有add(String str)方法，即向TopKRecord中加入字符串；
 *     3、含有printTopK()方法，即打印加入次数最多的前k个字符串，打印有哪些字符串和对应出现的次数即可，不要求严格按排名顺序打印。
 *     要求：
 *     在任何时刻，add方法的时间复杂度不超过O(logk)；
 *     在任何时刻，printTopK方法的时间复杂度不超过O(k)。
 *     解决方案：
 *        问题的关键在于，字符串出现的次数是动态的，当然也可以像TopKTimesInArray一样，每加入一个字符串，就更新哈希表以及小根堆。
 *        这样可以做到add方法的时间复杂度为O(1)，但是，每次printTopK的时候，都需要遍历一遍哈希表并且重新构建小根堆，
 *        时间复杂度为O(N*logk)。要做到printTopK的时间复杂度为O(logk)，我们就希望每加入一个字符串的时候，
 *        可以利用到之前创建的小根堆，而不是直接重建小根堆。
 *        因此，在TopKTimesInArray的基础上改进一下，每次放入小根堆的元素都记录下它在小根堆中的位置以及它的词频。
 *        即使用valueNodeMap和nodeIndexMap分别记录元素的词频和在堆中的位置，例如"A"出现10次，则valueNodeMap存在记录：
 *        (key="A", value=("A", 10))，假设"A"也在堆中，并且位置为5，则nodeIndexMap存在记录：(key=("A", 10), value=5)。
 *        这样的好处是：假设一个字符串出现了一次，如果字符串已经在小根堆中，此时只需要在小根堆中找到这个字符串所在的位置，
 *        让该字符串的词频加1，然后从该位置开始向下调整小根堆即可。如果该字符串之前不在小根堆中，
 *        只需要看它的词频加一后是否大于堆顶的词频，如果大的话，更新堆顶，并向下调整堆。
 * </p>
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
        /**
         * 记录堆中的位置
         */
        private Map<Node, Integer> nodeIndexMap;
        /**
         * 记录词频
         */
        private Map<String, Node> valueNodeMap;

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
