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

    /**
     * 数组的最后一个元素是头节点
     * 左侧都比头节点小，右侧都比头节点大，以此划分数组，递归建立二叉树
     */
    private static Node buildTree(int[] array, int start, int end) {
        if (start > end) {
            return null;
        }
        Node head = new Node(array[end]);
        // left是比头节点小的最后位置，right是比头节点大的第一个位置
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

    /**
     * 检查是否可以生成搜索二叉树
     * 头节点的值一定是该数组最后一个元素
     * 再根据搜索二叉树的性质，头节点会将数组分成左右两边，左边都比头节点小，右边都比头节点大
     */
    private static boolean isValid(int[] array, int start, int end) {
        if (array == null || array.length == 0) {
            return false;
        }

        if (start == end) {
            return true;
        }
        // left是比头节点小的最后位置，right是比头节点大的第一个位置
        int left = -1, right = end;
        for (int i = start; i < end; i++) {
            if (array[i] < array[end]) {
                left = i;
            } else {
                right = right == end ? i : right;
            }
        }

        // 只有两个元素时会出现该情况
        if (left == -1 || right == end) {
            return isValid(array, start, end - 1);
        }
        // left和right相差一个位置才是合法的
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
