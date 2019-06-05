/**
 * <p>Title: IsBinarySearchTree</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/11/3</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.binarytree;

/**
 * <p>判断二叉树是否为搜索二叉树</p>
 * <p>
 *     对二叉树进行中序遍历，使用Morris算法，@see com.wz.binarytree.MorrisTraversal
 *     搜索二叉树中序遍历结果是依次递增的，如果遍历中出现降序，说明不是搜索二叉树
 *     出现降序时不要立即返回false，因为Morris算法分为调整和恢复两个阶段，需要恢复树的结构
 * </p>
 *
 * @author wangzi
 */
public class IsBinarySearchTree {
    private static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    public static boolean isBST(Node head) {
        if (head == null) {
            return true;
        }

        boolean result = true;
        Node pre = null, cur1 = head, cur2 = null;
        while (cur1 != null) {
            cur2 = cur1.left;
            if (cur2 != null) {
                while (cur2.right != null && cur2.right != cur1) {
                    cur2 = cur2.right;
                }
                // cur1左子树中最右节点的right指针指向cur1
                if (cur2.right == null) {
                    cur2.right = cur1;
                    cur1 = cur1.left;
                    continue;
                } else {
                    // 恢复right指针
                    cur2.right = null;
                }
            }
            // 搜索二叉树中序遍历结果是依次递增的，出现降序时说明不是搜索二叉树
            if (pre != null && pre.value > cur1.value) {
                result = false;
            }
            pre = cur1;
            cur1 = cur1.right;
        }
        return result;
    }

    public static void main(String[] args) {
        Node head = new Node(4);
        System.out.println(isBST(head));

        head.left = new Node(2);
        System.out.println(isBST(head));

        head.right = new Node(6);
        System.out.println(isBST(head));

        head.left.left = new Node(1);
        System.out.println(isBST(head));

        head.left.right = new Node(3);
        System.out.println(isBST(head));

        head.right.left = new Node(5);
        System.out.println(isBST(head));
    }
}
