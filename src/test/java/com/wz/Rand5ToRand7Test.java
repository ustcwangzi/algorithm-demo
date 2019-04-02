package com.wz;

import java.util.Random;

/**
 * <p>利用等概率随机产生1～5的随机函数rand1To5实现等概率随机产生1～7的随机函数</p>
 *
 * @author wangzi
 */
public class Rand5ToRand7Test {

    private static int solution() {
        int num;
        do {
            num = (rand1To5() - 1) * 5 + rand1To5() - 1;
        } while (num > 20);
        return num % 7 + 1;
    }

    private static int rand1To5() {
        return new Random().nextInt(5) + 1;
    }

    public static void main(String[] args) {
        int times = 1000000;
        int[] array = new int[8];
        for (int i = 0; i < times; i++) {
            array[solution()]++;
        }
        for (int i = 0; i < array.length; i++) {
            System.out.println(i + " appears " + array[i] + " times");
        }
    }
}
