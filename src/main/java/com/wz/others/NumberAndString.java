/**
 * <p>Title: NumberAndString</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/1/25</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.others;

/**
 * <p>一种字符串和数字的对应关系</p>
 * <p>
 *     一个char类型的数组array，其中所有的字符都不同。
 *     例如，array = ['A', 'B', 'C', … 'Z']，则字符串与整数的对应关系如下：
 *        A,B,C...Z, AA,AB...AZ,BA,BB...ZZ, AAA...ZZZ,  AAAA...
 *        1,2,3...26,27,28...52,53,54...702,703...18278,18279...
 *        array = ['A,' 'B', 'C']，则字符串与整数的对应关系如下：
 *        A,B,C,AA,AB...CC,AAA...CCC,AAAA...
 *        1,2,3,4,　5...12, 14...39, 40...
 *     给定一个数组array，实现根据对应关系完成字符串与整数相互转换。
 *     解决方案：
 *        似于10进制数与k进制数相互转换，不同的是，这次的k进制数每一位上不能取0，取值的范围是[1, k]，我们把它叫做k伪进制数。
 *        从数字到字符串。
 *        以chs = ['A', 'B', 'C']，n = 72为例子描述该过程：
 *        1、chs的长度为3，所以这是一个3伪进制，从低位到高位依次为1,3,9,27,81...
 *        2、从1开始减，72减1剩余71，71减3剩余68，68减9剩余59，59减27剩余32，32减81，不够减。此时就知道想要表达十进制数的72，
 *           只需要使用3伪进制的前4位，也就是27,9,3,1。
 *           换句话说，既然k伪进制中每个位上的值都不能等于0，就从低位到高位把每个位置的都值都先减去1，看这个数到底需要前几位。
 *        3、步骤二剩余的数是32，同时前四位的值各用了一遍。接下来看32最多可以用几个27，最多用1个，加上之前使用的一个，一个要用两个27，
 *           所以该位置上应该填 'B'。32%27的结果是5，然后看5能用几个9，一个也用不了，所以该位置应该填 'A'；
 *           再看5能用几个3，能用一个，加上之前的一个，一共要用2个3，所以该位置填 'B'；
 *           5%3的结果是2，再看2能用几个1，能用两个，加上之前的一个，一共要用3个1，所以该位置应该填 'C'；所以结果是 "BABC"。
 *        从字符串到数字。
 *        以chs = ['A','B','C']，str = "ABBA"为例，
 *        可以知道这个字符串的含义是27有1个，9有2个，3有2个，1有1个，所以对应的数字是52。
 * </p>
 * <p>
 *     数字转字符串时间复杂度为O(logN)
 * </p>
 *
 * @author wangzi
 */
public class NumberAndString {
    public static String convertToString(char[] array, int number) {
        if (array == null || array.length == 0 || number < 1) {
            return "";
        }
        // 伪k进制
        int k = array.length;
        // array的每一位代表的基数
        int cur = 1;
        // 能用到位数
        int validLength = 0;
        // 从低位到高位看number最多能用几个base伪进制数的位
        while (number >= cur) {
            validLength++;
            number -= cur;
            cur *= k;
        }

        char[] result = new char[validLength];
        int index;
        // 从高位到低位反着回去看每个位上的值最多是多少
        for (int i = 0; i < result.length; i++) {
            cur /= k;
            // 转换成array的某一位
            index = number / cur;
            result[i] = (index < 0 || index > array.length - 1) ? 0 : array[index];
            number %= cur;
        }
        return String.valueOf(result);
    }

    public static int convertToNumber(char[] array, String str) {
        if (array == null || array.length == 0 || str == null || str.length() == 0) {
            return 0;
        }
        char[] strArray = str.toCharArray();
        // 伪k进制
        int k = array.length;
        // array的每一位代表的基数
        int cur = 1;
        int result = 0;
        for (int i = strArray.length - 1; i > -1; i--) {
            result += getPositionFromChar(array, strArray[i]) * cur;
            cur *= k;
        }
        return result;
    }

    private static int getPositionFromChar(char[] array, char ch) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == ch) {
                return i + 1;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        char[] array = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
                'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
        System.out.println(convertToString(array, 273980105));
        System.out.println(convertToNumber(array, "WANGZI"));
    }
}
