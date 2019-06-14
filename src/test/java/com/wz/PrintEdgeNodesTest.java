package com.wz;

/**
 * <p>头节点、叶节点以及每一层的最左最右节点为边界节点，逆时针打印二叉树的边界节点</p>
 *
 * @author wangzi
 */
public class PrintEdgeNodesTest {
    private static class Node {
        public int value;
        public Node left;
        public Node right;

        private Node(int value) {
            this.value = value;
        }
    }

    private static void solution(Node head) {
        if (head == null) {
            return;
        }
        // 树高
        int height = getHeight(head, 0);
        // 记录每一层的最左、最右节点
        Node[][] edges = new Node[height][2];
        // 填充每一层的最左、最右节点
        fillEdges(head, 0, edges);

        // 打印左边界
        for (Node[] nodes : edges) {
            System.out.print(nodes[0].value + " ");
        }
        // 打印叶节点
        printLeafNode(head, 0, edges);
        // 打印右边界
        for (int i = height - 1; i >= 0; i--) {
            if (edges[i][0] != edges[i][1]) {
                System.out.print(edges[i][1].value + " ");
            }
        }
    }

    private static void printLeafNode(Node head, int height, Node[][] edges) {
        if (head == null) {
            return;
        }
        if (head.left == null && head.right == null && head != edges[height][0] && head != edges[height][1]) {
            System.out.print(head.value + " ");
        }
        printLeafNode(head.left, height + 1, edges);
        printLeafNode(head.right, height + 1, edges);
    }

    private static int getHeight(Node head, int height) {
        if (head == null) {
            return height;
        }
        return Math.max(getHeight(head.left, height + 1), getHeight(head.right, height + 1));
    }

    private static void fillEdges(Node head, int height, Node[][] edges) {
        if (head == null) {
            return;
        }
        edges[height][0] = edges[height][0] == null ? head : edges[height][0];
        edges[height][1] = head;
        fillEdges(head.left, height + 1, edges);
        fillEdges(head.right, height + 1, edges);
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

        solution(head);
    }
}
