/**
 * <p>Title: FindKMajorityTest</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/4/14</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz;

import com.wz.arrayandmatrix.FindKMajority;

import java.util.*;

/**
 * <p></p>
 *
 * @author wangzi
 */
public class FindKMajorityTest {
    private static Integer[] solution(int[] array, int k) {
        Map<Integer, Integer> candidateMap = new HashMap<>();
        for (int cur : array) {
            if (candidateMap.containsKey(cur)) {
                // 次数加一
                candidateMap.put(cur, candidateMap.get(cur) + 1);
            } else {
                if (candidateMap.size() == k - 1) {
                    allCandidateMinusOne(candidateMap);
                } else {
                    candidateMap.put(cur, 1);
                }
            }
        }

        int threshold = array.length / k;
        List<Integer> result = new ArrayList<>();
        Map<Integer, Integer> timesMap = getTimesMap(array, candidateMap);
        for (Map.Entry<Integer, Integer> entry : timesMap.entrySet()) {
            if (entry.getValue() > threshold) {
                result.add(entry.getKey());
            }
        }
        return result.toArray(new Integer[result.size()]);
    }

    private static Map<Integer, Integer> getTimesMap(int[] array, Map<Integer, Integer> candidateMap) {
        Map<Integer, Integer> timesMap = new HashMap<>(candidateMap.size());
        for (int cur : array) {
            if (!candidateMap.containsKey(cur)) {
                continue;
            }
            if (timesMap.containsKey(cur)) {
                timesMap.put(cur, timesMap.get(cur) + 1);
            } else {
                timesMap.put(cur, 1);
            }
        }
        return timesMap;
    }

    private static void allCandidateMinusOne(Map<Integer, Integer> map) {
        List<Integer> removeKeys = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (entry.getValue() == 1) {
                removeKeys.add(entry.getKey());
            } else {
                map.put(entry.getKey(), entry.getValue() - 1);
            }
        }
        for (Integer key : removeKeys) {
            map.remove(key);
        }
    }

    public static void main(String[] args) {
        int times = 100;
        boolean result = true;
        for (int i = 0; i < times; i++) {
            for (int j = 2; j < times; j++) {
                int[] array = RandomUtils.genRandomArray();
                Integer[] resultA = solution(array, j);
                Integer[] resultB = FindKMajority.kMajor(array, j);
                Arrays.sort(resultA);
                Arrays.sort(resultB);
                if (!Arrays.equals(resultA, resultB)) {
                    result = false;
                    System.out.println("Error, array:" + Arrays.toString(array) + ",k:" + j);
                }
            }
        }
        if (result) {
            System.out.println("Past");
        }
    }
}
