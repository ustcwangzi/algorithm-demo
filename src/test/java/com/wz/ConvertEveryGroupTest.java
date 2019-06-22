/**
 * <p>Title: ConvertEveryGroupTest</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019-06-22</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz;

/**
 * <p>单链表每K个节点为一组，组内逆序，最后不足K个节点时，保持不变</p>
 *
 * @author wangzi
 */
public class ConvertEveryGroupTest {
    private static class Node {
        public int value;
        public Node next;

        private Node(int value) {
            this.value = value;
        }
    }

    private static Node solution(Node head, int k) {
        if (head == null || head.next == null || k < 2) {
            return head;
        }

        Node cur = head, left = null, start, next;
        int count = 1;
        while (cur != null) {
            next = cur.next;
            if (count == k) {
                start = left == null ? head : left.next;
                // 首次逆置时改变链表头节点
                head = left == null ? cur : head;
                // 逆置[start,cur]，位于left与next之间
                reverse(left, start, cur, next);
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
     * 逆置[start,end]，逆置后以left为队首，right为队尾
     */
    private static void reverse(Node left, Node start, Node end, Node right) {
        Node pre = start;
        // 每次需要逆置的节点，及其下一个节点
        Node cur = pre.next, next;
        while (cur != right) {
            next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        // start到end逆置完成，与left、right连接在一起
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

        head = solution(head, 3);
        Node cur = head;
        while (cur != null) {
            System.out.print(cur.value + " ");
            cur = cur.next;
        }

        System.out.println();

        head = solution(head, 2);
        while (head != null) {
            System.out.print(head.value + " ");
            head = head.next;
        }
    }
}
