/**
 * <p>Title: LongestConsecutive</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/11/24</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.recursionanddp;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>数组中的最长连续序列</p>
 * <p>
 *     给定无序数组array，求出其中最长的连续序列长度。
 *     例如array=[100,4,200,1,3,2]，最长的连续序列为[1,2,3,4]，长度为4。
 *     解决过程：
 *     1、生成哈希表map，key代表遍历过的某个元素，value代表key所在的连续序列长度
 *     2、从左到右遍历array，假设遍历到array[i]，若array[i]之前出现过，直接遍历下一个元素，否则将(array[i],1)加入map，
 *        代表array[i]单独作为一个连续序列。
 *     2.1、看map中是否存在array[i]-1，若存在，将array[i]-1所在的序列与array[i]合并，
 *          合并后长度为len，最小值为left，最大值为right，在map中更新left与right记录，更新为(left,len)和(right,len)；
 *     2.2、看map中是否存在array[i]+1，若存在，将array[i]+1所在的序列与array[i]合并，
 *          合并后长度为len，最小值为left，最大值为right，在map中更新left与right记录，更新为(left,len)和(right,len)；
 *     3、遍历过程中用全局变量max记录每次合并后的序列的长度最大值，最后返回max。
 *     整个过程中，只是每个连续序列最小值和最大值在map中的记录有意义，中间数的记录不再更新，因为再也不会用到。
 * </p>
 * <p>
 *     时间复杂度为O(N)，空间复杂度为O(N)
 * </p>
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
                // 合并arr[i]与前面的子数组
                max = Math.max(max, merge(map, array[i] - 1, array[i]));
            }
            if (map.containsKey(array[i] + 1)) {
                // 合并arr[i]与后面的子数组
                max = Math.max(max, merge(map, array[i], array[i] + 1));
            }
        }
        return max;
    }

    /**
     * 合并连续序列
     * 将less所在序列与more所在序列合并
     */
    private static int merge(Map<Integer, Integer> map, int less, int more) {
        // 序列中最小的元素
        int left = less - map.get(less) + 1;
        // 序列中最大的元素
        int right = more + map.get(more) - 1;
        // 合并后序列长度
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
