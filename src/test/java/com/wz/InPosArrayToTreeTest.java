package com.wz;

import com.wz.binarytree.PreInPosArrayToTree;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>通过中序和后序数组重建二叉树</p>
 *
 * @author wangzi
 */
public class InPosArrayToTreeTest {
    private static class Node {
        public int value;
        public Node left;
        public Node right;

        private Node(int value) {
            this.value = value;
        }
    }

    private static Node solution(int[] inArray, int[] posArray) {
        int length = inArray.length;
        Map<Integer, Integer> map = new HashMap<>(length);
        for (int i = 0; i < length; i++) {
            map.put(inArray[i], i);
        }
        return buildTree(inArray, 0, length - 1, posArray, 0, length - 1, map);
    }

    private static Node buildTree(int[] inArray, int inStart, int inEnd, int[] posArray, int posStart, int posEnd,
                                  Map<Integer, Integer> map) {
        if (posStart > posEnd) {
            return null;
        }
        Node head = new Node(posArray[posEnd]);
        int inIndex = map.get(posArray[posEnd]);
        head.left = buildTree(inArray, inStart, inIndex - 1, posArray, posStart, posStart + inIndex - inStart - 1, map);
        head.right = buildTree(inArray, inIndex + 1, inEnd, posArray, posStart + inIndex - inStart, posEnd - 1, map);
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
        int[] inArray = {4, 2, 5, 1, 6, 3, 7};
        int[] posArray = {4, 5, 2, 6, 7, 3, 1};
        StringBuilder result1 = new StringBuilder();
        StringBuilder result2 = new StringBuilder();
        preOrder(solution(inArray, posArray), result1);
        PreInPosArrayToTree.preOrder(PreInPosArrayToTree.inPosToTree(inArray, posArray), result2);
        System.out.println(result1.toString().equals(result2.toString()));

        inArray = new int[]{4, 2, 8, 5, 9, 1, 6, 3, 7};
        posArray = new int[]{4, 8, 9, 5, 2, 6, 7, 3, 1};
        result1 = new StringBuilder();
        result2 = new StringBuilder();
        preOrder(solution(inArray, posArray), result1);
        PreInPosArrayToTree.preOrder(PreInPosArrayToTree.inPosToTree(inArray, posArray), result2);
        System.out.println(result1.toString().equals(result2.toString()));
    }
}
