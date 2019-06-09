/**
 * <p>Title: RecoverBinarySearchTree</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/10/28</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.binarytree;

import java.util.Stack;

/**
 * <p>调整搜索二叉树中的两个错误节点</p>
 * <p>
 *     一棵二叉树所有节点值都不同，原本是搜索二叉树，但是其中有两个节点调换了位置，要求找到这两个错误节点并恢复
 *     找到错误节点：
 *          对所有节点值都不同的搜索二叉树进行中序遍历，得到的结果一定是升序，若有两个节点位置错误，会出现降序的情况
 *          如果中序遍历时出现了两次降序，则第一个错误节点是第一次出现降序时较大的节点，第二个错误节点是第二次出现降序时较小的节点
 *          eg. 原顺序为{1,2,3,4,5,6}，因位置错误出现了{1,5,3,4,2,6}，
 *              第一次降序为5->3，错误节点是5，第二次降序为4->2，错误节点是2
 *          如果中序遍历时只出现了一次降序，则第一个错误节点是本次降序时较大的节点，第二个错误节点是本次降序时较小的节点
 *          eg. 原顺序为{1,2,3,4,5,6}，因位置错误出现了{1,3,2,4,5,6}，
 *              只有一次降序3->2，第一个错误节点是3，第二个错误节点是2
 *     恢复搜索二叉树：
 *          找到两个错误节点和各自的父节点
 *          第一个错误节点记为err1，父节点为err1Parent，左孩子为err1Left，右孩子为err1Right
 *          第二个错误节点记为err2，父节点为err2Parent，左孩子为err2Left，右孩子为err2Right
 *          注意：err2中序遍历时出现在err1后面，整理后，会出现14种情况：
 *           1、err1是头节点，err1是err2的父节点，此时err2只可能是err1的右孩子
 *           2、err1是头节点，err1不是err2的父节点，err2是err2Parent的左孩子
 *           3、err1是头节点，err1不是err2的父节点，err2是err2Parent的右孩子
 *          -------------------------------------------------------------
 *           4、err2是头节点，err2是err1的父节点，此时err1只可能是err2的左孩子
 *           5、err2是头节点，err2不是err1的父节点，err1是err1Parent的左孩子
 *           6、err2是头节点，err2不是err1的父节点，err1是err1Parent的右孩子
 *          -------------------------------------------------------------
 *           7、err1和err2都不是头节点，err1是err2的父节点，此时err2只可能是err1的右孩子，err1是err1Parent的左孩子
 *           8、err1和err2都不是头节点，err1是err2的父节点，此时err2只可能是err1的右孩子，err1是err1Parent的右孩子
 *           9、err1和err2都不是头节点，err2是err1的父节点，此时err1只可能是err2的左孩子，err2是err2Parent的左孩子
 *          10、err1和err2都不是头节点，err2是err1的父节点，此时err1只可能是err2的左孩子，err2是err2Parent的右孩子
 *          11、err1和err2都不是头节点，并且都不是对方的父节点，err1是err1Parent的左孩子，err2是err2Parent的左孩子
 *          12、err1和err2都不是头节点，并且都不是对方的父节点，err1是err1Parent的左孩子，err2是err2Parent的右孩子
 *          13、err1和err2都不是头节点，并且都不是对方的父节点，err1是err1Parent的右孩子，err2是err2Parent的左孩子
 *          14、err1和err2都不是头节点，并且都不是对方的父节点，err1是err1Parent的右孩子，err2是err2Parent的右孩子
 *          当情况1至情况3发生时，二叉树新的头节点为err2，当情况4至情况6发生时，二叉树新的头节点为err1，其他情况二叉树头节点不变
 * </p>
 *
 * @author wangzi
 */
public class RecoverBinarySearchTree {
    private static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    public static Node recoverTree(Node head) {
        Node[] errs = getTwoErrNodes(head);
        Node[] parents = getTwoErrParents(head, errs[0], errs[1]);
        Node err1 = errs[0], err1Parent = parents[0], err1Left = err1.left, err1Right = err1.right;
        Node err2 = errs[1], err2Parent = parents[1], err2Left = err2.left, err2Right = err2.right;
        if (err1 == head) {
            if (err1 == err2Parent) {
                // case1
                err1.left = err2Left;
                err1.right = err2Right;
                err2.right = err1;
                err2.left = err1Left;
            } else if (err2Parent.left == err2) {
                // case2
                err2Parent.left = err1;
                err2.left = err1Left;
                err2.right = err1Right;
                err1.left = err2Left;
                err1.right = err2Right;
            } else {
                // case3
                err2Parent.right = err1;
                err2.left = err1Left;
                err2.right = err1Right;
                err1.left = err2Left;
                err1.right = err2Right;
            }
            head = err2;
        } else if (err2 == head) {
            if (err2 == err1Parent) {
                // case4
                err2.left = err1Left;
                err2.right = err1Right;
                err1.left = err2;
                err1.right = err2Right;
            } else if (err1Parent.left == err1) {
                // case5
                err1Parent.left = err2;
                err1.left = err2Left;
                err1.right = err2Right;
                err2.left = err1Left;
                err2.right = err1Right;
            } else {
                // case6
                err1Parent.right = err2;
                err1.left = err2Left;
                err1.right = err2Right;
                err2.left = err1Left;
                err2.right = err1Right;
            }
            head = err1;
        } else {
            if (err1 == err2Parent) {
                if (err1Parent.left == err1) {
                    // case7
                    err1Parent.left = err2;
                    err1.left = err2Left;
                    err1.right = err2Right;
                    err2.left = err1Left;
                    err2.right = err1;
                } else {
                    // case8
                    err1Parent.right = err2;
                    err1.left = err2Left;
                    err1.right = err2Right;
                    err2.left = err1Left;
                    err2.right = err1;
                }
            } else if (err2 == err1Parent) {
                if (err2Parent.left == err2) {
                    // case9
                    err2Parent.left = err1;
                    err2.left = err1Left;
                    err2.right = err1Right;
                    err1.left = err2;
                    err1.right = err2Right;
                } else {
                    // case10
                    err2Parent.right = err1;
                    err2.left = err1Left;
                    err2.right = err1Right;
                    err1.left = err2;
                    err1.right = err2Right;
                }
            } else {
                if (err1Parent.left == err1) {
                    if (err2Parent.left == err2) {
                        // case11
                        err1.left = err2Left;
                        err1.right = err2Right;
                        err2.left = err1Left;
                        err2.right = err1Right;
                        err1Parent.left = err2;
                        err2Parent.left = err1;
                    } else {
                        // case12
                        err1.left = err2Left;
                        err1.right = err2Right;
                        err2.left = err1Left;
                        err2.right = err1Right;
                        err1Parent.left = err2;
                        err2Parent.right = err1;
                    }
                } else {
                    if (err2Parent.left == err2) {
                        // case13
                        err1.left = err2Left;
                        err1.right = err2Right;
                        err2.left = err1Left;
                        err2.right = err1Right;
                        err1Parent.right = err2;
                        err2Parent.left = err1;
                    } else {
                        // case14
                        err1.left = err2Left;
                        err1.right = err2Right;
                        err2.left = err1Left;
                        err2.right = err1Right;
                        err1Parent.right = err2;
                        err2Parent.right = err1;
                    }
                }
            }
        }
        return head;
    }

    /**
     * 找到两个错误节点
     */
    private static Node[] getTwoErrNodes(Node head) {
        Node[] errorNodes = new Node[2];
        if (head == null) {
            return errorNodes;
        }

        Stack<Node> stack = new Stack<>();
        Node pre = null;
        while (!stack.isEmpty() || head != null) {
            if (head != null) {
                stack.push(head);
                head = head.left;
            } else {
                head = stack.pop();
                if (pre != null && pre.value > head.value) {
                    errorNodes[0] = errorNodes[0] == null ? pre : errorNodes[0];
                    errorNodes[1] = head;
                }
                pre = head;
                head = head.right;
            }
        }
        return errorNodes;
    }

    /**
     * 找到两个错误节点的父节点
     */
    private static Node[] getTwoErrParents(Node head, Node err1, Node err2) {
        Node[] parents = new Node[2];
        if (head == null) {
            return parents;
        }

        Stack<Node> stack = new Stack<>();
        while (!stack.isEmpty() || head != null) {
            if (head != null) {
                stack.push(head);
                head = head.left;
            } else {
                head = stack.pop();
                if (head.left == err1 || head.right == err1) {
                    parents[0] = head;
                }
                if (head.left == err2 || head.right == err2) {
                    parents[1] = head;
                }
                head = head.right;
            }
        }
        return parents;
    }

    private static boolean isBST(Node head) {
        if (head == null) {
            return false;
        }

        Stack<Node> stack = new Stack<>();
        Node pre = null;
        while (!stack.isEmpty() || head != null) {
            if (head != null) {
                stack.push(head);
                head = head.left;
            } else {
                head = stack.pop();
                if (pre != null && pre.value > head.value) {
                    return false;
                }
                pre = head;
                head = head.right;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Node head = new Node(5);
        head.left = new Node(3);
        head.right = new Node(7);
        head.left.left = new Node(2);
        head.left.right = new Node(4);
        head.right.left = new Node(6);
        head.right.right = new Node(8);
        head.left.left.left = new Node(1);

        System.out.println(isBST(head));

        // case1, 7 -> err1, 5 -> err2
        System.out.println("case 1");
        Node head1 = new Node(7);
        head1.left = new Node(3);
        head1.right = new Node(5);
        head1.left.left = new Node(2);
        head1.left.right = new Node(4);
        head1.right.left = new Node(6);
        head1.right.right = new Node(8);
        head1.left.left.left = new Node(1);
        System.out.println(isBST(head1));
        Node res1 = recoverTree(head1);
        System.out.println(isBST(res1));

        // case2, 6 -> err1, 5 -> err2
        System.out.println("case 2");
        Node head2 = new Node(6);
        head2.left = new Node(3);
        head2.right = new Node(7);
        head2.left.left = new Node(2);
        head2.left.right = new Node(4);
        head2.right.left = new Node(5);
        head2.right.right = new Node(8);
        head2.left.left.left = new Node(1);
        System.out.println(isBST(head2));
        Node res2 = recoverTree(head2);
        System.out.println(isBST(res2));

        // case3, 8 -> err1, 5 -> err2
        System.out.println("case 3");
        Node head3 = new Node(8);
        head3.left = new Node(3);
        head3.right = new Node(7);
        head3.left.left = new Node(2);
        head3.left.right = new Node(4);
        head3.right.left = new Node(6);
        head3.right.right = new Node(5);
        head3.left.left.left = new Node(1);
        System.out.println(isBST(head3));
        Node res3 = recoverTree(head3);
        System.out.println(isBST(res3));

        // case4, 5 -> err1, 3 -> err2
        System.out.println("case 4");
        Node head4 = new Node(3);
        head4.left = new Node(5);
        head4.right = new Node(7);
        head4.left.left = new Node(2);
        head4.left.right = new Node(4);
        head4.right.left = new Node(6);
        head4.right.right = new Node(8);
        head4.left.left.left = new Node(1);
        System.out.println(isBST(head4));
        Node res4 = recoverTree(head4);
        System.out.println(isBST(res4));

        // case5, 5 -> err1, 2 -> err2
        System.out.println("case 5");
        Node head5 = new Node(2);
        head5.left = new Node(3);
        head5.right = new Node(7);
        head5.left.left = new Node(5);
        head5.left.right = new Node(4);
        head5.right.left = new Node(6);
        head5.right.right = new Node(8);
        head5.left.left.left = new Node(1);
        System.out.println(isBST(head5));
        Node res5 = recoverTree(head5);
        System.out.println(isBST(res5));

        // case6, 5 -> err1, 4 -> err2
        System.out.println("case 6");
        Node head6 = new Node(4);
        head6.left = new Node(3);
        head6.right = new Node(7);
        head6.left.left = new Node(2);
        head6.left.right = new Node(5);
        head6.right.left = new Node(6);
        head6.right.right = new Node(8);
        head6.left.left.left = new Node(1);
        System.out.println(isBST(head6));
        Node res6 = recoverTree(head6);
        System.out.println(isBST(res6));

        // case7, 4 -> err1, 3 -> err2
        System.out.println("case 7");
        Node head7 = new Node(5);
        head7.left = new Node(4);
        head7.right = new Node(7);
        head7.left.left = new Node(2);
        head7.left.right = new Node(3);
        head7.right.left = new Node(6);
        head7.right.right = new Node(8);
        head7.left.left.left = new Node(1);
        System.out.println(isBST(head7));
        Node res7 = recoverTree(head7);
        System.out.println(isBST(res7));

        // case8, 8 -> err1, 7 -> err2
        System.out.println("case 8");
        Node head8 = new Node(5);
        head8.left = new Node(3);
        head8.right = new Node(8);
        head8.left.left = new Node(2);
        head8.left.right = new Node(4);
        head8.right.left = new Node(6);
        head8.right.right = new Node(7);
        head8.left.left.left = new Node(1);
        System.out.println(isBST(head8));
        Node res8 = recoverTree(head8);
        System.out.println(isBST(res8));

        // case9, 3 -> err1, 2 -> err2
        System.out.println("case 9");
        Node head9 = new Node(5);
        head9.left = new Node(2);
        head9.right = new Node(7);
        head9.left.left = new Node(3);
        head9.left.right = new Node(4);
        head9.right.left = new Node(6);
        head9.right.right = new Node(8);
        head9.left.left.left = new Node(1);
        System.out.println(isBST(head9));
        Node res9 = recoverTree(head9);
        System.out.println(isBST(res9));

        // case10, 7 -> err1, 6 -> err2
        System.out.println("case 10");
        Node head10 = new Node(5);
        head10.left = new Node(3);
        head10.right = new Node(6);
        head10.left.left = new Node(2);
        head10.left.right = new Node(4);
        head10.right.left = new Node(7);
        head10.right.right = new Node(8);
        head10.left.left.left = new Node(1);
        System.out.println(isBST(head10));
        Node res10 = recoverTree(head10);
        System.out.println(isBST(res10));

        // case11, 6 -> err1, 2 -> err2
        System.out.println("case 11");
        Node head11 = new Node(5);
        head11.left = new Node(3);
        head11.right = new Node(7);
        head11.left.left = new Node(6);
        head11.left.right = new Node(4);
        head11.right.left = new Node(2);
        head11.right.right = new Node(8);
        head11.left.left.left = new Node(1);
        System.out.println(isBST(head11));
        Node res11 = recoverTree(head11);
        System.out.println(isBST(res11));

        // case12, 8 -> err1, 2 -> err2
        System.out.println("case 12");
        Node head12 = new Node(5);
        head12.left = new Node(3);
        head12.right = new Node(7);
        head12.left.left = new Node(8);
        head12.left.right = new Node(4);
        head12.right.left = new Node(6);
        head12.right.right = new Node(2);
        head12.left.left.left = new Node(1);
        System.out.println(isBST(head12));
        Node res12 = recoverTree(head12);
        System.out.println(isBST(res12));

        // case13, 6 -> err1, 4 -> err2
        System.out.println("case 13");
        Node head13 = new Node(5);
        head13.left = new Node(3);
        head13.right = new Node(7);
        head13.left.left = new Node(2);
        head13.left.right = new Node(6);
        head13.right.left = new Node(4);
        head13.right.right = new Node(8);
        head13.left.left.left = new Node(1);
        System.out.println(isBST(head13));
        Node res13 = recoverTree(head13);
        System.out.println(isBST(res13));

        // case14, 8 -> err1, 4 -> err2
        System.out.println("case 14");
        Node head14 = new Node(5);
        head14.left = new Node(3);
        head14.right = new Node(7);
        head14.left.left = new Node(2);
        head14.left.right = new Node(8);
        head14.right.left = new Node(6);
        head14.right.right = new Node(4);
        head14.left.left.left = new Node(1);
        System.out.println(isBST(head14));
        Node res14 = recoverTree(head14);
        System.out.println(isBST(res14));
    }
}
