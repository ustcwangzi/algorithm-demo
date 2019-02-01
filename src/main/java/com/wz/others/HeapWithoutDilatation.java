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
 * <p>
 *     堆结构一般是使用固定长度的数组结构来实现的。这样的实现虽然足够经典，但存在扩容的负担，比如不断向堆中增加元素，使得固定数组快耗尽时，
 *     就不得不申请一个更大的固定数组，然后把原来数组中的对象复制到新的数组中完成堆的扩容，所以，如果扩容时堆中的元素个数为N，
 *     那么扩容行为的时间复杂度为O(N)。请设计一种没有扩容负担的堆结构，即在任何时刻有关堆的操作时间复杂度都不超过O(logN)。
 *     要求：
 *        1、没有扩容的负担；
 *        2、可以生成小根堆，也可以生成大根堆；
 *        3、包含getHead方法，返回当前堆顶的值；
 *        4、包含getSize方法，返回当前堆的大小；
 *        5、包含add(x)方法，即向堆中添加新的元素x，操作后依然是大/小根堆；
 *        6、包含popHead方法，即删除并返回堆顶的值，操作后依然是大/小根堆；
 *        7、如果堆中的节点个数为N，那么各个方法的时间复杂度为：
 *           getHead:O(1)，getSize:O(1)，add:O(logN)，popHead:O(logN)。
 *     解决方案：
 *        实现完全二叉树的结构，并含有堆调整的过程，其中节点结构比经典二叉树节点多一个指向读节点的parent指针。
 *        添加元素时，首先生成一个二叉树结的类型的实例，假设节点为cur，把cur加入到二叉树上：
 *        1、若size==0，说明当前堆没有节点，直接进行简单赋值即可
 *        2、如果size>0，说明当前堆有节点，需要先找到cur应该加到什么位置，last在堆中的什么位置很关键，具体分为三种情况：
 *        2.1、last是当前层的最后一个节点，那么cur应该加到新一层的最左位置；
 *        2.2、last是last父节点的左孩子，那么cur应该加到last父节点的右孩子的位置；
 *        2.3、last既不是情况一也不是情况二，需要向上寻找的过程。如图HeapWithoutDilatation.png中9-7和9-8。
 *             以last为当前节点，然后看当前节点是不是其父节点的左孩子，若不是则一直向上。比如图9-7的节点I，会寻找到D结束，
 *             发现D是B的左孩子，cur应该加到B的右子树的最左节点的左孩子的位置上，即E的左孩子位置；图9-8中，寻找到B结束，
 *             发现B是A的最孩子，cur应该加到A的右子树的最左节点的左孩子的位置上，即F的左孩子位置。
 *        3、加完cur后，cur成为新的last，令last=cur，同时size++
 *        4、还需要进行堆的调整，假设节点添加后如图9-9，很明显，last需要往上调整，调整后变为9-10。
 *        删除并返回头节点的过程为：
 *        1、若size==0，说明当前堆没有节点，直接返回null
 *        2、若size==1，说明当前堆只有一个节点，返回节点值然后清空堆即可
 *        3、若size>1，把当前堆顶节点记为result，把last放在堆顶位置作为新的头节点，然后从头部开始进行堆调整，
 *           先把last与整个堆断开，记为oldLast，last应该更新为oldLast之前的节点，分为三种情况：
 *        3.1、若oldLast是当前层的最左节点，那么last应该变为上一层的最右节点；
 *        3.2、若lodLast是其父节点的右孩子，那么last应该变为父节点的左孩子；
 *        3.3、last既不是情况一也不是情况二，需要向上寻找的过程。如图HeapWithoutDilatation.png中9-11和9-12。
 *             以oldLast为当前节点，然后看当前节点是不是其父节点的右孩子，若不是则一直向上。如图9-11的节点J，会寻找到E结束，
 *             发现E是B的右孩子，last应该设为B的左子树的最右节点，即节点I；图9-12中，寻找到C结束，
 *             发现C是A的右孩子，last应该设为A的左子树的最右节点，即节点K。
 *        4、断开oldLast之后，堆中元素少一个，size--，若减1后size==1，说明断开后只剩下一个节点，令oldLast为新的头节点，返回result即可
 *        5、若断开oldLast之后，size>1，那么将oldLast设为新节点之后，需要从堆顶开始往下调整堆结构。
 * </p>
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
