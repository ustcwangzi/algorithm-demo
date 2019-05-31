package com.wz;

/**
 * <p>二叉树节点之间的最大距离</p>
 *
 * @author wangzi
 */
public class MaxDistanceInTreeTest {
    private static class Node {
        public int value;
        public Node left;
        public Node right;

        private Node(int value) {
            this.value = value;
        }
    }

    private static int solution(Node head) {
        int[] depth = new int[1];
        return getMaxDistance(head, depth);
    }

    /**
     * 一棵以head为头节点的树上，最大距离只可能来自以下三种情况：
     * 1、head的左子树上节点间的最大距离
     * 2、head的右子树上节点间的最大距离
     * 3、head左子树最大深度 + 1 + head右子树最大深度
     * 递归函数返回节点间的最大距离，最大深度通过depth[]数组引用的方式获取结果
     */
    private static int getMaxDistance(Node head, int[] depth) {
        if (head == null) {
            depth[0] = 0;
            return 0;
        }
        // 递归左子树获取左子树节点间最大距离
        int leftMaxDistance = getMaxDistance(head.left, depth);
        // 通过数组获取左子树递归过程中统计出的子树深度
        int leftMaxDepth = depth[0];
        // 递归右子树获取右子树节点间最大距离
        int rightMaxDistance = getMaxDistance(head.right, depth);
        // 通过数组获取右子树递归过程中统计出的子树深度
        int rightMaxDepth = depth[0];
        // 通过数组记录当前结点的高度
        depth[0] = Math.max(leftMaxDepth, rightMaxDepth) + 1;
        // 三种情况中的最大值就是以当前节点为根的树中节点间最大距离
        return Math.max(Math.max(leftMaxDistance, rightMaxDistance), leftMaxDepth + rightMaxDepth + 1);
    }

    public static void main(String[] args) {
        Node head = new Node(1);
        System.out.println(solution(head));
        head.left = new Node(2);
        System.out.println(solution(head));
        head.right = new Node(3);
        System.out.println(solution(head));
        head.left.left = new Node(4);
        System.out.println(solution(head));
        head.left.right = new Node(5);
        System.out.println(solution(head));
        head.right.left = new Node(6);
        System.out.println(solution(head));
        head.right.right = new Node(7);
        System.out.println(solution(head));
        head.left.left.left = new Node(8);
        System.out.println(solution(head));
        head.right.left.right = new Node(9);
        System.out.println(solution(head));
    }
}
