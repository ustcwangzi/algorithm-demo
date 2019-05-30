package com.wz;

import com.wz.binarytree.PreInPosArrayToTree;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>通过先序和后序数组重建二叉树，除叶节点外，其他所有节点都有左孩子和右孩子</p>
 *
 * @author wangzi
 */
public class PrePosArrayToTreeTest {
    private static class Node {
        public int value;
        public Node left;
        public Node right;

        private Node(int value) {
            this.value = value;
        }
    }

    private static Node solution(int[] preArray, int[] posArray) {
        int length = preArray.length;
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < length; i++) {
            map.put(posArray[i], i);
        }
        return buildTree(preArray, 0, length - 1, posArray, 0, length - 1, map);
    }

    private static Node buildTree(int[] preArray, int preStart, int preEnd, int[] posArray, int posStart, int posEnd,
                                  Map<Integer, Integer> map) {
        Node head = new Node(preArray[preStart]);
        if (preStart == preEnd) {
            return head;
        }
        // 先序数组中当前根结点的下一个节点是左子树的根结点，该节点在后序数组中处于左子树最后的位置，因此以该节点划分左右子树
        int index = map.get(preArray[++preStart]);
        // 左子树，index-posStart 为左子树的节点数
        head.left = buildTree(preArray, preStart, preStart + index - posStart, posArray, posStart, index, map);
        // 右子树
        head.right = buildTree(preArray, preStart + index - posStart + 1, preEnd, posArray, index + 1, posEnd - 1, map);
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
        int[] posArray = {4, 5, 2, 6, 7, 3, 1};
        StringBuilder result1 = new StringBuilder();
        StringBuilder result2 = new StringBuilder();
        preOrder(solution(preArray, posArray), result1);
        PreInPosArrayToTree.preOrder(PreInPosArrayToTree.prePosToTree(preArray, posArray), result2);
        System.out.println(result1.toString().equals(result2.toString()));

        preArray = new int[]{1, 2, 4, 5, 8, 9, 3, 6, 7};
        posArray = new int[]{4, 8, 9, 5, 2, 6, 7, 3, 1};
        result1 = new StringBuilder();
        result2 = new StringBuilder();
        preOrder(solution(preArray, posArray), result1);
        PreInPosArrayToTree.preOrder(PreInPosArrayToTree.prePosToTree(preArray, posArray), result2);
        System.out.println(result1.toString().equals(result2.toString()));
    }

}
