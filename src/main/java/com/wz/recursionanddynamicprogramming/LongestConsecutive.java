/**
 * <p>Title: LongestConsecutive</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/11/24</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.recursionanddynamicprogramming;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>数组中的最长连续序列</p>
 *
 * @author wangzi
 */
public class LongestConsecutive {
    public static int longestConsecutive(int[] array) {
        if (array == null || array.length == 0) {
            return 0;
        }

        int max = 1;
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < array.length; i++) {
            if (map.containsKey(array[i])) {
                continue;
            }
            map.put(array[i], 1);
            if (map.containsKey(array[i] - 1)) {
                max = Math.max(max, merge(map, array[i] - 1, array[i]));
            }
            if (map.containsKey(array[i] + 1)) {
                max = Math.max(max, merge(map, array[i], array[i] + 1));
            }
        }
        return max;
    }

    private static int merge(Map<Integer, Integer> map, int less, int more) {
        int left = less - map.get(less) + 1;
        int right = more + map.get(more) - 1;
        int len = right - left + 1;
        map.put(left, len);
        map.put(right, len);
        return len;
    }

    public static void main(String[] args) {
        int[] array = {100, 4, 200, 1, 3, 2};
        System.out.println(longestConsecutive(array));
    }
}
