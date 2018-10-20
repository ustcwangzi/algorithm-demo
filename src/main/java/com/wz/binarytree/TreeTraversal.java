/**
 * <p>Title: TreeTraversal</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/10/20</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.binarytree;

import java.util.Stack;

/**
 * <p>二叉树遍历</p>
 * <p>
 *     递归方式实现三种遍历较为简单，不再赘述，非递归方式，主要是采用了栈来实现
 *     非递归先序遍历：
 *          1、将头节点head压入栈stack
 *          2、从stack弹出栈顶元素cur，再将cur的右孩子压栈、左孩子压栈
 *          3、重复步骤二，直到栈空结束
 *     非递归中序遍历：
 *          1、令cur=head
 *          2、把cur压入栈stack，并令cur=cur.left
 *          3、不断重复步骤二，直到cur为null，从stack弹出一个节点node，令cur=node.right，继续重复步骤二
 *          4、当stack为空并且cur为null时，过程结束
 *     非递归后序遍历方案一：
 *          1、将头节点head压入栈stack1
 *          2、从stack1弹出栈顶元素cur，再将cur的左孩子压栈、右孩子压栈，并将cur压入栈stack2
 *          3、不断重复步骤二，直到stack1为空
 *          4、从stack2依次弹出元素
 *     非递归后序遍历方案二：
 *          1、将头节点head压入栈stack，令cur代表最近一次弹出的节点，令top代表当前栈顶节点
 *          2、每次令top等于当前的栈顶节点，但是不弹出，分三种情况：
 *          2.1、若top的左孩子不空，且cur不等于top的左孩子也不等于top的右孩子，说明top的左子树还未处理过，将top的左孩子入栈
 *          2.2、若2.1不成立，且top的右孩子不空，且cur不等于top的右孩子，说明top的右子树还未处理过，将top的右孩子入栈
 *          2.3、若2.1和2.2都不成立，说明top的左右子树均处理过，弹出top，然后令cur=top
 *          3、不断重复步骤二，直到栈空结束
 * </p>
 *
 * @author wangzi
 */
public class TreeTraversal {
    private static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    /**
     * 先序遍历-递归
     */
    public static void preOrderRecursive(Node head) {
        if (head == null) {
            return;
        }
        System.out.print(head.value + " ");
        preOrderRecursive(head.left);
        preOrderRecursive(head.right);
    }

    /**
     * 中序遍历-递归
     */
    public static void inOrderRecursive(Node head) {
        if (head == null) {
            return;
        }
        inOrderRecursive(head.left);
        System.out.print(head.value + " ");
        inOrderRecursive(head.right);
    }

    /**
     * 后序遍历-递归
     */
    public static void posOrderRecursive(Node head) {
        if (head == null) {
            return;
        }
        posOrderRecursive(head.left);
        posOrderRecursive(head.right);
        System.out.print(head.value + " ");
    }

    /**
     * 先序遍历-非递归
     */
    public static void preOrderUnRecursive(Node head) {
        if (head == null) {
            return;
        }
        Stack<Node> stack = new Stack<>();
        stack.push(head);
        while (!stack.isEmpty()) {
            head = stack.pop();
            System.out.print(head.value + " ");
            // 节点弹出时，先压入右孩子，再压入左孩子
            if (head.right != null) {
                stack.push(head.right);
            }
            if (head.left != null) {
                stack.push(head.left);
            }
        }
    }

    /**
     * 中序遍历-非递归
     */
    public static void inOrderUnRecursive(Node head) {
        if (head == null) {
            return;
        }
        Stack<Node> stack = new Stack<>();
        while (!stack.isEmpty() || head != null) {
            if (head != null) {
                // 不停的压入左孩子
                stack.push(head);
                head = head.left;
            } else {
                head = stack.pop();
                System.out.print(head.value + " ");
                // 节点弹出时，压入右孩子
                head = head.right;
            }
        }
    }

    /**
     * 后序遍历-非递归，使用两个栈
     */
    public static void posOrderUnRecursiveOne(Node head) {
        if (head == null) {
            return;
        }
        Stack<Node> stack1 = new Stack<>();
        Stack<Node> stack2 = new Stack<>();
        stack1.push(head);
        while (!stack1.isEmpty()) {
            head = stack1.pop();
            // 弹出的节点全部压入stack2
            stack2.push(head);
            // 节点弹出时，先压入左孩子，再压入右孩子
            if (head.left != null) {
                stack1.push(head.left);
            }
            if (head.right != null) {
                stack1.push(head.right);
            }
        }
        while (!stack2.isEmpty()) {
            System.out.print(stack2.pop().value + " ");
        }
    }

    /**
     * 后序遍历-非递归，使用一个栈
     */
    public static void posOrderUnRecursiveTwo(Node head) {
        if (head == null) {
            return;
        }
        Stack<Node> stack = new Stack<>();
        stack.push(head);
        Node top = null;
        while (!stack.isEmpty()) {
            // head代表最近一次弹出的节点，top代表当前栈顶节点
            top = stack.peek();
            if (top.left != null && head != top.left && head != top.right) {
                // 说明左子树还未处理过
                stack.push(top.left);
            } else if (top.right != null && head != top.right) {
                // 说明右子树还未处理过
                stack.push(top.right);
            } else {
                // 说明左右子树均处理完毕
                System.out.print(stack.pop().value + " ");
                head = top;
            }
        }
    }

    public static void main(String[] args) {
        Node head = new Node(5);
        head.left = new Node(3);
        head.right = new Node(8);
        head.left.left = new Node(2);
        head.left.right = new Node(4);
        head.left.left.left = new Node(1);
        head.right.left = new Node(7);
        head.right.left.left = new Node(6);
        head.right.right = new Node(10);
        head.right.right.left = new Node(9);
        head.right.right.right = new Node(11);

        System.out.println("==============recursive==============");
        System.out.print("pre-order: ");
        preOrderRecursive(head);
        System.out.println();
        System.out.print("in-order: ");
        inOrderRecursive(head);
        System.out.println();
        System.out.print("pos-order: ");
        posOrderRecursive(head);
        System.out.println();

        System.out.println("=============unRecursive=============");
        System.out.print("pre-order: ");
        preOrderUnRecursive(head);
        System.out.println();
        System.out.print("in-order: ");
        inOrderUnRecursive(head);
        System.out.println();
        System.out.print("pos-order: ");
        posOrderUnRecursiveOne(head);
        System.out.println();
        System.out.print("pos-order: ");
        posOrderUnRecursiveTwo(head);
    }
}
