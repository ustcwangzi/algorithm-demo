package com.wz;

import com.wz.string.MinWindowLength;

import java.util.Random;

public class MinWindowLengthTest {
    private static int solution(String str, String target) {
        char[] strArray = str.toCharArray();
        char[] targetArray = target.toCharArray();
        // 对于key字符，str还欠target字符串value个
        int[] map = new int[256];
        for (char s : targetArray) {
            map[s]++;
        }

        // 被框住子串的左边界、右边界
        int left = 0, right = 0;
        // str[left...right]还欠target多少
        int deficit = targetArray.length;
        // 最终结果
        int result = 0;
        while (right < strArray.length) {
            map[strArray[right]]--;
            if (map[strArray[right]] >= 0) {
                deficit--;
            }
            if (deficit == 0) {
                while (map[strArray[left]] < 0) {
                    map[strArray[left++]]++;
                }
                result = Math.min(result, right - left + 1);
                deficit++;
                map[strArray[left++]]++;
            }
            right++;
        }
        return result;
    }

    public static void main(String[] args) {
        int times = 100;
        boolean result = true;
        for (int i = 0; i < times; i++) {
            String str = RandomUtils.genRandomString();
            String target = RandomUtils.genRandomString(new Random().nextInt(10) + 1);
            if (solution(str, target) != MinWindowLength.minLength(str, target)) {
                result = false;
                System.out.println("Error, str:" + str);
            }
        }
        if (result) {
            System.out.println("Past");
        }
    }
}
