/**
 * <p>Title: IsCompleteBinaryTree</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/11/3</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.binarytree;

import java.util.LinkedList;
import java.util.Queue;

/**
 * <p>判断二叉树是否为完全二叉树</p>
 * <p>
 *     采用以下方法进行判断：
 *     1、层次遍历二叉树，每层从左到右依次遍历所有节点
 *     2、如果当前节点有右孩子，但没有左孩子，直接返回false
 *     3、如果当前节点不同时有左右孩子，那之后的节点必须全是叶节点
 *     4、遍历过程中如果不返回false，遍历结束后返回true
 * </p>
 *
 * @author wangzi
 */
public class IsCompleteBinaryTree {
    private static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    public static boolean isBST(Node head) {
        if (head == null) {
            return true;
        }

        boolean isLeaf = false;
        Node left = null, right = null;
        Queue<Node> queue = new LinkedList<>();
        queue.offer(head);
        while (!queue.isEmpty()) {
            head = queue.poll();
            left = head.left;
            right = head.right;

            // 叶节点不能有孩子
            if (isLeaf && (left != null || right != null)) {
                return false;
            }
            // 只有右孩子没有左孩子
            if (left == null && right != null) {
                return false;
            }

            if (left != null) {
                queue.offer(left);
            }
            if (right != null) {
                queue.offer(right);
            } else {
                // 没有右孩子，那么之后的节点必须全是叶节点
                isLeaf = true;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Node head = new Node(4);
        head.left = new Node(2);
        head.right = new Node(6);
        head.left.left = new Node(1);
        head.right.left = new Node(5);

        System.out.println(isBST(head));

        head.left.right = new Node(3);
        System.out.println(isBST(head));
    }
}
