/**
 * <p>Title: RemoveGivenValue</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/10/13</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.list;

/**
 * <p>删除给定值</p>
 *
 * @author wangzi
 */
public class RemoveGivenValue {
    private static class Node {
        public int value;
        public Node next;

        public Node(int value) {
            this.value = value;
        }
    }

    public static Node remove(Node head, int num) {
        if (head == null) {
            return null;
        }

        // 找到第一个值不为num的节点作为头节点
        while (head != null) {
            if (head.value != num) {
                break;
            }
            head = head.next;
        }

        Node pre = head, cur = head;
        while (cur != null) {
            if (cur.value == num) {
                pre.next = cur.next;
            } else {
                pre = cur;
            }
            cur = cur.next;
        }
        return head;
    }

    public static void main(String[] args) {
        Node head = new Node(1);
        head.next = new Node(1);
        head.next.next = new Node(3);
        head.next.next.next = new Node(3);
        head.next.next.next.next = new Node(1);
        head.next.next.next.next.next = new Node(2);
        head.next.next.next.next.next.next = new Node(1);

        head = remove(head, 1);
        while (head != null) {
            System.out.print(head.value + " ");
            head = head.next;
        }
    }
}
