/**
 * <p>Title: AddList</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/10/7</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.list;

import java.util.Stack;

/**
 * <p>两个链表生成相加链表</p>
 * <p>
 *     如9->3->7与5->3，相加后等到：9->9->0
 *     方案一：
 *          1、将两个链表分别遍历，遍历过程中将值压栈
 *          2、分别出栈，值相加，同时用carry记录是否存在进位
 *     方案二：
 *          1、将两个链表进行逆序
 *          2、遍历逆序后的链表，将值相加，同时用carry记录是否存在进位
 *          3、再次逆序，即调整为原来的顺序
 * </p>
 *
 * @author wangzi
 */
public class AddList {
    private static class Node {
        public int value;
        public Node next;

        public Node(int value) {
            this.value = value;
        }
    }

    public static Node addOne(Node head1, Node head2) {
        if (head1 == null || head2 == null) {
            return head1 != null ? head1 : head2;
        }

        Stack<Integer> stack1 = new Stack<>();
        Stack<Integer> stack2 = new Stack<>();
        while (head1 != null) {
            stack1.push(head1.value);
            head1 = head1.next;
        }
        while (head2 != null) {
            stack2.push(head2.value);
            head2 = head2.next;
        }

        int num1 = 0, num2 = 0, count = 0, carry = 0;
        Node cur = null, pre = null;
        while (!stack1.isEmpty() || !stack2.isEmpty()) {
            num1 = stack1.isEmpty() ? 0 : stack1.pop();
            num2 = stack2.isEmpty() ? 0 : stack2.pop();
            count = num1 + num2 + carry;
            pre = cur;
            cur = new Node(count % 10);
            cur.next = pre;
            carry = count / 10;
        }

        if (carry == 1) {
            pre = cur;
            cur = new Node(1);
            cur.next = pre;
        }

        return cur;
    }

    public static Node addTwo(Node head1, Node head2) {
        head1 = reverse(head1);
        head2 = reverse(head2);
        int num1 = 0, num2 = 0, count = 0, carry = 0;
        Node cur1 = head1, cur2 = head2, cur = null, pre = null;
        while (cur1 != null || cur2 != null) {
            num1 = cur1 != null ? cur1.value : 0;
            num2 = cur2 != null ? cur2.value : 0;
            count = num1 + num2 + carry;
            pre = cur;
            cur = new Node(count % 10);
            cur.next = pre;
            carry = count / 10;

            cur1 = cur1 != null ? cur1.next : null;
            cur2 = cur2 != null ? cur2.next : null;
        }

        if (carry == 1) {
            pre = cur;
            cur = new Node(1);
            cur.next = pre;
        }

        reverse(head1);
        reverse(head2);
        return cur;
    }

    private static Node reverse(Node head) {
        if (head == null || head.next == null) {
            return head;
        }
        Node pre = null, next = null;
        while (head != null) {
            next = head.next;
            head.next = pre;
            pre = head;
            head = next;
        }
        return pre;
    }

    public static void main(String[] args) {
        Node head1 = new Node(9);
        head1.next = new Node(9);
        head1.next.next = new Node(9);
        Node head2 = new Node(1);

        Node result = addOne(head1, head2);
        while (result != null) {
            System.out.print(result.value + " ");
            result = result.next;
        }

        System.out.println();

        result = addTwo(head1, head2);
        while (result != null) {
            System.out.print(result.value + " ");
            result = result.next;
        }
    }
}
