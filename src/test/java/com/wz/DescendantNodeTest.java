package com.wz;

/**
 * <p>二叉树中找到一个节点的后继节点(中序遍历的下一个节点)，其中每个节点都存在指上父节点的指针</p>
 *
 * @author wangzi
 */
public class DescendantNodeTest {
    private static class Node {
        public int value;
        public Node left;
        public Node right;
        public Node parent;

        private Node(int value) {
            this.value = value;
        }
    }

    private static Node solution(Node node) {
        if (node.right != null) {
            return getMostLeft(node.right);
        }

        Node parent = node.parent;
        while (parent != null && parent.left != node) {
            node = parent;
            parent = node.parent;
        }
        return parent;
    }

    private static Node getMostLeft(Node node) {
        if (node == null) {
            return null;
        }
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    public static void main(String[] args) {
        Node head = new Node(6);
        head.parent = null;
        head.left = new Node(3);
        head.left.parent = head;
        head.left.left = new Node(1);
        head.left.left.parent = head.left;
        head.left.left.right = new Node(2);
        head.left.left.right.parent = head.left.left;
        head.left.right = new Node(4);
        head.left.right.parent = head.left;
        head.left.right.right = new Node(5);
        head.left.right.right.parent = head.left.right;
        head.right = new Node(9);
        head.right.parent = head;
        head.right.left = new Node(8);
        head.right.left.parent = head.right;
        head.right.left.left = new Node(7);
        head.right.left.left.parent = head.right.left;
        head.right.right = new Node(10);
        head.right.right.parent = head.right;

        Node node = head.left.left;
        System.out.println(node.value + " next: " + solution(node).value);
        node = head.left;
        System.out.println(node.value + " next: " + solution(node).value);
        node = head.left.right.right;
        System.out.println(node.value + " next: " + solution(node).value);
        node = head;
        System.out.println(node.value + " next: " + solution(node).value);
        node = head.right;
        System.out.println(node.value + " next: " + solution(node).value);
        node = head.right.left;
        System.out.println(node.value + " next: " + solution(node).value);
        node = head.right.right;
        System.out.println(node.value + " next: " + solution(node));
    }
}
