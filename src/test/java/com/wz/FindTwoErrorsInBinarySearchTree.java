/**
 * <p>Title: FindTwoErrorsInBinarySearchTree</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019-06-09</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz;

import java.util.Arrays;
import java.util.Stack;

/**
 * <p>找到搜索二叉树中的两个错误节点</p>
 *
 * @author wangzi
 */
public class FindTwoErrorsInBinarySearchTree {
    private static class Node {
        public int value;
        public Node left;
        public Node right;

        private Node(int value) {
            this.value = value;
        }
    }

    /**
     * 搜索二叉树的中序遍历是升序，若有两个节点位置错误，会出现降序的情况
     * 若出现了两次降序，则第一个错误节点是第一次降序时较大的节点，第二个错误节点是第二次降序时较小的节点，如{1,5,3,4,2,6}
     * 若只出现了一次降序，则第一个错误节点是本次降序时较大的节点，第二个错误节点是本次降序时较小的节点，如{1,3,2,4,5,6}
     */
    private static Node[] solution(Node head) {
        Node[] result = new Node[2];
        if (head == null) {
            return result;
        }
        // 用栈实现中序遍历
        Stack<Node> stack = new Stack<>();
        Node pre = null;
        while (!stack.isEmpty() || head != null) {
            if (head != null) {
                stack.push(head);
                head = head.left;
            } else {
                head = stack.pop();
                if (pre != null && pre.value > head.value) {
                    result[0] = result[0] == null ? pre : result[0];
                    result[1] = head;
                }
                pre = head;
                head = head.right;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        Node head1 = new Node(7);
        head1.left = new Node(3);
        head1.right = new Node(5);
        head1.left.left = new Node(2);
        head1.left.right = new Node(4);
        head1.right.left = new Node(6);
        head1.right.right = new Node(8);
        head1.left.left.left = new Node(1);
        Node[] result = solution(head1);
        if (result != null) {
            Arrays.stream(result).forEach(node -> System.out.print(node.value + " "));
        }

        System.out.println();

        Node head2 = new Node(6);
        head2.left = new Node(3);
        head2.right = new Node(7);
        head2.left.left = new Node(2);
        head2.left.right = new Node(4);
        head2.right.left = new Node(5);
        head2.right.right = new Node(8);
        head2.left.left.left = new Node(1);
        result = solution(head2);
        if (result != null) {
            Arrays.stream(result).forEach(node -> System.out.print(node.value + " "));
        }
    }
}
