/**
 * <p>Title: ReceiveAndPrintOrderLine</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/1/31</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.others;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>一种消息接收并打印的结构设计</p>
 *
 * @author wangzi
 */
public class ReceiveAndPrintOrderLine {

    private static class Node {
        public int num;
        public Node next;

        public Node(int num) {
            this.num = num;
        }
    }

    public static class MessageBox {
        /**
         * 连续区间开始节点
         */
        private Map<Integer, Node> headMap;
        /**
         * 连续区间结束节点
         */
        private Map<Integer, Node> tailMap;
        /**
         * 最后一个打印的数
         */
        private int lastPrint;

        public MessageBox() {
            this.headMap = new HashMap<>();
            this.tailMap = new HashMap<>();
            this.lastPrint = 0;
        }

        public void receive(int num) {
            if (num < 1) {
                return;
            }
            Node cur = new Node(num);
            headMap.put(num, cur);
            tailMap.put(num, cur);

            if (tailMap.containsKey(num - 1)) {
                tailMap.get(num - 1).next = cur;
                tailMap.remove(num - 1);
                headMap.remove(num);
            }
            if (headMap.containsKey(num + 1)) {
                cur.next = headMap.get(num + 1);
                tailMap.remove(num);
                headMap.remove(num + 1);
            }
            if (headMap.containsKey(lastPrint + 1)) {
                print();
            }
        }

        private void print() {
            Node node = headMap.get(++lastPrint);
            headMap.remove(lastPrint);
            while (node != null) {
                System.out.print(node.num + " ");
                node = node.next;
                lastPrint++;
            }
            tailMap.remove(--lastPrint);
            System.out.println();
        }
    }

    public static void main(String[] args) {
        MessageBox box = new MessageBox();

        // - 2
        box.receive(2);
        // 1 2 -> print, trigger is 1
        box.receive(1);

        // - 4
        box.receive(4);
        // - 4 5
        box.receive(5);
        // - 4 5 - 7
        box.receive(7);
        // - 4 5 - 7 8
        box.receive(8);
        // - 4 5 6 7 8
        box.receive(6);
        // 3 4 5 6 7 8 -> print, trigger is 3
        box.receive(3);

        // 9 -> print, trigger is 9
        box.receive(9);

        // 10 -> print, trigger is 10
        box.receive(10);

        // - 12
        box.receive(12);
        // - 12 13
        box.receive(13);
        // 11 12 13 -> print, trigger is 11
        box.receive(11);
    }
}
