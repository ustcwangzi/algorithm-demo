package com.wz;

import com.wz.string.RemoveKZeros;

public class RemoveKZerosTest {
    private static String solution(String str, int k) {
        if (str == null || str.length() == 0 || str.length() < k) {
            return str;
        }
        char[] array = str.toCharArray();
        int count = 0, start = -1;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == '0') {
                count++;
                start = start == -1 ? i : start;
            } else {
                if (count == k) {
                    while (count-- != 0) {
                        array[start++] = 0;
                    }
                }
                count = 0;
                start = -1;
            }
        }
        if (count == k) {
            while (count-- != 0) {
                array[start++] = 0;
            }
        }
        return String.valueOf(array);
    }

    public static void main(String[] args) {
        int times = 100;
        boolean result = true;
        for (int i = 0; i < times; i++) {
            String str = RandomUtils.genRandomString();
            if (!solution(str, 2).equals(RemoveKZeros.remove(str, 2))) {
                result = false;
                System.out.println("Error, str:" + str);
            }
        }
        if (result) {
            System.out.println("Past");
        }
    }

}
