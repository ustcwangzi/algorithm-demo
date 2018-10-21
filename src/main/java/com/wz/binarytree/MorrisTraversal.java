/**
 * <p>Title: MorrisTraversal</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/10/21</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.binarytree;

/**
 * <p>Morris二叉树遍历</p>
 * <p>
 *     时间复杂度为O(N)，空间复杂度为O(1)
 *     中序遍历：
 *          1、假设当前子树的头节点为h，让h的左子树中最右节点的right指针指向h，以相同方法处理h的左子树，
 *             直到某个节点没有左子树时记为node，进入步骤二
 *          2、从node开始通过每个节点的right指针进行移动，并依次打印，假设当前移动到节点cur
 *             对每个cur节点判断该节点的左子树中最右节点的right指针是否指向cur
 *          2.1、若是，将cur节点左子树中最右节点的right指针指向null，即恢复right指针，然后重复步骤二
 *          2.2、若不是，以cur为头节点的子树重新回到步骤一执行
 *     先序遍历：
 *          先序遍历是对中序遍历实现的简单改写
 *          中序遍历的打印时机是在步骤二的移动过程中，而先序遍历只需把打印时间放在步骤一发生时即可
 *     后序遍历：
 *          后序遍历也是对中序遍历的改写，逻辑是需要依次逆序打印所有节点的左子树的右边界，时机在right指针恢复时
 *     假设当前二叉树的根节点为4，左右孩子为2、6，2的左右孩子为1、3，6的左右孩子为5、7
 *     对于先序遍历，步骤一时会将3的right指向4，1的right指向2，接下来过程如下：
 *          1、打印节点1，cur指向2，满足步骤2.1，令1的right指向null，打印节点2
 *          2、cur指向3，满足步骤2.2，进入步骤一，但因为节点3没有子树，快速结束，打印节点3
 *          3、cur指向4，令3的right指向null，打印节点4
 *          4、cur指向6，满足步骤2.2，进入步骤一，5的right指向6
 *          5、cur指向5，满足步骤2.2，进入步骤一，但因为节点5没有子树，快速结束，打印节点5
 *          6、cur指向6，满足步骤2.1，令5的right指向null，打印节点6
 *          7、cur指向7，满足步骤2.2，进入步骤一，但因为节点7没有子树，快速结束，打印节点7
 *          8、cur指向null，整个过程结束
 * </p>
 *
 * @author wangzi
 */
public class MorrisTraversal {
    private static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    public static void inOrder(Node head) {
        if (head == null) {
            return;
        }
        Node cur1 = head;
        Node cur2 = null;
        while (cur1 != null) {
            cur2 = cur1.left;
            if (cur2 != null) {
                while (cur2.right != null && cur2.right != cur1) {
                    cur2 = cur2.right;
                }
                // 将cur1的左子树中最右节点的right指针指向cur1
                if (cur2.right == null) {
                    cur2.right = cur1;
                    cur1 = cur1.left;
                    continue;
                } else {
                    // 恢复right指针的指向
                    cur2.right = null;
                }
            }
            System.out.print(cur1.value + " ");
            cur1 = cur1.right;
        }
    }

    public static void preOrder(Node head) {
        if (head == null) {
            return;
        }
        Node cur1 = head;
        Node cur2 = null;
        while (cur1 != null) {
            cur2 = cur1.left;
            if (cur2 != null) {
                while (cur2.right != null && cur2.right != cur1) {
                    cur2 = cur2.right;
                }
                if (cur2.right == null) {
                    cur2.right = cur1;
                    System.out.print(cur1.value + " ");
                    cur1 = cur1.left;
                    continue;
                } else {
                    cur2.right = null;
                }
            } else {
                System.out.print(cur1.value + " ");
            }
            cur1 = cur1.right;
        }
    }

    public static void posOrder(Node head) {
        if (head == null) {
            return;
        }
        Node cur1 = head;
        Node cur2 = null;
        while (cur1 != null) {
            cur2 = cur1.left;
            if (cur2 != null) {
                while (cur2.right != null && cur2.right != cur1) {
                    cur2 = cur2.right;
                }
                if (cur2.right == null) {
                    cur2.right = cur1;
                    cur1 = cur1.left;
                    continue;
                } else {
                    cur2.right = null;
                    printEdge(cur1.left);
                }
            }
            cur1 = cur1.right;
        }
        printEdge(head);
    }

    private static void printEdge(Node head) {
        Node tail = reverseEdge(head);
        Node cur = tail;
        while (cur != null) {
            System.out.print(cur.value + " ");
            cur = cur.right;
        }
        reverseEdge(tail);
    }

    /**
     * right指针逆序
     */
    private static Node reverseEdge(Node from) {
        Node pre = null;
        Node next = null;
        while (from != null) {
            next = from.right;
            from.right = pre;
            pre = from;
            from = next;
        }
        return pre;
    }

    public static void main(String[] args) {
        Node head = new Node(4);
        head.left = new Node(2);
        head.right = new Node(6);
        head.left.left = new Node(1);
        head.left.right = new Node(3);
        head.right.left = new Node(5);
        head.right.right = new Node(7);

        inOrder(head);
        System.out.println();
        preOrder(head);
        System.out.println();
        posOrder(head);
    }
}
