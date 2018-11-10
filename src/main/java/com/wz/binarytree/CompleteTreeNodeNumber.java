/**
 * <p>Title: CompleteTreeNodeNumber</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/11/10</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.binarytree;

/**
 * <p>统计完全二叉树的节点数</p>
 * <p>
 *     遍历整个树可以之间求的所有节点数，但不是最优解法，此处不再赘述
 *     具体过程如下：
 *     1、如果head==null，说明是空树，直接返回0
 *     2、如果不是空树，就求树的高度，方法是找到树的最左节点能到哪一层，高度记为h
 *     3、node为当前节点，l为node所在层数，h为整棵树的层数始终不变，
 *        找到node右子树的最左节点，如果能到达最后一层，说明node的整棵左子树都是满二叉树，且层数为h-1
 *        一棵层数为h-1的满二叉树，其节点数为2^(h-1) - 1，加上node本身，节点数为2^(h-1)，递归求右子树节点数，加起来即可
 *        如果node右子树的最左节点么没有到达最后一层，说明node整棵右子树都是满二叉树，且层数为h-l-1，
 *        一棵层数为h-l-1的满二叉树，其节点数为2^(h-l-1) - 1，加上node本身，节点数为2^(h-l-1)，递归求左子树节点数，加起来即可
 * </p>
 * <p>时间复杂度为O(h*h)，其中h为二叉树的高度</p>
 *
 * @author wangzi
 */
public class CompleteTreeNodeNumber {
    private static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    public static int nodeNum(Node head) {
        if (head == null) {
            return 0;
        }
        return search(head, 1, mostLeftLevel(head, 1));
    }

    private static int search(Node node, int level, int height) {
        if (level == height) {
            return 1;
        }

        if (mostLeftLevel(node.right, level + 1) == height) {
            return (1 << (height - level)) + search(node.right, level + 1, height);
        } else {
            return (1 << (height - level - 1)) + search(node.left, level + 1, height);
        }
    }

    /**
     * 以node为头节点的树的高度
     */
    private static int mostLeftLevel(Node node, int level) {
        while (node != null) {
            level++;
            node = node.left;
        }
        return level - 1;
    }

    public static void main(String[] args) {
        Node head = new Node(1);
        head.left = new Node(2);
        head.right = new Node(3);
        head.left.left = new Node(4);
        head.left.right = new Node(5);
        head.right.left = new Node(6);

        System.out.println(nodeNum(head));
    }
}
