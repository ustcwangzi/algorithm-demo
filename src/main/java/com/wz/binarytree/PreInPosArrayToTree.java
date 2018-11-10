/**
 * <p>Title: PreInPosArrayToTree</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/10/21</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.binarytree;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wangzi
 */
public class PreInPosArrayToTree {
    private static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    /**
     * 先序和中序数组重建二叉树
     */
    public static Node preInArrayToTree(int[] preArray, int[] inArray) {
        if (preArray == null || inArray == null) {
            return null;
        }

        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < inArray.length; i++) {
            map.put(inArray[i], i);
        }
        return preInArrayToTree(preArray, 0, preArray.length - 1, inArray, 0, inArray.length - 1, map);
    }

    private static Node preInArrayToTree(int[] preArray, int preStart, int preEnd, int[] inArray, int inStart, int inEnd,
                                         Map<Integer, Integer> map) {
        if (preStart > preEnd) {
            return null;
        }

        Node head = new Node(preArray[preStart]);
        // 节点在中序遍历中的位置，以此分割左右子树
        int index = map.get(preArray[preStart]);
        // index-inStart 代表分割分割后左子树节点数
        head.left = preInArrayToTree(preArray, preStart + 1, preStart + index - inStart,
                inArray, inStart, index - 1, map);
        head.right = preInArrayToTree(preArray, preStart + index - inStart + 1, preEnd,
                inArray, index + 1, inEnd, map);
        return head;
    }

    /**
     * 中序和后序数组重建二叉树
     */
    public static Node inPosToTree(int[] inArray, int[] posArray) {
        if (inArray == null || posArray == null) {
            return null;
        }

        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < inArray.length; i++) {
            map.put(inArray[i], i);
        }
        return inPosToTree(inArray, 0, inArray.length - 1, posArray, 0, posArray.length - 1, map);
    }

    private static Node inPosToTree(int[] inArray, int inStart, int inEnd, int[] posArray, int posStart, int posEnd,
                                    Map<Integer, Integer> map) {
        if (posStart > posEnd) {
            return null;
        }

        Node head = new Node(posArray[posEnd]);
        // 节点在中序遍历中的位置，以此分割左右子树
        int index = map.get(posArray[posEnd]);
        // index-inStart 代表分割分割后左子树节点数
        head.left = inPosToTree(inArray, inStart, index - 1, posArray, posStart, posStart + index - inStart - 1, map);
        head.right = inPosToTree(inArray, index + 1, inEnd, posArray, posStart + index - inStart, posEnd - 1, map);
        return head;
    }

    /**
     * 先序和后序数组重建二叉树
     */
    public static Node prePosToTree(int[] preArray, int[] posArray) {
        if (preArray == null || posArray == null) {
            return null;
        }

        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < posArray.length; i++) {
            map.put(posArray[i], i);
        }
        return prePosToTree(preArray, 0, preArray.length - 1, posArray, 0, posArray.length - 1, map);
    }

    private static Node prePosToTree(int[] preArray, int preStart, int preEnd, int[] posArray, int posStart, int posEnd,
                                     Map<Integer, Integer> map) {
        Node head = new Node(posArray[posEnd--]);
        if (preStart == preEnd) {
            return head;
        }

        // 节点在后序遍历中的位置，以此分割左右子树　
        int index = map.get(preArray[++preStart]);
        // index - posStart 代表分割分割后左子树节点数
        head.left = prePosToTree(preArray, preStart, preStart + index - posStart, posArray, posStart, index, map);
        head.right = prePosToTree(preArray, preStart + index - posStart + 1, preEnd, posArray, index + 1, posEnd, map);
        return head;
    }

    private static void printPreOrder(Node head) {
        if (head == null) {
            return;
        }
        System.out.print(head.value + " ");
        printPreOrder(head.left);
        printPreOrder(head.right);
    }

    public static void main(String[] args) {
        int[] preArray = {1, 2, 4, 5, 8, 9, 3, 6, 7};
        int[] inArray = {4, 2, 8, 5, 9, 1, 6, 3, 7};
        int[] posArray = {4, 8, 9, 5, 2, 6, 7, 3, 1};
        printPreOrder(preInArrayToTree(preArray, inArray));
        System.out.println();
        printPreOrder(inPosToTree(inArray, posArray));
        System.out.println();
        printPreOrder(prePosToTree(preArray, posArray));
    }
}
