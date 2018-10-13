/**
 * <p>Title: FindIntersect</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/10/13</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.list;

/**
 * <p>两个链表的第一个相交节点</p>
 * <p>
 *     获取两个链表第一个相交节点
 *     可分为三个子问题：
 *          一、链表是否有环，有环时获取第一个入环节点
 *          二、判断两个无环链表是否相交，相交时获取第一个相交节点
 *          三、判断两个有环链表是否相交，相交时获取第一个相交节点
 *     解答问题一：
 *          1、设置一个慢指针slow和一个快指针fast，slow每次移动一步，fast每次移动两步
 *          2、无环时，fast一定先遇到终点
 *          3、有环时，fast和slow在中间某处相遇，此时fast回到head，slow还在相遇处，然后fast和slow均每次移动一步
 *          4、fast和slow再次相遇时的节点，就是第一个入环的节点
 *     通过问题一得知链表是否有环，如果一个有环一个无环，肯定不能相交，能相交的情况分为两种：
 *         一种是两者均无环，即问题二；另一种是两者均有环，即问题三
 *     解答问题二：
 *          1、分别统计两个链表的长度len1、len2和两个链表的最后一个节点end1和end2
 *          2、如果end1 != end2，说明两个链表不相交，否则进入步骤三
 *          3、若链表1较长，链表1先走len1-len2步，若链表2较长，链表2先走len2-len1步，
 *             然后两个链表一起走，第一次相遇时的节点就是第一个相交的节点
 *          eg. 链表1长100，链表2长30，链表1先走70步，然后链表1和2一起走剩下的30步，一定会同时到达第一个相交节点
 *     解答问题三：
 *          1、如果loop1 == loop2，只需要考虑head1到loop1与head2到loop2这两段无环处的相交情况即可，转化为问题二
 *          2、如果loop1 != loop2，链表1从loop1出发，在回到loop1之前如果没遇到loop2，则说明两者不相交，
 *             如果遇到loop2，则两者相交，返回loop1或loop2均可，只不过是loop1离链表1较近，loop2离链表2较近
 *     时间复杂度为O(N+M)，空间复杂度为O(1)
 * </p>
 *
 * @author wangzi
 */
public class FindIntersect {
    public static class Node {
        public int value;
        public Node next;

        public Node(int value) {
            this.value = value;
        }
    }

    public static Node getIntersectNode(Node head1, Node head2) {
        if (head1 == null || head2 == null) {
            return null;
        }
        Node loop1 = getLoopNode(head1);
        Node loop2 = getLoopNode(head2);
        if (loop1 == null && loop2 == null) {
            return getIntersectForNoLoop(head1, head2);
        }
        if (loop1 != null && loop2 != null) {
            return getIntersectForLoop(head1, loop1, head2, loop2);
        }
        return null;
    }

    /**
     * 获取第一个入环的节点
     */
    private static Node getLoopNode(Node head) {
        if (head == null || head.next == null || head.next.next == null) {
            return null;
        }

        Node slow = head.next;
        Node fast = head.next.next;
        while (slow != fast) {
            // 不存在环
            if (fast.next == null || fast.next.next == null) {
                return null;
            }
            slow = slow.next;
            fast = fast.next.next;
        }

        // 存在环，slow不动，fast回到head
        fast = head;
        // slow和fast再次相遇时的节点就是第一个入环的节点
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }
        return slow;
    }

    /**
     * 无环时，获取第一个相交的节点
     */
    private static Node getIntersectForNoLoop(Node head1, Node head2) {
        return getIntersectForNoLoop(head1, null, head2, null);
    }

    /**
     * 获取无环处第一个相交的节点
     *
     * @param start1 链表1开始节点
     * @param end1   链表1结束节点
     * @param start2 链表2开始节点
     * @param end2   链表2结束节点
     * @return 第一个相交的节点
     */
    private static Node getIntersectForNoLoop(Node start1, Node end1, Node start2, Node end2) {
        if (start1 == null || start2 == null) {
            return null;
        }
        Node cur1 = start1;
        Node cur2 = start2;
        int n = 0;
        while (cur1.next != end1) {
            n++;
            cur1 = cur1.next;
        }
        while (cur2.next != end2) {
            n--;
            cur2 = cur2.next;
        }
        // 此时 n = len1-len2

        // cur1为长度较大的链表头
        cur1 = n > 0 ? start1 : start2;
        // cur2为长度较小的链表头
        cur2 = cur1 == start1 ? start2 : start1;

        // cur1向右移动n个位置
        n = Math.abs(n);
        while (n > 0) {
            n--;
            cur1 = cur1.next;
        }
        // cur1和cur2相遇时的节点就是第一个相交的节点
        while (cur1 != cur2) {
            cur1 = cur1.next;
            cur2 = cur2.next;
        }
        return cur1;
    }

    /**
     * 有环时，获取第一个相交的节点
     */
    private static Node getIntersectForLoop(Node head1, Node loop1, Node head2, Node loop2) {
        // 两链表入环节点相同，仅考虑非环处即可
        if (loop1 == loop2) {
            return getIntersectForNoLoop(head1, loop1, head2, loop2);
        } else {
            // 从loop1出发如果在回到loop1之前遇到loop2，说明相交
            Node cur = loop1.next;
            while (cur != loop1) {
                if (cur == loop2) {
                    return loop1;
                }
                cur = cur.next;
            }
            return null;
        }
    }

    public static void main(String[] args) {
        // 1->2->3->4->5->6->7->null
        Node head1 = new Node(1);
        head1.next = new Node(2);
        head1.next.next = new Node(3);
        head1.next.next.next = new Node(4);
        head1.next.next.next.next = new Node(5);
        head1.next.next.next.next.next = new Node(6);
        head1.next.next.next.next.next.next = new Node(7);

        // 0->9->8->6->7->null
        Node head2 = new Node(0);
        head2.next = new Node(9);
        head2.next.next = new Node(8);
        // 不相交
        System.out.println(getIntersectNode(head1, head2));

        // 8->6
        head2.next.next.next = head1.next.next.next.next.next;
        // 无环
        System.out.println(getIntersectNode(head1, head2).value);

        // 1->2->3->4->5->6->7->4...
        head1 = new Node(1);
        head1.next = new Node(2);
        head1.next.next = new Node(3);
        head1.next.next.next = new Node(4);
        head1.next.next.next.next = new Node(5);
        head1.next.next.next.next.next = new Node(6);
        head1.next.next.next.next.next.next = new Node(7);
        head1.next.next.next.next.next.next = head1.next.next.next;

        // 0->9->8->2...
        head2 = new Node(0);
        head2.next = new Node(9);
        head2.next.next = new Node(8);
        // 8->2
        head2.next.next.next = head1.next;
        // 第一个入环节点相同
        System.out.println(getIntersectNode(head1, head2).value);

        // 0->9->8->6->4->5->6..
        head2 = new Node(0);
        head2.next = new Node(9);
        head2.next.next = new Node(8);
        // 8->6
        head2.next.next.next = head1.next.next.next.next.next;
        // 第一个入环节点不同
        System.out.println(getIntersectNode(head1, head2).value);
    }
}
