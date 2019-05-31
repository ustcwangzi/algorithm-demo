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
 *     1、head的左子树上节点间的最大距离
 *     2、head的右子树上节点间的最大距离
 *     3、head左子树最大深度 + 1 + head右子树最大深度
 *     三个值中最大的那个就是整棵树中节点间的最远距离
 *     整个过程采用后序遍历：对当前结点h，先获取左子树节点间最大距离以及左子树最大深度，
 *     再获取右子树节点间最大距离以及右子树深度，最后统计出h的节点间最大距离以及h的最大深度并返回上层。
 *     递归获取两个值：子树的节点间最大距离、子树的最大深度，
 *     其中，子树的节点间最大距离由递归函数的返回值返回，子树最大深度通过一个数组引用的方式获取结果。
 * </p>
 * <p>时间复杂度为O(N)</p>
 *
 * @author wangzi
 */
public class MaxDistanceInTree {
    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    public static int maxDistance(Node head) {
        int[] depth = new int[1];
        return posOrder(head, depth);
    }

    private static int posOrder(Node head, int[] depth) {
        if (head == null) {
            depth[0] = 0;
            return 0;
        }

        // 左子树节点间最大距离
        int leftMaxDistance = posOrder(head.left, depth);
        // 左子树最大深度
        int leftMaxDepth = depth[0];
        // 右子树节点间最大距离
        int rightMaxDistance = posOrder(head.right, depth);
        // 右子树最大深度
        int rightMaxDepth = depth[0];
        // 当前节点高度
        depth[0] = Math.max(leftMaxDepth, rightMaxDepth) + 1;
        // 最终的最大距离
        return Math.max(Math.max(leftMaxDistance, rightMaxDistance), leftMaxDepth + rightMaxDepth + 1);
    }

    public static void main(String[] args) {
        Node head = new Node(1);
        System.out.println(maxDistance(head));
        head.left = new Node(2);
        System.out.println(maxDistance(head));
        head.right = new Node(3);
        System.out.println(maxDistance(head));
        head.left.left = new Node(4);
        System.out.println(maxDistance(head));
        head.left.right = new Node(5);
        System.out.println(maxDistance(head));
        head.right.left = new Node(6);
        System.out.println(maxDistance(head));
        head.right.right = new Node(7);
        System.out.println(maxDistance(head));
        head.left.left.left = new Node(8);
        System.out.println(maxDistance(head));
        head.right.left.right = new Node(9);
        System.out.println(maxDistance(head));
    }
}
