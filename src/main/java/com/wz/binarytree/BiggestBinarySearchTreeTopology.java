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
 * <p>
 *     已知所有节点值都不同，返回其中符合搜索二叉树的最大拓扑结构的大小
 *     方案一：
 *          考查head的孩子节点，根据孩子节点的值从head开始按照二叉搜索的方式移动，如果最后能够移动到同一个孩子节点上
 *          就说明这个孩子节点可以作为这个拓扑结构的一部分，并继续考查这个孩子节点的孩子节点
 *          eg. 根节点为12，左右孩子为10、13，10的左右孩子为4、14，13的左右孩子为20、16
 *          1、考查节点12，考查队列{10,13}
 *          2、考查节点10，往12左侧找，能够找到，10加入拓扑结构，同时10的孩子加入考查队列{13,4,14}
 *          3、考查节点13，往12右侧找，能够找到，13加入拓扑结构，同时13的孩子加入考查队列{4,14,20,16}
 *          4、考查节点4，往12左侧找，往10左侧找，能够找到，4加入拓扑结构，4无孩子，考查队列{14,20,16}
 *          5、考查节点14，往12右侧找，不可能找到，14不能加入拓扑结构，考查队列{20,16}
 *          6、考查节点20，往12右侧找，往13右侧找，找不到，20不能加入拓扑结构，考查队列{16}
 *          6、和上述过程类似，16加入拓扑结构
 *     方案二：
 *          基于拓扑贡献记录，来找到最大的拓扑结构
 *          每一个节点有两个值(left,right)，left代表的是该节点的左子树可以为该节点为头的拓扑结构贡献的节点个数，
 *          right代表的是该节点的右子树可以为该节点为头的拓扑结构贡献的节点个数
 *          eg. 根节点为12，左右孩子为10、13，10的左右孩子为4、14，13的左右孩子为20、16
 *          节点4、14、20、16能够贡献的节点为均为(0,0)，节点10为(1,0)，节点13为(0,1)，节点12为(2,2)
 *     方案一时间复杂度为O(N*N)，方案二时间复杂度最好为O(N)、最差为O(N*logN)
 * </p>
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
