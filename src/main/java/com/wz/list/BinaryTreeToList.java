/**
 * <p>Title: BinaryTreeToList</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/10/14</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.list;

import java.util.LinkedList;
import java.util.Queue;

/**
 * <p>二叉树转换为双向链表</p>
 * <p>
 *     将二叉搜索树转换为双向链表，对于每个节点，原right指针等价于next，原left指针等价于pre
 *     方案一：
 *          对二叉树进行中序遍历，每个节点放入队列中，然后从队列依次弹出节点，重新连接所有节点即可
 *       时间复杂度为O(N)，空间复杂度为O(N)
 *     方案二：
 *          使用递归将二叉树转换为结构特殊的双向链表，特殊之处在于该双向链表的尾节点的right指针指向该双向链表的头节点
 *          这种特殊结构便于快速找到双向链表的头尾节点，从而省去遍历过程
 *          递归处理左右子树，获取左子树头尾节点、右子树头尾节点，然后将左子树、根节点、右子树连接起来，最终返回尾节点
 *          递归结束后得到的双向链表尾节点的right指针指向的就是头节点，然后将尾节点right指针指向null，恢复正常结构
 *       时间复杂度为O(N)，空间复杂度为O(h)，h为二叉树的高度
 * </p>
 *
 * @author wangzi
 */
public class BinaryTreeToList {
    private static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    public static Node convertOne(Node head) {
        if (head == null) {
            return null;
        }

        Queue<Node> queue = new LinkedList<>();
        queue = traverseInOrderToQueue(head, queue);
        if (queue.isEmpty()) {
            return head;
        }

        head = queue.poll();
        Node pre = head;
        Node cur = null;
        // 根据出队的顺序连接各个节点
        while (!queue.isEmpty()) {
            cur = queue.poll();
            pre.right = cur;
            cur.left = pre;
            pre = cur;
        }
        return head;
    }

    public static Node convertTwo(Node head) {
        if (head == null) {
            return null;
        }

        Node lastNode = process(head);
        head = lastNode.right;
        // 尾节点的right指针原本指向该双向链表的头节点，置为null恢复
        lastNode.right = null;
        return head;
    }

    /**
     * 将二叉树转换为结构特殊的双向链表，并返回链表的尾节点
     * 特殊之处在于该双向链表的尾节点的right指针指向该双向链表的头节点
     * 这种特殊的结构，便于快速找到双向链表的头尾节点，省去遍历的过程
     */
    private static Node process(Node head) {
        if (head == null) {
            return null;
        }

        // 左子树尾节点
        Node leftEnd = process(head.left);
        // 右子树尾节点
        Node rightEnd = process(head.right);
        // 左子树头节点(由左子树尾节点的right指针指向)
        Node leftStart = leftEnd != null ? leftEnd.right : null;
        // 右子树头节点(由右子树尾节点的right指针指向)
        Node rightStart = rightEnd != null ? rightEnd.right : null;
        // 连接左子树、根节点、右子树
        if (leftEnd != null && rightEnd != null) {
            leftEnd.right = head;
            head.left = leftEnd;
            head.right = rightStart;
            rightStart.left = head;
            rightEnd.right = leftStart;
            return rightEnd;
        } else if (leftEnd != null) {
            leftEnd.right = head;
            head.left = leftEnd;
            head.right = leftStart;
            return head;
        } else if (rightEnd != null) {
            head.right = rightStart;
            rightStart.left = head;
            rightEnd.right = head;
            return rightEnd;
        } else {
            head.right = head;
            return head;
        }
    }

    /**
     * 中序遍历二叉树，结果存在队列中
     */
    private static Queue<Node> traverseInOrderToQueue(Node head, Queue<Node> queue) {
        if (head == null) {
            return queue;
        }

        traverseInOrderToQueue(head.left, queue);
        queue.offer(head);
        traverseInOrderToQueue(head.right, queue);
        return queue;
    }

    private static void printTreeInOrder(Node head) {
        if (head == null) {
            return;
        }
        printTreeInOrder(head.left);
        System.out.print(head.value + " ");
        printTreeInOrder(head.right);
    }

    private static void printDoubleLinkedList(Node head) {
        if (head == null) {
            return;
        }

        Node end = null;
        while (head != null) {
            System.out.print(head.value + " ");
            end = head;
            head = head.right;
        }
        System.out.print("| ");
        while (end != null) {
            System.out.print(end.value + " ");
            end = end.left;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Node head = new Node(5);
        head.left = new Node(2);
        head.right = new Node(9);
        head.left.left = new Node(1);
        head.left.right = new Node(3);
        head.left.right.right = new Node(4);
        head.right.left = new Node(7);
        head.right.right = new Node(10);
        head.left.left = new Node(1);
        head.right.left.left = new Node(6);
        head.right.left.right = new Node(8);

        printTreeInOrder(head);
        System.out.println();
        head = convertOne(head);
        printDoubleLinkedList(head);

        head = new Node(5);
        head.left = new Node(2);
        head.right = new Node(9);
        head.left.left = new Node(1);
        head.left.right = new Node(3);
        head.left.right.right = new Node(4);
        head.right.left = new Node(7);
        head.right.right = new Node(10);
        head.left.left = new Node(1);
        head.right.left.left = new Node(6);
        head.right.left.right = new Node(8);

        printTreeInOrder(head);
        System.out.println();
        head = convertTwo(head);
        printDoubleLinkedList(head);
    }
}
