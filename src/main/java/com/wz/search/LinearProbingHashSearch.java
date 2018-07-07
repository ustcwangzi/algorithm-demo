/**
 * <p>Title: LinearProbingHashSearch</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/7/7</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.search;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>基于线性探测的散列表查找</p>
 *
 * <p>发生冲突的元素直接放在下一个位置</p>
 *
 * @author wangzi
 */
public class LinearProbingHashSearch<Key, Value> {
    private static final int INIT_CAPACITY = 4;

    /** 存储键值对的数量 */
    private int paris;
    /** hash表的长度 */
    private int tableSize;
    private Key[] keys;
    private Value[] values;

    public LinearProbingHashSearch() {
        this(INIT_CAPACITY);
    }

    public LinearProbingHashSearch(int capacity) {
        this.paris = 0;
        this.tableSize = capacity;
        this.keys = (Key[]) new Object[capacity];
        this.values = (Value[]) new Object[capacity];
    }

    public int size(){
        return paris;
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

    private int hash(Key key){
        return (key.hashCode() & 0x7fffffff) % tableSize;
    }

    private void resize(int capacity){
        LinearProbingHashSearch<Key, Value> temp = new LinearProbingHashSearch<>(capacity);
        for (int i = 0; i < tableSize; i++){
            if (keys[i] != null){
                temp.put(keys[i], values[i]);
            }
        }
        keys = temp.keys;
        values = temp.values;
        tableSize = temp.tableSize;
    }

    public void put(Key key, Value value){
        if (key == null){
            throw new IllegalArgumentException("argument is null");
        }
        if (value == null){
            delete(key);
            return;
        }

        if (paris >= tableSize/2){
            resize(2*tableSize);
        }

        int i;
        for (i = hash(key); keys[i] != null; i = (i+1) % tableSize){
            if (keys[i].equals(key)){
                values[i] = value;
                return;
            }
        }
        keys[i] = key;
        values[i] = value;
        paris++;
    }

    public Value get(Key key) {
        if (key == null){
            throw new IllegalArgumentException("argument is null");
        }
        for (int i = hash(key); keys[i] != null; i = (i+1) % tableSize){
            if (keys[i].equals(key)){
                return values[i];
            }
        }
        return null;
    }

    public void delete(Key key){
        if (key == null){
            throw new IllegalArgumentException("argument is null");
        }
        if (!contains(key)){
            return;
        }

        int i = hash(key);
        while (!key.equals(keys[i])){
            i = (i + 1) % tableSize;
        }
        keys[i] = null;
        values[i] = null;

        // rehash
        i = (i + 1) % tableSize;
        while (keys[i] != null){
            Key keyToRehash = keys[i];
            Value valToRehash = values[i];
            keys[i] = null;
            values[i] = null;
            paris--;
            put(keyToRehash, valToRehash);
            i = (i + 1) % tableSize;
        }

        paris--;
        if (paris > 0 && paris <= tableSize/8){
            resize(tableSize/2);
        }
    }

    public Iterable<Key> keys(){
        List<Key> list = new ArrayList<>();
        for (int i = 0; i < tableSize; i++) {
           if (keys[i] != null){
                list.add(keys[i]);
            }
        }
        return list;
    }

    private boolean check(){
        for (int i = 0; i < tableSize; i++) {
            if (keys[i] != null && get(keys[i]) != values[i]) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        LinearProbingHashSearch<String, Integer> st = new LinearProbingHashSearch<>();
        st.put("W", 1);
        st.put("A", 8);
        st.put("N", 8);
        st.put("G", 9);
        st.put("Z", 6);
        st.put("I", 7);
        System.out.println(st.size());
        System.out.println(st.get("A"));
        System.out.println(st.keys());
        System.out.println(st.check());
    }
}
