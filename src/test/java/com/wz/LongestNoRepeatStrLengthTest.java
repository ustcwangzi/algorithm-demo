package com.wz;

import com.wz.string.LongestNoRepeatSubstring;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>无重复字符的最长子串长度</p>
 *
 * @author wangzi
 */
public class LongestNoRepeatStrLengthTest {
    private static int solution(String str) {
        int result = 0;
        // 最近出现重复字符的位置
        int preIndex = -1;
        // 字符上次出现的位置
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < str.length(); i++) {
            char cur = str.charAt(i);
            // 字符cur再次出现，更新最近出现的位置
            if (map.containsKey(cur)) {
                preIndex = Math.max(preIndex, map.get(cur));
            }
            // preIndex+1...i是无重复元素的
            result = Math.max(result, i - preIndex);
            map.put(cur, i);
        }
        return result;
    }

    public static void main(String[] args) {
        int times = 100;
        boolean result = true;
        for (int i = 0; i < times; i++) {
            String str = RandomUtils.genRandomString();
            if (solution(str) != LongestNoRepeatSubstring.maxUniqueLength(str)) {
                result = false;
                System.out.println("Error, str:" + str);
            }
        }
        if (result) {
            System.out.println("Past");
        }
    }
}
