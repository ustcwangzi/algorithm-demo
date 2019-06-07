/**
 * <p>Title: IsBalancedTreeTest</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019-06-07</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz;

/**
 * <p>判断二叉树是否是平衡二叉树</p>
 *
 * @author wangzi
 */
public class IsBalancedTreeTest {
    private static class Node {
        public int value;
        public Node left;
        public Node right;

        private Node(int value) {
            this.value = value;
        }
    }

    private static boolean solution(Node head) {
        if (head == null) {
            return true;
        }
        boolean[] result = {true};
        getHeight(head, result);
        return result[0];
    }

    /**
     * 整体采用后续遍历，获取左子树和右子树的高度，若高度之差大于一，说明不平衡
     * 递归返回结果为"树的高度"，另外，利用数组result[]记录"是否平衡"
     */
    private static int getHeight(Node head, boolean[] result) {
        if (head == null) {
            return 0;
        }
        int left = getHeight(head.left, result);
        int right = getHeight(head.right, result);
        if (Math.abs(left - right) > 1) {
            result[0] = false;
        }
        return Math.max(left, right) + 1;
    }

    public static void main(String[] args) {
        Node head = new Node(1);

        head.left = new Node(2);
        System.out.println(solution(head));

        head.left.left = new Node(4);
        System.out.println(solution(head));

        head.left.right = new Node(5);
        System.out.println(solution(head));

        head.right = new Node(6);
        System.out.println(solution(head));

        head.right.right = new Node(7);
        System.out.println(solution(head));

        head.right.right.left = new Node(8);
        System.out.println(solution(head));

        head.right.right.right = new Node(9);
        System.out.println(solution(head));

        head.right.left = new Node(10);
        System.out.println(solution(head));
    }
}
