/**
 * <p>Title: SetCoverage</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/9/9</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.grokking;

import java.util.*;

/**
 * <p>集合覆盖</p>
 * <p>
 *     使用贪婪算法，每次选择覆盖了最多的未覆盖地区的信号塔
 *     完美的解决方案需要列出每个可能的信号塔集合，然后选出覆盖所有区域的最小集合，时间为O(2^n)
 *     这种情况下的贪婪算法，只能得到近似结果，但比完美解决方案的效率高很多，时间为O(n * n)
 * </p>
 *
 * @author wangzi
 */
public class SetCoverage {
    /**
     * 需要信号的地区集合
     */
    private static Set<Integer> areaSet = new HashSet<>();
    /**
     * 信号塔覆盖地区
     */
    private static Map<String, Set<Integer>> coverageMap = new HashMap<>();

    private static Set<String> findStation() {
        Set<String> stations = new HashSet<>();
        while (!areaSet.isEmpty()) {
            // 在剩余集合中寻找覆盖区域最大的信号塔
            String bestStation = null;
            // 覆盖区域
            Set<Integer> statesCovered = new HashSet<>();

            for (Map.Entry<String, Set<Integer>> entry : coverageMap.entrySet()) {
                // 覆盖区域
                Set<Integer> cover = new HashSet<>(areaSet);
                // 取交集
                cover.retainAll(entry.getValue());

                if (cover.size() > statesCovered.size()) {
                    bestStation = entry.getKey();
                    statesCovered = cover;
                }
            }
            // 去除已覆盖的地区
            areaSet.removeIf(statesCovered::contains);
            if (bestStation != null) {
                stations.add(bestStation);
                coverageMap.remove(bestStation);
            }
        }
        return stations;
    }

    public static void main(String[] args) {
        areaSet = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5, 6));
        coverageMap.put("A", new HashSet<>(Arrays.asList(1, 2, 3)));
        coverageMap.put("B", new HashSet<>(Arrays.asList(2, 3, 8)));
        coverageMap.put("C", new HashSet<>(Arrays.asList(4, 5, 9)));
        coverageMap.put("D", new HashSet<>(Arrays.asList(3, 4)));
        coverageMap.put("E", new HashSet<>(Arrays.asList(1, 2, 6)));
        coverageMap.put("F", new HashSet<>(Collections.singleton(6)));

        System.out.println(findStation());
    }
}
