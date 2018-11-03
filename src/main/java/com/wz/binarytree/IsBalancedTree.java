/**
 * <p>Title: IsBalancedTree</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/11/3</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.binarytree;

/**
 * <p>判断二叉树是否为平衡二叉树</p>
 * <p>
 *     整理上采用二叉树的后序遍历，对任何节点node来说，先遍历node的左子树
 *     遍历中获取两个信息：node的左子树是否平衡、左子树最深到哪一层leftHeight
 *     如果左子树不平衡直接结束，否则以同样的方式遍历node的右子树，记录rightHeight
 *     最后，如果leftHeight与rightHeight之差大于1，说明不平衡
 * </p>
 *
 * @author wangzi
 */
public class IsBalancedTree {
    private static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    public static boolean isBalance(Node head) {
        boolean[] result = new boolean[]{true};
        getHeight(head, 1, result);
        return result[0];
    }

    private static int getHeight(Node head, int level, boolean[] result) {
        if (head == null) {
            return level;
        }

        int leftHeight = getHeight(head.left, level + 1, result);
        if (!result[0]) {
            return level;
        }

        int rightHeight = getHeight(head.right, level + 1, result);
        if (!result[0]) {
            return level;
        }
        if (Math.abs(leftHeight - rightHeight) > 1) {
            result[0] = false;
        }

        return Math.max(leftHeight, rightHeight);
    }

    public static void main(String[] args) {
        Node head1 = new Node(1);
        head1.left = new Node(2);
        head1.left.left = new Node(4);
        head1.left.right = new Node(5);

        System.out.println(isBalance(head1));

        Node head2 = new Node(1);
        head2.left = new Node(2);
        head2.right = new Node(3);
        head2.left.left = new Node(4);
        head2.left.right = new Node(5);
        head2.right.left = new Node(6);
        head2.right.right = new Node(7);

        System.out.println(isBalance(head2));
    }
}
