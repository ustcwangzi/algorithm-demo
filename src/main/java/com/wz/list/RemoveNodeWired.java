/**
 * <p>Title: RemoveNodeWired</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/10/14</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.list;

/**
 * <p>怪异的节点删除方式</p>
 * <p>
 *     不知道链表的头节点，删除给定的节点node
 *     具体过程：
 *          将要删除节点的值替换为本节点下一个节点的值，然后删除下一个节点即可
 *          eg. 1->2->3，要删除节点2，将2变成3，然后删除节点3
 *     产生的问题：
 *          1、无法删除最后一个节点，因为它没有下一个节点
 *          2、本质上不是删除节点，而是把节点的值进行了替换
 * </p>
 *
 * @author wangzi
 */
public class RemoveNodeWired {
    public static class Node {
        public int value;
        public Node next;

        public Node(int value) {
            this.value = value;
        }
    }

    public static void remove(Node node) {
        if (node == null) {
            return;
        }
        Node next = node.next;
        if (next == null) {
            throw new RuntimeException("can not remove last node.");
        }

        node.value = next.value;
        node.next = next.next;
    }

    public static void main(String[] args) {
        Node head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);

        Node node = head;
        remove(node);
        while (head != null) {
            System.out.print(head.value + " ");
            head = head.next;
        }
    }
}
