/**
 * <p>Title: MaxDistanceInTree</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/10/21</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.binarytree;

/**
 * <p>二叉树节点之间的最大距离</p>
 * <p>
 *     二叉树上从节点A出发，可以向上或向下，沿途节点只能经过一次，到达节点B时，路径上的节点数叫做A到B的距离
 *     一棵以head为头节点的树上，最大距离只可能来自以下三种情况：
 *     1、head的左子树上的最大距离
 *     2、head的右子树上的最大距离
 *     3、head左子树上离head.left最远的距离 + 1 + head右子树上离head.right最远的距离
 *     三个值中最大的那个就是整棵树中的最远距离
 *     整个过程为后序遍历，在二叉树的每颗子树上执行以下步骤：
 *     假设子树头节点为h，处理h的左子树得到两个信息：左子树上的最大距离leftMax、左子树上距离h.left的最远距离maxFromLeft，
 *     同理，处理h的右子树得到两个信息：右子树上的最大距离rightMax、右子树上距离h.right的最远距离maxFromRight，
 *     maxFromLeft + 1 + maxFromRight就是跨h节点情况下的最大距离，再于leftMax、rightMax比较，最大值作为h树上的最大距离，即返回值
 *     maxFromLeft + 1是h左子树上离h的最远距离，maxFromRight + 1是h右子树上离h的最远距离，两者较大的是h树上距离h的最远距离，即record[0]
 * </p>
 * <p>时间复杂度为O(N)</p>
 *
 * @author wangzi
 */
public class MaxDistanceInTree {
    private static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    public static int maxDistance(Node head) {
        int[] record = new int[1];
        return posOrder(head, record);
    }

    private static int posOrder(Node head, int[] record) {
        if (head == null) {
            record[0] = 0;
            return 0;
        }

        // 左子树上的最大距离
        int leftMax = posOrder(head.left, record);
        // 左子树上距离h左孩子的最远距离
        int maxFromLeft = record[0];
        // 右子树上的最大距离
        int rightMax = posOrder(head.right, record);
        // 右子树上距离h右孩子的最远距离
        int maxFromRight = record[0];
        // 跨h节点情况下的最大距离
        int curNodeMax = maxFromLeft + maxFromRight + 1;
        // 距离h最远的距离
        record[0] = Math.max(maxFromLeft, maxFromRight) + 1;
        // h树上的最大距离
        return Math.max(Math.max(leftMax, rightMax), curNodeMax);
    }

    public static void main(String[] args) {
        Node head = new Node(1);
        head.left = new Node(2);
        head.right = new Node(3);
        head.left.left = new Node(4);
        head.left.right = new Node(5);
        head.right.left = new Node(6);
        head.right.right = new Node(7);
        head.left.left.left = new Node(8);
        head.right.left.right = new Node(9);
        System.out.println(maxDistance(head));
    }
}
