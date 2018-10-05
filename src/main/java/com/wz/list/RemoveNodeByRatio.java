/**
 * <p>Title: RemoveNodeByRatio</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/10/5</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.list;

/**
 * <p>删除链表的中间节点和指定比例a/b处节点</p>
 * <p>
 *     删除中间节点：
 *         链表长度为1时无需删除，为2时删除头节点，为3时删除第二个节点，为4时删除第二个节点...
 *         即链表长度没增加2(3，5，7...)，要删除的节点就后移一位，根据这个可以找到要删除节点的前一个节点
 *     删除a/b处节点：
 *         (a * n / b)向上取整后就是要删除的节点，然后根据这个找到要删除节点的前一个节点即可
 * </p>
 *
 * @author wangzi
 */
public class RemoveNodeByRatio {
    public static class Node {
        public int value;
        public Node next;

        public Node(int value) {
            this.value = value;
        }
    }

    public static Node removeMidNode(Node head) {
        if (head == null || head.next == null) {
            return head;
        }
        if (head.next.next == null) {
            return head.next;
        }

        Node pre = head;
        Node cur = head.next.next;
        // 找到要删除节点的前一个节点
        while (cur.next != null && cur.next.next != null) {
            pre = pre.next;
            cur = cur.next.next;
        }
        pre.next = pre.next.next;
        return head;
    }

    public static Node removeByRatio(Node head, int a, int b) {
        if (a < 1 || a > b) {
            return head;
        }

        // 要删除的节点
        int n = 0;
        Node cur = head;
        while (cur != null) {
            n++;
            cur = cur.next;
        }
        n = (int) Math.ceil(((double) (a * n)) / (double) b);
        if (n == 1) {
            head = head.next;
        } else if (n > 1) {
            cur = head;
            // 找到要删除节点的前一个节点
            while (--n != 1) {
                cur = cur.next;
            }
            cur.next = cur.next.next;
        }
        return head;
    }

    public static void main(String[] args) {
        Node head1 = new Node(1);
        head1.next = new Node(2);
        head1.next.next = new Node(3);
        head1 = removeMidNode(head1);
        while (head1 != null) {
            System.out.print(head1.value + " ");
            head1 = head1.next;
        }

        System.out.println();

        Node head2 = new Node(1);
        head2.next = new Node(2);
        head2.next.next = new Node(3);
        head2.next.next.next = new Node(4);
        head2.next.next.next.next = new Node(5);
        head2 = removeByRatio(head2, 1, 5);
        while (head2 != null) {
            System.out.print(head2.value + " ");
            head2 = head2.next;
        }
    }
}
