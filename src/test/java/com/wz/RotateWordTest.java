package com.wz;

import com.wz.string.RotateString;

import java.util.Arrays;

/**
 * <p>单次翻转</p>
 */
public class RotateWordTest {

    private static char[] solution(char[] array) {
        reverse(array, 0, array.length - 1);
        int left = -1, right = -1;
        for (int i = 0; i < array.length; i++) {
            if (array[i] != ' ') {
                left = i == 0 || array[i - 1] == ' ' ? i : left;
                right = i == array.length - 1 || array[i + 1] == ' ' ? i : right;
            }
            if (left != -1 && right != -1) {
                reverse(array, left, right);
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
            if (!Arrays.equals(solution(array), RotateString.rotateWord(array))) {
                result = false;
                System.out.println("Error, array:" + Arrays.toString(array));
            }
        }
        if (result) {
            System.out.println("Past");
        }
    }
}
