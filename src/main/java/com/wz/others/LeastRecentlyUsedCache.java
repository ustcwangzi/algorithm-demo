/**
 * <p>Title: LeastRecentlyUsedCache</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/1/20</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.others;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>设计可以变更的缓存结构</p>
 * <p>
 *     设计一种缓存结构，该结构在构造时确定大小，假设大小为K，并存在两个功能：
 *     set(key, value)：将记录(key, value)插入该结构；get(key)：返回key对应的value。
 *     请实现该结构，并满足以下要求：
 *     1、set和get的时间复杂度为O(1)；
 *     2、某个key的set或get一旦发生，认为这个key的记录成了最常使用的；
 *     3、当缓存的大小超过K时，移除最不经常使用的记录，即set或get最久远的。
 *     例如：
 *        假设缓存结构的实例是cache，大小为3，并依次发生如下行为：
 *        1、cache.set("A", 1)，最经常使用的记录为("A", 1)；
 *        2、cache.set("B", 2)，最经常使用的记录为("B", 2)，最不经常使用的记录为("A", 1)；
 *        3、cache.set("C", 3)，最经常使用的记录为("C", 3)，最不经常使用的记录还是("A", 1)；
 *        4、cache.get("A")，最经常使用的记录为("A", 1)，最不经常使用的记录为("B", 2)；
 *        5、cache.set("D", 4)，cache大小超过3，移除最不经常使用的记录("B", 2)，加入新纪录("D", 4)，
 *           最经常使用的记录为("D", 4)，最不经常使用的记录为("C", 3)。
 *     解决方案：
 *        使用双端队列+哈希表的方式实现。双向链表节点结构如Node所示，利用Node实现双向链表结构DoubleLinkedList。
 *        DoubleLinkedList有以下三种操作：
 *        1、新增节点，将新加入的节点放在链表的尾部，并将该节点设置为新的尾部
 *        2、对于链表中的任意节点，都可以分离出来并放到整个链表的尾部
 *        3、移除head节点并返回这个节点，然后将head设置为原来head节点的下一个节点。
 *        然后使用DoubleLinkedList来实现缓存结构：
 *        1、新节点加入时，把节点加入到DoubleLinkedList的尾部
 *        2、get或set某个key时，把这个key对应的节点在DoubleLinkedList中调整到尾部
 *        3、一旦cache满了，就删除DoubleLinkedList的当前头节点
 *        为了能让每个key都能快速找到在DoubleLinkedList中所对应的节点node，同时让每个节点node都能找到各自的key，
 *        这里使用两个map分别记录key到node的映射keyNodeMap、以及node到key的映射nodeKeyMap。
 * </p>
 *
 * @author wangzi
 */
public class LeastRecentlyUsedCache {

    /**
     * 双向链表节点
     */
    private static class Node<V> {
        public V value;
        /**
         * 前一个节点
         */
        public Node<V> next;
        /**
         * 后一个节点
         */
        public Node<V> last;

        public Node(V value) {
            this.value = value;
        }
    }

    /**
     * 双向链表
     */
    private static class DoubleLinkedList<V> {
        /**
         * 头节点
         */
        private Node<V> tail;
        /**
         * 尾节点
         */
        private Node<V> head;

        /**
         * 加入节点，放在链表尾部
         */
        public void addNode(Node<V> node) {
            if (node == null) {
                return;
            }
            if (this.head == null) {
                this.head = node;
                this.tail = node;
            } else {
                this.tail.next = node;
                node.last = tail;
                this.tail = node;
            }
        }

        /**
         * 将节点node移到链表尾部
         */
        public void moveNodeToTail(Node<V> node) {
            if (this.tail == node) {
                return;
            }
            if (this.head == node) {
                this.head = node.next;
                this.head.last = null;
            } else {
                // 断开链接
                node.last.next = node.next;
                node.next.last = node.last;
            }
            // 移到尾部
            node.last = this.tail;
            node.next = null;
            this.tail.next = node;
            this.tail = node;
        }

        /**
         * 移除head节点
         */
        public Node<V> removeHead() {
            if (this.head == null) {
                return null;
            }

            Node<V> result = this.head;
            if (this.head == this.tail) {
                this.head = null;
                this.tail = null;
            } else {
                this.head = result.next;
                result.next = null;
                this.head.last = null;
            }
            return result;
        }
    }

    public static class MyCache<K, V> {
        private Map<K, Node<V>> keyNodeMap;
        private Map<Node<V>, K> nodeKeyMap;
        private DoubleLinkedList<V> nodeList;
        private int capacity;

        public MyCache(int capacity) {
            if (capacity < 1) {
                throw new RuntimeException("should be more than 0.");
            }
            this.keyNodeMap = new HashMap<>();
            this.nodeKeyMap = new HashMap<>();
            this.nodeList = new DoubleLinkedList<>();
            this.capacity = capacity;
        }

        /**
         * set时，若存在将其移动到尾部，不存在则新增到尾部
         * 同时检查cache的大小，满了则删除链表中的头部节点
         */
        public void set(K key, V value) {
            if (this.keyNodeMap.containsKey(key)) {
                Node<V> node = this.keyNodeMap.get(key);
                node.value = value;
                this.nodeList.moveNodeToTail(node);
            } else {
                Node<V> node = new Node<>(value);
                this.keyNodeMap.put(key, node);
                this.nodeKeyMap.put(node, key);
                this.nodeList.addNode(node);
                if (this.keyNodeMap.size() == this.capacity + 1) {
                    this.removeMostUnUsedCache();
                }
            }
        }

        /**
         * get时将记录移动到尾部
         */
        public V get(K key) {
            if (this.keyNodeMap.containsKey(key)) {
                Node<V> result = this.keyNodeMap.get(key);
                this.nodeList.moveNodeToTail(result);
                return result.value;
            }
            return null;
        }

        private void removeMostUnUsedCache() {
            Node<V> node = this.nodeList.removeHead();
            K key = this.nodeKeyMap.get(node);
            this.nodeKeyMap.remove(node);
            this.keyNodeMap.remove(key);
        }
    }

    public static void main(String[] args) {
        MyCache<String, Integer> cache = new MyCache<>(3);
        cache.set("A", 1);
        cache.set("B", 2);
        cache.set("C", 3);

        System.out.println(cache.get("B"));
        System.out.println(cache.get("A"));

        cache.set("D", 4);

        System.out.println(cache.get("D"));
        System.out.println(cache.get("C"));
    }
}
