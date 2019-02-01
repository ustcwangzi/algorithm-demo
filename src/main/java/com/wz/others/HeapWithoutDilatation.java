/**
 * <p>Title: HeapWithoutDilatation</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/2/1</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.others;

import java.util.Comparator;

/**
 * <p>设计一个没有扩容负担的堆结构</p>
 *
 * @author wangzi
 */
public class HeapWithoutDilatation {

    /**
     * 节点结构，比经典二叉树节点多一个指向读节点的parent指针
     */
    private static class Node<K> {
        public K value;
        public Node<K> left;
        public Node<K> right;
        public Node<K> parent;

        public Node(K value) {
            this.value = value;
        }
    }

    public static class MyHeap<K> {
        /**
         * 头节点
         */
        private Node<K> head;
        /**
         * 尾节点，即最后一排最后一个节点
         */
        private Node<K> last;
        /**
         * 堆大小
         */
        private long size;
        /**
         * 决定是大根堆还是小根堆
         */
        private Comparator<K> comparator;

        public MyHeap(Comparator<K> comparator) {
            this.head = null;
            this.last = null;
            this.size = 0;
            this.comparator = comparator;
        }

        public K getHead() {
            return head == null ? null : head.value;
        }

        public long getSize() {
            return size;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        /**
         * 添加节点
         */
        public void add(K value) {
            Node<K> cur = new Node<>(value);
            if (size == 0) {
                head = cur;
                last = cur;
                size++;
                return;
            }

            Node<K> node = last;
            Node<K> parent = node.parent;
            while (parent != null && node != parent.left) {
                node = parent;
                parent = node.parent;
            }

            Node<K> nodeToAdd = null;
            if (parent == null) {
                // 最后一层已满，需要新加一层
                nodeToAdd = mostLeft(head);
                nodeToAdd.left = cur;
                cur.parent = nodeToAdd;
            } else if (parent.right == null) {
                // 添加到右孩子的位置
                parent.right = cur;
                cur.parent = parent;
            } else {
                // 添加到左孩子的位置
                nodeToAdd = mostLeft(parent.right);
                nodeToAdd.left = cur;
                cur.parent = nodeToAdd;
            }

            last = cur;
            // 调整
            heapInsertModify();
            size++;
        }

        /**
         * 删除堆顶节点
         */
        public K popHead() {
            if (size == 0) {
                return null;
            }

            Node<K> result = head;
            if (size == 1) {
                head = null;
                last = null;
                size--;
                return result.value;
            }

            // 把last和堆断开，记为oldLast，然后last变为oldLast节点之前的那个节点
            Node<K> oldLast = popLastAndSetPreviousLast();
            if (size == 1) {
                head = oldLast;
                last = oldLast;
                return result.value;
            }

            // oldLat作为新的头节点
            Node<K> headLeft = result.left;
            Node<K> headRight = result.right;
            oldLast.left = headLeft;
            if (headLeft != null) {
                headLeft.parent = oldLast;
            }
            oldLast.right = headRight;
            if (headRight != null) {
                headRight.parent = oldLast;
            }

            result.left = null;
            result.right = null;
            head = oldLast;

            // 从堆顶开始往下进行堆调整
            heapify(oldLast);
            return result.value;
        }

        private Node<K> mostLeft(Node<K> node) {
            while (node.left != null) {
                node = node.left;
            }
            return node;
        }

        private Node<K> mostRight(Node<K> node) {
            while (node.right != null) {
                node = node.right;
            }
            return node;
        }

        private void heapInsertModify() {
            Node<K> node = last;
            Node<K> parent = node.parent;
            if (parent != null && comparator.compare(node.value, parent.value) < 0) {
                last = parent;
            }
            while (parent != null && comparator.compare(node.value, parent.value) < 0) {
                swapClosedTwoNodes(node, parent);
                parent = node.parent;
            }
            if (head.parent != null) {
                head = head.parent;
            }
        }

        private void heapify(Node<K> node) {
            Node<K> left = node.left;
            Node<K> right = node.right;
            Node<K> most = node;
            while (left != null) {
                if (left != null && comparator.compare(left.value, most.value) < 0) {
                    most = left;
                }
                if (right != null && comparator.compare(right.value, most.value) < 0) {
                    most = right;
                }
                if (most != node) {
                    swapClosedTwoNodes(most, node);
                } else {
                    break;
                }
                left = node.left;
                right = node.right;
                most = node;
            }
            if (node.parent == last) {
                last = node;
            }
            while (node.parent != null) {
                node = node.parent;
            }
            head = node;
        }

        private void swapClosedTwoNodes(Node<K> node, Node<K> parent) {
            if (node == null || parent == null) {
                return;
            }
            Node<K> parentParent = parent.parent;
            Node<K> parentLeft = parent.left;
            Node<K> parentRight = parent.right;
            Node<K> nodeLeft = node.left;
            Node<K> nodeRight = node.right;
            node.parent = parentParent;
            if (parentParent != null) {
                if (parent == parentParent.left) {
                    parentParent.left = node;
                } else {
                    parentParent.right = node;
                }
            }
            parent.parent = node;
            if (nodeLeft != null) {
                nodeLeft.parent = parent;
            }
            if (nodeRight != null) {
                nodeRight.parent = parent;
            }
            if (node == parent.left) {
                node.left = parent;
                node.right = parentRight;
                if (parentRight != null) {
                    parentRight.parent = node;
                }
            } else {
                node.left = parentLeft;
                node.right = parent;
                if (parentLeft != null) {
                    parentLeft.parent = node;
                }
            }
            parent.left = nodeLeft;
            parent.right = nodeRight;
        }

        /**
         * 把last和堆断开，记为oldLast，然后last变为oldLast节点之前的那个节点
         */
        private Node<K> popLastAndSetPreviousLast() {
            Node<K> node = last;
            Node<K> parent = node.parent;
            while (parent != null && node != parent.right) {
                node = parent;
                parent = node.parent;
            }
            if (parent == null) {
                // last位于最左侧，断开后，last应该变为上一层的最右节点
                node = last;
                parent = node.parent;
                node.parent = null;
                if (node == parent.left) {
                    parent.left = null;
                } else {
                    parent.right = null;
                }
                last = mostRight(head);
            } else {
                Node<K> newLast = mostRight(parent.left);
                node = last;
                parent = node.parent;
                node.parent = null;
                if (node == parent.left) {
                    parent.left = null;
                } else {
                    parent.right = null;
                }
                last = newLast;
            }
            size--;
            return node;
        }
    }
}
