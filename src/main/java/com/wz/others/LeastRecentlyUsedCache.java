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
