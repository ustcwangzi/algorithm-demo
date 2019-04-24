package com.wz;

import com.wz.string.LongestNoRepeatSubstring;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>无重复字符的最长子串</p>
 *
 * @author wangzi
 */
public class LongestNoRepeatStrTest {
    private static String solution(String str) {
        int maxLength = 0, end = -1;
        // 最近出现重复字符的位置
        int preIndex = -1;
        // 字符上次出现的位置
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < str.length(); i++) {
            char cur = str.charAt(i);
            if (map.containsKey(cur)) {
                preIndex = Math.max(preIndex, map.get(cur));
            }
            // preIndex+1...i是无重复元素的
            if (i - preIndex > maxLength) {
                // 更新最大长度和结尾位置
                maxLength = i - preIndex;
                end = i;
            }
            map.put(cur, i);
        }
        return str.substring(end - maxLength + 1, end + 1);
    }

    public static void main(String[] args) {
        int times = 100;
        boolean result = true;
        for (int i = 0; i < times; i++) {
            String str = RandomUtils.genRandomString();
            if (!solution(str).equals(LongestNoRepeatSubstring.maxUniqueString(str))) {
                result = false;
                System.out.println("Error, str:" + str);
            }
        }
        if (result) {
            System.out.println("Past");
        }
    }
}
