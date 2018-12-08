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
 * <p>数组中两个字符串的最小距离</p>
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
        private Map<String, Map<String, Integer>> record;

        public Record(String[] array) {
            record = new HashMap<>();
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

            Map<String, Integer> strMap = record.get(str);
            for (Map.Entry<String, Integer> entry : indexMap.entrySet()) {
                String key = entry.getKey();
                int index = entry.getValue();
                if (!key.equals(str)) {
                    Map<String, Integer> latestMap = record.get(key);
                    int curMin = i - index;
                    if (strMap.containsKey(key)) {
                        if (strMap.get(key) > curMin) {
                            strMap.put(key, curMin);
                            latestMap.put(str, curMin);
                        }
                    } else {
                        strMap.put(key, curMin);
                        latestMap.put(str, curMin);
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
