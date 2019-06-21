package com.wz;

import java.util.LinkedList;
import java.util.Queue;

public class BinaryTreeToListTest {
    private static class Node {
        public int value;
        public Node left;
        public Node right;

        private Node(int value) {
            this.value = value;
        }
    }

    private static Node solution(Node head) {
        if (head == null) {
            return null;
        }
        Queue<Node> queue = new LinkedList<>();
        inOrder(head, queue);
        head = queue.poll();
        Node pre = head, cur;
        while (!queue.isEmpty()) {
            cur = queue.poll();
            cur.left = pre;
            pre.right = cur;
            pre = cur;
        }
        return head;
    }

    private static void inOrder(Node head, Queue<Node> queue) {
        if (head == null) {
            return;
        }
        inOrder(head.left, queue);
        queue.offer(head);
        inOrder(head.right, queue);
    }

    private static void print(Node head) {
        if (head == null) {
            return;
        }

        Node end = null;
        while (head != null) {
            System.out.print(head.value + " ");
            end = head;
            head = head.right;
        }
        System.out.print("| ");
        while (end != null) {
            System.out.print(end.value + " ");
            end = end.left;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Node head = new Node(5);
        head.left = new Node(2);
        head.right = new Node(9);
        head.left.left = new Node(1);
        head.left.right = new Node(3);
        head.left.right.right = new Node(4);
        head.right.left = new Node(7);
        head.right.right = new Node(10);
        head.left.left = new Node(1);
        head.right.left.left = new Node(6);
        head.right.left.right = new Node(8);

        head = solution(head);
        print(head);
    }
}
