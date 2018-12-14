/**
 * <p>Title: LowestLexicography</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/12/14</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.string;

/**
 * <p>字符串中最长无重复子串的长度</p>
 * <p>
 *     给定一个字符串str，获取str中最长无重复子串的长度。
 *     例如，"abcd"返回4；"aabc"返回3。
 *     解决方案：
 *        用map保存字符最近出现的位置，key为str[i]；pre表示以str[i-1]结尾时，最长无重复子串开始位置的前一个位置，
 *        pre+1表示以str[i-1]结尾时，最长无重复子串开始位置，也就是说，以str[i-1]结尾的最长无重复子串向左扩到pre位置停止；
 *        map[str[i]]表示之前的遍历中最近一次出现str[i]字符的位置，假设时位置A，以str[i]结尾的最长无重复子串必然不包含位置A的，
 *        因为str[A] == str[i]，属于重复字符；
 *        如果pre位置在A位置的左边，而A位置不能包含进来，且str[A+1...i-1]都是不重复的，因此以str[i]结尾的最长无重复子串为str[A+1...i]；
 *        如果pre位置在A位置的右边，以str[i-1]结尾的最长无重复子串向左扩到pre位置停止，且str[pre+1...i-1]肯定不包含A位置，
 *        因此以str[i]结尾的最长无重复子串为str[pre+1...i]；
 *        综上可知，pre位置和A位置哪个在右边，就作为新的pre去计算下一个位置的字符，然后maxLength记录整个过程中的最大值。
 * </p>
 * <p>
 *     时间复杂度为O(N)，空间复杂度为O(M)，M为字符编码范围
 * </p>
 *
 * @author wangzi
 */
public class LongestNoRepeatSubstring {
    public static int maxUniqueLength(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }

        char[] array = str.toCharArray();
        // 记录字符最近一次出现的位置
        int[] map = new int[256];
        for (int i = 0; i < 256; i++) {
            map[i] = -1;
        }
        int pre = -1, cur = 0;
        int maxLength = -1;
        for (int i = 0; i < array.length; i++) {
            // map[array[i]]表示之前的遍历中最近一次出现array[i]字符的位置
            // 与pre位置比较，哪个在右边，就作为新的pre去结算下一个位置的字符
            pre = Math.max(pre, map[array[i]]);
            // 以str[i]结尾的最长无重复子串长度
            cur = i - pre;
            maxLength = Math.max(maxLength, cur);
            map[array[i]] = i;
        }

        return maxLength;
    }

    public static String maxUniqueString(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }

        char[] array = str.toCharArray();
        // 记录字符最近以此出现的位置
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
        System.out.println(maxUniqueLength("abcddf"));
        System.out.println(maxUniqueString("abcddf"));
        System.out.println(maxUniqueLength("aabceddefopq"));
        System.out.println(maxUniqueString("aabceddefopq"));
    }
}
