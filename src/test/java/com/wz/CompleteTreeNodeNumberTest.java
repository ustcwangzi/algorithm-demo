/**
 * <p>Title: CompleteTreeNodeNumberTest</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019-05-26</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz;

/**
 * <p>完全二叉树节点数</p>
 *
 * @author wangzi
 */
public class CompleteTreeNodeNumberTest {
    private static class Node {
        public int value;
        public Node left;
        public Node right;

        private Node(int value) {
            this.value = value;
        }
    }

    private static int solution(Node head) {
        if (head == null) {
            return 0;
        }
        int height = getMostLeftLevel(head, 1);
        return getCount(head, 1, height);
    }

    private static int getCount(Node node, int level, int height) {
        if (level == height) {
            return 1;
        }
        if (getMostLeftLevel(node.right, level + 1) == height) {
            return (1 << (height - level)) + getCount(node.right, level + 1, height);
        } else {
            return (1 << (height - level - 1)) + getCount(node.left, level + 1, height);
        }
    }

    private static int getMostLeftLevel(Node node, int level) {
        while (node != null) {
            level++;
            node = node.left;
        }
        return level - 1;
    }

    public static void main(String[] args) {
        Node head = new Node(1);
        System.out.println(solution(head));
        head.left = new Node(2);
        System.out.println(solution(head));
        head.right = new Node(3);
        System.out.println(solution(head));
        head.left.left = new Node(4);
        System.out.println(solution(head));
        head.left.right = new Node(5);
        System.out.println(solution(head));
        head.right.left = new Node(6);
        System.out.println(solution(head));
        head.right.right = new Node(7);
        System.out.println(solution(head));
    }
}
