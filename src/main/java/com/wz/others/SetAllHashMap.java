/**
 * <p>Title: SetAllHashMap</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/1/19</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.others;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>设计有setAll功能的哈希表</p>
 *
 * @author wangzi
 */
public class SetAllHashMap {

    private static class Value<V> {
        private V value;
        private long time;

        public Value(V value, long time) {
            this.value = value;
            this.time = time;
        }

        public V getValue() {
            return value;
        }

        public long getTime() {
            return time;
        }
    }

    public static class MyHashMap<K, V> {
        private Map<K, Value<V>> baseMap;
        private long time;
        private Value<V> setAll;

        public MyHashMap() {
            this.baseMap = new HashMap<>();
            this.time = 0;
            this.setAll = new Value<>(null, -1);
        }

        public boolean containsKey(K key) {
            return this.baseMap.containsKey(key);
        }

        public void put(K key, V value) {
            // 标记记录创建的时间戳
            this.baseMap.put(key, new Value<>(value, this.time++));
        }

        public void setAll(V value) {
            // 标记setAll的时间戳
            this.setAll = new Value<>(value, this.time++);
        }

        public V get(K key) {
            if (!this.containsKey(key)) {
                return null;
            }
            if (this.baseMap.get(key).getTime() > this.setAll.getTime()) {
                // setAll 之后的数据，使用记录的值
                return this.baseMap.get(key).getValue();
            } else {
                // setAll 之前的数据，使用setAll的值
                return this.setAll.getValue();
            }
        }
    }

    public static void main(String[] args) {
        MyHashMap<String, Integer> map = new MyHashMap<>();
        map.put("Tom", 1);
        map.put("James", 2);

        System.out.println(map.containsKey("Tom"));
        System.out.println(map.get("Tom"));

        map.setAll(3);
        map.put("Rose", 4);
        System.out.println(map.get("Tom"));
        System.out.println(map.get("James"));
        System.out.println(map.get("Rose"));
    }
}
