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
 * <p>先序、中序和后序数组两两结合重新二叉树</p>
 * <p>
 *     先序和中序数组重建二叉树：
 *          先序数组中最左边的元素就是树的头节点，记为h，然后在中序数组中找到h，假设位置i，
 *          那么在中序数组中，i左边的数组就是头节点左子树的中序数组，假设长度为l，则左子树的先序数组就是先序数组中h往右长度也为l的数组
 *          用左子树的先序和中序数组，递归建立左子树
 *          i右边的数组就是头节点右子树的中序数组，假设长度为r，则先序数组中右侧等长部分就是头节点右子树的先序数组
 *          用右子树的先序和中序数组，递归建立右子树
 *     中序和后序数组重建二叉树：
 *          与先序和中序数组重建二叉树过程类似，只是在先序和中序数组重建二叉树中，使用先序数组最左边的元素来对中序数组进行划分，
 *          而在中序和后序数组重建二叉树中，使用的是后序数组最右边的元素来对中序数组进行划分
 *     先序和后序数组重建二叉树：
 *          大多数情况下，根据先序和后序数组无法还原二叉树，因为不同结构的树可能右同样的先序和后序数组
 *          比如，一棵树头节点为1，左孩子为2，右孩子为null，另一棵树头节点为1，左孩子为null，右孩子为2，先序都为[1,2]，后序都为[2,1]
 *          只有满足这样条件的树才能被先序和后序数组重建：除叶节点外，其他所有节点都有左孩子和右孩子
 *          后序数组中最右边的元素就是树的头节点，该元素等于先序数组中最左边的元素，先序数组中最左边元素的下一个元素就是左子树的开始节点
 *          即左子树的头节点，该左子树在后序数组中最右边的位置肯定为头节点，因此找到该元素在后序数组中的位置，依此来分割左右子树
 *     eg. 先序数组为 {1, 2, 4, 5, 8, 9, 3, 6, 7}
 *         中序数组为 {4, 2, 8, 5, 9, 1, 6, 3, 7}
 *         后序数组为 {4, 8, 9, 5, 2, 6, 7, 3, 1}
 *         先序和中序重建二叉树：头节点为1，左子树节点为{4, 2, 8, 5, 9}，右子树节点为{6, 3, 7} ...
 *         中序和后序重建二叉树：头节点为1，左子树节点为{4, 2, 8, 5, 9}，右子树节点为{6, 3, 7} ...
 *         先序和后序重建二叉树：头节点为1，左子树头节点为2，左子树节点为{4, 8, 9, 5, 2}，右子树节点为{6, 7, 3} ...
 * </p>
 * <p>时间复杂度为O(N)</p>
 *
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

        // 节点在后序遍历中的位置，以此分割左右子树，preArray[++preStart] 代表左子树的开始节点
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
