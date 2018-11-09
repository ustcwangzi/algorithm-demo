/**
 * <p>Title: MaxDistanceInTree</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/10/21</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.binarytree;

/**
 * <p>二叉树节点之间的最大距离</p>
 *
 * @author wangzi
 */
public class MaxDistanceInTree {
    private static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    public static int maxDistance(Node head) {
        int[] record = new int[1];
        return posOrder(head, record);
    }

    private static int posOrder(Node head, int[] record) {
        if (head == null) {
            record[0] = 0;
            return 0;
        }

        // 左子树上的最大距离
        int leftMax = posOrder(head.left, record);
        // 左子树上距离h左孩子的最远距离
        int maxFromLeft = record[0];
        // 右子树上的最大距离
        int rightMax = posOrder(head.right, record);
        // 右子树上距离h右孩子的最远距离
        int maxFromRight = record[0];
        // 跨h节点情况下的最大距离
        int curNodeMax = maxFromLeft + maxFromRight + 1;
        // 距离h最远的距离
        record[0] = Math.max(maxFromLeft, maxFromRight) + 1;
        return Math.max(Math.max(leftMax, rightMax), curNodeMax);
    }

    public static void main(String[] args) {
        Node head = new Node(1);
        head.left = new Node(2);
        head.right = new Node(3);
        head.left.left = new Node(4);
        head.left.right = new Node(5);
        head.right.left = new Node(6);
        head.right.right = new Node(7);
        head.left.left.left = new Node(8);
        head.right.left.right = new Node(9);
        System.out.println(maxDistance(head));
    }
}
