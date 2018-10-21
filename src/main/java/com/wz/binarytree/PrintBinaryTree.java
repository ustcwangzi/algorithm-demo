/**
 * <p>Title: PrintBinaryTree</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/10/21</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.binarytree;

/**
 * <p>直观的打印二叉树</p>
 *
 * @author wangzi
 */
public class PrintBinaryTree {
    private static final String SYMBOL_HEAD = "H";
    private static final String SYMBOL_UPPER = "^";
    private static final String SYMBOL_DOWN = "v";
    private static final int LENGTH = 17;

    private static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    public static void printTree(Node head) {
        printInOrder(head, 0, SYMBOL_HEAD);
    }

    private static void printInOrder(Node head, int height, String symbol) {
        if (head == null) {
            return;
        }
        printInOrder(head.right, height + 1, SYMBOL_DOWN);
        String val = symbol + head.value + symbol;
        int lenMid = val.length();
        int lenLeft = (LENGTH - lenMid) / 2;
        int lenRight = LENGTH - lenMid - lenLeft;
        val = getSpace(lenLeft) + val + getSpace(lenRight);
        System.out.println(getSpace(height * LENGTH) + val);
        printInOrder(head.left, height + 1, SYMBOL_UPPER);
    }

    private static String getSpace(int num) {
        String space = " ";
        StringBuilder builder = new StringBuilder("");
        for (int i = 0; i < num; i++) {
            builder.append(space);
        }
        return builder.toString();
    }

    public static void main(String[] args) {
        Node head = new Node(1);
        head.left = new Node(2);
        head.right = new Node(3);
        head.left.left = new Node(4);
        head.right.left = new Node(5);
        head.right.right = new Node(6);
        head.left.left.right = new Node(7);
        printTree(head);
    }
}
