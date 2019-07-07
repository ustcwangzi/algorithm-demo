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
 * <p>构造数组的MaxTree，数组中无重复元素</p>
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

    /**
     * 对每一个元素，从左边和右边各选择第一个比该元素大的值，选择其中较小的作为父节点
     * 若左边和右边都没有比它大的，说明该节点就是整个树的根结点
     * 利用栈找每一个元素左边和右边第一个比它大的元素：
     * 正向遍历数组，若当前元素i大于栈顶j，弹出j，弹出后栈顶为k，说明j左边第一个比它大的元素就是k，记录该关系，然后i入栈
     * 数组为空时，若栈不空，则依次出栈，弹出j，弹出后栈顶为k，说明j左边第一个比它大的元素就是k，记录该关系，然后继续出栈
     * 逆向遍历数组，以同样的方式，可以获取每一个元素右边第一个比它的大元素
     */
    private static Node solution(int[] array) {
        if (array == null || array.length == 0) {
            return null;
        }

        int length = array.length;
        Node[] nodeArray = new Node[length];
        for (int i = 0; i < length; i++) {
            nodeArray[i] = new Node(array[i]);
        }
        // 每一个节点左侧第一个比它大的节点
        Map<Node, Node> leftBigMap = new HashMap<>(length);
        // 每一个节点右侧第一个比它大的节点
        Map<Node, Node> rightBigMap = new HashMap<>(length);

        fillBigMap(nodeArray, leftBigMap, rightBigMap);

        Node head = null;
        // 构造二叉树
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
        // 正向遍历，获取当前节点左边第一个比自己大的元素
        for (Node cur : array) {
            while (!stack.isEmpty() && stack.peek().value < cur.value) {
                popStackSetMap(stack, leftBigMap);
            }
            stack.push(cur);
        }
        while (!stack.isEmpty()) {
            popStackSetMap(stack, leftBigMap);
        }

        // 逆向遍历，获取当前节点右边第一个比自己大的元素
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

    /**
     * 栈顶出栈，同时设置第一个比自己大的元素
     */
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
