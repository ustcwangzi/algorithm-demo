/**
 * <p>Title: TreeTraversalWithoutRecursive</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019-06-16</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * <p>非递归遍历二叉树</p>
 *
 * @author wangzi
 */
public class TreeTraversalWithoutRecursive {
    private static class Node {
        public int value;
        public Node left;
        public Node right;

        private Node(int value) {
            this.value = value;
        }
    }

    /**
     * 先序遍历
     * 第一步：遍历左子树，边遍历边打印，并将节点存入栈中，以后需借助这些节点进入右子树
     * 第二步：出栈，根据栈顶节点进入右子树，然后重复步骤一
     */
    private static void preOrder(Node head) {
        if (head == null) {
            return;
        }

        Stack<Node> stack = new Stack<>();
        while (!stack.isEmpty() || head != null) {
            if (head != null) {
                // 边遍历边打印，并将节点入栈
                System.out.print(head.value + " ");
                stack.push(head);
                head = head.left;
            } else {
                // 左子树已空，根据栈顶节点进入右子树，开始新的左子树遍历
                head = stack.pop();
                head = head.right;
            }
        }
    }

    /**
     * 中序遍历
     * 第一步：遍历左子树，将节点存入栈中
     * 第二步：出栈，打印栈顶元素，同时根据栈顶节点进入右子树
     */
    private static void inOrder(Node head) {
        if (head == null) {
            return;
        }

        Stack<Node> stack = new Stack<>();
        while (!stack.isEmpty() || head != null) {
            if (head != null) {
                // 一直遍历到左子树最左边，边遍历边将节点入栈
                stack.push(head);
                head = head.left;
            } else {
                // 左子树已空，出栈并打印栈顶元素，根据栈顶节点进入右子树，开始新的左子树遍历
                head = stack.pop();
                System.out.print(head.value + " ");
                head = head.right;
            }
        }
    }

    /**
     * 后序遍历
     * 第一步：一直遍历到左子树最左边，边遍历边将节点入栈
     * 第二步：出栈，检查该节点是否无右子树或者右子树已被遍历，若满足则访问该节点，
     * 否则将节点重新入栈，同时根据该节点进入右子树，开始新的左子树遍历(同步骤一)
     */
    private static void posOrder(Node head) {
        if (head == null) {
            return;
        }

        Stack<Node> stack = new Stack<>();
        // 一直遍历到左子树最左边，边遍历边将节点入栈
        while (head != null) {
            stack.push(head);
            head = head.left;
        }

        // 最近访问的节点
        Node lastVisit = null;

        while (!stack.isEmpty()) {
            head = stack.pop();
            // 根结点被访问的前提是：根结点无右子树或右子树已被遍历
            if (head.right == null || head.right == lastVisit) {
                System.out.print(head.value + " ");
                lastVisit = head;
            } else {
                // 重新将根结点入栈，并进入右子树，开始对右子树的左子树进行遍历
                stack.push(head);
                head = head.right;
                while (head != null) {
                    stack.push(head);
                    head = head.left;
                }
            }
        }
    }

    /**
     * 层次遍历
     * 利用队列的先进先出实现层次遍历
     */
    private static void levelOrder(Node head) {
        if (head == null) {
            return;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.add(head);
        // 当前层最后节点、下一层最后节点，用以控制换行
        Node last = head, nextLast = null;

        while (!queue.isEmpty()) {
            head = queue.poll();
            System.out.print(head.value + " ");
            if (head.left != null) {
                // 下一层最后节点是最新加入队列的节点
                nextLast = head.left;
                queue.offer(head.left);
            }
            if (head.right != null) {
                nextLast = head.right;
                queue.offer(head.right);
            }
            // 已到达当前层最后节点，需要换行
            if (head == last && !queue.isEmpty()) {
                System.out.println();
                last = nextLast;
            }
        }
    }

    public static void main(String[] args) {
        Node head = new Node(5);
        head.left = new Node(3);
        head.right = new Node(8);
        head.left.left = new Node(2);
        head.left.right = new Node(4);
        head.left.left.left = new Node(1);
        head.right.left = new Node(7);
        head.right.left.left = new Node(6);
        head.right.right = new Node(10);
        head.right.right.left = new Node(9);
        head.right.right.right = new Node(11);

        System.out.print("pre-order: ");
        preOrder(head);
        System.out.println();

        System.out.print("in-order: ");
        inOrder(head);
        System.out.println();

        System.out.print("pos-order: ");
        posOrder(head);
        System.out.println();

        System.out.println("level-order: ");
        levelOrder(head);
    }
}
