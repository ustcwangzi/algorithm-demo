/**
 * <p>Title: ReversePartList</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/10/6</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.list;

/**
 * <p>反转部分单链表</p>
 * <p>
 *     反转链表from到to之间的节点
 *     过程：
 *          1、找到第from-1个节点pre和第to+1个节点post，反转from和to之间的部分，然后正确连接pre和post
 *          2、如果pre为NULL，说明反转部分包含头节点，则返回新的头节点，即原第to个节点，否则还是原头节点
 * </p>
 *
 * @author wangzi
 */
public class ReversePartList {
    public static class Node {
        public int value;
        public Node next;

        public Node(int value) {
            this.value = value;
        }
    }

    public static Node reversePart(Node head, int from, int to) {
        int len = 0;
        Node node1 = head;
        // 反转部分的前一个节点
        Node pre = null;
        // 反转部分的后一个节点
        Node post = null;
        while (node1 != null) {
            len++;
            pre = (len == from - 1) ? node1 : pre;
            post = (len == to + 1) ? node1 : post;
            node1 = node1.next;
        }

        if (from > to || from < 1 || to > len) {
            return head;
        }

        // node1指向位置from
        node1 = pre == null ? head : pre.next;
        Node node2 = node1.next;
        // node1.next指向位置to+1
        node1.next = post;
        Node next = null;
        // 反转from到to之间的节点
        while (node2 != post) {
            next = node2.next;
            node2.next = node1;
            node1 = node2;
            node2 = next;
        }

        if (pre != null) {
            pre.next = node1;
            return head;
        }
        // 反转部分包含头节点
        return node1;
    }

    public static void main(String[] args) {
        Node head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        head.next.next.next = new Node(4);
        head = reversePart(head, 2, 3);
        while (head != null) {
            System.out.print(head.value + " ");
            head = head.next;
        }
    }

}
