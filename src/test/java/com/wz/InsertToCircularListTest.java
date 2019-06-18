package com.wz;

/**
 * <p>有序环形单链表中插入新节点</p>
 *
 * @author wangzi
 */
public class InsertToCircularListTest {
    private static class Node {
        public int value;
        public Node next;

        private Node(int value) {
            this.value = value;
        }
    }

    private static Node solution(Node head, int num) {
        Node node = new Node(num);
        if (head == null) {
            node.next = node;
            return node;
        }

        Node pre = head, cur = head.next;
        // 寻找插入点
        while (cur != head) {
            if (pre.value <= num && cur.value >= num) {
                break;
            }
            pre = cur;
            cur = cur.next;
        }

        // 插入新节点
        pre.next = node;
        node.next = cur;
        // 判断头节点是否需要调整
        return head.value < num ? head : node;
    }

    private static void print(Node head) {
        if (head == null) {
            return;
        }
        System.out.print(head.value + " ");
        Node cur = head.next;
        while (cur != head) {
            System.out.print(cur.value + " ");
            cur = cur.next;
        }
        System.out.println("-> " + head.value);
    }

    public static void main(String[] args) {
        Node head = null;
        head = solution(head, 2);
        print(head);
        head = solution(head, 1);
        print(head);
        head = solution(head, 4);
        print(head);
        head = solution(head, 3);
        print(head);
        head = solution(head, 5);
        print(head);
        head = solution(head, 0);
        print(head);
    }
}
