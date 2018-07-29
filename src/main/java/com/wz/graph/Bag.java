/**
 * <p>Title: Bag</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/7/8</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.graph;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * <p>复合数据结构</p>
 *
 * @author wangzi
 */
public class Bag<Item> implements Iterable<Item> {
    private Node<Item> first;
    private int size;

    private static class Node<Item> {
        private Item item;
        private Node<Item> next;
    }

    public Bag() {
        this.first = null;
        this.size = 0;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public int size() {
        return size;
    }

    /**
     * 头插法加入元素
     */
    public void add(Item item) {
        Node<Item> oldFirst = first;
        first = new Node<>();
        first.item = item;
        first.next = oldFirst;
        size++;
    }

    @Override
    public Iterator<Item> iterator() {
        return new ListIterator<>(first);
    }

    private class ListIterator<Item> implements Iterator<Item> {
        private Node<Item> current;

        public ListIterator(Node<Item> first) {
            this.current = first;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    public static void main(String[] args) {
        Bag<String> bag = new Bag<>();
        bag.add("W");
        bag.add("A");
        bag.add("N");
        bag.add("G");
        bag.forEach(s -> System.out.println(s));
    }
}
