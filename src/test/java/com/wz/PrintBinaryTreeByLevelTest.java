package com.wz;

import java.util.LinkedList;
import java.util.Queue;

public class PrintBinaryTreeByLevelTest {
    private static class Node {
        public int value;
        public Node left;
        public Node right;

        private Node(int value) {
            this.value = value;
        }
    }

    private static void solution(Node head) {
        if (head == null) {
            return;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.offer(head);
        Node last = head, nextLast = null;
        int level = 1;
        System.out.print("Level " + (level++) + " : ");
        while (!queue.isEmpty()) {
            Node cur = queue.poll();
            System.out.print(cur.value + " ");
            if (cur.left != null) {
                queue.offer(cur.left);
                nextLast = cur.left;
            }
            if (cur.right != null) {
                queue.offer(cur.right);
                nextLast = cur.right;
            }
            if (last == cur && !queue.isEmpty()) {
                System.out.println();
                System.out.print("Level " + (level++) + " : ");
                last = nextLast;
            }
        }
    }

    public static void main(String[] args) {
        Node head = new Node(1);
        head.left = new Node(2);
        head.right = new Node(3);
        head.left.left = new Node(4);
        head.right.left = new Node(5);
        head.right.right = new Node(6);
        head.right.left.left = new Node(7);
        head.right.left.right = new Node(8);
        solution(head);
    }
}
