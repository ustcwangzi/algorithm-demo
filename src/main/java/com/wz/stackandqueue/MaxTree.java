/**
 * <p>Title: MaxTree</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/10/3</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.stackandqueue;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * <p>构造数组的MaxTree</p>
 * <p>
 *     将一个没有重复元素的数组生成对应的MaxTree，要求时间和空间复杂度为O(N)
 *     建立这棵树的原则：
 *          1、每一个数的父节点是它左边第一个比它大的数和它右边第一个比它大的数中，较小的那个
 *          2、如果一个数左边和右边都没有比它大的数，说明这个数是数组中最大的，是这个树的根节点
 *     利用栈找出每一个数左边第一个比它大的数：
 *          从左到右遍历每一个数，栈中保持递减序列，新来的数如果比栈顶元素小，则直接入栈，
 *        否则弹出栈顶。以[3,1,2]为例，首先3入栈，接下来1比3小，1入栈，得到1左边第一个比它大的数为3；
 *        接下来2比1大，1出栈，2比3小，2入栈，得到2左边第一个比它大的数为3。
 *     用同样的方法从右到左遍历，可得到每个数右边第一个比它大的数。
 * </p>
 *
 * @author wangzi
 */
public class MaxTree {
    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int value) {
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
            return value;
        }
    }

    public static Node getMaxTree(int[] array) {
        Node[] nodeArray = new Node[array.length];
        for (int i = 0; i < array.length; i++) {
            nodeArray[i] = new Node(array[i]);
        }

        Stack<Node> stack = new Stack<>();
        Map<Node, Node> leftBigMap = new HashMap<>();
        Map<Node, Node> rightBigMap = new HashMap<>();

        for (int i = 0; i < nodeArray.length; i++) {
            Node curNode = nodeArray[i];
            while (!stack.isEmpty() && stack.peek().value < curNode.value) {
                popStackSetMap(stack, leftBigMap);
            }
            stack.push(curNode);
        }

        while (!stack.isEmpty()) {
            popStackSetMap(stack, leftBigMap);
        }

        for (int i = nodeArray.length - 1; i >= 0; i--) {
            Node curNode = nodeArray[i];
            while (!stack.isEmpty() && stack.peek().value < curNode.value) {
                popStackSetMap(stack, rightBigMap);
            }
            stack.push(curNode);
        }
        while (!stack.isEmpty()) {
            popStackSetMap(stack, rightBigMap);
        }

        Node head = null;
        for (int i = 0; i < nodeArray.length; i++) {
            Node curNode = nodeArray[i];
            Node left = leftBigMap.get(curNode);
            Node right = rightBigMap.get(curNode);
            if (left == null && right == null) {
                head = curNode;
            } else if (left == null) {
                if (right.left == null) {
                    right.left = curNode;
                } else {
                    right.right = curNode;
                }
            } else if (right == null) {
                if (left.left == null) {
                    left.left = curNode;
                } else {
                    left.right = curNode;
                }
            } else {
                Node parent = left.value < right.value ? left : right;
                if (parent.left == null) {
                    parent.left = curNode;
                } else {
                    parent.right = curNode;
                }
            }
        }
        return head;
    }

    private static void popStackSetMap(Stack<Node> stack, Map<Node, Node> map) {
        Node popNode = stack.pop();
        if (stack.isEmpty()) {
            map.put(popNode, null);
        } else {
            map.put(popNode, stack.peek());
        }
    }

    public static void printPreOrder(Node head) {
        if (head == null) {
            return;
        }
        System.out.print(head.value + " ");
        printPreOrder(head.left);
        printPreOrder(head.right);
    }

    public static void printInOrder(Node head) {
        if (head == null) {
            return;
        }
        printPreOrder(head.left);
        System.out.print(head.value + " ");
        printPreOrder(head.right);
    }

    public static void main(String[] args) {
        int[] array = {3, 4, 5, 1, 2};
        Node head = getMaxTree(array);
        printPreOrder(head);
        System.out.println();
        printInOrder(head);
    }

}
