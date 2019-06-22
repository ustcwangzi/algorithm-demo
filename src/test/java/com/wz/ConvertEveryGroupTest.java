/**
 * <p>Title: ConvertEveryGroupTest</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019-06-22</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz;

/**
 * <p></p>
 *
 * @author wangzi
 */
public class ConvertEveryGroupTest {
    private static class Node {
        public int value;
        public Node next;

        private Node(int value) {
            this.value = value;
        }
    }

    private static Node solution(Node head, int k) {
        if (head == null || head.next == null || k < 2) {
            return head;
        }

        Node cur = head, left = null, start, next;
        int count = 1;
        while (cur != null) {
            next = cur.next;
            if (count == k) {
                start = left == null ? head : left.next;
                head = left == null ? cur : head;
                reverse(left, start, cur, next);
                left = start;
                count = 0;
            }
            count++;
            cur = next;
        }
        return head;
    }

    private static void reverse(Node left, Node start, Node end, Node right) {
        Node pre = start;
        Node cur = pre.next, next;
        while (cur != right) {
            next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        if (left != null) {
            left.next = end;
        }
        start.next = right;
    }

    public static void main(String[] args) {
        Node head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        head.next.next.next = new Node(4);
        head.next.next.next.next = new Node(5);
        head.next.next.next.next.next = new Node(6);
        head.next.next.next.next.next.next = new Node(7);

        head = solution(head, 3);
        while (head != null) {
            System.out.print(head.value + " ");
            head = head.next;
        }
    }
}
