/**
 * <p>Title: LeastRecentlyUsedCache</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/1/21</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.others;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * <p>设计RandomPool结构</p>
 *
 * @author wangzi
 */
public class RandomPool {
    public static class Pool<K> {
        /**
         * key到index的映射
         */
        private Map<K, Integer> keyIndexMap;
        /**
         * index到key的映射
         */
        private Map<Integer, K> indexKeyMap;
        /**
         * 目前pool的大小
         */
        private int size;

        public Pool() {
            this.keyIndexMap = new HashMap<>();
            this.indexKeyMap = new HashMap<>();
            this.size = 0;
        }

        public void insert(K key) {
            if (!this.keyIndexMap.containsKey(key)) {
                this.keyIndexMap.put(key, this.size);
                this.indexKeyMap.put(this.size++, key);
            }
        }

        public void delete(K key) {
            if (!this.keyIndexMap.containsKey(key)) {
                return;
            }
            int deleteIndex = this.keyIndexMap.get(key);
            // 最新加入key的index
            int lastIndex = --this.size;
            // 最新加入的key
            K lastKey = this.indexKeyMap.get(lastIndex);
            // 把lastKey放到key的位置上，保证index还是连续的
            this.keyIndexMap.put(lastKey, deleteIndex);
            this.indexKeyMap.put(deleteIndex, lastKey);
            // 删除(key,deleteIndex)
            this.keyIndexMap.remove(key);
            this.indexKeyMap.remove(lastIndex);
        }

        public K getRandom() {
            if (this.size == 0) {
                return null;
            }
            return this.indexKeyMap.get(new Random().nextInt(this.size));
        }
    }

    public static void main(String[] args) {
        Pool<String> pool = new Pool<>();
        pool.insert("A");
        pool.insert("B");
        pool.insert("C");
        for (int i = 0; i < 20; i++) {
            System.out.print(pool.getRandom() + " ");
        }

        System.out.println();

        pool.delete("B");
        for (int i = 0; i < 20; i++) {
            System.out.print(pool.getRandom() + " ");
        }
    }
}
