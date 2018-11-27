/**
 * <p>Title: ConvertStringToInteger</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/11/27</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.string;


/**
 * <p>整数字符串转成整数值</p>
 * <p>
 *     给定字符串str，如果str符合日常书写习惯，并且属于32位整数范围，返回str所代表的整数值，否则返回0。
 *     例如，"123"返回123；"023"不符合日常书写习惯，返回0；"A2"返回0；"2147483648"溢出了，返回0；"-123"返回-123。
 *     解决方案：
 *     首先检查str是否符合日常书写的整数形式：
 *     1、如果str不以"-"开头，也不以数字字符开头，比如"A12"，返回false
 *     2、如果str以"-"开头，但长度为1，即"-"，返回false；如果长度大于1，但是"-"后面紧跟着"0"，比如"-01"，返回false
 *     3、如果str以"0"开头，但长度大于1，比如"023"，返回false
 *     4、检查str[1...N-1]是否都是数字字符，发现不是数字字符返回false，否则返回true。
 *     如果str不符合以上检查，返回0即可，否则进行以下转换过程：
 *     1、positive表示转换结果是正数还是负数，minQuotient为32位整数最小值除以10得到的商，minRemainder为32位整数最小值除以10得到的余数，
 *        result表示转换的结果，初始时result=0。
 *     2、32位整数最小值为-2147483648，最大值为2147483647，可以看出最小值绝对值比最大值绝对值大1，即负数比正数拥有更大的绝对值范围，
 *        因此转换过程中一律以负数的形式记录结果，最后再决定返回的数到底是什么。比如"123"转换结果为-123，positive=true，最后返回123。
 *     3、如果str以"-"开头，则从str[1]开始从左到右遍历str，否则从str[0]开始从左到右遍历str。比如"135"，遍历到'1'时，
 *        result=result*10+(-1)=-1；遍历到'3'时，result=result*10+(-3)=-13；遍历到'5'时，result=result*10+(-5)=-135。
 *        遍历过程中如何判断result溢出呢，假设当前字符为'a'，那么'0'-a就是当前字符所代表数字的负数形式，记为cur，如果在result加上cur之前，
 *        result小于minQuotient，那么当result加上cur之后一定会溢出，因为result加上cur的时候会乘10；如果在result加上cur之前，
 *        result等于minQuotient，同时cur小于minRemainder，那么当result加上cur之后一定会溢出。出现任何一种溢出的情况时，直接返回0。
 *     4、遍历后得到的result根据positive的符号决定返回值，positive为true说明应该返回正数，否则应该返回负数，如果result正好是32位整数的
 *        最小值，同时positive位true，说明发生了溢出，返回0。
 * </p>
 *
 * @author wangzi
 */
public class ConvertStringToInteger {

    public static int convert(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }

        char[] charArray = str.toCharArray();
        if (!isValid(charArray)) {
            return 0;
        }

        // 符号位
        boolean positive = charArray[0] != '-';
        // 商
        int minQuotient = Integer.MIN_VALUE / 10;
        // 余数
        int minRemainder = Integer.MIN_VALUE % 10;

        int result = 0, cur;
        for (int i = positive ? 0 : 1; i < charArray.length; i++) {
            // 转换过程中一律以负数的形式记录
            cur = '0' - charArray[i];
            // 溢出
            if ((result < minQuotient) || (result == minQuotient && cur < minRemainder)) {
                return 0;
            }
            result = result * 10 + cur;
        }
        if (positive && result == Integer.MIN_VALUE) {
            return 0;
        }

        return positive ? -result : result;
    }

    private static boolean isValid(char[] chars) {
        if (chars[0] != '-' && (chars[0] < '0' || chars[0] > '9')) {
            return false;
        }
        if (chars[0] == '-' && (chars.length == 1 || chars[1] == '0')) {
            return false;
        }
        if (chars[0] == '0' && chars.length > 1) {
            return false;
        }
        for (int i = 1; i < chars.length; i++) {
            if (chars[i] < '0' || chars[i] > '9') {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        // max in java
        System.out.println(convert("2147483647"));

        // min in java
        System.out.println(convert("-2147483648"));

        // overflow
        System.out.println(convert("2147483648"));

        // overflow
        System.out.println(convert("-2147483649"));

        System.out.println(convert("123"));

        System.out.println(convert("-123"));
    }
}
