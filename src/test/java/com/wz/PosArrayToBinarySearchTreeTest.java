package com.wz;

/**
 * <p>根据后序数组重建搜索二叉树</p>
 *
 * @author wangzi
 */
public class PosArrayToBinarySearchTreeTest {
    private static class Node {
        public int value;
        public Node left;
        public Node right;

        private Node(int value) {
            this.value = value;
        }
    }

    private static Node solution(int[] array) {
        if (!isValid(array, 0, array.length - 1)) {
            return null;
        }

        return buildTree(array, 0, array.length - 1);
    }

    private static Node buildTree(int[] array, int start, int end) {
        if (start > end) {
            return null;
        }
        Node head = new Node(array[end]);
        int left = -1, right = end;
        for (int i = start; i < end; i++) {
            if (array[i] < array[end]) {
                left = i;
            } else {
                right = right == end ? i : right;
            }
        }
        head.left = buildTree(array, start, left);
        head.right = buildTree(array, right, end - 1);
        return head;
    }

    private static boolean isValid(int[] array, int start, int end) {
        if (array == null || array.length == 0) {
            return false;
        }

        if (start == end) {
            return true;
        }
        int left = -1, right = end;
        for (int i = start; i < end; i++) {
            if (array[i] < array[end]) {
                left = i;
            } else {
                right = right == end ? i : right;
            }
        }

        if (left == -1 || right == end) {
            return isValid(array, start, end - 1);
        }
        if (left != right - 1) {
            return false;
        }
        return isValid(array, start, left) && isValid(array, right, end - 1);
    }

    private static void inOrder(Node head) {
        if (head == null) {
            return;
        }
        inOrder(head.left);
        System.out.print(head.value + " ");
        inOrder(head.right);
    }

    public static void main(String[] args) {
        int[] array = {2, 1, 3, 6, 5, 7, 4};

        Node head = solution(array);
        inOrder(head);
    }
}
