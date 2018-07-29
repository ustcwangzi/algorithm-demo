/**
 * <p>Title: BinarySearch</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/6/24</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.search;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>二分查找</p>
 * <p>
 *     基于有序数组实现二分查找
 *     rank()使用迭代实现，保留了以下性质：
 *     1、若数组中存在该键，返回该键的位置，即数组中小于它的键的数量
 *     2、若数组中不存在该键，依然返回数组中小于它的键的数量
 * </p>
 * <p>N个键的有序数组中进行二分查找，最多需要 logN + 1 次比较</p>
 * <p>最坏情况下，插入成本是 N，查找成本是 logN</p>
 * <p>平均情况下，插入成本是 2*N，查找成本是 logN</p>
 *
 * @author wangzi
 */
public class BinarySearch<Key extends Comparable, Value> {
    private static final int INIT_CAPACITY = 10;
    private int n;
    private Key[] keys;
    private Value[] values;

    public BinarySearch() {
        this(INIT_CAPACITY);
    }

    public BinarySearch(int capacity) {
        keys = (Key[]) new Comparable[capacity];
        values = (Value[]) new Object[capacity];
    }

    private void resize(int capacity) {
        assert capacity >= n;
        Key[] tmpk = (Key[]) new Comparable[capacity];
        Value[] tmpv = (Value[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            tmpk[i] = keys[i];
            tmpv[i] = values[i];
        }
        keys = tmpk;
        values = tmpv;
    }

    public int size() {
        return n;
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
        if (isEmpty()) {
            return null;
        }
        int i = rank(key);
        if (i < n && keys[i].compareTo(key) == 0) {
            return values[i];
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

        int i = rank(key);
        // key已存在
        if (i < n && keys[i].compareTo(key) == 0) {
            values[i] = value;
            return;
        }

        if (n == keys.length) {
            resize(keys.length * 2);
        }

        // i之后的元素向后移动
        for (int j = n; j > i; j--) {
            keys[j] = keys[j - 1];
            values[j] = values[j - 1];
        }
        keys[i] = key;
        values[i] = value;
        n++;

        assert isSorted();
    }

    public void delete(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument is null");
        }
        if (isEmpty()) {
            return;
        }

        int i = rank(key);
        // key不存在
        if (i == n || keys[i].compareTo(key) != 0) {
            return;
        }

        // 从i开始，元素向前移动
        for (int j = i; j < n - 1; j++) {
            keys[j] = keys[j + 1];
            values[j] = values[j + 1];
        }
        n--;
        keys[n] = null;
        values[n] = null;

        if (n > 0 && n == keys.length / 4) {
            resize(keys.length / 2);
        }

        assert isSorted();
    }

    /**
     * 返回小于给定key的键的数量
     */
    public int rank(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument is null");
        }
        int low = 0, high = n - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            int cmp = key.compareTo(keys[mid]);
            if (cmp < 0) {
                high = mid - 1;
            } else if (cmp > 0) {
                low = mid + 1;
            } else {
                return mid;
            }
        }
        return low;
    }

    public Iterable<Key> keys(Key low, Key high) {
        if (low == null) {
            throw new IllegalArgumentException("argument is null");
        }
        if (high == null) {
            throw new IllegalArgumentException("argument is null");
        }

        List<Key> list = new ArrayList<>();
        if (low.compareTo(high) > 0) {
            return list;
        }

        for (int i = rank(low); i < rank(high); i++) {
            list.add(keys[i]);
        }
        if (contains(high)) {
            list.add(keys[rank(high)]);
        }
        return list;
    }

    private boolean isSorted() {
        for (int i = 1; i < size(); i++) {
            if (keys[i].compareTo(keys[i - 1]) < 0) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        BinarySearch<String, Integer> bs = new BinarySearch<>();
        bs.put("W", 1);
        bs.put("A", 8);
        bs.put("N", 8);
        bs.put("G", 9);
        System.out.println(bs.isSorted());
    }
}
