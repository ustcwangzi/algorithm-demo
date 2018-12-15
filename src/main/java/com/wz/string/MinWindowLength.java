/**
 * <p>Title: MinWindowLength</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/12/15</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.string;

/**
 * <p>最小包含子串的长度</p>
 *
 * @author wangzi
 */
public class MinWindowLength {

    public static int minLength(String str, String target) {
        if (str == null || target == null || str.length() < target.length()) {
            return 0;
        }

        char[] strArray = str.toCharArray();
        char[] targetArray = target.toCharArray();
        // 对于key字符，str还欠target字符串value个
        int[] map = new int[256];
        for (char s : targetArray) {
            map[s]++;
        }

        // 被框住子串的左边界、右边界
        int left = 0, right = 0;
        // str[left...right]还欠target多少
        int match = targetArray.length;
        // 最终结果
        int minLen = Integer.MAX_VALUE;
        while (right < strArray.length) {
            map[strArray[right]]--;
            if (map[strArray[right]] >= 0) {
                match--;
            }
            if (match == 0) {
                // match为0时，left右移
                while (map[strArray[left]] < 0) {
                    map[strArray[left++]]++;
                }
                minLen = Math.min(minLen, right - left + 1);
                match++;
                map[strArray[left++]]++;
            }
            right++;
        }
        return minLen == Integer.MAX_VALUE ? 0 : minLen;
    }

    public static void main(String[] args) {
        System.out.println(minLength("adabbca", "acb"));
    }

}
