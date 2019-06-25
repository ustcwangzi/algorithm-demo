package com.wz;

public class ReversePartListTest {
    private static class Node {
        public int value;
        public Node next;

        private Node(int value) {
            this.value = value;
        }
    }

    private static Node solution(Node head, int from, int to) {
        if (head == null || head.next == null || from < 1 || to <= from) {
            return head;
        }

        int len = 0;
        Node cur = head, left = null, end = null, last = null;
        while (cur != null) {
            len++;
            left = (len == from - 1) ? cur : left;
            end = len == to ? cur : end;
            cur = cur.next;
            last = cur == null ? last : cur;
        }
        if (end == null) {
            return head;
        }

        reverse(left, left == null ? head : left.next, end, end.next);
        return left == null ? end : head;
    }

    private static void reverse(Node left, Node start, Node end, Node right) {
        Node pre = start, cur = start.next, next;
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
        Node head1 = new Node(1);
        head1.next = new Node(2);
        head1.next.next = new Node(3);
        head1.next.next.next = new Node(4);
        head1 = solution(head1, 2, 3);
        while (head1 != null) {
            System.out.print(head1.value + " ");
            head1 = head1.next;
        }

        System.out.println();

        Node head2 = new Node(1);
        head2.next = new Node(2);
        head2.next.next = new Node(3);
        head2.next.next.next = new Node(4);
        head2 = solution(head2, 1, 4);
        while (head2 != null) {
            System.out.print(head2.value + " ");
            head2 = head2.next;
        }
    }
}
