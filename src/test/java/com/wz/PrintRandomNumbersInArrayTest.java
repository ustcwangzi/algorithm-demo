/**
 * <p>Title: PrintRandomNumbersInArrayTest</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/3/16</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz;

import com.wz.others.PrintRandomNumbersInArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * <p>长度位N的不重复数组等概率随机输出M个数</p>
 *
 * @author wangzi
 */
public class PrintRandomNumbersInArrayTest {
    private static int[] solution(int[] array, int m) {
        int[] result = new int[m];
        int count = 0;
        while (count < m) {
            // 随机[0, length-count)
            int index = new Random().nextInt(array.length - count);
            result[count++] = array[index];
            // 交换到最后位置，保证下次不会再出现
            swap(array, index, array.length - count);
        }
        return result;
    }

    private static void swap(int[] array, int self, int other) {
        if (self == other) {
            return;
        }
        int tmp = array[self];
        array[self] = array[other];
        array[other] = tmp;
    }

    private static boolean checkRepeat(int[] array) {
        List<Integer> list = new ArrayList<>(array.length);
        int count = (int) Arrays.stream(array).filter(s -> {
            boolean contain = list.contains(s);
            list.add(s);
            return !contain;
        }).count();
        return count == array.length;
    }

    public static void main(String[] args) {
        int times = 100;
        boolean result = true;
        for (int i = 1; i <= times; i++) {
            int[] array = RandomUtils.genRandomArrayNoRepeat(i + 10);
            if (!checkRepeat(solution(array, i)) || !checkRepeat(PrintRandomNumbersInArray.printRandom(array, i))) {
                result = false;
                System.out.println("Error");
            }
        }
        if (result) {
            System.out.println("Past");
        }
    }


}
