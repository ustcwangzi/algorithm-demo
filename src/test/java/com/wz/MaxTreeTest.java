/**
 * <p>Title: MaxTreeTest</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019-07-07</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * <p></p>
 *
 * @author wangzi
 */
public class MaxTreeTest {
    public static class Node {
        public int value;
        public Node left;
        public Node right;

        private Node(int value) {
            this.value = value;
        }
    }

    private static Node solution(int[] array) {
        if (array == null || array.length == 0) {
            return null;
        }

        int length = array.length;
        Node[] nodeArray = new Node[length];
        for (int i = 0; i < length; i++) {
            nodeArray[i] = new Node(array[i]);
        }
        Map<Node, Node> leftBigMap = new HashMap<>(length);
        Map<Node, Node> rightBigMap = new HashMap<>(length);

        fillBigMap(nodeArray, leftBigMap, rightBigMap);

        Node head = null;
        for (Node cur : nodeArray) {
            Node left = leftBigMap.get(cur);
            Node right = rightBigMap.get(cur);
            if (left == null && right == null) {
                head = cur;
            } else if (left == null) {
                if (right.left == null) {
                    right.left = cur;
                } else {
                    right.right = cur;
                }
            } else if (right == null) {
                if (left.left == null) {
                    left.left = cur;
                } else {
                    left.right = cur;
                }
            } else {
                Node parent = left.value < right.value ? left : right;
                if (parent.left == null) {
                    parent.left = cur;
                } else {
                    parent.right = cur;
                }
            }
        }
        return head;
    }

    private static void fillBigMap(Node[] array, Map<Node, Node> leftBigMap, Map<Node, Node> rightBigMap) {
        Stack<Node> stack = new Stack<>();
        for (Node cur : array) {
            while (!stack.isEmpty() && stack.peek().value < cur.value) {
                popStackSetMap(stack, leftBigMap);
            }
            stack.push(cur);
        }
        while (!stack.isEmpty()) {
            popStackSetMap(stack, leftBigMap);
        }

        for (int i = array.length - 1; i > -1; i--) {
            Node cur = array[i];
            while (!stack.isEmpty() && stack.peek().value < cur.value) {
                popStackSetMap(stack, rightBigMap);
            }
            stack.push(cur);
        }
        while (!stack.isEmpty()) {
            popStackSetMap(stack, rightBigMap);
        }
    }

    private static void popStackSetMap(Stack<Node> stack, Map<Node, Node> bigMap) {
        Node cur = stack.pop();
        if (!stack.isEmpty()) {
            bigMap.put(cur, stack.peek());
        }
    }

    private static void preOrder(Node head) {
        if (head == null) {
            return;
        }
        System.out.print(head.value + " ");
        preOrder(head.left);
        preOrder(head.right);
    }

    public static void main(String[] args) {
        int[] array = {3, 4, 5, 1, 2};
        Node head = solution(array);
        preOrder(head);
    }
}
