package com.wz;

import com.wz.others.ManacherAlgorithm;

public class ManacherAlgorithmTest {
    private static int solution(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }

        char[] manacher = convertToManacher(str.toCharArray());
        // 回文半径
        int[] radius = new int[manacher.length];
        // 最右回文右边界及其对称中心
        int right = -1, center = -1;
        // 最大回文半径
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < manacher.length; i++) {
            // 核心
            radius[i] = right > i ? Math.min(radius[2 * center - i], right - i) : 1;
            while (i + radius[i] < manacher.length && i - radius[i] > -1) {
                if (manacher[i + radius[i]] == manacher[i - radius[i]]) {
                    radius[i]++;
                } else {
                    break;
                }
            }
            if (i + radius[i] > right) {
                right = i + radius[i];
                center = i;
            }
            max = Math.max(max, radius[i]);
        }
        return max - 1;
    }

    private static char[] convertToManacher(char[] array) {
        char[] manacher = new char[2 * array.length + 1];
        int index = 0;
        for (int i = 0; i < manacher.length; i++) {
            manacher[i] = (i & 1) == 0 ? '#' : array[index++];
        }
        return manacher;
    }

    public static void main(String[] args) {
        int times = 100;
        boolean result = true;
        for (int i = 0; i < times; i++) {
            String str = RandomUtils.genRandomString();
            if (solution(str) != ManacherAlgorithm.longestPalindromeLength(str)) {
                result = false;
                System.out.println("Error, str:" + str);
            }
        }
        if (result) {
            System.out.println("Past");
        }
    }
}
