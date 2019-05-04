/**
 * <p>Title: MinDistance</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/12/8</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.string;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>数组中两个字符的最小距离</p>
 * <p>
 *     问题一：
 *        给定字符串数组array，在给定两个字符串self和other，求出array中self和other的最小距离，
 *        如果self或other为null，或不存在，返回-1。
 *     问题二：
 *        在问题一的基础上，如果查询发生的次数有很多，如何把每次查询的时间复杂度将为O(1)。
 *     问题一解答：
 *        从左到右遍历array，latestSelf记录最近一次出现self的位置，latestOther记录最近一次出现other的位置，
 *        假设当前遍历到位置i
 *        1、若字符串为self，那么i-latestOther就是当前self和左边离它最近的other之间的距离
 *        2、若字符串为other，那么i-latestSelf就是当前other和左边离它最近的self之间的距离
 *        用min记录这些距离中的最小值就是最后的结果。
 *     问题二解答：
 *        使用哈希表保存各个字符串直接的最小距离，其中key为String类型，value还是一个哈希表类型
 *        把外层的哈希表叫作外哈希表，value代表的哈希表叫作内哈希表，外哈希表的key代表array中某个字符串
 *        内哈希表的key表示字符串，value表示其他字符串到key的最小距离。这样在查询时只用了两次哈希查询操作。
 * </p>
 * <p>
 *     问题一时间复杂度为O(1)，空间复杂度为O(1)
 *     问题二生成记录的时间复杂度为O(N*N)，记录所用的空间复杂度为O(N*N)，生成记录后的查询操作时间复杂度为O(1)
 * </p>
 *
 * @author wangzi
 */
public class MinDistance {
    public static int minDistance(String[] array, String self, String other) {
        if (self == null || other == null) {
            return -1;
        }
        if (self.equals(other)) {
            return 0;
        }

        int latestSelf = -1;
        int latestOther = -1;
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(self)) {
                min = Math.min(min, latestOther == -1 ? min : i - latestOther);
                latestSelf = i;
            }
            if (array[i].equals(other)) {
                min = Math.min(min, latestSelf == -1 ? min : i - latestSelf);
                latestOther = i;
            }
        }

        return min == Integer.MAX_VALUE ? -1 : min;
    }

    public static class Record {
        /**
         * 所有距离保存在record中
         */
        private Map<String, Map<String, Integer>> record;

        public Record(String[] array) {
            record = new HashMap<>();
            // 每个字符串最后出现的位置
            Map<String, Integer> indexMap = new HashMap<>();

            for (int i = 0; i < array.length; i++) {
                String cur = array[i];
                update(indexMap, cur, i);
                indexMap.put(cur, i);
            }
        }

        private void update(Map<String, Integer> indexMap, String str, int i) {
            if (!record.containsKey(str)) {
                record.put(str, new HashMap<>());
            }

            // str与其他字符串的距离
            Map<String, Integer> strDistanceMap = record.get(str);
            for (Map.Entry<String, Integer> entry : indexMap.entrySet()) {
                // 字符串
                String key = entry.getKey();
                // 字符串最后出现的位置
                int index = entry.getValue();
                if (!key.equals(str)) {
                    // key与其他字符串的距离
                    Map<String, Integer> keyDistanceMap = record.get(key);
                    int curMin = i - index;
                    if (strDistanceMap.containsKey(key)) {
                        if (strDistanceMap.get(key) > curMin) {
                            strDistanceMap.put(key, curMin);
                            keyDistanceMap.put(str, curMin);
                        }
                    } else {
                        strDistanceMap.put(key, curMin);
                        keyDistanceMap.put(str, curMin);
                    }
                }
            }
        }

        public int minDistance(String self, String other) {
            if (self == null || other == null) {
                return -1;
            }
            if (self.equals(other)) {
                return 0;
            }

            if (record.containsKey(self) && record.get(self).containsKey(other)) {
                return record.get(self).get(other);
            }
            return -1;
        }
    }

    public static void main(String[] args) {
        String[] array = {"4", "2", "2", "3", "2", "2", "3", "1", "1", "3"};
        System.out.println(minDistance(array, "4", "3"));
        System.out.println(minDistance(array, "2", "3"));
        System.out.println(minDistance(array, "2", "1"));

        System.out.println("======");

        Record record = new Record(array);
        System.out.println(record.minDistance("4", "3"));
        System.out.println(record.minDistance("2", "3"));
        System.out.println(record.minDistance("2", "1"));
    }
}
