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
 * <p>
 *     方案一：
 *          后序遍历二叉树，假设当前遍历到节点cur，因为是后序遍历，所以先处理cur的两个子树
 *          假设处理左子树时返回left，处理右子树时返回right，则：
 *          1、如果cur等于null或self或other，则返回cur
 *          2、如果left和right都为空，说明cur的整颗子树没有发现self或other，返回null
 *          3、如果left和right都不为空，说明左子树和右子树都发现过self或other，说明self和other向上过程中首次在cur相遇，返回cur
 *          4、如果left和right只有一个不为空，记为node，则node要么为self或other中的一个，要么已经是self和other的公共祖先，返回node
 *     方案二：
 *          建立二叉树中每个节点对应的父节点信息，保存在Map中
 *          查找时，找到self所有的父节点Set，然后other往上移动，移动过程中发现某个节点在Set这中，这个节点就是self和other的公共祖先
 *          建立Map时间复杂度为O(N)，空间复杂度为O(N)，查询操作的时间复杂度为O(h)，其中h为二叉树高度
 *     方案三：
 *          直接建立任意两个节点之间的最近公共祖先，对二叉树中的每颗子树都进行以下步骤
 *          假设子树的头街道为h，h所有的子节点和h节点的最近公共祖先都是h，h左子树的每个节点和h右子树的每个节点最近公共祖先都是h
 *          建立Map时间复杂度为O(N*N)，空间复杂度为O(N*N)，查询操作的时间复杂度为O(1)
 * </p>
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
            // self所有的父节点
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

        /**
         * 对每个节点初始化空的Map
         */
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

        /**
         * 头节点所有的子节点和头节点的最近公共祖先都是头节点
         */
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

        /**
         * 头节点左子树所有节点和头节点右子树所有节点的最近公共祖先都是头节点
         */
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
