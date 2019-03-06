/**
 * <p>Title: RandomUtils</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/2/24</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz;

import java.util.Random;

/**
 * <p></p>
 *
 * @author wangzi
 */
public class RandomUtils {
    private static final int SIZE = 100;

    public static int genRandomNumber() {
        return new Random().nextInt(SIZE);
    }

    public static String genRandomString() {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < random.nextInt(SIZE); i++) {
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    public static int[] genRandomArray() {
        int[] array = new int[SIZE];
        for (int i = 0; i < SIZE; i++) {
            array[i] = new Random().nextInt(SIZE);
        }
        return array;
    }

    public static int[] genRandomArray(int m) {
        int[] array = new int[m];
        for (int i = 0; i < m; i++) {
            array[i] = new Random().nextInt(SIZE);
        }
        return array;
    }
}
