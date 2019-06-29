/**
 * <p>Title: RemoveLastKthNodeTest</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019-06-29</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz;

/**
 * <p>删除单链表倒数第K个节点</p>
 *
 * @author wangzi
 */
public class RemoveLastKthNodeTest {
    private static class Node {
        public int value;
        public Node next;

        private Node(int value) {
            this.value = value;
        }
    }

    /**
     * 从头节点开始遍历，每遍历一个节点k减一，直到尾部
     * 此时k>0说明要删除的节点不存在，k==0说明要删除头节点
     * 否则，重新从头开始遍历，每遍历一个节点k加一，k为0时停止，当前的节点就是要删除节点的前一个节点
     */
    private static Node solution(Node head, int k) {
        if (head == null || k < 1) {
            return head;
        }

        Node cur = head;
        while (cur != null) {
            k--;
            cur = cur.next;
        }

        if (k == 0) {
            // 删除头节点
            head = head.next;
        } else if (k < 0) {
            cur = head;
            while (++k != 0) {
                cur = cur.next;
            }
            // 节点删除
            cur.next = cur.next.next;
        }
        return head;
    }

    public static void main(String[] args) {
        Node head1 = new Node(1);
        head1.next = new Node(2);
        head1.next.next = new Node(3);
        head1 = solution(head1, 2);
        while (head1 != null) {
            System.out.print(head1.value + " ");
            head1 = head1.next;
        }

        System.out.println();

        Node head2 = new Node(1);
        head2.next = new Node(2);
        head2.next.next = new Node(3);
        head2 = solution(head2, 3);
        while (head2 != null) {
            System.out.print(head2.value + " ");
            head2 = head2.next;
        }

        System.out.println();
    }
}
