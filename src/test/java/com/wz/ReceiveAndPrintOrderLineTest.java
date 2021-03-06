package com.wz;

import com.wz.others.ReceiveAndPrintOrderLine;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>接收消息的同时，不停的打印连续数字</p>
 *
 * @author wangzi
 */
public class ReceiveAndPrintOrderLineTest {
    private static class Node {
        public int number;
        public Node next;

        public Node(int number) {
            this.number = number;
        }
    }

    private static class MessageBox {
        /**
         * 连续区间头节点
         */
        private Map<Integer, Node> headMap;
        /**
         * 连续区间尾节点
         */
        private Map<Integer, Node> tailMap;
        /**
         * 最后一次输出的数
         */
        private int lastPrint;

        public MessageBox() {
            this.headMap = new HashMap<>();
            this.tailMap = new HashMap<>();
            this.lastPrint = 0;
        }

        private void solution(int number) {
            Node cur = new Node(number);
            headMap.put(number, cur);
            tailMap.put(number, cur);

            // 区间合并
            if (tailMap.containsKey(number - 1)) {
                tailMap.get(number - 1).next = cur;
                tailMap.remove(number - 1);
                headMap.remove(number);
            }
            if (headMap.containsKey(number + 1)) {
                cur.next = headMap.get(number + 1);
                headMap.remove(number + 1);
                tailMap.remove(number);
            }
            // 检查是否需要输出
            if (headMap.containsKey(lastPrint + 1)) {
                print();
            }
        }

        private void print() {
            Node node = headMap.get(++lastPrint);
            headMap.remove(lastPrint);
            while (node != null) {
                System.out.print(node.number + " ");
                node = node.next;
                lastPrint++;
            }
            tailMap.remove(--lastPrint);
            System.out.println();
        }
    }

    public static void main(String[] args) {
        MessageBox box = new MessageBox();
        ReceiveAndPrintOrderLine.MessageBox messageBox = new ReceiveAndPrintOrderLine.MessageBox();
        int[] array = RandomUtils.genRandomArray(100);
        for (int cur : array) {
            box.solution(cur);
        }
        System.out.println("=========");
        for (int cur : array) {
            messageBox.receive(cur);
        }
    }
}
