package com.wz;

import java.util.Deque;
import java.util.LinkedList;

/**
 * <p>按照ZigZag打印二叉树</p>
 *
 * @author wangzi
 */
public class PrintBinaryTreeByZigZagTest {
    private static class Node {
        public int value;
        public Node left;
        public Node right;

        private Node(int value) {
            this.value = value;
        }
    }

    /**
     * 采用双端队列的实现层次遍历，使用last控制换行，使用forward控制打印方向
     * 从左到右打印，则从队列头部弹出节点、从尾部加入节点，加入时先左孩子、后右孩子
     * 从右到左打印，则从队列尾部弹出节点、从头部加入节点，加入时先右孩子、后左孩子
     */
    private static void solution(Node head) {
        if (head == null) {
            return;
        }
        Deque<Node> deque = new LinkedList<>();
        deque.offerLast(head);
        boolean forward = true;
        // 当前行最后输出的节点、下一行最后输出的节点
        Node last = head, nextLast = null;

        int level = 1;
        printLevelAndOrientation(level++, forward);

        while (!deque.isEmpty()) {
            Node cur;
            // 头出尾入、先左后右
            if (forward) {
                cur = deque.pollFirst();
                if (cur.left != null) {
                    // 下一行最后输出的节点是当前行有孩子的节点中最先加入queue的
                    nextLast = nextLast == null ? cur.left : nextLast;
                    deque.offerLast(cur.left);
                }
                if (cur.right != null) {
                    nextLast = nextLast == null ? cur.right : nextLast;
                    deque.offerLast(cur.right);
                }
            } else {
                // 尾出头入、先右后左
                cur = deque.pollLast();
                if (cur.right != null) {
                    nextLast = nextLast == null ? cur.right : nextLast;
                    deque.offerFirst(cur.right);
                }
                if (cur.left != null) {
                    nextLast = nextLast == null ? cur.left : nextLast;
                    deque.offerFirst(cur.left);
                }
            }

            System.out.print(cur.value + " ");
            // 需要换行
            if (cur == last && !deque.isEmpty()) {
                forward = !forward;
                last = nextLast;
                nextLast = null;
                System.out.println();
                printLevelAndOrientation(level++, forward);
            }
        }
    }

    private static void printLevelAndOrientation(int level, boolean forward) {
        System.out.print("Level " + level + " from ");
        System.out.print(forward ? "left to right: " : "right to left: ");
    }

    public static void main(String[] args) {
        Node head = new Node(1);
        head.left = new Node(2);
        head.right = new Node(3);
        head.left.left = new Node(4);
        head.right.left = new Node(5);
        head.right.right = new Node(6);
        head.right.left.left = new Node(7);
        head.right.left.right = new Node(8);
        solution(head);
    }
}
