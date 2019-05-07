package com.wz;

import com.wz.string.IsDeformation;

public class IsDeformationTest {
    private static boolean solution(String self, String other) {
        if (self.length() != other.length()) {
            return false;
        }

        char[] selfArray = self.toCharArray();
        char[] otherArray = other.toCharArray();
        int[] map = new int[256];
        for (char cur : selfArray) {
            map[cur]++;
        }
        for (char cur : otherArray) {
            if (map[cur]-- == 0) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int times = 100;
        boolean result = true;
        for (int i = 0; i < times; i++) {
            String self = RandomUtils.genRandomString();
            String other = RandomUtils.genRandomString();
            if (solution(self, other) != IsDeformation.isDeformation(self, other)) {
                result = false;
                System.out.println("Error, self:" + self + ", other:" + other);
            }
        }
        if (result) {
            System.out.println("Past");
        }
    }
}
