package com.wz;

import com.wz.recursionanddp.LongestConsecutive;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class LongestConsecutiveTest {
    private static int solution(int[] array) {
        Map<Integer, Integer> map = new HashMap<>();
        int result = 0;
        for (int cur : array) {
            if (map.containsKey(cur)) {
                continue;
            }
            map.put(cur, 1);
            if (map.containsKey(cur - 1)) {
                result = Math.max(result, merge(map, cur - 1, cur));
            }
            if (map.containsKey(cur + 1)) {
                result = Math.max(result, merge(map, cur, cur + 1));
            }
        }
        return result;
    }

    private static int merge(Map<Integer, Integer> map, int small, int big) {
        int left = small - map.get(small) + 1;
        int right = big + map.get(big) - 1;
        int len = right - left + 1;
        map.put(left, len);
        map.put(right, len);
        return len;
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
