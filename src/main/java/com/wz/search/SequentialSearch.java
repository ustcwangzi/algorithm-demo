/**
 * <p>Title: SequentialSearch</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/6/24</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.search;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>顺序查找</p>
 * <p>
 *     基于链表实现顺序查找
 * </p>
 * <p>
 * <p>最坏情况下，插入成本是 N，查找成本是 N</p>
 * <p>平均情况下，插入成本是 N，查找成本是 N/2</p>
 *
 * @author wangzi
 */
public class SequentialSearch<Key, Value> {
    /**
     * 键值对数量
     */
    private int paris;
    /**
     * 头节点
     */
    private Node first;

    private class Node {
        private Key key;
        private Value value;
        private Node next;

        public Node(Key key, Value value, Node next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    public int size() {
        return paris;
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
        if (key == null) {
            throw new IllegalArgumentException("argument is null");
        }
        for (Node i = first; i != null; i = i.next) {
            if (key.equals(i.key)) {
                return i.value;
            }
        }
        return null;
    }

    public void put(Key key, Value value) {
        if (key == null) {
            throw new IllegalArgumentException("argument is null");
        }
        if (value == null) {
            delete(key);
            return;
        }
        for (Node i = first; i != null; i = i.next) {
            if (key.equals(i.key)) {
                i.value = value;
                return;
            }
        }
        first = new Node(key, value, first);
        paris++;
    }

    public void delete(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument is null");
        }
        first = delete(first, key);
    }

    /**
     * 从head开始查找链表中键为key的节点，删除
     */
    public Node delete(Node head, Key key) {
        if (head == null) {
            return null;
        }
        if (key.equals(head.key)) {
            paris--;
            return head.next;
        }
        head.next = delete(head.next, key);
        return head;
    }

    public Iterable<Key> keys() {
        List<Key> list = new ArrayList<>();
        for (Node i = first; i != null; i = i.next) {
            list.add(i.key);
        }
        return list;
    }

    public static void main(String[] args) {
        SequentialSearch<String, Integer> st = new SequentialSearch<>();
        st.put("W", 1);
        st.put("A", 8);
        st.put("N", 8);
        st.put("G", 9);
        System.out.println(st.get("A"));
        System.out.println(st.keys());
    }
}
