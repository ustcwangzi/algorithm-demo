/**
 * <p>Title: FindKMajority</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/12/28</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.arrayandmatrix;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * <p>数组中找到出现次数大于N/K的数</p>
 *
 * @author wangzi
 */
public class FindKMajority {
    public static void printHalfMajor(int[] array) {
        if (array == null || array.length == 0) {
            return;
        }
        // 候选
        int candidate = 0;
        // 次数
        int times = 0;
        // 每次删掉两个不同的数
        for (int cur : array) {
            if (times == 0) {
                candidate = cur;
                times = 1;
            } else if (cur == candidate) {
                times++;
            } else {
                times--;
            }
        }
        times = 0;
        // 统计出现次数
        for (int cur : array) {
            if (cur == candidate) {
                times++;
            }
        }
        System.out.println(times > array.length / 2 ? candidate : "no such number");
    }

    public static void printKMajor(int[] array, int k) {
        if (k < 2) {
            System.out.println("the vaule k is invalid");
            return;
        }
        // 候选
        Map<Integer, Integer> candidateMap = new HashMap<>();
        for (int cur : array) {
            if (candidateMap.containsKey(cur)) {
                candidateMap.put(cur, candidateMap.get(cur) + 1);
            } else {
                if (candidateMap.size() == k - 1) {
                    allCandidateMinusOne(candidateMap);
                } else {
                    candidateMap.put(cur, 1);
                }
            }
        }

        // 真实出现次数
        Map<Integer, Integer> realTimesMap = getRealTimesMap(array, candidateMap);
        boolean hasPrint = false;
        for (Map.Entry<Integer, Integer> entry : candidateMap.entrySet()) {
            Integer key = entry.getKey();
            if (realTimesMap.get(key) > array.length / k) {
                hasPrint = true;
                System.out.print(key + " ");
            }
        }
        System.out.println(hasPrint ? "" : "no such number");
    }

    /**
     * 每个候选的点数全部减1，减1后等于0的候选需要删除
     */
    private static void allCandidateMinusOne(Map<Integer, Integer> map) {
        List<Integer> removeList = new LinkedList<>();
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            Integer key = entry.getKey();
            Integer value = entry.getValue();
            if (value == 1) {
                removeList.add(key);
            }
            map.put(key, value - 1);
        }
        for (Integer removeKey : removeList) {
            map.remove(removeKey);
        }
    }

    /**
     * 获取每个候选的真实出现次数
     */
    private static Map<Integer, Integer> getRealTimesMap(int[] array, Map<Integer, Integer> candidateMap) {
        Map<Integer, Integer> realTimesMap = new HashMap<>();
        for (int cur : array) {
            if (realTimesMap.containsKey(cur)) {
                realTimesMap.put(cur, realTimesMap.get(cur) + 1);
            } else {
                realTimesMap.put(cur, 1);
            }
        }
        return realTimesMap;
    }

    public static void main(String[] args) {
        int[] array1 = {1, 2, 3, 1, 1, 2, 1};
        printHalfMajor(array1);
        printKMajor(array1, 4);

        int[] array2 = {1, 2, 3};
        printHalfMajor(array2);
        printKMajor(array2, 2);
    }
}
