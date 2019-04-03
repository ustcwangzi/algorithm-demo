package com.wz;

/**
 * <p>利用rand01p等概率产生1～6</p>
 *
 * @author wangzi
 */
public class Rand1pToRand6Test {

    private static int solution() {
        int num;
        do {
            // rand03()*4 等概率产生 0,4,8,12
            // (0,4,8,12) + (0,1,2,3) 等概率产生 0～15
            // 将12～15分配到0～11
            num = rand03() * 4 + rand03();
        } while (num > 11);
        // (0～11)%6 随机产生 0～5
        return num % 6 + 1;
    }

    /**
     * rand01()*2 等概率产生 0,2
     * (0,2) + (0,1) 等概率产生 0,1,2,3
     */
    private static int rand03() {
        return rand01() * 2 + rand01();
    }

    /**
     * 等概率随机产生0,1
     */
    private static int rand01() {
        int num;
        do {
            // rand01p两次都产生0和两次都产生1的概率相等
            num = rand01p();
        } while (num == rand01p());
        return num;
    }

    /**
     * 已知条件，以p概率产生0，以1-p概率产生1
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
