/**
 * <p>Title: RelocateLinkedList</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/10/14</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.list;

/**
 * <p>按照左右半区重新组合单链表</p>
 * <p>
 *     将链表分为左右半区，左半区为L1->L2->...，右半区为R1->R2->...，调整为L1->R1->L2->R2->...
 *     解决过程：
 *          1、找到左半区最后一个节点mid
 *          2、根据mid，将左右半区分离成两个链表
 *          3、将两个链表按照给定要求合并起来
 *     时间复杂度为O(N)，空间复杂度为O(1)
 * </p>
 *
 * @author wangzi
 */
public class RelocateLinkedList {
    private static class Node {
        public int value;
        public Node next;

        public Node(int value) {
            this.value = value;
        }
    }

    public static void relocate(Node head) {
        if (head == null || head.next == null) {
            return;
        }

        // 中间节点
        Node mid = head;
        Node right = head.next;
        while (right.next != null && right.next.next != null) {
            mid = mid.next;
            right = right.next.next;
        }

        // 右半部分开始节点
        right = mid.next;
        mid.next = null;
        merge(head, right);
    }

    private static void merge(Node left, Node right) {
        Node next = null;
        while (left.next != null) {
            next = right.next;
            right.next = left.next;
            left.next = right;
            left = right.next;
            right = next;
        }
        left.next = right;
    }

    public static void main(String[] args) {
        Node head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        head.next.next.next = new Node(4);
        head.next.next.next.next = new Node(5);

        relocate(head);
        while (head != null) {
            System.out.print(head.value + " ");
            head = head.next;
        }
    }
}
