/**
 * <p>Title: SortedArrayToBalancedBinarySearchTree</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/11/4</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.binarytree;

/**
 * <p>有序数组生成平衡搜索二叉树</p>
 * <p>
 *     给定一个无重复元素的有序数组，用这个数组生成一颗平衡搜索二叉树，要求中序遍历结果与数组一致
 *     使用递归方式解决：
 *     用有序数组中中间的元素生成搜索二叉树的头节点，然后左边的元素生成左子树、右边的元素生成右子树即可
 * <p>
 * </p>
 *
 * @author wangzi
 */
public class SortedArrayToBalancedBinarySearchTree {
    private static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    public static Node generateTree(int[] array) {
        if (array == null) {
            return null;
        }
        return generate(array, 0, array.length - 1);
    }

    private static Node generate(int[] array, int start, int end) {
        if (start > end) {
            return null;
        }
        int mid = (start + end) / 2;
        Node head = new Node(array[mid]);
        head.left = generate(array, start, mid - 1);
        head.right = generate(array, mid + 1, end);
        return head;
    }

    /**
     * 中序遍历
     *
     * @see com.wz.binarytree.MorrisTraversal#inOrder(com.wz.binarytree.MorrisTraversal.Node)
     */
    private static void inOrder(Node head) {
        if (head == null) {
            return;
        }
        Node cur1 = head, cur2 = null;
        while (cur1 != null) {
            cur2 = cur1.left;
            if (cur2 != null) {
                while (cur2.right != null && cur2.right != cur1) {
                    cur2 = cur2.right;
                }
                if (cur2.right == null) {
                    cur2.right = cur1;
                    cur1 = cur1.left;
                    continue;
                } else {
                    cur2.right = null;
                }
            }
            System.out.print(cur1.value + " ");
            cur1 = cur1.right;
        }
    }

    public static void main(String[] args) {
        int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        Node head = generateTree(array);
        inOrder(head);
    }
}
