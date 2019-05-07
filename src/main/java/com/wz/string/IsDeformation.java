/**
 * <p>Title: IsDeformation</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/11/25</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.string;

/**
 * <p>两个字符串是否互为变形词</p>
 * <p>
 *     给定字符串self和other，如果两个字符串中出现的字符种类相同而且每种字符次数也一样，那么self与other互为变形词。
 *     判断两个字符串是否互为变形词。
 *     如果两个字符串长度不同直接返回false，长度相同时，假设出现的字符编码值在0～255直接，那么生成一个长度为256的数组map，
 *     map[a]=b表示字符编码为a的字符出现了b次。
 *     遍历字符串self，统计每种字符出现的次数，比如遍历到字符'a'，其编码值为97，则令map[97]++。
 *     再遍历字符串other，每遍历一个字符，都在map中将词频减下来，如果减少后的值小于0，直接返回false。
 *     如果遍历完other，map中的值没有出现负值，则返回true。
 *     如果字符的类型很多，可以使用哈希表代替长度为256的整型数组，整体过程不变。
 * </p>
 * <p>
 *     若字符种类为M，字符串长度为N，那么该方法时间复杂度为O(N)，空间复杂度为O(M)
 * </p>
 *
 * @author wangzi
 */
public class IsDeformation {
    public static boolean isDeformation(String self, String other) {
        if (self == null || other == null || self.length() != other.length()) {
            return false;
        }

        char[] chSelf = self.toCharArray();
        char[] chOther = other.toCharArray();
        int[] map = new int[256];
        for (char cur : chSelf) {
            // 记录字符cur出现的次数
            map[cur]++;
        }
        for (char cur : chOther) {
            if (map[cur]-- == 0) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        String self = "abcabcabc";
        String other = "bcacbaacb";
        System.out.println(isDeformation(self, other));
    }
}
