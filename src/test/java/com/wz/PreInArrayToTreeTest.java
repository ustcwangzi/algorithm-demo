package com.wz;

import com.wz.binarytree.PreInPosArrayToTree;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>通过先序和中序数组重建二叉树</p>
 *
 * @author wangzi
 */
public class PreInArrayToTreeTest {
    private static class Node {
        public int value;
        public Node left;
        public Node right;

        private Node(int value) {
            this.value = value;
        }
    }

    private static Node solution(int[] preArray, int[] inArray) {
        int length = preArray.length;
        Map<Integer, Integer> map = new HashMap<>(length);
        for (int i = 0; i < length; i++) {
            map.put(inArray[i], i);
        }
        return buildTree(preArray, 0, length - 1, inArray, 0, length, map);
    }

    private static Node buildTree(int[] preArray, int preStart, int preEnd, int[] inArray, int inStart, int inEnd,
                                  Map<Integer, Integer> map) {
        if (preStart > preEnd) {
            return null;
        }
        Node head = new Node(preArray[preStart]);
        // 以节点在中序遍历中的位置来分割左右子树
        int inIndex = map.get(preArray[preStart]);
        // 左子树，inIndex-inStart 为左子树的节点数
        head.left = buildTree(preArray, preStart + 1, preStart + inIndex - inStart, inArray, inStart, inIndex - 1, map);
        // 右子树
        head.right = buildTree(preArray, preStart + inIndex - inStart + 1, preEnd, inArray, inIndex + 1, inEnd, map);
        return head;
    }

    private static void preOrder(Node head, StringBuilder result) {
        if (head == null) {
            return;
        }
        result.append(head.value).append(" ");
        preOrder(head.left, result);
        preOrder(head.right, result);
    }

    public static void main(String[] args) {
        int[] preArray = {1, 2, 4, 5, 3, 6, 7};
        int[] inArray = {4, 2, 5, 1, 6, 3, 7};
        StringBuilder result1 = new StringBuilder();
        StringBuilder result2 = new StringBuilder();
        preOrder(solution(preArray, inArray), result1);
        PreInPosArrayToTree.preOrder(PreInPosArrayToTree.preInArrayToTree(preArray, inArray), result2);
        System.out.println(result1.toString().equals(result2.toString()));

        preArray = new int[]{1, 2, 4, 5, 8, 9, 3, 6, 7};
        inArray = new int[]{4, 2, 8, 5, 9, 1, 6, 3, 7};
        result1 = new StringBuilder();
        result2 = new StringBuilder();
        preOrder(solution(preArray, inArray), result1);
        PreInPosArrayToTree.preOrder(PreInPosArrayToTree.preInArrayToTree(preArray, inArray), result2);
        System.out.println(result1.toString().equals(result2.toString()));
    }
}
