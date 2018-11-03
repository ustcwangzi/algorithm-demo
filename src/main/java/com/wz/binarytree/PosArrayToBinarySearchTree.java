/**
 * <p>Title: PosArrayToBinarySearchTree</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/11/3</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.binarytree;

/**
 * <p>根据后序数组重建搜索二叉树</p>
 * <p>
 *     如果一个数组是二叉树后序遍历的结果，那么头节点的值一定是该数组最后一个元素
 *     再根据搜索二叉树的性质，头节点会将数组分成左右两边，左边都比头节点小，右边都比头节点大
 *     比如数据为[2,1,3,6,5,7,4]，头节点为4，[2,1,3]在左边，[6,5,7]在右边
 *     不满足上述情况时，说明数组不是搜索二叉树后序遍历的结果
 *     使用头节点将数组划分成左右两个数组，相当于二叉树的左右子树，然后递归进行下去即可
 * </p>
 *
 * @author wangzi
 */
public class PosArrayToBinarySearchTree {
    private static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    public static boolean isPosArray(int[] array) {
        if (array == null || array.length == 0) {
            return false;
        }
        return isPosArray(array, 0, array.length - 1);
    }

    private static boolean isPosArray(int[] array, int start, int end) {
        if (start == end) {
            return true;
        }

        // less代表最后一个比头节点大的，more代表第一个比头节点小的
        int less = -1, more = end;
        for (int i = start; i < end; i++) {
            if (array[end] > array[i]) {
                less = i;
            } else {
                more = more == end ? i : more;
            }
        }

        if (less == -1 || more == end) {
            return isPosArray(array, start, end - 1);
        }
        if (less != more - 1) {
            return false;
        }
        return isPosArray(array, start, less) && isPosArray(array, more, end - 1);
    }

    public static Node posArrayToBST(int[] array) {
        if (array == null) {
            return null;
        }
        return posArrayToBST(array, 0, array.length - 1);
    }

    private static Node posArrayToBST(int[] array, int start, int end) {
        if (start > end) {
            return null;
        }

        Node head = new Node(array[end]);
        // less和more将数组分为左右两部分，代表左右两个子树
        int less = -1, more = end;
        for (int i = start; i < end; i++) {
            if (array[end] > array[i]) {
                less = i;
            } else {
                more = more == end ? i : more;
            }
        }

        head.left = posArrayToBST(array, start, less);
        head.right = posArrayToBST(array, more, end - 1);
        return head;
    }

    public static void main(String[] args) {
        int[] array = {2, 1, 3, 6, 5, 7, 4};
        System.out.println(isPosArray(array));

        Node head = posArrayToBST(array);
        System.out.println(head.value);
    }
}
