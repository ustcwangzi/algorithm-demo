package com.wz;

/**
 * <p>对链表进行选择排序</p>
 *
 * @author wangzi
 */
public class ListSelectionSortTest {
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

        // 已排序链表的尾节点
        Node tail = null;
        Node cur = head;
        // 未排序链表中最小节点及其前一个节点
        Node small, smallPre;
        while (cur != null) {
            small = cur;
            // 获取最小节点的前一个节点
            smallPre = getSmallestPreNode(cur);
            if (smallPre != null) {
                // 将最小节点从未排序链表中去除
                small = smallPre.next;
                smallPre.next = small.next;
            }

            cur = cur == small ? cur.next : cur;
            // 将最小节点加入到已排序链表的尾部
            if (tail == null) {
                head = small;
            } else {
                tail.next = small;
            }
            // 更新已排序链表的尾节点
            tail = small;
        }
        return head;
    }

    /**
     * 找到以head为头节点的链表中最小节点的前一个节点
     */
    private static Node getSmallestPreNode(Node head) {
        if (head == null || head.next == null) {
            return null;
        }

        // 最小节点及其前一个节点
        Node small = head, smallPre = null;
        Node pre = head, cur = head.next;
        while (cur != null) {
            if (cur.value < small.value) {
                small = cur;
                smallPre = pre;
            }
            pre = cur;
            cur = cur.next;
        }

        return smallPre;
    }

    public static void main(String[] args) {
        Node head = new Node(3);
        head.next = new Node(1);
        head.next.next = new Node(4);
        head.next.next.next = new Node(2);

        head = solution(head);
        while (head != null) {
            System.out.print(head.value + " ");
            head = head.next;
        }
    }
}
