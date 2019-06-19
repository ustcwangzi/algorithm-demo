package com.wz;

public class ListSelectionSortTest {
    private static class Node {
        public int value;
        public Node next;

        private Node(int value) {
            this.value = value;
        }
    }

    private static Node solution(Node head) {
        if (head == null || head.next == null) {
            return head;
        }
        Node tail = null;
        Node cur = head;
        Node small, smallPre;
        while (cur != null) {
            small = cur;
            smallPre = getSmallestPreNode(cur);
            if (smallPre != null) {
                small = smallPre.next;
                smallPre.next = small.next;
            }

            cur = cur == small ? cur.next : cur;
            if (tail == null) {
                head = small;
            } else {
                tail.next = small;
            }
            tail = small;
        }
        return head;
    }

    private static Node getSmallestPreNode(Node head) {
        if (head == null || head.next == null) {
            return null;
        }
        Node small = head, smallPre = null;
        Node pre = head, cur = head.next;
        while (cur != null) {
            if (cur.value < small.value) {
                small = cur;
                smallPre = pre;
            }
            pre = cur;
            cur = cur.next;
        }
        return smallPre;
    }

    public static void main(String[] args) {
        Node head = new Node(3);
        head.next = new Node(1);
        head.next.next = new Node(4);
        head.next.next.next = new Node(2);

        head = solution(head);
        while (head != null) {
            System.out.print(head.value + " ");
            head = head.next;
        }
    }
}
