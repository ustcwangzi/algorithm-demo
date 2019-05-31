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

    private static int getMaxDistance(Node cur, int[] depth) {
        if (cur == null) {
            depth[0] = 0;
            return 0;
        }
        int leftMaxDistance = getMaxDistance(cur.left, depth);
        int leftMaxDepth = depth[0];
        int rightMaxDistance = getMaxDistance(cur.right, depth);
        int rightMaxDepth = depth[0];
        depth[0] = Math.max(leftMaxDepth, rightMaxDepth) + 1;
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
