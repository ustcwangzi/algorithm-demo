package com.wz;

import com.wz.recursionanddp.JumpGame;

import java.util.Arrays;

public class JumpGameTest {
    private static int solution(int[] array) {
        int result = 0;
        int cur = 0;
        int next = 0;
        for (int i = 0; i < array.length; i++) {
            if (cur < i) {
                result++;
                cur = next;
            }
            next = Math.max(next, i + array[i]);
        }
        return result;
    }

    public static void main(String[] args) {
        int times = 100;
        boolean result = true;
        for (int i = 0; i < times; i++) {
            int[] array = RandomUtils.genRandomArray();
            if (solution(array) != JumpGame.jump(array)) {
                result = false;
                System.out.println("Error, array:" + Arrays.toString(array));
            }
        }
        if (result) {
            System.out.println("Past");
        }
    }
}
