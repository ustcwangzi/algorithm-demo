/**
 * <p>Title: LowestLexicography</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/12/13</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.string;

import java.util.Arrays;

/**
 * <p>拼接所有字符串产生字典顺序最小的字符串</p>
 * <p>
 *     给定一个字符串列类型的数组array，找出一种拼接顺序，是的将所有字符串拼接起来组成的字符串是所有可能性中字典顺序最小的。
 *     例如：
 *     array=["abc", "de"]，可以拼接成"abcde"、"deabc"，但前者的字典顺序更小，所以返回"abcde"；
 *     array=["b", "ba"]，可以拼接成"bba"、"bab"，但后者的字典顺序更小，所以返回"bab"。
 *     解决方案：
 *        假设有两个字符串，分别极为a和b，a和b拼接起来的字符串表示为a.b。那么如果a.b的字典顺序小于b.a，就把字符串a放在前面，
 *        否则把字符串b放在前面。每两个字符串之间都按照这个标准进行比较，以此标准排序后，再依次拼接起来的字符串就是最终的结果。
 * </p>
 *
 * @author wangzi
 */
public class LowestLexicography {

    public static String lowestString(String[] array) {
        if (array == null || array.length == 0) {
            return "";
        }

        // 排序
        Arrays.sort(array, (self, other) -> (self + other).compareTo(other + self));
        StringBuilder builder = new StringBuilder();
        // 拼接
        Arrays.stream(array).forEach(builder::append);
        return builder.toString();
    }

    public static void main(String[] args) {
        String[] array1 = {"jibw", "ji", "jp", "bw", "jibw"};
        System.out.println(lowestString(array1));

        String[] array2 = {"ba", "b"};
        System.out.println(lowestString(array2));
    }
}
