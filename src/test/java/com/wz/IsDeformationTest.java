package com.wz;

import com.wz.string.IsDeformation;

/**
 * <p>两个字符串中字符种类和出现次数是否相同</p>
 *
 * @author wangzi
 */
public class IsDeformationTest {
    private static boolean solution(String self, String other) {
        if (self.length() != other.length()) {
            return false;
        }

        char[] selfArray = self.toCharArray();
        char[] otherArray = other.toCharArray();
        // 假设字符集为256
        int[] map = new int[256];
        // 统计每种字符出现的次数
        for (char cur : selfArray) {
            map[cur]++;
        }
        for (char cur : otherArray) {
            // 如果出现的次数不相等，直接返回false
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
