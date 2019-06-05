package com.wz;

/**
 * <p>判断二叉树是否为搜索二叉树(中序遍历结果有序)</p>
 *
 * @author wangzi
 */
public class IsBinarySearchTreeTest {
    private static class Node {
        public int value;
        public Node left;
        public Node right;

        private Node(int value) {
            this.value = value;
        }
    }

    private static boolean solution(Node head) {
        int[] pre = new int[1];
        pre[0] = Integer.MIN_VALUE;
        return isBinarySearchTree(head, pre);
    }

    /**
     * 搜索二叉树中序遍历结果依次递增，如果遍历中出现降序，说明不是搜索二叉树
     * 递归返回结果为"是否依次递增"，另外，利用数组pre[]记录中序遍历的前一个值，用以与当前值比较
     */
    private static boolean isBinarySearchTree(Node head, int[] pre) {
        if (head == null) {
            return true;
        }
        if (!isBinarySearchTree(head.left, pre)) {
            return false;
        }

        // 出现降序
        if (head.value < pre[0]) {
            return false;
        }
        pre[0] = head.value;

        return isBinarySearchTree(head.right, pre);
    }

    public static void main(String[] args) {
        Node head = new Node(4);
        System.out.println(solution(head));

        head.left = new Node(2);
        System.out.println(solution(head));

        head.right = new Node(6);
        System.out.println(solution(head));

        head.left.left = new Node(1);
        System.out.println(solution(head));

        head.left.right = new Node(3);
        System.out.println(solution(head));

        head.right.left = new Node(5);
        System.out.println(solution(head));
    }
}
