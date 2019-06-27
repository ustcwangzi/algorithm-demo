package com.wz;

/**
 * <p>删除单链表的中间节点</p>
 *
 * @author wangzi
 */
public class RemoveMiddleNodeTest {
    private static class Node {
        public int value;
        public Node next;

        private Node(int value) {
            this.value = value;
        }
    }

    private static Node solution(Node head) {
        if (head == null || head.next == null) {
            return head;
        }
        if (head.next.next == null) {
            return head.next;
        }

        // pre每次移动一步，cur每次移动两步，停止时pre到达中间节点的前一个节点
        Node pre = head, cur = head.next.next;
        while (cur.next != null && cur.next.next != null) {
            pre = pre.next;
            cur = cur.next.next;
        }
        // 删除中间节点，即pre的下一个节点
        pre.next = pre.next.next;
        return head;
    }

    public static void main(String[] args) {
        Node head1 = new Node(1);
        head1.next = new Node(2);
        head1.next.next = new Node(3);
        head1 = solution(head1);
        while (head1 != null) {
            System.out.print(head1.value + " ");
            head1 = head1.next;
        }

        System.out.println();

        Node head2 = new Node(1);
        head2.next = new Node(2);
        head2.next.next = new Node(3);
        head2.next.next.next = new Node(4);
        head2 = solution(head2);
        while (head2 != null) {
            System.out.print(head2.value + " ");
            head2 = head2.next;
        }
    }
}
