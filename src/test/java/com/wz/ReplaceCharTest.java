package com.wz;

import com.wz.string.ModifyAndReplace;

import java.util.Arrays;

/**
 * <p>数组足够长，将' '替换为'%20'</p>
 *
 * @author wangzi
 */
public class ReplaceCharTest {
    private static char[] solution(char[] array) {
        // 左半区长度、空格数量
        int len, blankNum = 0;
        for (len = 0; len < array.length && array[len] != 0; len++) {
            if (array[len] == ' ') {
                blankNum++;
            }
        }

        // 替换后的新长度
        int newLen = len + 2 * blankNum;
        // 左半区从右到左遍历，非' '直接复制，' '替换
        for (int i = len - 1; i > -1; i--) {
            if (array[i] != ' ') {
                array[--newLen] = array[i];
            } else {
                array[--newLen] = '0';
                array[--newLen] = '2';
                array[--newLen] = '%';
            }
        }
        return array;
    }

    public static void main(String[] args) {
        int times = 100;
        boolean result = true;
        for (int i = 0; i < times; i++) {
            char[] array = RandomUtils.genRandomCharArrayWithEmpty(50);
            if (!Arrays.equals(solution(Arrays.copyOf(array, array.length)),
                    ModifyAndReplace.replace(Arrays.copyOf(array, array.length)))) {
                result = false;
                System.out.println("Error, array:" + Arrays.toString(array));
            }
        }
        if (result) {
            System.out.println("Past");
        }
    }
}
