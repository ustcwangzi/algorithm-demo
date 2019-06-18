/**
 * <p>Title: InsertToCircularList</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/10/14</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.list;

/**
 * <p>有序环形单链表中插入新节点</p>
 *
 * @author wangzi
 */
public class InsertToCircularList {
    private static class Node {
        public int value;
        public Node next;

        public Node(int value) {
            this.value = value;
        }
    }

    public static Node insert(Node head, int num) {
        Node node = new Node(num);
        if (head == null) {
            node.next = node;
            return node;
        }

        Node pre = head;
        Node cur = head.next;
        // 寻找插入位置
        while (cur != head) {
            if (pre.value <= num && cur.value >= num) {
                break;
            }
            pre = cur;
            cur = cur.next;
        }
        pre.next = node;
        node.next = cur;
        return head.value < num ? head : node;
    }

    private static void printCircularList(Node head) {
        if (head == null) {
            return;
        }
        System.out.print("Circular List: " + head.value + " ");
        Node cur = head.next;
        while (cur != head) {
            System.out.print(cur.value + " ");
            cur = cur.next;
        }
        System.out.println("-> " + head.value);
    }

    public static void main(String[] args) {
        Node head = null;
        head = insert(head, 2);
        printCircularList(head);
        head = insert(head, 1);
        printCircularList(head);
        head = insert(head, 4);
        printCircularList(head);
        head = insert(head, 3);
        printCircularList(head);
        head = insert(head, 5);
        printCircularList(head);
        head = insert(head, 0);
        printCircularList(head);
    }
}
