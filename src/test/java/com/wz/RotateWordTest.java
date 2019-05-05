package com.wz;

import com.wz.string.RotateString;

import java.util.Arrays;

/**
 * <p>单词翻转</p>
 *
 * @author wangzi
 */
public class RotateWordTest {

    private static char[] solution(char[] array) {
        // 将整个数组翻转
        reverse(array, 0, array.length - 1);
        // 找到每个单词的开始、结束位置，进行单次翻转
        int left = -1, right = -1;
        for (int i = 0; i < array.length; i++) {
            if (array[i] != ' ') {
                left = i == 0 || array[i - 1] == ' ' ? i : left;
                right = i == array.length - 1 || array[i + 1] == ' ' ? i : right;
            }
            if (left != -1 && right != -1) {
                reverse(array, left, right);
                left = -1;
                right = -1;
            }
        }
        return array;
    }

    private static void reverse(char[] array, int start, int end) {
        char tmp;
        while (start < end) {
            tmp = array[start];
            array[start] = array[end];
            array[end] = tmp;
            start++;
            end--;
        }
    }

    public static void main(String[] args) {
        int times = 100;
        boolean result = true;
        for (int i = 0; i < times; i++) {
            char[] array = RandomUtils.genRandomCharArray();
            if (!Arrays.equals(solution(Arrays.copyOf(array, array.length)),
                    RotateString.rotateWord(Arrays.copyOf(array, array.length)))) {
                result = false;
                System.out.println("Error, array:" + Arrays.toString(array));
            }
        }
        if (result) {
            System.out.println("Past");
        }
    }
}
