/**
 * <p>Title: RandomUtils</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/2/24</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * <p></p>
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

    protected static int[] genRandomArray() {
        int[] array = new int[SIZE];
        for (int i = 0; i < SIZE; i++) {
            array[i] = new Random().nextInt(SIZE);
        }
        return array;
    }

    protected static int[] genRandomArrayNoRepeat(int m) {
        List<Integer> list = new ArrayList<>(m);
        int[] result = new int[m];
        for (int i = 0; i < m; i++) {
            int number = new Random().nextInt(SIZE+m);
            while (list.contains(number)) {
                number = new Random().nextInt(SIZE+m);
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
