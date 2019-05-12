/**
 * <p>Title: RandomUtils</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/2/24</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * <p>随机函数</p>
 *
 * @author wangzi
 */
public class RandomUtils {
    private static final int SIZE = 100;

    protected static int genRandomNumber() {
        return new Random().nextInt(SIZE);
    }

    protected static String genRandomString() {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < random.nextInt(SIZE); i++) {
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    protected static String[] genRandomStringArray() {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        String[] result = new String[SIZE];
        for (int i = 0; i < result.length; i++) {
            int number = random.nextInt(62);
            result[i] = String.valueOf(str.charAt(number));
        }
        return result;
    }

    protected static char[] genRandomCharArray() {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789* ";
        Random random = new Random();
        char[] result = new char[SIZE];
        for (int i = 0; i < result.length; i++) {
            int number = random.nextInt(63);
            result[i] = str.charAt(number);
        }
        return result;
    }

    protected static char[] genRandomCharArrayWithEmpty(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789 ";
        Random random = new Random();
        char[] result = new char[SIZE];
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(63);
            result[i] = str.charAt(number);
        }
        return result;
    }

    protected static String genRandomString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    protected static String genRandomExpress(int length) {
        String str = "&|^";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            if ((i & 1) == 1) {
                sb.append(str.charAt(random.nextInt(3)));
            } else {
                sb.append(random.nextInt());
            }
        }
        return sb.toString();
    }

    protected static String genRandomParentheses() {
        String str = "()";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < random.nextInt(SIZE); i++) {
            int number = random.nextInt(2);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    protected static int[] genShuffleArray() {
        int[] array = new int[SIZE];
        List<Integer> list = new ArrayList<>(SIZE);
        for (int i = 0; i < SIZE; i++) {
            list.add(i + 1);
        }
        Collections.shuffle(list);
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }
        return array;
    }

    protected static int[] genRandomArray() {
        int[] array = new int[SIZE];
        for (int i = 0; i < SIZE; i++) {
            array[i] = new Random().nextInt(SIZE);
        }
        return array;
    }

    protected static int[] genRandomArrayWithMinus() {
        int[] array = new int[SIZE];
        for (int i = 0; i < SIZE; i++) {
            int value = new Random().nextInt(SIZE);
            boolean minus = new Random().nextBoolean();
            array[i] = minus ? -value : value;
        }
        return array;
    }

    protected static double[] genRandomDoubleArray() {
        double[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9,
                10, 20, 30, 40, 50, 60, 70, 80, 90,
                -1, -2, -3, -4, -5, -6, -7, -8, -9,
                0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9};
        int size = array.length;
        for (int i = 0; i < size; i++) {
            array[i] = array[new Random().nextInt(size)];
        }
        return array;
    }

    protected static int[] genRandomArrayNoRepeat(int m) {
        List<Integer> list = new ArrayList<>(m);
        int[] result = new int[m];
        for (int i = 0; i < m; i++) {
            int number = new Random().nextInt(SIZE + m);
            while (list.contains(number)) {
                number = new Random().nextInt(SIZE + m);
            }
            list.add(number);
            result[i] = number;
        }

        return result;
    }

    protected static int[] genRandomArray(int m) {
        int[] array = new int[m];
        for (int i = 0; i < m; i++) {
            array[i] = new Random().nextInt(SIZE);
        }
        return array;
    }
}
