/**
 * <p>Title: LowestLexicography</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/12/14</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.string;

/**
 * <p>字符串中最长无重复子串</p>
 *
 * @author wangzi
 */
public class LongestNoRepeatSubstring {
    public static String maxUniqueString(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }

        char[] array = str.toCharArray();
        int[] map = new int[256];
        for (int i = 0; i < 256; i++) {
            map[i] = -1;
        }
        int pre = -1, cur = 0, end = -1;
        int maxLength = -1;
        for (int i = 0; i < array.length; i++) {
            pre = Math.max(pre, map[array[i]]);
            cur = i - pre;
            if (cur > maxLength) {
                maxLength = cur;
                end = i;
            }
            map[array[i]] = i;
        }

        return str.substring(end - maxLength + 1, end + 1);
    }

    public static void main(String[] args) {
        System.out.println(maxUniqueString("abcddf"));
        System.out.println(maxUniqueString("aabceddefopq"));
    }
}
