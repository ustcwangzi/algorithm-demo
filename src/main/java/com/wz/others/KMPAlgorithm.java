/**
 * <p>Title: KMPAlgorithm</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/2/18</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.others;

/**
 * <p>KMP算法</p>
 *
 * @author wangzi
 */
public class KMPAlgorithm {

    public static int kmpSearch(String str, String match) {
        if (str == null || match == null || match.length() < 1 || str.length() < match.length()) {
            return -1;
        }
        char[] strArray = str.toCharArray();
        char[] matchArray = match.toCharArray();
        int[] nextArray = getNextArray(matchArray);
        int strIndex = 0, matchIndex = 0;
        while (strIndex < str.length() && matchIndex < match.length()) {
            if (matchIndex == -1 || strArray[strIndex] == matchArray[matchIndex]) {
                strIndex++;
                matchIndex++;
            } else {
                matchIndex = nextArray[matchIndex];
            }
        }
        return matchIndex == match.length() ? strIndex - matchIndex : -1;
    }

    private static int[] getNextArray(char[] array) {
        if (array.length == 1) {
            return new int[]{-1};
        }
        int[] next = new int[array.length];
        next[0] = -1;
        int j = 0, k = -1;
        while (j < next.length - 1) {
            // array[k]表示前缀，array[j]表示后缀
            if (k == -1 || array[j] == array[k]) {
                if (array[++j] == array[++k]) {
                    // 当两个字符相等时要跳过
                    next[j] = next[k];
                } else {
                    next[j] = k;
                }
            } else {
                k = next[k];
            }
        }
        return next;
    }

    public static void main(String[] args) {
        String str = "abcabcababaccc";
        String match = "ababa";
        System.out.println(kmpSearch(str, match));
    }
}
