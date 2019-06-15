/**
 * <p>Title: TreeTraversalRecursive</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019-06-15</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz;

/**
 * <p>递归遍历二叉树</p>
 *
 * @author wangzi
 */
public class TreeTraversalRecursive {
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
        System.out.print(head.value + " ");
        preOrder(head.left);
        preOrder(head.right);
    }

    private static void inOrder(Node head) {
        if (head == null) {
            return;
        }
        inOrder(head.left);
        System.out.print(head.value + " ");
        inOrder(head.right);
    }

    private static void posOrder(Node head) {
        if (head == null) {
            return;
        }
        posOrder(head.left);
        posOrder(head.right);
        System.out.print(head.value + " ");
    }

    private static void levelOrder(Node head) {
        int height = getHeight(head, 0);
        for (int i = 1; i <= height; i++) {
            levelOrder(head, i);
            System.out.println();
        }
    }

    private static void levelOrder(Node head, int level) {
        if (head == null || level < 1) {
            return;
        }
        if (level == 1) {
            System.out.print(head.value + " ");
            return;
        }
        levelOrder(head.left, level - 1);
        levelOrder(head.right, level - 1);
    }

    private static int getHeight(Node head, int height) {
        if (head == null) {
            return height;
        }
        return Math.max(getHeight(head.left, height + 1), getHeight(head.right, height + 1));
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
