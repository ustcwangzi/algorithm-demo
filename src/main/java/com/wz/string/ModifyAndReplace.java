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
 *
 * @author wangzi
 */
public class ModifyAndReplace {
    public static void replace(char[] array) {
        if (array == null || array.length == 0) {
            return;
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
        int j = len + num * 2 - 1;
        // 从右到左复制
        for (int i = len - 1; i > -1; i--) {
            if (array[i] != ' ') {
                // 非空格直接复制
                array[j--] = array[i];
            } else {
                // 空格替换
                array[j--] = '0';
                array[j--] = '2';
                array[j--] = '%';
            }
        }
    }

    public static void modify(char[] array) {
        if (array == null || array.length == 0) {
            return;
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
    }

    public static void main(String[] args) {
        char[] array1 = {'a', ' ', 'b', ' ', ' ', 'c', 0, 0, 0, 0, 0, 0, 0, 0};
        replace(array1);
        System.out.println(Arrays.toString(array1));
        char[] array2 = {'1', '2', '*', '*', '3', '4', '5'};
        modify(array2);
        System.out.println(Arrays.toString(array2));
    }
}
