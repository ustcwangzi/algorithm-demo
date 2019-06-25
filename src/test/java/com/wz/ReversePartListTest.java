package com.wz;

/**
 * <p>逆置部分单链表</p>
 *
 * @author wangzi
 */
public class ReversePartListTest {
    private static class Node {
        public int value;
        public Node next;

        private Node(int value) {
            this.value = value;
        }
    }

    private static Node solution(Node head, int from, int to) {
        if (head == null || head.next == null || from < 1 || to <= from) {
            return head;
        }

        int len = 0;
        Node cur = head;
        // 需要逆置的前一个节点、需要逆置的最后一个节点
        Node left = null, end = null;
        while (cur != null) {
            len++;
            left = (len == from - 1) ? cur : left;
            end = len == to ? cur : end;
            cur = cur.next;
        }
        // 说明to大于len
        if (end == null) {
            return head;
        }

        reverse(left, left == null ? head : left.next, end, end.next);
        return left == null ? end : head;
    }

    /**
     * 逆置[start,end]，逆置后以left为队首，right为队尾
     */
    private static void reverse(Node left, Node start, Node end, Node right) {
        // 需要逆置的前一个节点、需要逆置的节点、及其下一个节点
        Node pre = start, cur = start.next, next;
        while (cur != right) {
            next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        // 逆置后，和left、right连接一起
        if (left != null) {
            left.next = end;
        }
        start.next = right;
    }

    public static void main(String[] args) {
        Node head1 = new Node(1);
        head1.next = new Node(2);
        head1.next.next = new Node(3);
        head1.next.next.next = new Node(4);
        head1 = solution(head1, 2, 3);
        while (head1 != null) {
            System.out.print(head1.value + " ");
            head1 = head1.next;
        }

        System.out.println();

        Node head2 = new Node(1);
        head2.next = new Node(2);
        head2.next.next = new Node(3);
        head2.next.next.next = new Node(4);
        head2 = solution(head2, 1, 4);
        while (head2 != null) {
            System.out.print(head2.value + " ");
            head2 = head2.next;
        }
    }
}
