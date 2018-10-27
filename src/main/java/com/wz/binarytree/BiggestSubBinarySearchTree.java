/**
 * <p>Title: BiggestSubBinarySearchTree</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/10/27</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.binarytree;

/**
 * <p>最大搜索二叉子树</p>
 *
 * @author wangzi
 */
public class BiggestSubBinarySearchTree {
    private static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    public static Node biggestSubBST(Node head) {
        // 0->size, 1->min, 2->max
        int[] record = new int[3];
        return posOrder(head, record);
    }

    private static Node posOrder(Node head, int[] record) {
        if (head == null) {
            record[0] = 0;
            record[1] = Integer.MAX_VALUE;
            record[2] = Integer.MIN_VALUE;
            return null;
        }
        int value = head.value;
        Node left = head.left;
        Node right = head.right;

        // 左子树头节点、节点数、最小值、最大值
        Node leftBST = posOrder(left, record);
        int leftSize = record[0];
        int leftMin = record[1];
        int leftMax = record[2];

        // 右子树头节点、节点数、最小值、最大值
        Node rightBST = posOrder(right, record);
        int rightSize = record[0];
        int rightMin = record[1];
        int rightMax = record[2];

        record[1] = Math.min(leftMin, value);
        record[2] = Math.max(rightMax, value);
        // 头节点、左右子树满足搜索树，连在一起
        if (left == leftBST && right == rightBST && leftMax < value && value < rightMin) {
            record[0] = leftSize + rightSize + 1;
            return head;
        }
        // 头节点、左右子树满足不搜索树，返回节点数较多的
        record[0] = Math.max(leftSize, rightSize);
        return leftSize > rightSize ? leftBST : rightBST;
    }

    public static void main(String[] args) {
        Node head = new Node(6);
        head.left = new Node(1);
        head.left.left = new Node(0);
        head.left.right = new Node(3);
        head.right = new Node(12);
        head.right.left = new Node(10);
        head.right.left.left = new Node(4);
        head.right.left.left.left = new Node(2);
        head.right.left.left.right = new Node(5);
        head.right.left.right = new Node(14);
        head.right.left.right.left = new Node(11);
        head.right.left.right.right = new Node(15);
        head.right.right = new Node(13);
        head.right.right.left = new Node(20);
        head.right.right.right = new Node(16);

        Node bst = biggestSubBST(head);
        System.out.println(bst.value);
    }
}
