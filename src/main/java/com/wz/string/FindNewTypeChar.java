/**
 * <p>Title: FindNewTypeChar</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/12/15</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.string;

/**
 * <p>找到指定位置的新类型字符</p>
 * <p>
 *     新类型字符定义如下：
 *     1、新类型字符是长度为1或2的字符串
 *     2、表现形式可以只是小写字母，如"e"；也可以是大写字母+小写字母，如"Ab"；还可以是大写字母+大写字母，如"DC"。
 *     现给定一个字符串str，str一定是若干新类型字符正确组合的结果，比如"eaBBCi"，由新类型字符"e"、"a"、"BB"和"Ci"组成。
 *     再给定一个整数k，代表str中的位置。请获取k位置处的新类型字符。
 *     例如：str="aaABCDEcBCg"
 *          k=7时，返回"Ec"；k=4时，"CD"；k=10时，"g"。
 *     解决方案：
 *        一种本方法就是从0位置开始，从左到右依次划分出新类型字符，到k位置的时候就知道新类型字符是什么，此处不再详述。
 *        另一种更快的方法，从k-1位置开始，向左统计连续出现的大写字母的数量upperNum，遇到小写字母就停止。
 *        1、如果upperNum为奇数，str[k-1...k]就是需要的新类型字符，如例1；
 *        2、如果upperNum为偶数且str[k]是大写字母，str[k...k+1]就是需要的新类型字符，如例2；
 *        3、如果upperNum为偶数且str[k]是小写字母，str[k]就是需要的新类型字符，如例3。
 * </p>
 *
 * @author wangzi
 */
public class FindNewTypeChar {

    public static String getNewChar(String str, int k) {
        if (str == null || str.length() == 0 || k < 0 || k >= str.length()) {
            return "";
        }

        char[] array = str.toCharArray();
        int upperNum = 0;
        for (int i = k - 1; i >= 0; i--) {
            if (array[i] < 'A' || array[i] > 'Z') {
                break;
            }
            upperNum++;
        }
        // upperNum为奇数
        if ((upperNum & 1) == 1) {
            return str.substring(k - 1, k + 1);
        }
        // upperNum为偶数，且位置k上是大写字母
        if (array[k] >= 'A' && array[k] <= 'Z') {
            return str.substring(k, k + 2);
        }
        // upperNum为偶数，且位置k上是小写字母
        return String.valueOf(array[k]);
    }

    public static void main(String[] args) {
        String str = "aaABCDEcBCg";
        System.out.println(getNewChar(str, 7));
        System.out.println(getNewChar(str, 4));
        System.out.println(getNewChar(str, 10));
    }
}
