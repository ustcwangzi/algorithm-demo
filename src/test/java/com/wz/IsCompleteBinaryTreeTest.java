package com.wz;

import java.util.LinkedList;
import java.util.Queue;

/**
 * <p>判断二叉树是否为完全二叉树(叶节点都在最后两层，最后一层的空缺只能在左边)</p>
 *
 * @author wangzi
 */
public class IsCompleteBinaryTreeTest {
    private static class Node {
        public int value;
        public Node left;
        public Node right;

        private Node(int value) {
            this.value = value;
        }
    }

    /**
     * 利用队列对二叉树进行层次遍历
     * 如果当前节点有右孩子，但没有左孩子，直接返回false
     * 如果当前节点不存在右孩子，那么之后的节点必须全是叶节点
     */
    private static boolean solution(Node head) {
        if (head == null) {
            return true;
        }

        boolean isLeaf = false;
        Queue<Node> queue = new LinkedList<>();
        queue.offer(head);
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            // 之后的节点必须全是叶节点
            if (isLeaf && (node.left != null || node.right != null)) {
                return false;
            }
            // 不能只存在右孩子
            if (node.right != null && node.left == null) {
                return false;
            }
            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            } else {
                isLeaf = true;
            }
        }
        return true;
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

        head.right.left = new Node(5);
        System.out.println(solution(head));

        head.left.right = new Node(3);
        System.out.println(solution(head));
    }
}
