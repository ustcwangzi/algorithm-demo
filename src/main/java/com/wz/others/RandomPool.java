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
 * <p>
 *     设计RandomPool结构，在该结构中有如下三个功能：
 *     1、insert(key)：将key加入到该结构，做到不重复加入；
 *     2、delete(key)：将原本结构中的key移除；
 *     3、getRandom()：等概率随机返回结构中的任何一个key。
 *     要求insert、delete和getRandom操作的时间复杂度都是O(1)。
 *     解决方案：
 *        1、使用两个map分别记录key到index的映射keyIndexMap、以及index到key的映射indexKeyMap
 *        2、使用size记录目前Pool的大小，初识时size为0
 *        3、执行insert(key)时，将(key,size)放入keyIndexMap，将(size,key)放入indexKeyMap，然后size加1，即每次insert后size自增
 *        4、执行delete(key)时，假设Poll最新加入的key为lastKey，index为lastIndex，要删除的key为deleteKey，index为deleteIndex。
 *           在keyIndexMap中将(lastKey,lastIndex)变为(lastKey,deleteIndex)；
 *           在indexKeyMap中将(deleteIndex,deleteKey)变为(deleteIndex,lastKey)；
 *           在keyIndexMap中删除(deleteKey,deleteIndex)；
 *           在indexKeyMap中删除(lastIndex,lastKey)；
 *           然后size减1。这样做相当于把lastKey放到了deleteKey的位置上，保证记录的index还是连续的。
 *        5、执行getRandom()时，根据当前size随机得到一个index，步骤四可以保证index在[0...size-1]上，都对应着有效的key，
 *           然后把index对应的key返回即可。
 * </p>
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
