/**
 * <p>Title: Rand1ToRandN</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/1/14</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.others;

/**
 * <p>从随机到随机的扩展</p>
 *
 * @author wangzi
 */
public class Rand1ToRandN {

    /**
     * 利用rand1To5生成rand1To7
     */
    public static int rand1To7() {
        int num;
        do {
            num = (rand1To5() - 1) * 5 + rand1To5() - 1;
        } while (num > 20);
        return num % 7 + 1;
    }

    /**
     * 已知条件
     */
    private static int rand1To5() {
        return (int) (Math.random() * 5) + 1;
    }

    /**
     * 利用rand01生成rand1To6
     */
    public static int rand1To6() {
        int num;
        do {
            num = rand0To3() * 4 + rand0To3();
        } while (num > 11);
        return num % 6 + 1;
    }

    /**
     * 利用rand01生成rand0To3
     */
    private static int rand0To3() {
        return rand01() * 2 + rand01();
    }

    /**
     * 利用rand01p生成rand01
     */
    private static int rand01() {
        int num;
        do {
            num = rand01p();
        } while (num == rand01p());
        return num;
    }

    /**
     * 已知条件
     */
    private static int rand01p() {
        // you can change p to what you like, but it must be (0,1)
        double p = 0.83;
        return Math.random() < p ? 0 : 1;
    }

    /**
     * 利用rand1ToM生成rand1ToN
     */
    public static int rand1ToN(int n, int m) {
        int[] mSystemNum = getMSystemNum(n - 1, m);
        int[] randNum = getRandMSystemNumLessN(mSystemNum, m);
        return getNumFromMSystemNum(randNum, m) + 1;
    }

    /**
     * 将value转换为m进制的数
     */
    private static int[] getMSystemNum(int value, int m) {
        int[] result = new int[32];
        int index = result.length - 1;
        while (value != 0) {
            result[index--] = value % m;
            value = value / m;
        }
        return result;
    }

    /**
     * 将m进制的数转换为十进制数
     */
    private static int getNumFromMSystemNum(int[] mSystemNum, int m) {
        int res = 0;
        for (int i = 0; i != mSystemNum.length; i++) {
            res = res * m + mSystemNum[i];
        }
        return res;
    }

    /**
     * 等概率随机产生一个0~mSystemNum范围上的数，m进制表达
     */
    private static int[] getRandMSystemNumLessN(int[] mSystemNum, int m) {
        int[] result = new int[mSystemNum.length];
        int start = 0;
        while (mSystemNum[start] == 0) {
            start++;
        }
        int index = start;
        boolean lastEqual = true;
        while (index < mSystemNum.length) {
            result[index] = rand1ToM(m) - 1;
            if (lastEqual) {
                if (result[index] > mSystemNum[index]) {
                    index = start;
                    lastEqual = true;
                    continue;
                } else {
                    lastEqual = result[index] == mSystemNum[index];
                }
            }
            index++;
        }
        return result;
    }

    /**
     * 已知条件
     */
    public static int rand1ToM(int m) {
        return (int) (Math.random() * m) + 1;
    }

    private static void printCountArray(int[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.println(i + " appears " + array[i] + " times");
        }
    }

    public static void main(String[] args) {
        int times = 100000;
        int[] array = new int[8];
        for (int i = 0; i < times; i++) {
            array[rand1To7()]++;
        }
        printCountArray(array);

        System.out.println("=====================");

        array = new int[7];
        for (int i = 0; i < times; i++) {
            array[rand1To6()]++;
        }
        printCountArray(array);

        System.out.println("=====================");

        int n = 17;
        int m = 3;
        array = new int[n + 1];
        for (int i = 0; i != 2000000; i++) {
            array[rand1ToN(n, m)]++;
        }
        printCountArray(array);
    }
}
