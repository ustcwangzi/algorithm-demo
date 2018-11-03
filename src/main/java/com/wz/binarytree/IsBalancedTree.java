/**
 * <p>Title: IsBalancedTree</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/11/3</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.binarytree;

/**
 * <p>判断二叉树是否为平衡二叉树</p>
 *
 * @author wangzi
 */
public class IsBalancedTree {
    private static boolean result = true;

    private static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    public static boolean isBalance(Node head) {
        getHeight(head, 1);
        return result;
    }

    private static int getHeight(Node head, int level) {
        if (head == null) {
            return level;
        }
        int leftHeight = getHeight(head.left, level + 1);
        if (!result) {
            return level;
        }
        int rightHeight = getHeight(head.right, level);
        if (Math.abs(leftHeight - rightHeight) > 1) {
            result = false;
        }
        return Math.max(leftHeight, rightHeight);
    }

    public static void main(String[] args) {
        Node head = new Node(1);
        head.left = new Node(2);
        head.right = new Node(3);
        head.left.left = new Node(4);
        head.left.right = new Node(5);
        head.right.left = new Node(6);
        head.right.right = new Node(7);

        System.out.println(isBalance(head));
    }
}
