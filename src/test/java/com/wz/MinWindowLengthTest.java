package com.wz;

import com.wz.string.MinWindowLength;

import java.util.Random;

/**
 * <p>包含给定字符串中所有字符的最小子串长度</p>
 *
 * @author wangzi
 */
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
        // right右移是在归还，left右移是在取回，deficit为0时才右移left
        while (right < strArray.length) {
            // right右移时，表示在还给target字符
            map[strArray[right]]--;
            // 减过之后大于等于0，说明确实需要还，因此总的欠target的字符减少
            if (map[strArray[right]] >= 0) {
                deficit--;
            }
            // deficit为0时，left右移，表示在取回字符
            if (deficit == 0) {
                // map中的value小于0，说明还多了，可以取回
                while (map[strArray[left]] < 0) {
                    map[strArray[left++]]++;
                }
                result = Math.min(result, right - left + 1);
                // 不能取时停止，然后left再右移一次，表示此时开始又要欠target字符了
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
