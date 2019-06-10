package com.wz;

import java.util.Deque;
import java.util.LinkedList;

/**
 * <p>按照ZigZag打印二叉树</p>
 *
 * @author wangzi
 */
public class PrintBinaryTreeByZigZagTest {
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
        Deque<Node> deque = new LinkedList<>();
        deque.offerLast(head);
        boolean forward = true;
        Node last = head, nextLast = null;

        int level = 1;
        printLevelAndOrientation(level++, forward);

        while (!deque.isEmpty()) {
            Node cur;
            if (forward) {
                cur = deque.pollFirst();
                if (cur.left != null) {
                    nextLast = nextLast == null ? cur.left : nextLast;
                    deque.offerLast(cur.left);
                }
                if (cur.right != null) {
                    nextLast = nextLast == null ? cur.right : nextLast;
                    deque.offerLast(cur.right);
                }
            } else {
                cur = deque.pollLast();
                if (cur.right != null) {
                    nextLast = nextLast == null ? cur.right : nextLast;
                    deque.offerFirst(cur.right);
                }
                if (cur.left != null) {
                    nextLast = nextLast == null ? cur.left : nextLast;
                    deque.offerFirst(cur.left);
                }
            }

            System.out.print(cur.value + " ");
            if (cur == last && !deque.isEmpty()) {
                forward = !forward;
                last = nextLast;
                nextLast = null;
                System.out.println();
                printLevelAndOrientation(level++, forward);
            }
        }
    }

    private static void printLevelAndOrientation(int level, boolean forward) {
        System.out.print("Level " + level + " from ");
        System.out.print(forward ? "left to right: " : "right to left: ");
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
