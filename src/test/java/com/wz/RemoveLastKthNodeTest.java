/**
 * <p>Title: RemoveLastKthNodeTest</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019-06-29</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz;

/**
 * <p></p>
 *
 * @author wangzi
 */
public class RemoveLastKthNodeTest {
    private static class Node {
        public int value;
        public Node next;

        private Node(int value) {
            this.value = value;
        }
    }

    private static Node solution(Node head, int k) {
        if (head == null || k < 1) {
            return head;
        }

        Node cur = head;
        while (cur != null) {
            k--;
            cur = cur.next;
        }
        if (k == 0) {
            head = head.next;
        } else if (k < 0) {
            cur = head;
            while (++k != 0) {
                cur = cur.next;
            }
            cur.next = cur.next.next;
        }
        return head;
    }

    public static void main(String[] args) {
        Node head1 = new Node(1);
        head1.next = new Node(2);
        head1.next.next = new Node(3);
        head1 = solution(head1, 2);
        while (head1 != null) {
            System.out.print(head1.value + " ");
            head1 = head1.next;
        }

        System.out.println();

        Node head2 = new Node(1);
        head2.next = new Node(2);
        head2.next.next = new Node(3);
        head2 = solution(head2, 3);
        while (head2 != null) {
            System.out.print(head2.value + " ");
            head2 = head2.next;
        }

        System.out.println();
    }
}
