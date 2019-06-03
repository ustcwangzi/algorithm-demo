package com.wz;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>有序数组生成平衡搜索二叉树</p>
 *
 * @author wangzi
 */
public class SortedArrayToBalancedBinarySearchTreeTest {
    private static class Node {
        public int value;
        public Node left;
        public Node right;

        private Node(int value) {
            this.value = value;
        }
    }

    private static Node solution(Integer[] array) {
        return buildTree(array, 0, array.length - 1);
    }

    /**
     * 用有序数组中间元素生成头节点，然后左边的元素生成左子树、右边的元素生成右子树
     */
    private static Node buildTree(Integer[] array, int start, int end) {
        if (start > end) {
            return null;
        }
        int mid = (start + end) / 2;
        Node head = new Node(array[mid]);
        head.left = buildTree(array, start, mid - 1);
        head.right = buildTree(array, mid + 1, end);
        return head;
    }

    /**
     * 中序遍历
     */
    private static void inOrder(Node head, List<Integer> list) {
        if (head == null) {
            return;
        }
        inOrder(head.left, list);
        list.add(head.value);
        inOrder(head.right, list);
    }

    public static void main(String[] args) {
        int times = 100;
        boolean result = true;
        for (int i = 0; i < times; i++) {
            Integer[] array = RandomUtils.genIncreaseArray(i + 1);
            Node head = solution(array);

            List<Integer> list = new ArrayList<>();
            inOrder(head, list);
            Integer[] traversal = new Integer[list.size()];
            list.toArray(traversal);

            if (!Arrays.equals(array, traversal)) {
                result = false;
                System.out.println("Error, array:" + Arrays.toString(array));
            }
        }
        if (result) {
            System.out.println("Past");
        }
    }
}
