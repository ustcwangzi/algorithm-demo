package com.wz;

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
        Node left = posOrder(head.left, record);
        int leftSize = record[0];
        int leftMin = record[1];
        int leftMax = record[2];

        Node right = posOrder(head.right, record);
        int rightSize = record[0];
        int rightMin = record[1];
        int rightMax = record[2];

        record[1] = Math.min(leftMin, head.value);
        record[2] = Math.max(rightMax, head.value);

        if (left == head.left && right == head.right && leftMax < head.value && rightMin > head.value) {
            record[0] = leftSize + rightSize + 1;
            return head;
        }
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
