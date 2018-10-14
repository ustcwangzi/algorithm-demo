/**
 * <p>Title: PartitionList</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/10/7</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.list;

/**
 * <p>按照给定值划分链表</p>
 * <p>
 *     方案一：
 *          把链表所有节点放入数组中，遍历数组调整元素位置，最后将数组节点重新连接起来
 *        时间复杂度O(N)，空间复杂度O(N)，调整后节点的先后次序可能与原链表不一致
 *     方案二：
 *          将原链表划分为三个链表，保存三个链表的首尾节点，然后将三个链表重新连接起来
 *        时间复杂度O(N)，空间复杂度O(1)，调整后节点的先后次序与原链表一致
 * </p>
 *
 * @author wangzi
 */
public class PartitionList {
    private static class Node {
        public int value;
        public Node next;

        public Node(int value) {
            this.value = value;
        }
    }

    public static Node partitionOne(Node head, int pivot) {
        if (head == null || head.next == null) {
            return head;
        }

        Node cur = head;
        int len = 0;
        // 获取链表长度
        while (cur != null) {
            len++;
            cur = cur.next;
        }

        cur = head;
        Node[] nodeArray = new Node[len];
        // 把链表放入数组中
        for (int i = 0; i < nodeArray.length; i++) {
            nodeArray[i] = cur;
            cur = cur.next;
        }
        // 调整数组中的元素位置
        arrayPartition(nodeArray, pivot);

        // 将调整位置后的数组中的节点连接起来
        for (int i = 1; i < nodeArray.length; i++) {
            nodeArray[i - 1].next = nodeArray[i];
        }
        nodeArray[nodeArray.length - 1].next = null;
        return nodeArray[0];
    }

    private static void arrayPartition(Node[] nodeArray, int pivot) {
        int left = -1;
        int right = nodeArray.length;
        int index = 0;
        while (index != right) {
            if (nodeArray[index].value < pivot) {
                swap(nodeArray, ++left, index++);
            } else if (nodeArray[index].value == pivot) {
                index++;
            } else {
                swap(nodeArray, --right, index);
            }
        }
    }

    private static void swap(Node[] nodeArray, int self, int other) {
        Node tmp = nodeArray[self];
        nodeArray[self] = nodeArray[other];
        nodeArray[other] = tmp;
    }

    public static Node partitionTwo(Node head, int pivot) {
        Node smallHead = null, smallTail = null;
        Node equalHead = null, equalTail = null;
        Node bigHead = null, bigTail = null;

        // 划分small、equal、big三部分
        Node next = null;
        while (head != null) {
            next = head.next;
            head.next = null;
            if (head.value < pivot) {
                if (smallHead == null) {
                    smallHead = head;
                    smallTail = head;
                } else {
                    smallTail.next = head;
                    smallTail = head;
                }
            } else if (head.value == pivot) {
                if (equalHead == null) {
                    equalHead = head;
                    equalTail = head;
                } else {
                    equalTail.next = head;
                    equalTail = head;
                }
            } else {
                if (bigHead == null) {
                    bigHead = head;
                    bigTail = head;
                } else {
                    bigTail.next = head;
                    bigTail = head;
                }
            }
            head = next;
        }

        // 将三部分重新连接起来
        if (smallTail != null) {
            smallTail.next = equalHead;
            equalTail = equalHead != null ? equalTail : smallTail;
        }
        if (equalTail != null) {
            equalTail.next = bigHead;
        }

        return smallHead != null ? smallHead : equalHead != null ? equalHead : bigHead;
    }

    public static void main(String[] args) {
        Node head1 = new Node(7);
        head1.next = new Node(9);
        head1.next.next = new Node(1);
        head1.next.next.next = new Node(8);
        head1.next.next.next.next = new Node(5);
        head1.next.next.next.next.next = new Node(2);
        head1.next.next.next.next.next.next = new Node(5);
        head1 = partitionOne(head1, 5);
        while (head1 != null) {
            System.out.print(head1.value + " ");
            head1 = head1.next;
        }

        System.out.println();

        Node head2 = new Node(7);
        head2.next = new Node(9);
        head2.next.next = new Node(1);
        head2.next.next.next = new Node(8);
        head2.next.next.next.next = new Node(5);
        head2.next.next.next.next.next = new Node(2);
        head2.next.next.next.next.next.next = new Node(5);
        head2 = partitionTwo(head2, 5);
        while (head2 != null) {
            System.out.print(head2.value + " ");
            head2 = head2.next;
        }
    }
}
