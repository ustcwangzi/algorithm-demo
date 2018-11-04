/**
 * <p>Title: DescendantNode</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/11/4</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.binarytree;

/**
 * <p>二叉树中找到一个节点的后继节点</p>
 * <p>
 *     二叉树节点新增一个指向父节点的parent指针，寻找后继节点，后继节点是中序遍历时的下一个节点
 *     方案一：
 *          顺着parent指针一直向上移动，找到头节点，然后进行中序遍历，在中序遍历的结果中找到node的下一个节点
 *          该方法需要N个空间存储中序遍历的结果，因为时间复杂度为O(N)，空间复杂度为O(N)，此处不再详述
 *     方案二：
 *          不需要遍历所有节点，如果node与后继节点之间的实际距离为L，则只需要走过L个节点，不需要额外空间
 *          1、如果node有右子树，那么后继节点就是右子树中的最左节点
 *          2、如果node没有右子树，但node是父节点的左孩子，如果是，则父节点就是后继节点
 *          3、如果node没有右子树，且node是父节点的🈶️右孩子，向上移动，假设移动到s时，s是父节点p的左孩子，则p是node的后继节点
 *          4、步骤三在向上移动中已经到了空节点还是没发现后继节点，则node不存在后继节点
 * </p>
 *
 * @author wangzi
 */
public class DescendantNode {
    private static class Node {
        public int value;
        public Node left;
        public Node right;
        public Node parent;

        public Node(int value) {
            this.value = value;
        }
    }

    public static Node getNextNode(Node node) {
        if (node == null) {
            return null;
        }

        // 🈶️有右子树时，后继节点就是右子树最左边的节点
        if (node.right != null) {
            return getLeftMost(node.right);
        }

        // 没有右子树，向上移动，直到找到存在左子树的节点
        Node parent = node.parent;
        while (parent != null && parent.left != node) {
            node = parent;
            parent = node.parent;
        }
        return parent;
    }

    /**
     * 获取最左节点
     */
    private static Node getLeftMost(Node node) {
        if (node == null) {
            return null;
        }
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    public static void main(String[] args) {
        Node head = new Node(6);
        head.parent = null;
        head.left = new Node(3);
        head.left.parent = head;
        head.left.left = new Node(1);
        head.left.left.parent = head.left;
        head.left.left.right = new Node(2);
        head.left.left.right.parent = head.left.left;
        head.left.right = new Node(4);
        head.left.right.parent = head.left;
        head.left.right.right = new Node(5);
        head.left.right.right.parent = head.left.right;
        head.right = new Node(9);
        head.right.parent = head;
        head.right.left = new Node(8);
        head.right.left.parent = head.right;
        head.right.left.left = new Node(7);
        head.right.left.left.parent = head.right.left;
        head.right.right = new Node(10);
        head.right.right.parent = head.right;

        Node node = head.left.left;
        System.out.println(node.value + " next: " + getNextNode(node).value);
        node = head.left;
        System.out.println(node.value + " next: " + getNextNode(node).value);
        node = head.left.right.right;
        System.out.println(node.value + " next: " + getNextNode(node).value);
        node = head;
        System.out.println(node.value + " next: " + getNextNode(node).value);
        node = head.right;
        System.out.println(node.value + " next: " + getNextNode(node).value);
        node = head.right.right;
        System.out.println(node.value + " next: " + getNextNode(node));
    }
}
