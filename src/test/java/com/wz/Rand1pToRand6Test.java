package com.wz;

/**
 * <p></p>
 *
 * @author wangzi
 */
public class Rand1pToRand6Test {

    private static int solution() {
        int num;
        do {
            num = rand03() * 4 + rand03();
        } while (num > 11);
        return num % 6 + 1;
    }

    private static int rand03() {
        return rand01() * 2 + rand01();
    }

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

    public static void main(String[] args) {
        int times = 1000000;
        int[] array = new int[7];
        for (int i = 0; i < times; i++) {
            array[solution()]++;
        }
        for (int i = 0; i < array.length; i++) {
            System.out.println(i + " appears " + array[i] + " times");
        }
    }
}
