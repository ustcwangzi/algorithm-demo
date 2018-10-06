/**
 * <p>Title: JosephusProblem</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/10/6</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.list;

/**
 * <p>环形单链表的约瑟夫问题</p>
 * <p>
 *     环形单链表，头节点从1开始计数，每计数到m则删除该节点，后续节点重新从1开始计数，直到剩余一个节点为止
 *     方案一：
 *          直接遍历，使用一个计算器count，等于m时删除节点，count置0，复杂度为O(N*m)
 *     方案二：
 *          剩余最后一个节点时，该节点编号为Num(1)=1
 *          剩余两个节点时，假设幸存节点编号为Num(2)
 *          ...
 *          剩余i个节点时，假设幸存节点编号为Num(i)
 *          现在需要找到Num(i)与Num(i-1)的关系
 *          1、从头开始计数，报A的是编号为B的节点，则A与B的对应关系为
 *              A：1   2   ...   i   i+1   i+2  ...
 *              B：1   2   ...   i    1     2   ...
 *             例如，总共3个节点，报1的编号为1，报2的编号为2...报4的编号为1...
 *             由此可以得到：B = (A-1)%i + 1
 *          2、编号为s的节点删除后，节点总数从i变为i-1，则每个节点编号的变化为
 *               i 个节点时：s-2   s-1   s   s+1   s+2  ...
 *              i-1个节点时：i-2   i-1   -    1     2   ...
 *             即编号为s的节点往后，s+1、s+2...的节点在新环中的编号为1、2...
 *             假设节点数为i时编号为old，节点数为i-1时编号为new
 *             由此可以得到：old = (new+s-1)%i + 1
 *          3、每次计数到m时节点删除，根据步骤一可得：s = (m-1)%i + 1，
 *             带入步骤二，简化后得到：old = (new+m-1)%i + 1
 *          通过以上步骤，可以得到：Num(i) = (Num(i-1) + m - 1) % i + 1
 *
 * </p>
 *
 * @author wangzi
 */
public class JosephusProblem {
    public static class Node {
        public int value;
        public Node next;

        public Node(int value) {
            this.value = value;
        }
    }

    public static Node josephusOne(Node head, int m) {
        if (head == null || head.next == head || m < 1) {
            return head;
        }

        Node last = head;
        while (last.next != head) {
            last = last.next;
        }

        int count = 0;
        while (head != last) {
            // 此时head指向要删除的节点，last指向前一个节点
            if (++count == m) {
                last.next = head.next;
                count = 0;
            } else {
                last = last.next;
            }
            head = last.next;
        }
        return head;
    }

    public static Node josephusTwo(Node head, int m) {
        if (head == null || head.next == head || m < 1) {
            return head;
        }

        Node cur = head.next;
        int position = 1;
        while (cur != head) {
            position++;
            cur = cur.next;
        }

        // 递归找到最后剩下的那个节点位置
        position = getLive(position, m);
        while (--position != 0) {
            head = head.next;
        }
        head.next = null;
        return head;
    }

    private static int getLive(int position, int m) {
        if (position == 1) {
            return 1;
        }
        return (getLive(position - 1, m) + m - 1) % position + 1;
    }

    public static void main(String[] args) {
        Node head1 = new Node(1);
        head1.next = new Node(2);
        head1.next.next = new Node(3);
        head1.next.next.next = new Node(4);
        head1.next.next.next.next = new Node(5);
        head1.next.next.next.next.next = head1;
        head1 = josephusOne(head1, 3);
        System.out.println(head1.value);

        Node head2 = new Node(1);
        head2.next = new Node(2);
        head2.next.next = new Node(3);
        head2.next.next.next = new Node(4);
        head2.next.next.next.next = new Node(5);
        head2.next.next.next.next.next = head2;
        head2 = josephusTwo(head2, 3);
        System.out.println(head2.value);
    }
}
