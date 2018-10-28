/**
 * <p>Title: ContainsTopology</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/10/28</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.binarytree;

/**
 * <p>一棵树是否包含另一棵树全部的拓扑结构</p>
 * <p>
 *     如果self中某棵子树头节点的值和other头节点的值一样，则从这两个头节点开始匹配
 *     匹配的每一步都是让self上的节点跟着other的先序遍历移动，每移动一步，检查两个节点的值是否相同
 *     该方法时间复杂度为O(N*M)
 * </p>
 *
 * @author wangzi
 */
public class ContainsTopology {
    private static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    public static boolean contains(Node self, Node other) {
        if (other == null) {
            return true;
        }
        if (self == null) {
            return false;
        }
        return check(self, other) || contains(self.left, other) || contains(self.right, other);
    }

    private static boolean check(Node self, Node other) {
        if (other == null) {
            return true;
        }
        if (self == null || self.value != other.value) {
            return false;
        }
        return check(self.left, other.left) && check(self.right, other.right);
    }

    public static void main(String[] args) {
        Node self = new Node(1);
        self.left = new Node(2);
        self.right = new Node(3);
        self.left.left = new Node(4);
        self.left.right = new Node(5);
        self.right.left = new Node(6);
        self.right.right = new Node(7);
        self.left.left.left = new Node(8);
        self.left.left.right = new Node(9);
        self.left.right.left = new Node(10);

        Node other = new Node(2);
        other.left = new Node(4);
        other.left.left = new Node(8);
        other.right = new Node(5);

        System.out.println(contains(self, other));
    }
}
