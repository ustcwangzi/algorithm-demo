/**
 * <p>Title: CompleteTreeNodeNumber</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/11/10</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.binarytree;

/**
 * <p>统计完全二叉树的节点数</p>
 *
 * @author wangzi
 */
public class CompleteTreeNodeNumber {
    private static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    public static int nodeNum(Node head) {
        if (head == null) {
            return 0;
        }
        return search(head, 1, mostLeftLevel(head, 1));
    }

    private static int search(Node node, int level, int height) {
        if (level == height) {
            return 1;
        }

        if (mostLeftLevel(node.right, level + 1) == height) {
            return (1 << (height - level)) + search(node.right, level + 1, height);
        } else {
            return (1 << (height - level - 1)) + search(node.left, level + 1, height);
        }
    }

    /**
     * 以node为头节点的树的高度
     */
    private static int mostLeftLevel(Node node, int level) {
        while (node != null) {
            level++;
            node = node.left;
        }
        return level - 1;
    }

    public static void main(String[] args) {
        Node head = new Node(1);
        head.left = new Node(2);
        head.right = new Node(3);
        head.left.left = new Node(4);
        head.left.right = new Node(5);
        head.right.left = new Node(6);

        System.out.println(nodeNum(head));
    }
}
