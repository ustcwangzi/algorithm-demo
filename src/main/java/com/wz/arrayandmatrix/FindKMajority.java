/**
 * <p>Title: FindKMajority</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/12/28</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.arrayandmatrix;

import java.util.*;

/**
 * <p>数组中找到出现次数大于N/K的数</p>
 * <p>
 *     问题一：
 *        给定一个整数数组array，找出出现次数大于一半的数。
 *     问题二：
 *        给定一个整数数组array，再给定一个整数K，找出所有出现次数大于N/K的数。
 *     问题一和问题二都可以使用哈希表来记录每个数的出现次数，但空间复杂度为O(N)，此处不再详述。
 *     核心思路：每次在数组中删除K个不同的数，直到剩下数的种类不足K时停止，那么如果某个数在数组中出现的次数大于N/K，则这个数最后一定会被剩下。
 *     问题一解答：
 *        每次在数组中删除两个不同的数，直到剩下的数只有一种，如果一个数出现次数大于一半，则这个数最后一定会被剩下。
 *        第一次循环时就是每次在数组中删除两个不同的数，candidate为候选，times为次数。
 *        times==0表示当前没有候选，把当前数设为候选，同时times设为1，说明此时找到了两个不同数中的第一个；
 *        times!=0，表示当前有候选，若当前数和候选一样，说明没有找到两个不同数中的另一个，times++表示反复出现的数在累计自己的点数；
 *        若当前数和候选不一样，说明找全了两个不同的数，但候选可能已经出现了多次，times--表示候选付出一个自己的点数，然后当前数也被删掉。
 *        这样相当于一次删掉了两个不同的数，如果times被减到0，说明候选的点数被消耗完，将数组中的下一个数作为候选。
 *        第二个循环用于统计第一个循环中剩下数的真实出现次数，因为一个数出现次数大于一半肯定会被剩下，但是剩下的数不一定出现次数大于一半，
 *        比如[1,2,1]，1会被剩下；而[1,2,3]没有任何数出现次数超过一半，但3会剩下。所以需要第二个循环统计剩下数的出现次数。
 *     问题二解答：
 *        每次在数组中删除k个不同的数，直到剩下的数的种类不足k。与问题一的解答类似，这里需要k-1个候选和k-1个times统计。过程：
 *        遍历到array[i]时，看array[i]是否与已经选出的某个候选相同；
 *        若与某个候选相同，则将那个候选的点数加1；若不同，看当前的候选是否选满，K-1就是满了，否则就是不满；
 *        如果不满，把array[i]作为新的候选，点数初始化为1；
 *        如果已满，说明发现了K个不同的数，array[i]就是第K个。把每个候选的点数减1，若某些候选点数减1后为0，还需要把这些候选删除。
 *        然后再遍历一次array，获取每个候选真实的出现次数。
 * </p>
 * <p>
 *     问题一时间复杂度为O(N)，空间复杂度为O(1)
 *     问题二时间复杂度为O(N*K)，空间复杂度为O(K)
 * </p>
 *
 * @author wangzi
 */
public class FindKMajority {
    public static int halfMajor(int[] array) {
        if (array == null || array.length == 0) {
            return -1;
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
                // candidate与当前值不同，删除当前值，candidate次数也减一
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
        return times > array.length / 2 ? candidate : -1;
    }

    public static Integer[] kMajor(int[] array, int k) {
        List<Integer> result = new ArrayList<>();
        if (k < 2) {
            System.out.println("the value k is invalid");
            return result.toArray(new Integer[result.size()]);
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
        Map<Integer, Integer> timesMap = getRealTimesMap(array, candidateMap);
        for (Map.Entry<Integer, Integer> entry : candidateMap.entrySet()) {
            Integer key = entry.getKey();
            if (timesMap.get(key) > array.length / k) {
                result.add(key);
            }
        }
        return result.toArray(new Integer[result.size()]);
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
            if (!candidateMap.containsKey(cur)) {
                continue;
            }
            if (realTimesMap.containsKey(cur)) {
                realTimesMap.put(cur, realTimesMap.get(cur) + 1);
            } else {
                realTimesMap.put(cur, 1);
            }
        }
        return realTimesMap;
    }

    public static void main(String[] args) {
        int[] array1 = {2, 1, 3, 1, 1, 2, 1};
        System.out.println(halfMajor(array1));
        System.out.println(Arrays.toString(kMajor(array1, 4)));

        int[] array2 = {1, 2, 3};
        System.out.println(halfMajor(array2));
        System.out.println(Arrays.toString(kMajor(array2, 2)));
    }
}
