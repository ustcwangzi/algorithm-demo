/**
 * <p>Title: RemoveLastKthNode</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/10/5</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.list;

/**
 * <p>删除单链表和双链表倒数第K个节点</p>
 * <p>
 *     从头到尾遍历链表，每移动一步，K值减一，移动到尾部时，会出现三种情况
 *        1、K大于0，说明不存在倒数第K个节点
 *        2、K等于0，说明倒数第K个节点就是头节点
 *        3、K小于0，需要找到倒数第K个节点的前一个节点
 *        3.1、重新从头开始遍历链表，每移动一步，K值加一
 *        3.2、K等于0时，停止移动，当前的节点就是要删除节点的前一个节点
 *             假设链表长度N，要删除倒数第K个节点，显然倒数第K个节点的前一个节点是第N-K个节点
 *           第一次遍历后K为K-N，第二次遍历时，当K为0就停止，因此会停在第N-K个节点的位置
 *      以1 -> 2 -> 3 -> 4，K=2为例，说明以上过程
 *        第一次遍历：1 -> 2 -> 3 -> 4
 *         K值变化 ： 1    0   -1   -2
 *        第一次遍历：1 -> 2 -> 3 -> 4
 *         K值变化 ：-1    0
 *        要删除节点的前一个节点是2，将2指向4即可实现删除节点3
 *      单链表和双链表处理方式类似，注意pre指针的重连即可
 * </p>
 *
 * @author wangzi
 */
public class RemoveLastKthNode {
    private static class Node {
        public int value;
        public Node next;

        public Node(int value) {
            this.value = value;
        }
    }

    public static class DoubleNode {
        public int value;
        public DoubleNode pre;
        public DoubleNode next;

        public DoubleNode(int value) {
            this.value = value;
        }
    }

    public static Node removeLastKthNode(Node head, int k) {
        if (head == null || k < 1) {
            return head;
        }

        Node cur = head;
        while (cur != null) {
            k--;
            cur = cur.next;
        }
        // 要删除的是头节点
        if (k == 0) {
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

    public static DoubleNode removeLastKthNode(DoubleNode head, int k) {
        if (head == null || k < 1) {
            return head;
        }

        DoubleNode cur = head;
        while (cur != null) {
            k--;
            cur = cur.next;
        }
        if (k == 0) {
            head = head.next;
            head.pre = null;
        } else if (k < 0) {
            cur = head;
            while (++k != 0) {
                cur = cur.next;
            }
            DoubleNode newNext = cur.next.next;
            cur.next = newNext;
            if (newNext != null) {
                newNext.pre = cur;
            }
        }
        return head;
    }

    public static void main(String[] args) {
        Node head1 = new Node(1);
        head1.next = new Node(2);
        head1.next.next = new Node(3);
        head1 = removeLastKthNode(head1, 2);
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
        head2 = removeLastKthNode(head2, 2);
        while (head2 != null) {
            System.out.print(head2.value + " ");
            head2 = head2.next;
        }
    }
}
