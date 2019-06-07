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
        getHeight(head, result);
        return result[0];
    }

    private static int getHeight(Node head, boolean[] result) {
        if (head == null) {
            return 0;
        }

        int leftHeight = getHeight(head.left, result);
        int rightHeight = getHeight(head.right, result);

        if (Math.abs(leftHeight - rightHeight) > 1) {
            result[0] = false;
        }

        return Math.max(leftHeight, rightHeight) + 1;
    }

    public static void main(String[] args) {
        Node head = new Node(1);

        head.left = new Node(2);
        System.out.println(isBalance(head));

        head.left.left = new Node(4);
        System.out.println(isBalance(head));

        head.left.right = new Node(5);
        System.out.println(isBalance(head));

        head.right = new Node(6);
        System.out.println(isBalance(head));

        head.right.right = new Node(7);
        System.out.println(isBalance(head));

        head.right.right.left = new Node(8);
        System.out.println(isBalance(head));

        head.right.right.right = new Node(9);
        System.out.println(isBalance(head));

        head.right.left = new Node(10);
        System.out.println(isBalance(head));
    }
}
