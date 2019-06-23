package com.wz;

import java.util.Stack;

/**
 * <p>判断一个链表是否为回文结构</p>
 *
 * @author wangzi
 */
public class IsPalindromeListTest {
    private static class Node {
        public int value;
        public Node next;

        private Node(int value) {
            this.value = value;
        }
    }

    /**
     * 对右半部分节点进行压栈，出栈时和左半部分节点相比较
     */
    private static boolean solution(Node head) {
        if (head == null || head.next == null) {
            return true;
        }

        // right指向右半区的开始
        Node cur = head, right = head.next;
        while (cur.next != null && cur.next.next != null) {
            right = right.next;
            cur = cur.next.next;
        }

        // 将右半区入栈
        Stack<Node> stack = new Stack<>();
        while (right != null) {
            stack.push(right);
            right = right.next;
        }

        // 右半区出栈，与左半区相比较
        while (!stack.isEmpty()) {
            if (head.value != stack.pop().value) {
                return false;
            }
            head = head.next;
        }

        return true;
    }

    public static void main(String[] args) {
        Node head1 = new Node(1);
        head1.next = new Node(2);
        head1.next.next = new Node(3);
        head1.next.next.next = new Node(2);
        head1.next.next.next.next = new Node(1);

        System.out.println(solution(head1));

        Node head2 = new Node(1);
        head2.next = new Node(2);
        head2.next.next = new Node(3);
        head2.next.next.next = new Node(3);
        head2.next.next.next.next = new Node(2);
        head2.next.next.next.next.next = new Node(1);

        System.out.println(solution(head2));
    }
}
