/**
 * <p>Title: BinarySearchTree</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/6/30</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.search;

import java.util.NoSuchElementException;

/**
 * <p>二叉查找树</p>
 * <p>
 *     二叉查找树是一个二叉树，其中每个节点都含有一个键
 *     且每个节点的键都大于其左子树中任意节点的键而小于右子树中的任意节点的键
 * </p>
 * <p>二叉查找树中，所有操作在最坏的情况下所需的时间都和树的高度成正比</p>
 * <p>最坏情况下，插入成本是 N，查找成本是 N</p>
 * <p>平均情况下，插入成本是 1.39*logN，查找成本是 1.39*logN</p>
 *
 * @author wangzi
 */
public class BinarySearchTree<Key extends Comparable<Key>, Value> {
    /**
     * 根节点
     */
    private Node root;

    private class Node {
        private Key key;
        private Value value;
        /**
         * 左右子树
         */
        private Node left, right;
        /**
         * 子树中的节点数
         */
        private int size;

        public Node(Key key, Value value, int size) {
            this.key = key;
            this.value = value;
            this.size = size;
        }
    }

    public int size() {
        return size(root);
    }

    private int size(Node node) {
        if (node == null) {
            return 0;
        }
        return node.size;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public boolean contains(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument is null");
        }
        return get(key) != null;
    }

    public Value get(Key key) {
        return get(root, key);
    }

    private Value get(Node node, Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument is null");
        }
        if (node == null) {
            return null;
        }

        int cmp = key.compareTo(node.key);
        if (cmp > 0) {
            return get(node.right, key);
        }
        if (cmp < 0) {
            return get(node.left, key);
        }
        return node.value;
    }

    public void put(Key key, Value value) {
        if (key == null) {
            throw new IllegalArgumentException("argument is null");
        }
        if (value == null) {
            delete(key);
        }
        root = put(root, key, value);
        assert isBST();
    }

    private Node put(Node node, Key key, Value value) {
        if (node == null) {
            return new Node(key, value, 1);
        }
        int cmp = key.compareTo(node.key);
        if (cmp > 0) {
            node.right = put(node.right, key, value);
        } else if (cmp < 0) {
            node.left = put(node.left, key, value);
        } else {
            node.value = value;
        }
        node.size = 1 + size(node.left) + size(node.right);
        return node;
    }

    public void delete(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument is null");
        }
        root = delete(root, key);
    }

    /**
     * 当要删除的节点只有一个子节点或没有子节点时，采用deleteMin()类似的方式即可
     * 当要删除的节点有两个子节点时，需要使用其后继节点填补它的位置
     * 它的后继节点就是其右子树中的最小节点，这样的替换依然能够保证树的有序性
     * 具体过程：
     * 1、将指向要删除的节点的链接保存为tmp
     * 2、将node指向它的后继节点 min(tmp.right)
     * 3、将node的右链接指向 deleteMin(tmp.right)，保证删除后依然是二叉查找树
     * 4、将node的左链接指向 tmp.left(所有键都小于被删除的节点以及其后继节点)
     */
    private Node delete(Node node, Key key) {
        if (node == null) {
            return null;
        }

        int cmp = key.compareTo(node.key);
        if (cmp > 0) {
            node.right = delete(node.right, key);
        } else if (cmp < 0) {
            node.left = delete(node.left, key);
        } else {
            if (node.right == null) {
                return node.left;
            }
            if (node.left == null) {
                return node.right;
            }
            Node tmp = node;
            node = min(tmp.right);
            node.right = deleteMin(tmp.right);
            node.left = tmp.left;
        }
        node.size = 1 + size(node.left) + size(node.right);
        return node;
    }

    public Key min() {
        if (isEmpty()) {
            throw new NoSuchElementException("table is empty");
        }
        return min(root).key;
    }

    private Node min(Node node) {
        if (node.left == null) {
            return node;
        }
        return min(node.left);
    }

    public Key max() {
        if (isEmpty()) {
            throw new NoSuchElementException("table is empty");
        }
        return max(root).key;
    }

    private Node max(Node node) {
        if (node.right == null) {
            return node;
        }
        return max(node.right);
    }

    public void deleteMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("table is empty");
        }
        root = deleteMin(root);
    }

    /**
     * 不断深入左子树，直到左子树为空，然后将指向该节点的链接指向该节点的右子树
     * 此时，已经没有任何链接执行要被删除的节点，即节点被删除
     */
    private Node deleteMin(Node node) {
        if (node.left == null) {
            return node.right;
        }
        node.left = deleteMin(node.left);
        node.size = 1 + size(node.left) + size(node.right);
        return node;
    }

    public void deleteMax() {
        if (isEmpty()) {
            throw new NoSuchElementException("table is empty");
        }
        root = deleteMax(root);
    }

    /**
     * 不断深入右子树，直到右子树为空，然后将指向该节点的链接指向该节点的左子树
     * 此时，已经没有任何链接执行要被删除的节点，即节点被删除
     */
    private Node deleteMax(Node node) {
        if (node.right == null) {
            return node.left;
        }
        node.right = deleteMax(node.right);
        node.size = 1 + size(node.left) + size(node.right);
        return node;
    }

    public int height() {
        return height(root);
    }

    private int height(Node node) {
        if (node == null) {
            return -1;
        }
        return 1 + Math.max(height(node.left), height(node.right));
    }

    private boolean isBST() {
        return isBST(root, null, null);
    }

    private boolean isBST(Node node, Key min, Key max) {
        if (node == null) {
            return true;
        }
        if (min != null && node.key.compareTo(min) <= 0) {
            return false;
        }
        if (max != null && node.key.compareTo(max) >= 0) {
            return false;
        }
        return isBST(node.left, min, node.key) && isBST(node.right, node.key, max);
    }

    private boolean isSizeConsistent() {
        return isSizeConsistent(root);
    }

    private boolean isSizeConsistent(Node node) {
        if (node == null) {
            return true;
        }
        if (node.size != 1 + size(node.left) + size(node.right)) {
            return false;
        }
        return isSizeConsistent(node.left) && isSizeConsistent(node.right);
    }

    public static void main(String[] args) {
        BinarySearchTree<String, Integer> bst = new BinarySearchTree<>();
        bst.put("W", 1);
        bst.put("A", 8);
        bst.put("N", 8);
        bst.put("G", 9);
        bst.put("Z", 6);
        bst.put("I", 3);
        System.out.println(bst.height());
        System.out.println(bst.min());
        System.out.println(bst.max());
        System.out.println(bst.contains("A"));
        System.out.println(bst.isBST());
        System.out.println(bst.isSizeConsistent());
    }
}
