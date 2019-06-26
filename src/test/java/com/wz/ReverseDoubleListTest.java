package com.wz;

/**
 * <p>逆置双链表</p>
 *
 * @author wangzi
 */
public class ReverseDoubleListTest {
    public static class DoubleNode {
        public int value;
        public DoubleNode pre;
        public DoubleNode next;

        private DoubleNode(int value) {
            this.value = value;
        }
    }

    private static DoubleNode solution(DoubleNode head) {
        DoubleNode pre = null, next;
        while (head != null) {
            next = head.next;
            head.next = pre;
            head.pre = next;
            pre = head;
            head = next;
        }
        return pre;
    }

    public static void main(String[] args) {
        DoubleNode head1 = new DoubleNode(1);
        head1.next = new DoubleNode(2);
        head1.next.pre = head1;
        head1 = solution(head1);
        while (head1 != null) {
            System.out.print(head1.value + " ");
            head1 = head1.next;
        }

        System.out.println();

        DoubleNode head2 = new DoubleNode(1);
        head2.next = new DoubleNode(2);
        head2.next.pre = head2;
        head2.next.next = new DoubleNode(3);
        head2.next.pre = head2.next;
        head2 = solution(head2);
        while (head2 != null) {
            System.out.print(head2.value + " ");
            head2 = head2.next;
        }
    }
}
