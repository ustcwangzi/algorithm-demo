package com.wz;

/**
 * <p>合并两个有序单链表</p>
 */
public class MergeTwoSortedLinkedListTest {
    private static class Node {
        public int value;
        public Node next;

        private Node(int value) {
            this.value = value;
        }
    }

    private static Node solution(Node self, Node other) {
        if (self == null || other == null) {
            return self == null ? other : self;
        }
        // 最终的头节点，指向self和other中头节点值较小的
        Node head = self.value <= other.value ? self : other;
        // 指向头节点值较小的链表
        Node cur1 = head == self ? self : other;
        // 指向头节点值较大的链表
        Node cur2 = head == self ? other : self;
        Node pre = cur1, next;
        while (cur1 != null && cur2 != null) {
            if (cur1.value <= cur2.value) {
                // 直接右移
                pre = cur1;
                cur1 = cur1.next;
            } else {
                // 将cur2插入pre和cur1之间
                next = cur2.next;
                pre.next = cur2;
                cur2.next = cur1;
                pre = cur2;
                cur2 = next;
            }
        }
        // 剩余部分直接放在pre之后
        pre.next = cur1 == null ? cur2 : cur1;
        return head;
    }

    public static void main(String[] args) {
        Node head1 = new Node(1);
        head1.next = new Node(3);
        head1.next.next = new Node(5);
        head1.next.next.next = new Node(7);
        head1.next.next.next.next = new Node(9);
        Node head2 = new Node(0);
        head2.next = new Node(2);
        head2.next.next = new Node(6);
        head2.next.next.next = new Node(7);

        Node head = solution(head1, head2);
        while (head != null) {
            System.out.print(head.value + " ");
            head = head.next;
        }
    }
}
