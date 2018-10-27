/**
 * <p>Title: BiggestBinarySearchTreeTopology</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/10/27</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.binarytree;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>符合搜索二叉树的最大拓扑结构</p>
 *
 * @author wangzi
 */
public class BiggestBinarySearchTreeTopology {
    private static class Node {
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

    private static class Record {
        public int left;
        public int right;

        public Record(int left, int right) {
            this.left = left;
            this.right = right;
        }
    }

    public static int bstTopoOne(Node head) {
        if (head == null) {
            return 0;
        }
        int max = maxTopo(head, head);
        max = Math.max(bstTopoOne(head.left), max);
        max = Math.max(bstTopoOne(head.right), max);
        return max;
    }

    private static int maxTopo(Node head, Node node) {
        if (head != null && node != null && isBSTNode(head, node)) {
            return maxTopo(head, node.left) + maxTopo(head, node.right) + 1;
        }
        return 0;
    }

    private static boolean isBSTNode(Node head, Node node) {
        if (head == null) {
            return false;
        }
        if (head == node) {
            return true;
        }
        return isBSTNode(head.value > node.value ? head.left : head.right, node);
    }

    public static int bstTopoTwo(Node head) {
        Map<Node, Record> map = new HashMap<>();
        return posOrder(head, map);
    }

    private static int posOrder(Node head, Map<Node, Record> map) {
        if (head == null) {
            return 0;
        }
        int leftSize = posOrder(head.left, map);
        int rightSize = posOrder(head.right, map);
        modifyMap(head.left, head.value, map, true);
        modifyMap(head.right, head.value, map, false);
        Record leftRecord = map.get(head.left);
        Record rightRecord = map.get(head.right);
        int leftBst = leftRecord == null ? 0 : leftRecord.left + leftRecord.right + 1;
        int rightBst = rightRecord == null ? 0 : rightRecord.left + rightRecord.right + 1;
        map.put(head, new Record(leftBst, rightBst));
        return Math.max(leftBst + rightBst + 1, Math.max(leftSize, rightSize));
    }

    private static int modifyMap(Node node, int value, Map<Node, Record> map, boolean isLeft) {
        if (node == null || !map.containsKey(node)) {
            return 0;
        }
        Record record = map.get(node);
        // 不满足搜索二叉树，节点移除，返回删除的节点数
        if ((isLeft && node.value > value) || (!isLeft && node.value < value)) {
            map.remove(node);
            return record.left + record.right + 1;
        } else {
            int minus = modifyMap(isLeft ? node.right : node.left, value, map, isLeft);
            if (isLeft) {
                record.right = record.right - minus;
            } else {
                record.left = record.left - minus;
            }
            map.put(node, record);
            return minus;
        }
    }

    public static void main(String[] args) {
        Node head = new Node(6);
        head.left = new Node(1);
        head.left.left = new Node(0);
        head.left.right = new Node(3);
        head.right = new Node(12);
        head.right.left = new Node(10);
        head.right.left.left = new Node(4);
        head.right.left.left.left = new Node(2);
        head.right.left.left.right = new Node(5);
        head.right.left.right = new Node(14);
        head.right.left.right.left = new Node(11);
        head.right.left.right.right = new Node(15);
        head.right.right = new Node(13);
        head.right.right.left = new Node(20);
        head.right.right.right = new Node(16);

        System.out.println(bstTopoOne(head));
        System.out.println(bstTopoTwo(head));
    }
}
