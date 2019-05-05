package com.wz;

import com.wz.string.RotateString;

import java.util.Arrays;
import java.util.Random;

/**
 * <p>部分字符翻转</p>
 *
 * @author wangzi
 */
public class RotatePartTest {
    private static char[] solution(char[] array, int k){
        // 翻转array[0...k-1]
        reverse(array, 0, k-1);
        // 翻转array[k+1...N-1]
        reverse(array, k, array.length-1);
        // 翻转array[0...N-1]
        reverse(array, 0, array.length-1);
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
            char[] array = RandomUtils.genRandomCharArrayWithEmpty();
            int k = new Random().nextInt(array.length);
            if (!Arrays.equals(solution(Arrays.copyOf(array, array.length), k),
                    RotateString.rotatePart(Arrays.copyOf(array, array.length), k))) {
                result = false;
                System.out.println("Error, array:" + Arrays.toString(array));
            }
        }
        if (result) {
            System.out.println("Past");
        }
    }
}
