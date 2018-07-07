/**
 * <p>Title: SeparateChainingHashSearch</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/7/7</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.search;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>基于拉链法的散列表</p>
 *
 * <p>发生冲突的元素都存储在链表中</p>
 *
 * @author wangzi
 */
public class SeparateChainingHashSearch<Key, Value> {
    private static final int INIT_CAPACITY = 4;

    /** 存储键值对的数量 */
    private int paris;
    /** hash表的长度 */
    private int tableSize;
    /** 使用链表存储数据 */
    private SequentialSearch<Key, Value>[] st;

    public SeparateChainingHashSearch() {
        this(INIT_CAPACITY);
    }

    public SeparateChainingHashSearch(int tableSize) {
        this.tableSize = tableSize;
        st = (SequentialSearch<Key, Value>[]) new SequentialSearch[tableSize];
        for (int i = 0; i < tableSize; i++){
            st[i] = new SequentialSearch<>();
        }
    }

    private void resize(int chains){
        SeparateChainingHashSearch<Key, Value> temp = new SeparateChainingHashSearch<>(chains);
        for (int i = 0; i< tableSize; i++){
            for (Key key : st[i].keys()){
                temp.put(key, st[i].get(key));
            }
        }
        this.paris = temp.paris;
        this.tableSize = temp.tableSize;
        this.st = temp.st;
    }

    private int hash(Key key){
        return (key.hashCode() & 0x7fffffff) % tableSize;
    }

    public int size(){
        return tableSize;
    }

    public boolean isEmpty(){
        return size() == 0;
    }

    public boolean contains(Key key){
        if (key == null){
            throw new IllegalArgumentException("argument is null");
        }
        return get(key) != null;
    }

    public Value get(Key key){
        if (key == null){
            throw new IllegalArgumentException("argument is null");
        }
        int i = hash(key);
        return st[i].get(key);
    }

    public void put(Key key, Value value){
        if (key == null){
            throw new IllegalArgumentException("argument is null");
        }
        if (value == null){
            delete(key);
            return;
        }

        if (paris >= 10*tableSize){
            resize(2*tableSize);
        }

        int i = hash(key);
        if (!st[i].contains(key)){
            paris++;
        }
        st[i].put(key, value);
    }

    public void delete(Key key){
        if (key == null){
            throw new IllegalArgumentException("argument is null");
        }

        int i = hash(key);
        if (st[i].contains(key)){
            paris--;
        }
        st[i].delete(key);

        if (tableSize > INIT_CAPACITY && paris <= 2*tableSize){
            resize(tableSize/2);
        }
    }

    public Iterable<Key> keys(){
        List<Key> list = new ArrayList<>();
        for (int i = 0; i < tableSize; i++) {
            for (Key key : st[i].keys()) {
                list.add(key);
            }
        }
        return list;
    }

    public static void main(String[] args) {
        SeparateChainingHashSearch<String, Integer> st = new SeparateChainingHashSearch<>();
        st.put("W", 1);
        st.put("A", 8);
        st.put("N", 8);
        st.put("G", 9);
        st.put("Z", 6);
        st.put("I", 7);
        System.out.println(st.size());
        System.out.println(st.get("A"));
        System.out.println(st.keys());
    }

}
