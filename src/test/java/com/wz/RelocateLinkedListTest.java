package com.wz;

/**
 * <p>将链表分为左右半区，左半区为L1->L2->...，右半区为R1->R2->...，调整为L1->R1->L2->R2->...</p>
 *
 * @author wangzi
 */
public class RelocateLinkedListTest {
    private static class Node {
        public int value;
        public Node next;

        private Node(int value) {
            this.value = value;
        }
    }

    /**
     * 找到中间节点，根据中间节点将链表划分为左右部分，再重新组合
     */
    private static void solution(Node head) {
        if (head == null || head.next == null) {
            return;
        }

        Node mid = head;
        Node right = head.next;
        // 找到中间节点
        while (right.next != null && right.next.next != null) {
            mid = mid.next;
            right = right.next.next;
        }
        // 右半部分开始节点
        right = mid.next;
        // 左半部分结束节点
        mid.next = null;
        merge(head, right);
    }

    /**
     * 交叉合并左右部分
     */
    private static void merge(Node left, Node right) {
        Node next;
        while (left.next != null) {
            next = right.next;
            right.next = left.next;
            left.next = right;
            left = right.next;
            right = next;
        }
        // 右部分剩余节点直接放在后面
        left.next = right;
    }

    public static void main(String[] args) {
        Node head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        head.next.next.next = new Node(4);
        head.next.next.next.next = new Node(5);

        solution(head);
        while (head != null) {
            System.out.print(head.value + " ");
            head = head.next;
        }
    }
}
