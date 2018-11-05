/**
 * <p>Title: NearestCommonAncestor</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/10/21</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.binarytree;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * <p>二叉树中两个节点最近的公共祖先</p>
 *
 * @author wangzi
 */
public class NearestCommonAncestor {
    private static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    public static Node nearestAncestor(Node head, Node self, Node other) {
        if (head == null || head == self || head == other) {
            return head;
        }

        Node left = nearestAncestor(head.left, self, other);
        Node right = nearestAncestor(head.right, self, other);
        if (left != null && right != null) {
            return head;
        }
        return left != null ? left : right;
    }

    public static class RecordOne {
        private Map<Node, Node> map;

        public RecordOne(Node head) {
            this.map = new HashMap<>();
            if (head != null) {
                map.put(head, null);
            }
            setMap(head);
        }

        private void setMap(Node head) {
            if (head == null) {
                return;
            }
            if (head.left != null) {
                map.put(head.left, head);
            }
            if (head.right != null) {
                map.put(head.right, head);
            }
            setMap(head.left);
            setMap(head.right);
        }

        public Node query(Node self, Node other) {
            Set<Node> path = new HashSet<>();
            while (map.containsKey(self)) {
                path.add(self);
                self = map.get(self);
            }
            while (!path.contains(other)) {
                other = map.get(other);
            }
            return other;
        }
    }

    public static class RecordTwo {
        private Map<Node, Map<Node, Node>> map;

        public RecordTwo(Node head) {
            map = new HashMap<>();
            initMap(head);
            setMap(head);
        }

        private void initMap(Node head) {
            if (head == null) {
                return;
            }

            map.put(head, new HashMap<>());
            initMap(head.left);
            initMap(head.right);
        }

        private void setMap(Node head) {
            if (head == null) {
                return;
            }

            headRecord(head.left, head);
            headRecord(head.right, head);
            subRecord(head);
            setMap(head.left);
            setMap(head.right);
        }

        private void headRecord(Node node, Node head) {
            if (node == null) {
                return;
            }

            map.get(node).put(head, head);
            headRecord(node.left, head);
            headRecord(node.right, head);
        }

        private void subRecord(Node head) {
            if (head == null) {
                return;
            }

            preLeft(head.left, head.right, head);
            subRecord(head.left);
            subRecord(head.right);
        }

        private void preLeft(Node left, Node right, Node head) {
            if (left == null) {
                return;
            }

            preRight(left, right, head);
            preLeft(left.left, right, head);
            preLeft(left.right, right, head);
        }

        private void preRight(Node left, Node right, Node head) {
            if (right == null) {
                return;
            }

            map.get(left).put(right, head);
            preRight(left, right.left, head);
            preRight(left, right.right, head);
        }

        public Node query(Node self, Node other) {
            if (self == other) {
                return self;
            }
            if (map.containsKey(self)) {
                return map.get(self).get(other);
            }
            if (map.containsKey(other)) {
                return map.get(other).get(self);
            }
            return null;
        }
    }

    public static void main(String[] args) {
        Node head = new Node(1);
        head.left = new Node(2);
        head.right = new Node(3);
        head.left.left = new Node(4);
        head.left.right = new Node(5);
        head.right.left = new Node(6);
        head.right.right = new Node(7);
        head.right.right.left = new Node(8);

        Node self = head.left.right;
        Node other = head.right.left;
        System.out.println(nearestAncestor(head, self, other).value);

        RecordOne recordOne = new RecordOne(head);
        System.out.println(recordOne.query(self, other).value);

        RecordTwo recordTwo = new RecordTwo(head);
        System.out.println(recordTwo.query(self, other).value);
    }
}
