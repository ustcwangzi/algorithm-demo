/**
 * <p>Title: ReplaceString</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/11/28</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.string;

/**
 * <p>替换字符串中连续出现的指定字符</p>
 *
 * @author wangzi
 */
public class ReplaceString {
    public static String replace(String str, String from, String to) {
        if (str == null || from == null || str.length() == 0 || from.length() == 0) {
            return str;
        }

        char[] chStr = str.toCharArray();
        char[] chFrom = from.toCharArray();
        int match = 0;
        for (int i = 0; i < chStr.length; i++) {
            if (chStr[i] == chFrom[match++]) {
                if (match == chFrom.length) {
                    clear(chStr, i, chFrom.length);
                    match = 0;
                }
            } else {
                if (chStr[i] == chFrom[0]) {
                    i--;
                }
                match = 0;
            }
        }

        String result = "", cur = "";
        for (int i = 0; i < chStr.length; i++) {
            if (chStr[i] != 0) {
                cur = cur + String.valueOf(chStr[i]);
            }
            // 连续为0的区域用to替换
            if (chStr[i] == 0 && (i == 0 || chStr[i - 1] != 0)) {
                result = result + cur + to;
                cur = "";
            }
        }
        return cur.length() == 0 ? result : result + cur;
    }

    private static void clear(char[] chas, int end, int len) {
        while (len-- != 0) {
            chas[end--] = 0;
        }
    }

    public static void main(String[] args) {
        String str = "abc1abcabc1234abcabcabc5678";
        String from = "abc";
        String to = "X";
        System.out.println(replace(str, from, to));

        str = "abc";
        from = "123";
        System.out.println(replace(str, from, to));
    }
}
