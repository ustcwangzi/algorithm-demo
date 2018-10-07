/**
 * <p>Title: CopyListWithRandom</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/10/7</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.list;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>复制含有随机指针节点的链表</p>
 * <p>
 *     节点中新增一个指针rand，该指针可以指向链表中任意节点，也可以指向null
 *     现在对链表进行复制，需要复制其所有的结构，即保证rand指针的正确性
 *     方案一：
 *          1、遍历链表，对每个节点生成其副本节点保存在Map中，此时每个副本节点的next和rand均指向null
 *          2、再次遍历链表，为每个副本节点设置next和rand指针，设置时从步骤一的Map中获取副本节点
 *          3、头节点的副本节点就是复制链表的头节点
 *          该方案时间复杂度O(N)，空间复杂度O(N)
 *     方案二：
 *          1、遍历链表，直接在原节点后面生成副本节点，原节点和副本节点在同一个链表中，副本节点rand指向null
 *          2、再次遍历链表，设置副本节点的rand指针
 *          3、此时副本节点的next和rand均已设置正确，将原节点和副本节点进行分离即可
 *          该方案时间复杂度O(N)，空间复杂度O(1)
 * </p>
 *
 * @author wangzi
 */
public class CopyListWithRandom {
    public static class Node {
        public int value;
        public Node next;
        public Node rand;

        public Node(int value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (other == null || getClass() != other.getClass()) {
                return false;
            }

            Node node = (Node) other;

            if (value != node.value) {
                return false;
            }
            return next != null ? next.equals(node.next) : node.next == null;
        }

        @Override
        public int hashCode() {
            int result = value;
            result = 31 * result + (next != null ? next.hashCode() : 0);
            return result;
        }
    }

    public static Node copyOne(Node head) {
        if (head == null) {
            return null;
        }

        Map<Node, Node> map = new HashMap<>();
        Node cur = head;
        // 每个节点生成相应的副本，副本节点next和rand均指向null
        while (cur != null) {
            map.put(cur, new Node(cur.value));
            cur = cur.next;
        }

        cur = head;
        // 遍历链表，设置每个副本节点的next和rand指针
        while (cur != null) {
            map.get(cur).next = map.get(cur.next);
            map.get(cur).rand = map.get(cur.rand);
            cur = cur.next;
        }

        return map.get(head);
    }

    public static Node copyTwo(Node head) {
        if (head == null) {
            return null;
        }

        Node cur = head;
        Node next = null;
        // 直接将副本节点放在每个原节点后面
        while (cur != null) {
            next = cur.next;
            cur.next = new Node(cur.value);
            cur.next.next = next;
            cur = next;
        }

        cur = head;
        Node curCopy = null;
        // 设置副本节点的rand指针
        while (cur != null) {
            next = cur.next.next;
            curCopy = cur.next;
            curCopy.rand = cur.rand != null ? cur.rand.next : null;
            cur = next;
        }

        cur = head;
        Node result = head.next;
        // 副本节点与原节点分开
        while (cur != null) {
            next = cur.next.next;
            curCopy = cur.next;
            cur.next = next;
            curCopy.next = next != null ? next.next : null;
            cur = next;
        }

        return result;
    }

    private static void print(Node head) {
        while (head != null) {
            String value = head.value + (head.rand != null ? "-" + head.rand.value : "");
            System.out.print(value + " ");
            head = head.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Node head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        head.next.next.next = new Node(4);
        head.next.next.next.next = new Node(5);
        head.next.next.next.next.next = new Node(6);
        head.rand = head.next.next.next.next.next;
        head.next.rand = head.next.next.next.next.next;
        head.next.next.rand = head.next.next.next.next;
        head.next.next.next.rand = head.next.next;
        head.next.next.next.next.rand = null;
        head.next.next.next.next.next.rand = head.next.next.next;

        print(head);
        print(copyOne(head));
        print(copyTwo(head));
    }
}
