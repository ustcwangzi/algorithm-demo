/**
 * <p>Title: PrintBinaryTreeByLevel</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/10/28</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.binarytree;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

/**
 * <p>按层打印二叉树</p>
 *
 * @author wangzi
 */
public class PrintBinaryTreeByLevel {
    private static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    public static void printByLevel(Node head) {
        if (head == null) {
            return;
        }

        Queue<Node> queue = new LinkedList<>();
        int level = 1;
        // 当前行最右节点
        Node last = head;
        // 下一行最右节点
        Node nextLast = null;
        queue.offer(head);
        System.out.print("Level " + (level++) + " : ");
        while (!queue.isEmpty()) {
            head = queue.poll();
            System.out.print(head.value + " ");
            if (head.left != null) {
                queue.offer(head.left);
                nextLast = head.left;
            }
            if (head.right != null) {
                queue.offer(head.right);
                nextLast = head.right;
            }
            // 本次输出的节点是last时，进行换行
            if (head == last && !queue.isEmpty()) {
                System.out.println();
                System.out.print("Level " + (level++) + " : ");
                last = nextLast;
            }
        }
    }

    public static void printByZigZag(Node head) {
        if (head == null) {
            return;
        }

        Deque<Node> deque = new LinkedList<>();
        int level = 1;
        // 正向输出
        boolean forward = true;
        // 当前行最后输出的节点
        Node last = head;
        // 下一行最后输出的节点
        Node nextLast = null;
        deque.offerFirst(head);
        printLevelAndOrientation(level++, forward);
        while (!deque.isEmpty()) {
            if (forward) {
                // 正向时，头出尾入(先左后右)
                head = deque.pollFirst();
                // 下一行最后输出的节点是当前行有孩子的节点中最先加入queue的
                if (head.left != null) {
                    nextLast = nextLast == null ? head.left : nextLast;
                    deque.offerLast(head.left);
                }
                if (head.right != null) {
                    nextLast = nextLast == null ? head.right : nextLast;
                    deque.offerLast(head.right);
                }
            } else {
                // 逆向时，尾出头入(先右后左)
                head = deque.pollLast();
                if (head.right != null) {
                    nextLast = nextLast == null ? head.right : nextLast;
                    deque.offerFirst(head.right);
                }
                if (head.left != null) {
                    nextLast = nextLast == null ? head.left : nextLast;
                    deque.offerFirst(head.left);
                }
            }
            System.out.print(head.value + " ");
            if (head == last && !deque.isEmpty()) {
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

        printByLevel(head);
        System.out.println();
        printByZigZag(head);
    }
}
