/**
 * <p>Title: LongestPathSum</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/10/27</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.binarytree;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>累加和为指定值的最长路径长度</p>
 * <p>
 *     二叉树节点值类型为整形，求累加和为sum的最长路径长度，不用考虑相加溢出的情况
 *     1、头节点head、指定值sum均已知，变量maxLen用以记录累加和为sum的最长路径长度
 *     2、用sumMap记录从head开始的一条路径上的累加和出现的情况，sumMap的key为累加和，value为该累加和在路径上最早出现的层数
 *     3、先序遍历二叉树，遍历到cur时，从head到cur父节点的累加和为preSum，cur所在层数为level，cur.value+preSum为curSum
 *        若sumMap中已包含curSum则不更新，否则将(curSum,level)放入sumMap，遇到累加和为sum时，更新maxLen，然后遍历cur的左右子树
 *        在sumMap中查询curSum出现的层数(即value)，若value等于level，说明curSum这个累加和是在本次遍历到cur时加上去的，
 *        同时该条记录不满足指定累加和的判断条件，因此删除该条记录
 * </p>
 *
 * @author wangzi
 */
public class LongestPathSum {
    private static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    public static int getMaxLength(Node head, int sum) {
        Map<Integer, Integer> sumMap = new HashMap<>();
        sumMap.put(0, 0);
        return preOrder(head, sum, 0, 1, 0, sumMap);
    }

    private static int preOrder(Node head, int sum, int preSum, int level, int maxLen, Map<Integer, Integer> sumMap) {
        if (head == null) {
            return maxLen;
        }
        int curSum = preSum + head.value;
        if (!sumMap.containsKey(curSum)) {
            sumMap.put(curSum, level);
        }
        // 找到累加和为指定值的节点
        if (sumMap.containsKey(curSum - sum)) {
            maxLen = Math.max(level - sumMap.get(curSum - sum), maxLen);
        }
        maxLen = preOrder(head.left, sum, curSum, level + 1, maxLen, sumMap);
        maxLen = preOrder(head.right, sum, curSum, level + 1, maxLen, sumMap);
        // curSum出现的层数等于level，说明curSum是本次遍历到cur时加上去的
        // 并且在前面已做过比较不满足条件，把该条记录删除
        if (level == sumMap.get(curSum)) {
            sumMap.remove(curSum);
        }
        return maxLen;
    }

    public static void main(String[] args) {
        Node head = new Node(-3);
        head.left = new Node(3);
        head.right = new Node(-9);
        head.left.left = new Node(1);
        head.left.right = new Node(0);
        head.left.right.left = new Node(1);
        head.left.right.right = new Node(6);
        head.right.left = new Node(2);
        head.right.right = new Node(1);

        System.out.println(getMaxLength(head, 6));
        System.out.println(getMaxLength(head, -9));
    }
}
