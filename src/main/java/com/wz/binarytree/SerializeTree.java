/**
 * <p>Title: SerializeTree</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/10/21</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.binarytree;

import java.util.LinkedList;
import java.util.Queue;

/**
 * <p>二叉树序列化和反序列化</p>
 *
 * @author wangzi
 */
public class SerializeTree {
    private static final String SYMBOL_NULL = "#";
    private static final String SYMBOL_END = "!";

    private static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    /**
     * 序列化-先序遍历
     */
    public static String serialByPreOrder(Node head) {
        if (head == null) {
            return SYMBOL_NULL + SYMBOL_END;
        }
        String result = head.value + SYMBOL_END;
        result += serialByPreOrder(head.left);
        result += serialByPreOrder(head.right);
        return result;
    }

    /**
     * 反序列化-先序遍历
     */
    public static Node reconstructByPreString(String str) {
        if (str == null || str.trim().length() == 0) {
            return null;
        }
        String[] values = str.split(SYMBOL_END);
        if (values.length == 0) {
            return null;
        }
        Queue<String> queue = new LinkedList<>();
        for (String value : values) {
            queue.offer(value);
        }
        return reconstructPreOrder(queue);
    }

    private static Node reconstructPreOrder(Queue<String> queue) {
        String value = queue.poll();
        if (SYMBOL_NULL.equals(value)) {
            return null;
        }
        Node head = new Node(Integer.valueOf(value));
        head.left = reconstructPreOrder(queue);
        head.right = reconstructPreOrder(queue);
        return head;
    }

    /**
     * 序列化-层次遍历
     */
    public static String serialByLevel(Node head) {
        if (head == null) {
            return SYMBOL_NULL + SYMBOL_END;
        }
        StringBuilder result = new StringBuilder(head.value + SYMBOL_END);
        Queue<Node> queue = new LinkedList<>();
        queue.offer(head);
        while (!queue.isEmpty()) {
            head = queue.poll();
            if (head.left != null) {
                result.append(head.left.value).append(SYMBOL_END);
                queue.offer(head.left);
            } else {
                result.append(SYMBOL_NULL).append(SYMBOL_END);
            }
            if (head.right != null) {
                result.append(head.right.value).append(SYMBOL_END);
                queue.offer(head.right);
            } else {
                result.append(SYMBOL_NULL).append(SYMBOL_END);
            }
        }
        return result.toString();
    }

    /**
     * 反序列化-层次遍历
     */
    public static Node reconstructByLevelString(String str) {
        if (str == null || str.trim().length() == 0) {
            return null;
        }
        String[] values = str.split(SYMBOL_END);
        if (values.length == 0) {
            return null;
        }
        int index = 0;
        Node head = generateNode(values[index++]);
        Queue<Node> queue = new LinkedList<>();
        if (head != null) {
            queue.offer(head);
        }
        Node node = null;
        while (!queue.isEmpty()) {
            node = queue.poll();
            node.left = generateNode(values[index++]);
            node.right = generateNode(values[index++]);
            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
        }
        return head;
    }

    private static Node generateNode(String val) {
        if (SYMBOL_NULL.equals(val)) {
            return null;
        }
        return new Node(Integer.valueOf(val));
    }

    private static void printPreOrder(Node head) {
        if (head == null) {
            return;
        }
        System.out.print(head.value + " ");
        printPreOrder(head.left);
        printPreOrder(head.right);
    }

    public static void main(String[] args) {
        Node head = new Node(1);
        head.left = new Node(2);
        head.right = new Node(3);
        head.left.left = new Node(4);
        head.right.right = new Node(5);

        printPreOrder(head);
        System.out.println();
        String str = serialByPreOrder(head);
        System.out.println(str);
        head = reconstructByPreString(str);
        printPreOrder(head);
        System.out.println();

        str = serialByLevel(head);
        System.out.println(str);
        head = reconstructByLevelString(str);
        printPreOrder(head);
    }
}
