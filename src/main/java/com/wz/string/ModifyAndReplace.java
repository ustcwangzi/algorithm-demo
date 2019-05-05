/**
 * <p>Title: ModifyAndReplace</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/12/6</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.string;

import java.util.Arrays;

/**
 * <p>字符串调整与替换</p>
 * <p>
 *     问题一：
 *        给定一个字符数组array[]，array右半区全是空字符，左半区不含有空字符，假设右半区足够大，请将左半区中所有的空格字符替换为"20%"。
 *        例如，左半区为"a b  c"，替换后为"a%20b%20%20c"。
 *     问题二：
 *        给定一个字符数组array[]，其中只含有数字字符和'*'字符，请将所有'*'字符挪到右边，数组字符挪到左边。
 *        例如，数组字符串为"123**45"，调整后为"**12345"。
 *     问题一解答：
 *        遍历array，可得到两个信息：左半区长度len、左半区空格数num，因此空格字符被"%20"替换后，长度将是len+2*num。
 *        接下来从左半区的最后一个字符(即位置len-1)开始从右到左遍历，同时将字符复制到新长度的最后位置。
 *        复制时，遇到空格字符就依次将'0'、'2'和'%'进行复制，否则直接复制原字符。这样就得到替换后的array数组。
 *     问题二解答：
 *        和问题一的解答类似，从最后一个字符(即位置array.length-1)开始从右到左复制，遇到数字字符直接复制，遇到'*'不复制，
 *        把数字字符复制完后，再把左侧剩下的部分全部设为'*'即可。
 * </p>
 * <p>
 *     问题一时间复杂度为O(N)，空间复杂度为O(1)；问题二时间复杂度为O(N)，空间复杂度为O(1)。
 * </p>
 *
 * @author wangzi
 */
public class ModifyAndReplace {
    public static char[] replace(char[] array) {
        if (array == null || array.length == 0) {
            return array;
        }

        // 左半区空格数
        int num = 0;
        // 左半区长度
        int len = 0;
        for (len = 0; len < array.length && array[len] != 0; len++) {
            if (array[len] == ' ') {
                num++;
            }
        }

        // 替换后的长度
        int j = len + num * 2;
        // 从右到左复制
        for (int i = len - 1; i > -1; i--) {
            if (array[i] != ' ') {
                // 非空格直接复制
                array[--j] = array[i];
            } else {
                // 空格替换
                array[--j] = '0';
                array[--j] = '2';
                array[--j] = '%';
            }
        }
        return array;
    }

    public static char[] modify(char[] array) {
        if (array == null || array.length == 0) {
            return array;
        }

        int j = array.length - 1;
        // 从右到左复制
        for (int i = array.length - 1; i > -1; i--) {
            if (array[i] != '*') {
                // 遇到数字直接复制
                array[j--] = array[i];
            }
        }
        for (; j > -1; ) {
            // 剩余部分设为'*'
            array[j--] = '*';
        }
        return array;
    }

    public static void main(String[] args) {
        char[] array1 = {'a', ' ', 'b', ' ', ' ', 'c', 0, 0, 0, 0, 0, 0, 0, 0};
        System.out.println(Arrays.toString(replace(array1)));
        char[] array2 = {'1', '2', '*', '*', '3', '4', '5'};
        System.out.println(Arrays.toString(modify(array2)));
    }
}
