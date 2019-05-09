package com.wz;

import com.wz.recursionanddp.LongestConsecutive;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>数组元素能组成的最长连续递增序列</p>
 *
 * @author wangzi
 */
public class LongestConsecutiveTest {
    private static int solution(int[] array) {
        // 记录元素所在的连续序列长度
        Map<Integer, Integer> map = new HashMap<>();
        int result = 0;
        for (int cur : array) {
            if (map.containsKey(cur)) {
                continue;
            }
            map.put(cur, 1);
            // 序列合并，长度相加
            if (map.containsKey(cur - 1)) {
                result = Math.max(result, merge(map, cur - 1, cur));
            }
            if (map.containsKey(cur + 1)) {
                result = Math.max(result, merge(map, cur, cur + 1));
            }
        }
        return result;
    }

    /**
     * 将small和big所在的连续序列进行合并，只更新最大最小值的序列长度，中间记录不再更新
     */
    private static int merge(Map<Integer, Integer> map, int left, int right) {
        // 序列中的最小元素
        int min = left - map.get(left) + 1;
        // 序列中的最大元素
        int max = right + map.get(right) - 1;
        int length = max - min + 1;
        map.put(min, length);
        map.put(max, length);
        return length;
    }

    public static void main(String[] args) {
        int times = 100;
        boolean result = true;
        for (int i = 0; i < times; i++) {
            int[] array = RandomUtils.genRandomArray();
            if (solution(array) != LongestConsecutive.longestConsecutive(array)) {
                result = false;
                System.out.println("Error, array:" + Arrays.toString(array));
            }
        }
        if (result) {
            System.out.println("Past");
        }
    }
}
