/**
 * <p>Title: ListSelectionSort</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/10/14</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.list;

/**
 * <p>对链表进行选择排序</p>
 * <p>
 *     可以借助数组等容器来实现排序，但是需要额外的空间，此处不采用
 *     排序过程：
 *          1、对于找到的第一个最小值节点肯定是整个链表的最小值节点，将其设为新的头节点
 *          2、每次在未排序的部分找到最小值节点，并把该节点从未排序部分删除
 *          3、把本次找到的最小值节点连接到排好序部分的链表尾部
 *          4、全部处理完毕后，整个链表都已经有序
 *      时间复杂度O(N*N)，空间复杂度O(1)
 * </p>
 *
 * @author wangzi
 */
public class ListSelectionSort {
    public static class Node {
        public int value;
        public Node next;

        public Node(int value) {
            this.value = value;
        }
    }

    public static Node sort(Node head) {
        // 排序完成部分的尾节点
        Node tail = null;
        // 最小值节点的前一个节点
        Node smallPre = null;
        // 最小值节点
        Node small = null;
        Node cur = head;
        while (cur != null) {
            small = cur;
            smallPre = getSmallestPreNode(cur);
            // 获取并删除最小值节点
            if (smallPre != null) {
                small = smallPre.next;
                smallPre.next = small.next;
            }

            cur = cur == small ? cur.next : cur;
            if (tail == null) {
                head = small;
            } else {
                tail.next = small;
            }
            tail = small;
        }
        return head;
    }

    /**
     * 获取最小值节点的前一个节点
     */
    private static Node getSmallestPreNode(Node head) {
        if (head == null || head.next == null) {
            return null;
        }
        // 最小值节点的前一个节点
        Node smallPre = null;
        // 最小值节点
        Node small = head;
        Node pre = head, cur = head.next;
        while (cur != null) {
            if (cur.value < small.value) {
                smallPre = pre;
                small = cur;
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

        head = sort(head);
        while (head != null) {
            System.out.print(head.value + " ");
            head = head.next;
        }
    }
}
