package com.wz;

public class InsertToCircularListTest {
    private static class Node {
        public int value;
        public Node next;

        private Node(int value) {
            this.value = value;
        }
    }

    private static Node solution(Node head, int num) {
        Node node = new Node(num);
        if (head == null) {
            node.next = node;
            return node;
        }

        Node pre = head, cur = head.next;
        while (cur != head) {
            if (pre.value <= num && cur.value >= num) {
                break;
            }
            pre = cur;
            cur = cur.next;
        }

        pre.next = node;
        node.next = cur;
        return head.value < num ? head : node;
    }

    private static void print(Node head) {
        if (head == null) {
            return;
        }
        System.out.print(head.value + " ");
        Node cur = head.next;
        while (cur != head) {
            System.out.print(cur.value + " ");
            cur = cur.next;
        }
        System.out.println("-> " + head.value);
    }

    public static void main(String[] args) {
        Node head = null;
        head = solution(head, 2);
        print(head);
        head = solution(head, 1);
        print(head);
        head = solution(head, 4);
        print(head);
        head = solution(head, 3);
        print(head);
        head = solution(head, 5);
        print(head);
        head = solution(head, 0);
        print(head);
    }
}
