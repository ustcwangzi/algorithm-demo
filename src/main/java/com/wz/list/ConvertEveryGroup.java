/**
 * <p>Title: ConvertEveryGroup</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/10/13</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.list;

import java.util.Stack;

/**
 * <p>将每K个节点分组逆序</p>
 * <p>
 *     每K个节点为一组，组内逆序，最后不足K个节点时，保持不变
 *     方案一：
 *          1、从左到右遍历链表，当栈的大小不足K时，节点入栈
 *          2、栈大小达到K，依次弹出节点连接起来，此时这一组逆序完成
 *          3、重复以上过程，直到链表遍历结束
 *        时间复杂度为O(N)，空间复杂度为O(K)
 *     方案二：
 *          使用变量记录每一组开始节点和结束节点，然后之间进行逆序
 *        时间复杂度为O(N)，空间复杂度为O(1)
 * </p>
 *
 * @author wangzi
 */
public class ConvertEveryGroup {
    private static class Node {
        public int value;
        public Node next;

        public Node(int value) {
            this.value = value;
        }
    }

    public static Node reverseOne(Node head, int k) {
        if (head == null || head.next == null || k < 2) {
            return head;
        }

        Stack<Node> stack = new Stack<>();
        Node newHead = head;
        Node cur = head;
        Node pre = null, next = null;
        while (cur != null) {
            next = cur.next;
            stack.push(cur);
            if (stack.size() == k) {
                // (pre,next)之间的节点需要进行逆序，这些节点在stack中
                pre = resign(stack, pre, next);
                // 首次逆序时保存头节点，后面不再改变
                newHead = newHead == head ? cur : newHead;
            }
            cur = next;
        }
        return newHead;
    }

    /**
     * 将stack中的节点逐个出栈连接起来，连接后，left为队首，right为队尾
     */
    private static Node resign(Stack<Node> stack, Node left, Node right) {
        Node cur = stack.pop();
        if (left != null) {
            left.next = cur;
        }
        Node next = null;
        while (!stack.isEmpty()) {
            next = stack.pop();
            cur.next = next;
            cur = next;
        }
        cur.next = right;
        return cur;
    }

    public static Node reverseTwo(Node head, int k) {
        if (head == null || head.next == null || k < 2) {
            return head;
        }

        Node cur = head;
        Node start = null, left = null, next = null;
        int count = 1;
        while (cur != null) {
            next = cur.next;
            if (count == k) {
                start = left == null ? head : left.next;
                // 首次逆序时保存头节点，后面不再改变
                head = left == null ? cur : head;
                // (left,next)之间的节点需要进行逆序，这些节点以start开始，cur结束
                resign(left, start, cur, next);
                left = start;
                // 下面会执行count++，因此此处count置0
                count = 0;
            }
            count++;
            cur = next;
        }
        return head;
    }

    /**
     * 逆置start到end之间的节点，逆置后left为队首，right为队尾
     */
    private static void resign(Node left, Node start, Node end, Node right) {
        Node pre = start;
        // 每次需要逆置的节点，及其下一个节点
        Node cur = start.next, next;
        while (cur != right) {
            next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        // start到end逆置完成，与left、right连接一起
        if (left != null) {
            left.next = end;
        }
        start.next = right;
    }

    public static void main(String[] args) {
        Node head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        head.next.next.next = new Node(4);
        head.next.next.next.next = new Node(5);
        head.next.next.next.next.next = new Node(6);
        head.next.next.next.next.next.next = new Node(7);

        head = reverseOne(head, 3);
        while (head != null) {
            System.out.print(head.value + " ");
            head = head.next;
        }

        System.out.println();

        head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        head.next.next.next = new Node(4);
        head.next.next.next.next = new Node(5);
        head.next.next.next.next.next = new Node(6);
        head.next.next.next.next.next.next = new Node(7);

        head = reverseTwo(head, 3);
        while (head != null) {
            System.out.print(head.value + " ");
            head = head.next;
        }
    }
}
