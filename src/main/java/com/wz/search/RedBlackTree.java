/**
 * <p>Title: RedBlackTree</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/7/1</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.search;

import java.util.NoSuchElementException;

/**
 * <p>红黑树</p>
 * <p>
 *     2-3查找树或为一颗空树，或由以下节点组成：
 *       2-节点：含有一个键和两个子树，左子树中的键都小于该节点，右子树中的键都大于该节点
 *       3-节点：含有两个键和三个子树，左子树中的键都小于该节点，中子树中的键都介于该节点的两个键之间，右子树中的键都大于该节点
 *     平衡的2-3查找树中，所有的空节点到根节点的距离都是相同的
 *     在大小为N的2-3查找树中，查找和插入操作访问的节点都不大于logN
 *
 *     红黑树背后的基本思想是用标准的二叉查找树和一些额外的信息来表示2-3查找树：
 *     黑链接是2-3树中的普通链接，
 *     红链接将两个2-节点连接起来构成一个3-节点，即3-节点表示为由一条左斜的红链接相连的两个2-节点
 *
 *     红黑树是含有红黑链接并满足以下条件的二叉查找树：
 *     1、红链接均为左链接
 *     2、没有任何一个节点同时和两条红链接相连
 *     3、平衡，任意空链接到根节点路径上的黑链接数量相同
 *     满足此定义的红黑树和相应的2-3树是一一对应的
 * </p>
 *
 * <p>一颗大小为N的红黑树的高度不超过2*logN</p>
 * <p>最坏情况下，插入成本是 2*logN，查找成本是 2*logN</p>
 * <p>平均情况下，插入成本是 1.001logN，查找成本是 1.001*logN</p>
 *
 * @author wangzi
 */
public class RedBlackTree<Key extends Comparable<Key>, Value> {
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    /** 根节点 */
    private Node root;

    private class Node{
        private Key key;
        private Value value;
        private Node left, right;
        /** 父链接颜色，红色为true */
        private boolean color;
        private int size;

        public Node(Key key, Value value, boolean color, int size) {
            this.key = key;
            this.value = value;
            this.color = color;
            this.size = size;
        }
    }

    public int size(){
        return size(root);
    }

    private int size(Node node){
        if (node == null){
            return 0;
        }
        return node.size;
    }

    private boolean isRed(Node node){
        return node != null && node.color == RED;
    }

    public boolean isEmpty(){
        return root == null;
    }

    public Value get(Key key){
        if (key == null) {
            throw new IllegalArgumentException("argument is null");
        }
        return get(root, key);
    }

    private Value get(Node node, Key key){
        while (node != null){
            int cmp = key.compareTo(node.key);
            if (cmp > 0){
                node = node.right;
            }else if (cmp < 0){
                node = node.left;
            }else {
                return node.value;
            }
        }
        return null;
    }

    public boolean contains(Key key){
        return get(key) != null;
    }

    public void put(Key key, Value value){
        if (key == null) {
            throw new IllegalArgumentException("argument is null");
        }

        if (value == null){
            delete(key);
            return;
        }

        root = put(root, key, value);
        root.color = BLACK;
    }

    /**
     * 插入新节点
     * 插入的实现和二叉查找树完全相同
     * 插入后沿着插入点向上，到根节点的路径中所经过的节点，进行以下三种操作保证树的平衡性：
     * 1、右节点是红色，左节点是黑色，进行左旋转
     * 2、左节点是红色，右节点是黑色，进行右旋转
     * 3、左右节点都是红色，进行颜色转换
     */
    private Node put(Node node, Key key, Value value){
        if (node == null){
            return new Node(key, value, RED, 1);
        }

        int cmp = key.compareTo(node.key);
        if (cmp > 0){
            node.right = put(node.right, key, value);
        }else if (cmp < 0){
            node.left = put(node.left, key, value);
        }else {
            node.value = value;
        }

        if (isRed(node.right) && !isRed(node.left)){
            node = rotateLeft(node);
        }
        if (isRed(node.left) && isRed(node.left.left)){
            node = rotateRight(node);
        }
        if (isRed(node.left) && isRed(node.right)){
            flipColors(node);
        }
        node.size = 1 + size(node.left) + size(node.right);

        return node;
    }

    public void deleteMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("empty tree");
        }

        if (!isRed(root.left) && !isRed(root.right)){
            root.color = RED;
        }

        root = deleteMin(root);
        if (!isEmpty()) {
            root.color = BLACK;
        }
    }

    /**
     * 删除最小节点，显然最小节点一定在最左边
     * 如果要删除的节点在3-node或者4-node中，直接删除即可
     * 如果要删除的节点是2-node，直接删除会破坏平衡，删除之前要进行一定的变换，变成3-node或者4-node
     * 2-node的条件就是，node.left和node.left.left均为黑色的
     * 具体流程：
     * 1、如果已经到了最底部，那么直接移除就行，移除的要求是最底部的节点一定是red
     * 2、如果遇到了2-node就借节点
     * 3、继续往下递归查找
     * 4、删除完毕，恢复红黑树
     */
    private Node deleteMin(Node node) {
        if (node.left == null){
            return null;
        }

        if (!isRed(node.left) && !isRed(node.left.left)){
            node = moveRedLeft(node);
        }

        node.left = deleteMin(node.left);
        return balance(node);
    }

    public void deleteMax() {
        if (isEmpty()) {
            throw new NoSuchElementException("empty tree");
        }

        if (!isRed(root.left) && !isRed(root.right)){
            root.color = RED;
        }

        root = deleteMax(root);
        if (!isEmpty()) {
            root.color = BLACK;
        }
    }

    /**
     * 删除最大节点，显然最大节点一定在最右边
     * 如果要删除的节点在3-node或者4-node中，直接删除即可
     * 如果要删除的节点是2-node，直接删除会破坏平衡，删除之前要进行一定的变换，变成3-node或者4-node
     * 2-node的条件就是，node.right和node.right.left均为黑色的
     * 具体流程：
     * 1、首先如果左旋则变为右旋，因为找最大节点在最右边
     * 2、如果，已经到了最底部，那么直接移除就行，移除的要求是最底部的节点一定是red
     * 3、如果遇到了2-node就借节点
     * 4、继续往下递归查找
     * 5、删除完毕，恢复红黑树
     */
    private Node deleteMax(Node node) {
        if (isRed(node.left)){
            node = rotateRight(node);
        }
        if (node.right == null){
            return null;
        }
        if (!isRed(node.right) && !isRed(node.right.left)){
            node = moveRedRight(node);
        }

        node.right = deleteMax(node.right);
        return balance(node);
    }

    public void delete(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument is null");
        }
        if (!contains(key)) {
            return;
        }

        if (!isRed(root.left) && !isRed(root.right)){
            root.color = RED;
        }

        root = delete(root, key);
        if (!isEmpty()) {
            root.color = BLACK;
        }
    }

    /**
     * 删除节点
     * 如果要删除一个节点，把要删除的那个节点和最底部的节点交换
     * 然后就变成删除最底部的节点，就可以转换成删除最大节点或者最小节点了
     *
     */
    private Node delete(Node node, Key key) {
        if (key.compareTo(node.key) < 0)  {
            if (!isRed(node.left) && !isRed(node.left.left)){
                node = moveRedLeft(node);
            }
            node.left = delete(node.left, key);
        }else {
            if (isRed(node.left)){
                node = rotateRight(node);
            }
            if (key.compareTo(node.key) == 0 && (node.right == null)){
                return null;
            }
            if (!isRed(node.right) && !isRed(node.right.left)){
                node = moveRedRight(node);
            }
            if (key.compareTo(node.key) == 0) {
                Node x = min(node.right);
                node.key = x.key;
                node.value = x.value;
                node.right = deleteMin(node.right);
            }else {
                node.right = delete(node.right, key);
            }
        }
        return balance(node);
    }

    /**
     * 右旋转
     * 旋转前，x是node的左孩子
     * 旋转后，x的右孩子变为node的左孩子，node变为x的右孩子
     */
    private Node rotateRight(Node node){
        Node x = node.left;
        node.left = x.right;
        x.right = node;
        x.color = x.right.color;
        x.right.color = RED;
        x.size = node.size;
        node.size = 1 + size(node.left) + size(node.right);
        return x;
    }

    /**
     * 左旋转
     * 旋转前，x是node的右孩子
     * 旋转后，x的左孩子变为node的右孩子，node变为x的左孩子
     */
    private Node rotateLeft(Node node){
        Node x = node.right;
        node.right = x.left;
        x.left = node;
        x.color = x.left.color;
        x.left.color = RED;
        x.size = node.size;
        node.size = 1 + size(node.left) + size(node.right);
        return x;
    }

    /**
     * 改变节点及其两个子节点的颜色
     */
    private void flipColors(Node node){
        node.color = !node.color;
        node.left.color = !node.left.color;
        node.right.color = ! node.right.color;
    }

    /**
     * 将node.left或node.left.left变为红色
     * 1、node.right.left为黑，说明兄弟节点也是2-node，从父节点借节点，直接color flip即可
     * 2、node.right.left为红，则可以直接从兄弟节点借一个节点过来
     */
    private Node moveRedLeft(Node node){
        flipColors(node);
        if (isRed(node.right.left)){
            node.right = rotateRight(node.right);
            node = rotateLeft(node);
            flipColors(node);
        }
        return node;
    }

    /**
     * 将node.right或node.right.right变为红色
     * 1、兄弟节点是2-node，则从父节点中借一个过来，然后和兄弟节点合并成一个4-node
     * 2、兄弟节点不是2-node，就可以直接从兄弟节点借一个节点过来
     */
    private Node moveRedRight(Node node){
        flipColors(node);
        if (isRed(node.left.left)){
            node = rotateRight(node);
            flipColors(node);
        }
        return node;
    }

    /**
     * 红黑树平衡
     */
    private Node balance(Node node){
        if (isRed(node.right)){
            node = rotateLeft(node);
        }
        if (isRed(node.left) && isRed(node.left.left)){
            node = rotateRight(node);
        }
        if (isRed(node.left) && isRed(node.right)){
            flipColors(node);
        }

        node.size = 1 + size(node.left) + size(node.right);
        return node;
    }

    public Key min() {
        if (isEmpty()) {
            throw new NoSuchElementException("empty tree");
        }
        return min(root).key;
    }

    private Node min(Node x) {
        if (x.left == null) {
            return x;
        }
        return min(x.left);
    }

    public Key max() {
        if (isEmpty()) {
            throw new NoSuchElementException("empty tree");
        }
        return max(root).key;
    }

    private Node max(Node x) {
        if (x.right == null){
            return x;
        }
        return max(x.right);
    }

    private boolean isSizeConsistent() {
        return isSizeConsistent(root);
    }

    private boolean isSizeConsistent(Node x) {
        if (x == null) {
            return true;
        }
        if (x.size != size(x.left) + size(x.right) + 1) {
            return false;
        }
        return isSizeConsistent(x.left) && isSizeConsistent(x.right);
    }

    private boolean is23() {
        return is23(root);
    }

    private boolean is23(Node x) {
        if (x == null) {
            return true;
        }
        if (isRed(x.right)){
            return false;
        }
        if (x != root && isRed(x) && isRed(x.left)) {
            return false;
        }
        return is23(x.left) && is23(x.right);
    }

    private boolean isBalanced() {
        int black = 0;
        Node x = root;
        while (x != null) {
            if(!isRed(x)){
                black++;
            }
            x = x.left;
        }
        return isBalanced(root, black);
    }

    private boolean isBalanced(Node x, int black) {
        if (x == null) {
            return black == 0;
        }
        if (!isRed(x)) {
            black--;
        }
        return isBalanced(x.left, black) && isBalanced(x.right, black);
    }

    public static void main(String[] args) {
        RedBlackTree<String, Integer> rbt = new RedBlackTree<>();
        rbt.put("W", 1);
        rbt.put("A", 8);
        rbt.put("N", 8);
        rbt.put("G", 9);
        rbt.put("Z", 6);
        rbt.put("I", 3);
        System.out.println(rbt.min());
        System.out.println(rbt.max());
        System.out.println(rbt.contains("A"));
        System.out.println(rbt.is23());
        System.out.println(rbt.isBalanced());
        System.out.println(rbt.isSizeConsistent());
    }
}

