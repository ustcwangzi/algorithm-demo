/**
 * <p>Title: ContainsTopologyTest</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019-06-08</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz;

/**
 * <p></p>
 *
 * @author wangzi
 */
public class ContainsTopologyTest {
    private static class Node {
        public int value;
        public Node left;
        public Node right;

        private Node(int value) {
            this.value = value;
        }
    }

    private static boolean solution(Node self, Node other) {
        if (other == null) {
            return true;
        }
        if (self == null) {
            return false;
        }
        return equals(self, other) || solution(self.left, other) || solution(self.right, other);
    }

    private static boolean equals(Node self, Node other) {
        if (other == null) {
            return true;
        }
        if (self == null || self.value != other.value) {
            return false;
        }
        return equals(self.left, other.left) && equals(self.right, other.right);
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

        System.out.println(solution(self, other));
    }
}
