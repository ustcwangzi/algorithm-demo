package com.wz;

import com.wz.others.KMPAlgorithm;

/**
 * <p>KMP算法</p>
 *
 * @author wangzi
 */
public class KMPAlgorithmTest {

    private static int solution(String str, String pattern) {
        if (str == null || str.length() < 1 || pattern == null || pattern.length() > str.length()) {
            return -1;
        }
        char[] strArray = str.toCharArray();
        char[] patternArray = pattern.toCharArray();
        int[] next = getNextArray(patternArray);

        int i = 0, j = 0;
        while (i < strArray.length && j < patternArray.length) {
            if (j == -1 || strArray[i] == patternArray[j]) {
                i++;
                j++;
            } else {
                j = next[j];
            }
        }
        return j == patternArray.length ? i - j : -1;
    }

    private static int[] getNextArray(char[] array) {
        int[] next = new int[array.length];
        next[0] = -1;
        if (array.length == 1) {
            return next;
        }

        int j = 0, k = -1;
        while (j < array.length - 1) {
            if (k == -1 || array[j] == array[k]) {
                j++;
                k++;
                // next[j] = k;
                if (array[j] == array[k]) {
                    next[j] = next[k];
                } else {
                    next[j] = k;
                }
            } else {
                k = next[k];
            }
        }

        return next;
    }

    public static void main(String[] args) {
        int times = 100;
        boolean result = true;
        for (int i = 0; i < times; i++) {
            String str = RandomUtils.genRandomString();
            if (solution(str, "ababc") != KMPAlgorithm.kmpSearch(str, "ababc")) {
                result = false;
                System.out.println("Error, str:" + str);
            }
        }
        if (result) {
            System.out.println("Past");
        }
    }
}
