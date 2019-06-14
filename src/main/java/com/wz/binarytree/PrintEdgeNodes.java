/**
 * <p>Title: PrintEdgeNodes</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/10/21</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.binarytree;

/**
 * <p>打印二叉树边界节点</p>
 * <p>
 *     按照两种方式分别逆时针打印二叉树的边界节点
 *     方式一：
 *          1、头节点、叶节点为边界节点
 *          2、每一层的最左最右节点是边界节点
 *     方式二：
 *          1、头节点、叶节点为边界节点
 *          2、左边界延伸路径、右边界延伸路径为边界节点
 *     方式一解决方案：
 *          1、获取每一层的最左和最后的节点
 *          2、从上到下输出每一层的最左节点
 *          3、先序遍历二叉树，输出叶节点(同时要不属于某一层的最左最右节点)
 *          4、从下到上输出每一层的最右节点
 *     方式二解决方案：
 *          1、最头节点开始，找到第一个既有左孩子、又有右孩子的节点，记为h，进入步骤二
 *          2、h的左子树进入步骤三，h的右子树进入步骤四
 *          3、输出左边界的延伸路径及h左子树的所有叶节点
 *          4、输出右边界的延伸路径及h右子树的所有叶节点
 * </p>
 *
 * @author wangzi
 */
public class PrintEdgeNodes {
    private static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    public static void printEdgeOne(Node head) {
        if (head == null) {
            return;
        }
        int height = getHeight(head, 0);
        // 每一层最左和最右节点
        Node[][] edgeMap = new Node[height][2];
        fillEdgeMap(head, 0, edgeMap);
        // 左边界节点
        for (Node[] nodes : edgeMap) {
            System.out.print(nodes[0].value + " ");
        }
        // 叶节点
        printLeafNode(head, 0, edgeMap);
        // 右边界节点
        for (int i = height - 1; i >= 0; i--) {
            if (edgeMap[i][0] != edgeMap[i][1]) {
                System.out.print(edgeMap[i][1].value + " ");
            }
        }
    }

    private static int getHeight(Node head, int height) {
        if (head == null) {
            return height;
        }
        return Math.max(getHeight(head.left, height + 1), getHeight(head.right, height + 1));
    }

    /**
     * 获取每一层的最左和最右节点
     */
    private static void fillEdgeMap(Node head, int height, Node[][] edgeMap) {
        if (head == null) {
            return;
        }
        edgeMap[height][0] = edgeMap[height][0] == null ? head : edgeMap[height][0];
        edgeMap[height][1] = head;
        fillEdgeMap(head.left, height + 1, edgeMap);
        fillEdgeMap(head.right, height + 1, edgeMap);
    }

    /**
     * 输出叶节点
     */
    private static void printLeafNode(Node head, int height, Node[][] map) {
        if (head == null) {
            return;
        }
        if (head.left == null && head.right == null && head != map[height][0] && head != map[height][1]) {
            System.out.print(head.value + " ");
        }
        printLeafNode(head.left, height + 1, map);
        printLeafNode(head.right, height + 1, map);
    }

    public static void printEdgeTwo(Node head) {
        if (head == null) {
            return;
        }
        System.out.print(head.value + " ");
        if (head.left != null && head.right != null) {
            printLeftEdge(head.left, true);
            printRightEdge(head.right, true);
        } else {
            printEdgeTwo(head.left != null ? head.left : head.right);
        }
    }

    /**
     * 左边界延伸路径节点及叶节点
     */
    private static void printLeftEdge(Node head, boolean print) {
        if (head == null) {
            return;
        }
        if (print || (head.left == null && head.right == null)) {
            System.out.print(head.value + " ");
        }
        printLeftEdge(head.left, print);
        printLeftEdge(head.right, print && head.left == null);
    }

    /**
     * 右边界延伸路径节点及叶节点
     */
    private static void printRightEdge(Node head, boolean print) {
        if (head == null) {
            return;
        }
        printRightEdge(head.left, print && head.right == null);
        printRightEdge(head.right, print);
        if (print || (head.left == null && head.right == null)) {
            System.out.print(head.value + " ");
        }
    }

    public static void main(String[] args) {
        Node head = new Node(1);
        head.left = new Node(2);
        head.right = new Node(3);
        head.left.right = new Node(4);
        head.right.left = new Node(5);
        head.right.right = new Node(6);
        head.left.right.left = new Node(7);
        head.left.right.right = new Node(8);
        head.right.left.left = new Node(9);
        head.right.left.right = new Node(10);
        head.left.right.right.right = new Node(11);
        head.right.left.left.left = new Node(12);
        head.left.right.right.right.left = new Node(13);
        head.left.right.right.right.right = new Node(14);
        head.right.left.left.left.left = new Node(15);
        head.right.left.left.left.right = new Node(16);

        printEdgeOne(head);
        System.out.println();
        printEdgeTwo(head);
    }
}
