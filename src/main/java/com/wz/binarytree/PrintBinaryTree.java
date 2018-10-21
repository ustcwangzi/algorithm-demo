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
 * <p>
 *     二叉树的大概样子是把打印结果顺时针旋转90度
 *     用三种符号来标识节点：
 *          H：说明该节点是头节点，无父节点
 *          ^：说明该节点的父节点在该节点所在列的前一列、在该节点所在行的上方
 *          v：说明该节点的父节点在该节点所在列的前一列、在该节点所在行的下方
 *     规定节点打印时所占用的统一长度：
 *          整型值占用长度最长的值是Integer.MIN_VALUE，所占长度为11，
 *       加上上面定义的标志符号，总长度为13，为了有区分度，将长度统一定义为17
 *     打印过程结合先右子树、再根节点、最后左子树的递归遍历过程
 * </p>
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
