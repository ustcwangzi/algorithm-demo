/**
 * <p>Title: CommonPartTest</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019-06-30</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz;

/**
 * <p>输出两个有序链表的公共部分</p>
 *
 * @author wangzi
 */
public class CommonPartTest {
    private static class Node {
        public int value;
        public Node next;

        private Node(int value) {
            this.value = value;
        }
    }

    private static void solution(Node self, Node other) {
        while (self != null && other != null) {
            // 值小的节点前移
            if (self.value > other.value) {
                other = other.next;
            } else if (self.value < other.value) {
                self = self.next;
            } else {
                // 值相同，则输出
                System.out.print(self.value + " ");
                self = self.next;
                other = other.next;
            }
        }
    }

    public static void main(String[] args) {
        Node node1 = new Node(2);
        node1.next = new Node(3);
        node1.next.next = new Node(5);
        node1.next.next.next = new Node(6);

        Node node2 = new Node(1);
        node2.next = new Node(2);
        node2.next.next = new Node(5);
        node2.next.next.next = new Node(7);
        node2.next.next.next.next = new Node(8);

        solution(node1, node2);
    }
}
