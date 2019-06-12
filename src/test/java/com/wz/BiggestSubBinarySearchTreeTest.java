package com.wz;

/**
 * <p>找到含有节点数最多的搜索二叉子树</p>
 *
 * @author wangzi
 */
public class BiggestSubBinarySearchTreeTest {
    private static class Node {
        public int value;
        public Node left;
        public Node right;

        private Node(int value) {
            this.value = value;
        }
    }

    private static Node solution(Node head) {
        // 存储节点数、最小值、最大值
        int[] record = new int[3];
        return posOrder(head, record);
    }

    /**
     * 递归结果返回最大搜索二叉子树的头节点，record存储子树的节点数、最小值、最大值
     */
    private static Node posOrder(Node head, int[] record) {
        if (head == null) {
            record[0] = 0;
            record[1] = Integer.MAX_VALUE;
            record[2] = Integer.MIN_VALUE;
            return null;
        }

        // 左子树头节点、节点数、最小值、最大值
        Node left = posOrder(head.left, record);
        int leftSize = record[0];
        int leftMin = record[1];
        int leftMax = record[2];

        // 右子树头节点、节点数、最小值、最大值
        Node right = posOrder(head.right, record);
        int rightSize = record[0];
        int rightMin = record[1];
        int rightMax = record[2];

        // 当前树最小值、最大值
        record[1] = Math.min(leftMin, head.value);
        record[2] = Math.max(rightMax, head.value);

        // 左子树最大值小于"当前头节点值"、并且右子树最小值大于"当前头节点值"时，可以进行合并
        if (left == head.left && right == head.right && leftMax < head.value && rightMin > head.value) {
            record[0] = leftSize + rightSize + 1;
            return head;
        }

        // 无法进行合并时，返回节点数较多的子树
        record[0] = Math.max(leftSize, rightSize);
        return leftSize > rightSize ? left : right;
    }

    public static void main(String[] args) {
        Node head = new Node(6);
        head.left = new Node(1);
        head.left.left = new Node(0);
        head.left.right = new Node(3);
        head.right = new Node(12);
        System.out.println(solution(head).value);

        head.right.left = new Node(10);
        head.right.left.left = new Node(4);
        System.out.println(solution(head).value);

        head.right.left.left.left = new Node(2);
        head.right.left.left.right = new Node(5);
        head.right.left.right = new Node(14);
        head.right.left.right.left = new Node(11);
        head.right.left.right.right = new Node(15);
        System.out.println(solution(head).value);

        head.right.right = new Node(13);
        head.right.right.left = new Node(20);
        head.right.right.right = new Node(16);
        System.out.println(solution(head).value);
    }
}
