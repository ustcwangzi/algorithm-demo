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
 * <p>
 *     给定三个字符串str、from和to，吧str中所有from的子串替换为to，对于连续出现的部分只替换为一个to字符串即可。
 *     例如，str="123abc"，from="abc"，to="X"，替换后为"123X"；
 *          str="123"，from="abc"，to="X"，替换后为"123"；
 *          str="123abcabc"，from="abc"，to="X"，替换后为"123X"。
 *     解决方案：
 *     把str看作字符类型的数组，把str中from部分的所有位置的字符编码设为0(即空字符)，过程如下：
 *     1、match表示目前匹配到from字符串的什么位置，初始时match=0
 *     2、从左到右遍历str的每个字符，假设当前遍历到str[i]
 *     3、如果str[i]==from[match]，如果match是from的最后一个字符的位置，说明str中发现了from字符串，则从i开始向左的M个位置，
 *        将字符编码设为0，M为from的长度，然后令match=0。如果match不是from的最后一个字符的位置，令match++，遍历str的下一个字符。
 *     4、如果str[i]!=from[match]，说明匹配失败，令match=0，即回到from开头重新匹配。
 *     通过上面过程后，将不为0的区域拼在一起，连续为0的部分用to来替换即可。
 * </p>
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
                // 匹配成功
                if (match == chFrom.length) {
                    clear(chStr, i, chFrom.length);
                    match = 0;
                }
            } else {
                // from从头匹配
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

    /**
     * 从end开始向左len个位置，将字符编码设为0
     */
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
