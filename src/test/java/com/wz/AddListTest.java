/**
 * <p>Title: AddListTest</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019-06-23</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz;

/**
 * <p>把链表看作整型数值，将两个链表相加，结果生成新的链表</p>
 *
 * @author wangzi
 */
public class AddListTest {
    private static class Node {
        public int value;
        public Node next;

        private Node(int value) {
            this.value = value;
        }
    }

    /**
     * 将两个链表逆序，然后遍历链表，值相加，用carry记录进位
     */
    private static Node solution(Node self, Node other) {
        if (self == null || other == null) {
            return self == null ? other : self;
        }
        self = reverse(self);
        other = reverse(other);

        // 每个链表的节点值、相加结果以及进位
        int num1, num2, count, carry = 0;
        Node cur1 = self, cur2 = other, cur = null, pre;
        while (cur1 != null || cur2 != null) {
            num1 = cur1 != null ? cur1.value : 0;
            num2 = cur2 != null ? cur2.value : 0;
            count = num1 + num2 + carry;
            // 相加是从低位向高位进行的，因为需要逆序生成新链表
            pre = cur;
            cur = new Node(count % 10);
            carry = count / 10;
            cur.next = pre;

            cur1 = cur1 != null ? cur1.next : null;
            cur2 = cur2 != null ? cur2.next : null;
        }
        // 最后一个进位单独生成节点
        if (carry == 1) {
            pre = cur;
            cur = new Node(1);
            cur.next = pre;
        }

        reverse(self);
        reverse(other);

        return cur;
    }

    /**
     * 链表逆序
     */
    private static Node reverse(Node head) {
        if (head == null || head.next == null) {
            return head;
        }
        Node pre = null, next;
        while (head != null) {
            next = head.next;
            head.next = pre;
            pre = head;
            head = next;
        }
        return pre;
    }

    public static void main(String[] args) {
        Node self = new Node(9);
        self.next = new Node(9);
        self.next.next = new Node(9);
        Node other = new Node(1);

        Node result = solution(self, other);
        while (result != null) {
            System.out.print(result.value + " ");
            result = result.next;
        }
    }
}
