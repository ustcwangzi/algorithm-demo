/**
 * <p>Title: PrintBinaryTreeByLevel</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/10/28</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.binarytree;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

/**
 * <p>按层打印二叉树</p>
 * <p>
 *     按层打印：
 *          开始时将头节点加入队列，然后从队列中依次遍历每一个节点，每输出一个节点就将其左右孩子节点加入队列，直到队列为空
 *          使用两个变量last和nextLast解决换行问题，last代表当前行最右节点、nextLast代表下一行最右节点
 *          遍历过程中如果节点等于last说明要进行换行，nextLast一直等于本行遍历过程中队列中最新加入的节点
 *     按ZigZag打印：
 *          使用双端队列，开始时将头节点加入双端队列，然后按照以下原则进行：
 *          1、如果是从左到右打印，则从双端队列头部弹出节点、从尾部加入节点，加入的顺序是先左孩子、后右孩子
 *          2、如果是从右到左打印，则从双端队列尾部弹出节点、从头部加入节点，加入的顺序是先右孩子、后左孩子
 *     eg.以代码中的二叉树为例，分别说明按层打印和按ZigZag打印的过程：
 *          输出： 1    2     3     4     5     6   7  8
 *          队列：2,3  3,4  4,5,6  5,6  6,7,8  7,8  8
 *          --------------------------------------------------
 *          输出： 1     3      2     4     5     6   8  7
 *          队列：2,3  5,6,2  4,5,6  5,6  6,7,8  7,8  7
 * </p>
 *
 * @author wangzi
 */
public class PrintBinaryTreeByLevel {
    private static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    public static void printByLevel(Node head) {
        if (head == null) {
            return;
        }

        Queue<Node> queue = new LinkedList<>();
        int level = 1;
        // 当前行最右节点
        Node last = head;
        // 下一行最右节点
        Node nextLast = null;
        queue.offer(head);
        System.out.print("Level " + (level++) + " : ");
        while (!queue.isEmpty()) {
            head = queue.poll();
            System.out.print(head.value + " ");
            if (head.left != null) {
                queue.offer(head.left);
                nextLast = head.left;
            }
            if (head.right != null) {
                queue.offer(head.right);
                nextLast = head.right;
            }
            // 本次输出的节点是last时，进行换行
            if (head == last && !queue.isEmpty()) {
                System.out.println();
                System.out.print("Level " + (level++) + " : ");
                last = nextLast;
            }
        }
    }

    public static void printByZigZag(Node head) {
        if (head == null) {
            return;
        }

        Deque<Node> deque = new LinkedList<>();
        int level = 1;
        // 正向输出
        boolean forward = true;
        // 当前行最后输出的节点
        Node last = head;
        // 下一行最后输出的节点
        Node nextLast = null;
        deque.offerFirst(head);
        printLevelAndOrientation(level++, forward);
        while (!deque.isEmpty()) {
            if (forward) {
                // 正向时，头出尾入(先左后右)
                head = deque.pollFirst();
                // 下一行最后输出的节点是当前行有孩子的节点中最先加入queue的
                if (head.left != null) {
                    nextLast = nextLast == null ? head.left : nextLast;
                    deque.offerLast(head.left);
                }
                if (head.right != null) {
                    nextLast = nextLast == null ? head.right : nextLast;
                    deque.offerLast(head.right);
                }
            } else {
                // 逆向时，尾出头入(先右后左)
                head = deque.pollLast();
                if (head.right != null) {
                    nextLast = nextLast == null ? head.right : nextLast;
                    deque.offerFirst(head.right);
                }
                if (head.left != null) {
                    nextLast = nextLast == null ? head.left : nextLast;
                    deque.offerFirst(head.left);
                }
            }
            System.out.print(head.value + " ");
            if (head == last && !deque.isEmpty()) {
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

        printByLevel(head);
        System.out.println();
        printByZigZag(head);
    }
}
