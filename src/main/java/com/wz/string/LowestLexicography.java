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
        String[] array1 = { "jibw", "ji", "jp", "bw", "jibw" };
        System.out.println(lowestString(array1));

        String[] array2 = { "ba", "b" };
        System.out.println(lowestString(array2));
    }
}
