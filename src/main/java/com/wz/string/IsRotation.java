/**
 * <p>Title: IsRotation</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/11/26</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.string;

/**
 * <p>两个字符串是否互为旋转词</p>
 * <p>
 *     把一个字符串str前面任意的部分移到后面形成的字符串叫做str的旋转词。
 *     例如str="1234"，那么"1234"、"2341"、"3412"、"4123"都是str的旋转词；"1ab2"与"ab12"不是互为旋转词。
 *     给定两个字符串self和other，判断self和other是否互为旋转词。
 *     解决过程：
 *     如果self和other长度不一样，直接返回false。长度一样时，使用两个other生成一个大字符串tmp，即tmp=other+other，
 *     如果tmp中包含字符串self，说明self与other互为旋转词，否则说明两个字符串不互为旋转词。因为tmp中包含了字符串other的所以旋转词。
 *     getIndexOf()的功能是如果tmp中包含self，返回self在tmp中的开始位置，如果不包含则返回-1，利用的是KMP算法，此处不做展开。
 * </p>
 * <p>
 *     KMP算法可在O(N)内完成，因此整个过程的时间复杂度为O(N)
 * </p>
 *
 * @author wangzi
 */
public class IsRotation {

    public static boolean isRotation(String self, String other) {
        if (self == null || other == null || self.length() != other.length()) {
            return false;
        }

        String tmp = other + other;
        return getIndexOf(tmp, self) != -1;
    }

    /**
     * KMP算法
     *   str：... ... ... ... |...c...|A| ...
     * match：|...a...|C| ... |...b...|B| ...
     * match：----> ...  ---> |...a...|C| ...
     * 上图中，str和match匹配到A字符和B字符时才发生的不匹配，因此c区域与b区域相等
     * 根据nextArray的定义，b区域又与a区域相等，所以a区域和c区域不用检查，直接将match滑到字符C开始匹配即可
     */
    private static int getIndexOf(String str, String match) {
        if (str == null || match == null || match.length() < 1 || str.length() < match.length()) {
            return -1;
        }

        char[] strArray = str.toCharArray();
        char[] matchArray = match.toCharArray();
        int[] nextArray = getNextArray(matchArray);
        int strIndex = 0, matchIndex = 0;
        while (strIndex < strArray.length && matchIndex < matchArray.length) {
            if (strArray[strIndex] == matchArray[matchIndex]) {
                strIndex++;
                matchIndex++;
            } else if (nextArray[matchIndex] == -1) {
                strIndex++;
            } else {
                // match向右滑动
                matchIndex = nextArray[matchIndex];
            }
        }
        return matchIndex == matchArray.length ? strIndex - matchIndex : -1;
    }

    /**
     * nextArray[i]的含义是：在match[0...i-1]中，以match[i-1]结尾的后缀子串(不包含match[0])
     * 与以match[0]开头的前缀子串(不包含match[i-1])，最大的匹配长度
     * eg. match为"aaaab"，nextArray[4]的最大匹配为match[1...3]与match[0...2]，即nextArray[4]=3
     * match为"abc1abc1"，nextArray[7]的最大匹配为match[4...6]与match[0...2]，即nextArray[7]=3
     */
    private static int[] getNextArray(char[] match) {
        if (match.length == 1) {
            return new int[]{-1};
        }
        int[] nextArray = new int[match.length];
        // 之前没有任何字符，故赋值为-1
        nextArray[0] = -1;
        // 任何子串的后缀不能包含第一个字符，因此nextArray[1]赋值为0
        nextArray[1] = 0;
        int position = 2;
        int cn = 0;
        while (position < nextArray.length) {
            if (match[position - 1] == match[cn]) {
                nextArray[position++] = ++cn;
            } else if (cn > 0) {
                cn = nextArray[cn];
            } else {
                nextArray[position++] = 0;
            }
        }
        return nextArray;
    }

    public static void main(String[] args) {
        System.out.println(isRotation("1ab2", "ab12"));
        System.out.println(isRotation("2ab1", "ab12"));
    }
}
