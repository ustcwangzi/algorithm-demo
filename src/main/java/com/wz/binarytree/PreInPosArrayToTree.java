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
        head.left = preInArrayToTree(preArray, preStart + 1, preStart + index - inStart,
                inArray, inStart, index - 1, map);
        head.right = preInArrayToTree(preArray, preStart + index - inStart + 1, preEnd,
                inArray, index + 1, inEnd, map);
        return head;
    }

    public static void main(String[] args) {
        int[] preArray = {1, 2, 4, 5, 8, 9, 3, 6, 7};
        int[] inArray = {4, 2, 8, 5, 9, 1, 6, 3, 7};
        int[] posArray = {4, 8, 9, 5, 2, 6, 7, 3, 1};
        System.out.println(preInArrayToTree(preArray, inArray).value);
    }
}
