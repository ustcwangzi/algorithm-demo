package com.wz;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>二叉树节点值类型为整形，求累加和为target的最长路径长度</p>
 *
 * @author wangzi
 */
public class LongestPathSumTest {
    private static class Node {
        public int value;
        public Node left;
        public Node right;

        private Node(int value) {
            this.value = value;
        }
    }

    private static int solution(Node head, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, 0);
        return preOrder(head, target, 0, 1, 0, map);
    }

    /**
     * 整体采用先序遍历
     * maxLen记录遍历到当前位置时累加和为target的最长路径长度
     * map记录累加和key在遍历过程中最早出现的层数
     * 遍历到cur时，从head到cur父节点的累加和为preSum，cur所在层数为level
     * 遇到累加和为target时，更新maxLen，然后遍历cur的左右子树
     */
    private static int preOrder(Node head, int target, int preSum, int level, int maxLen, Map<Integer, Integer> map) {
        if (head == null) {
            return maxLen;
        }
        int curSum = preSum + head.value;
        if (!map.containsKey(curSum)) {
            map.put(curSum, level);
        }
        // 累加和为target，更新最长路径
        if (map.containsKey(curSum - target)) {
            maxLen = Math.max(level - map.get(curSum - target), maxLen);
        }
        maxLen = preOrder(head.left, target, curSum, level + 1, maxLen, map);
        maxLen = preOrder(head.right, target, curSum, level + 1, maxLen, map);
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

        for (int i = -9; i < 6; i++) {
            System.out.print(solution(head, i) + " ");
        }
    }
}
