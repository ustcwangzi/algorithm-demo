/**
 * <p>Title: UniqueBinarySearchTree</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/11/10</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.binarytree;

import java.util.LinkedList;
import java.util.List;

/**
 * <p>统计和生成所有不同的二叉树</p>
 * <p>
 *     给定一个整数N，若N<1则代表空树，否则代表中序遍历为{1,2,3,...,N}，计算所有可能的个数及二叉树结构
 *     如果中序遍历有序切无重复值，则二叉树必为搜索二叉树，假设num(a)代表a个节点的搜索二叉树有多少种结构
 *     序列为{1, ..., i, ..., N}
 *     以1为头节点时，不存在左子树，右子树有N-1个节点，所以有num(N-1)中结构
 *     以i为头节点时，左子树有num(i-1)中结构，右子树有num(N-i)中结构，总有num(i-1)*num(N-i)种结构
 *     以N为头节点时，不存在右子树，左子树有num(N-1)结构
 *     把从1到N分别作为头节点时，所有可能的结构加起来就是最终的结构个数，时间复杂度为O(N*N)
 *
 *     以i为头节点的所有二叉树结构按如下步骤生成：
 *     1、用{1...i-1}递归生成左子树的所有结构，所有结构的头节点保存在leftNodes中
 *     2、用{i+1...N}递归生成右子树的所有结构，所有结构的头节点保存在rightNodes中
 *     3、以i为头节点情况下，leftNodes中每一种结构都可以与rightNodes中每一种结构构成单独的结构，且与其他结构不同
 *        为保证所有结构之间不相互交叉，所以对每一种结构都复制出新的树，保存在result中。
 * </p>
 *
 * @author wangzi
 */
public class UniqueBinarySearchTree {
    private static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    public static int numTrees(int n) {
        if (n < 2) {
            return 1;
        }
        int[] num = new int[n + 1];
        num[0] = 1;
        for (int i = 1; i < n + 1; i++) {
            for (int j = 1; j < i + 1; j++) {
                num[i] += num[j - 1] * num[i - j];
            }
        }
        return num[n];
    }

    public static List<Node> generateTrees(int n) {
        return generateTrees(1, n);
    }

    private static List<Node> generateTrees(int start, int end) {
        List<Node> result = new LinkedList<>();
        if (start > end) {
            result.add(null);
        }

        Node head;
        for (int i = start; i < end + 1; i++) {
            head = new Node(i);
            List<Node> leftNodes = generateTrees(start, i - 1);
            List<Node> rightNodes = generateTrees(i + 1, end);
            for (Node left : leftNodes) {
                for (Node right : rightNodes) {
                    head.left = left;
                    head.right = right;
                    result.add(cloneTree(head));
                }
            }
        }
        return result;
    }

    private static Node cloneTree(Node head) {
        if (head == null) {
            return null;
        }
        Node result = new Node(head.value);
        result.left = cloneTree(head.left);
        result.right = cloneTree(head.right);
        return result;
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
        System.out.println(numTrees(4));
        List<Node> result = generateTrees(4);
        result.forEach(node -> {
            printPreOrder(node);
            System.out.println();
        });
    }
}
