/**
 * <p>Title: RemoveRepetition</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/10/13</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.list;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>删除值重复的节点</p>
 * <p>
 *     方案一：
 *          节点的值放入hashSet中，如果已存在则删除，时间复杂度O(N)，空间复杂度O(N)
 *     方案二：
 *          类似与选择排序，遍历到每个节点时，都往后检查是否存在值相同的节点，存在则删除
 *          时间复杂度为O(N*N)，空间复杂度为O(1)
 * </p>
 *
 * @author wangzi
 */
public class RemoveRepetition {
    private static class Node {
        public int value;
        public Node next;

        public Node(int value) {
            this.value = value;
        }
    }

    public static void removeOne(Node head) {
        if (head == null || head.next == null) {
            return;
        }

        Set<Integer> set = new HashSet<>();
        Node pre = head;
        Node cur = head.next;
        set.add(head.value);
        while (cur != null) {
            if (set.contains(cur.value)) {
                // 删除节点cur
                pre.next = cur.next;
            } else {
                set.add(cur.value);
                pre = cur;
            }
            cur = cur.next;
        }
    }

    public static void removeTwo(Node head) {
        Node cur = head;
        Node pre = null, next = null;
        // 外层循环
        while (cur != null) {
            pre = cur;
            next = cur.next;
            // 内层循环，删除与cur值相等的节点
            while (next != null) {
                if (cur.value == next.value) {
                    pre.next = next.next;
                } else {
                    pre = next;
                }
                next = next.next;
            }
            cur = cur.next;
        }
    }

    public static void main(String[] args) {
        Node head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        head.next.next.next = new Node(3);
        head.next.next.next.next = new Node(4);
        head.next.next.next.next.next = new Node(4);
        head.next.next.next.next.next.next = new Node(2);

        removeOne(head);
        while (head != null) {
            System.out.print(head.value + " ");
            head = head.next;
        }

        System.out.println();

        head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        head.next.next.next = new Node(3);
        head.next.next.next.next = new Node(4);
        head.next.next.next.next.next = new Node(4);
        head.next.next.next.next.next.next = new Node(2);
        removeTwo(head);
        while (head != null) {
            System.out.print(head.value + " ");
            head = head.next;
        }
    }
}
