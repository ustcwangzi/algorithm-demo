/**
 * <p>Title: NearestCommonAncestorTest</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019-06-01</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz;

/**
 * <p>二叉树中两个节点最近的公共祖先</p>
 *
 * @author wangzi
 */
public class NearestCommonAncestorTest {
    private static class Node {
        public int value;
        public Node left;
        public Node right;

        private Node(int value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Node node = (Node) o;
            return value == node.value;
        }

        @Override
        public int hashCode() {
            return Integer.hashCode(value);
        }
    }

    /**
     * 整体采用后续遍历，在左右子树中寻找self和other，找到则返回，找不到返回null
     * 假设左子树中寻找结果为left，右子树中寻找结果为right
     * 若left和right都不为null，说明当前的头节点head就是其最近公共祖先
     * 若left和right有一个为null，另一个不是null，那么不为null的肯定是self和other中的一个
     * 若left和right都是null，说明未找到self和other
     */
    private static Node solution(Node head, Node self, Node other) {
        if (head == null || head.equals(self) || head.equals(other)) {
            return head;
        }

        // 在右子树中寻找self和other
        Node left = solution(head.left, self, other);
        // 在右子树中寻找self和other
        Node right = solution(head.right, self, other);

        if (left != null && right != null) {
            return head;
        }
        return left != null ? left : right;
    }

    public static void main(String[] args) {
        Node head = new Node(1);
        head.left = new Node(2);
        head.right = new Node(3);
        head.left.left = new Node(4);
        head.left.right = new Node(5);
        head.right.left = new Node(6);
        head.right.right = new Node(7);
        for (int i = 1; i < 8; i++) {
            for (int j = 1; j < 8; j++) {
                Node result = solution(head, new Node(i), new Node(j));
                System.out.println(i + "," + j + ": " + (result == null ? null : result.value));
            }
        }
    }
}
