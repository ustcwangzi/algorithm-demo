/**
 * <p>Title: TreeTraversalWithoutRecursive</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019-06-16</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * <p>非递归遍历二叉树</p>
 *
 * @author wangzi
 */
public class TreeTraversalWithoutRecursive {
    private static class Node {
        public int value;
        public Node left;
        public Node right;

        private Node(int value) {
            this.value = value;
        }
    }

    private static void preOrder(Node head) {
        if (head == null) {
            return;
        }
        Stack<Node> stack = new Stack<>();
        while (!stack.isEmpty() || head != null) {
            if (head != null) {
                System.out.print(head.value + " ");
                stack.push(head);
                head = head.left;
            } else {
                head = stack.pop();
                head = head.right;
            }
        }
    }

    private static void inOrder(Node head) {
        if (head == null) {
            return;
        }
        Stack<Node> stack = new Stack<>();
        while (!stack.isEmpty() || head != null) {
            if (head != null) {
                stack.push(head);
                head = head.left;
            } else {
                head = stack.pop();
                System.out.print(head.value + " ");
                head = head.right;
            }
        }
    }

    private static void posOrder(Node head) {
        if (head == null) {
            return;
        }
        Stack<Node> stack = new Stack<>();
        while (head != null) {
            stack.push(head);
            head = head.left;
        }

        Node lastVisit = null;
        while (!stack.isEmpty()) {
            head = stack.pop();
            if (head.right == null || head.right == lastVisit) {
                System.out.print(head.value + " ");
                lastVisit = head;
            } else if (head.left == lastVisit) {
                stack.push(head);
                head = head.right;
                while (head != null) {
                    stack.push(head);
                    head = head.left;
                }
            }
        }
    }

    private static void levelOrder(Node head) {
        if (head == null) {
            return;
        }
        Queue<Node> queue = new LinkedList<>();
        Node last = head, nextLast = null;
        queue.add(head);
        while (!queue.isEmpty()) {
            head = queue.poll();
            System.out.print(head.value + " ");
            if (head.left != null) {
                nextLast = head.left;
                queue.offer(head.left);
            }
            if (head.right != null) {
                nextLast = head.right;
                queue.offer(head.right);
            }
            if (head == last && !queue.isEmpty()) {
                System.out.println();
                last = nextLast;
            }
        }
    }

    public static void main(String[] args) {
        Node head = new Node(5);
        head.left = new Node(3);
        head.right = new Node(8);
        head.left.left = new Node(2);
        head.left.right = new Node(4);
        head.left.left.left = new Node(1);
        head.right.left = new Node(7);
        head.right.left.left = new Node(6);
        head.right.right = new Node(10);
        head.right.right.left = new Node(9);
        head.right.right.right = new Node(11);

        System.out.print("pre-order: ");
        preOrder(head);
        System.out.println();

        System.out.print("in-order: ");
        inOrder(head);
        System.out.println();

        System.out.print("pos-order: ");
        posOrder(head);
        System.out.println();

        System.out.println("level-order: ");
        levelOrder(head);
    }
}
