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
 * <p>
 *     哈希表常见的三个操作为put、get和containsKey，时间复杂度都是O(1)。现在想加一个setAll功能，把所有记录的value都更新成同一个值。
 *     请实现这种有setAll功能的哈希表，并且put、get、containsKey和setAll四个操作的时间复杂度都是O(1)。
 *     解决方案：
 *        加入一个时间戳结构。
 *        1、把每一个记录都加上一个时间，标记这条记录是何时创建的
 *        2、设置setAll时也加上一个时间，标记setAll是何时创建的
 *        3、查询记录时，如果某条记录的时间早于setAll记录的时间，说明setAll是在后面执行的，返回setAll记录的值；
 *           如果某条记录的时间晚于setAll记录的时间，说明记录是在后面创建的，返回该条记录的值。
 * </p>
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
