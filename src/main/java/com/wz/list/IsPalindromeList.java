/**
 * <p>Title: IsPalindromeList</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/10/6</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.list;

import java.util.Stack;

/**
 * <p>判断链表是否为回文结构</p>
 * <p>
 *     方案一：
 *          从左到右遍历链表，遍历过程中把每个节点压栈，然后出栈时再和原链表相比较，空间复杂度为O(N)
 *     方案二：
 *          与方案一类似，但是只对右半部分节点进行压栈，出栈时和左半部分节点相比较，节约一半的额外空间
 *     方案三：
 *          改变右半部分的结构，整个右半部分反转，指向中间节点，然后左右两部分同时向中间移动，无需额外空间
 * </p>
 *
 * @author wangzi
 */
public class IsPalindromeList {
    private static class Node {
        public int value;
        public Node next;

        public Node(int value) {
            this.value = value;
        }
    }

    public static boolean isPalindromeOne(Node head) {
        if (head == null || head.next == null) {
            return true;
        }
        // 将整个链表入栈
        Stack<Node> stack = new Stack<>();
        Node cur = head;
        while (cur != null) {
            stack.push(cur);
            cur = cur.next;
        }

        while (head != null) {
            if (head.value != stack.pop().value) {
                return false;
            }
            head = head.next;
        }
        return true;
    }

    public static boolean isPalindromeTwo(Node head) {
        if (head == null || head.next == null) {
            return true;
        }
        // 右半部分链表的开始节点
        Node right = head.next;
        Node cur = head;
        // 循环结束时，right指向右半部分开始，cur指向整个链表结束
        while (cur.next != null && cur.next.next != null) {
            right = right.next;
            cur = cur.next.next;
        }

        // 将右半部分链表入栈
        Stack<Node> stack = new Stack<>();
        while (right != null) {
            stack.push(right);
            right = right.next;
        }

        while (!stack.isEmpty()) {
            if (head.value != stack.pop().value) {
                return false;
            }
            head = head.next;
        }
        return true;
    }

    public static boolean isPalindromeThree(Node head) {
        if (head == null || head.next == null) {
            return true;
        }
        Node node1 = head;
        Node node2 = head;
        // 循环结束时，node1指向链表中间，node2指向整个链表结束
        while (node2.next != null && node2.next.next != null) {
            node1 = node1.next;
            node2 = node2.next.next;
        }
        // node2指向右半部分开始
        node2 = node1.next;
        // 断开右半部分
        node1.next = null;
        Node node3 = null;
        // 逆置右半部分
        while (node2 != null) {
            node3 = node2.next;
            node2.next = node1;
            node1 = node2;
            node2 = node3;
        }

        // 保存最后节点
        node3 = node1;
        node2 = head;
        boolean result = true;
        while (node1 != null && node2 != null) {
            if (node1.value != node2.value) {
                result = false;
                break;
            }
            node1 = node1.next;
            node2 = node2.next;
        }

        node1 = node3.next;
        node3.next = null;
        // 恢复链表结构
        while (node1 != null) {
            node2 = node1.next;
            node1.next = node3;
            node3 = node1;
            node1 = node2;
        }

        return result;
    }

    public static void main(String[] args) {
        Node head1 = new Node(1);
        head1.next = new Node(2);
        head1.next.next = new Node(3);
        head1.next.next.next = new Node(2);
        head1.next.next.next.next = new Node(1);

        System.out.println(isPalindromeOne(head1));
        System.out.println(isPalindromeTwo(head1));
        System.out.println(isPalindromeThree(head1));

        Node head2 = new Node(1);
        head2.next = new Node(2);
        head2.next.next = new Node(3);
        head2.next.next.next = new Node(3);
        head2.next.next.next.next = new Node(2);
        head2.next.next.next.next.next = new Node(1);

        System.out.println(isPalindromeOne(head2));
        System.out.println(isPalindromeTwo(head2));
        System.out.println(isPalindromeThree(head2));
    }

}
