/**
 * <p>Title: ManacherAlgorithm</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/2/17</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.others;

/**
 * <p>Manacher算法</p>
 *
 * @author wangzi
 */
public class ManacherAlgorithm {

    public static int longestPalindromeLength(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        char[] manacherArray = manacherArray(str);
        // 回文半径数组，radiusArray[i]-1 正好是原字符串中i对应的字符的最长回文串长度
        int[] radiusArray = new int[manacherArray.length];
        // 最右回文右边界
        int right = -1;
        // 最右回文右边界right的对称中心
        int center = -1;
        // 最大回文半径
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < manacherArray.length; i++) {
            // i以center为中心的对称点为 2*center-i
            radiusArray[i] = right > i ? Math.min(radiusArray[2 * center - i], right - 1) : 1;
            while (i + radiusArray[i] < manacherArray.length && i - radiusArray[i] > -1) {
                // 以i为中心，向左右扩展
                if (manacherArray[i + radiusArray[i]] == manacherArray[i - radiusArray[i]]) {
                    radiusArray[i]++;
                } else {
                    break;
                }
            }
            // 更新最右边界和对称中心
            if (i + radiusArray[i] > right) {
                right = i + radiusArray[i];
                center = i;
            }
            max = Math.max(max, radiusArray[i]);
        }
        return max - 1;
    }

    private static char[] manacherArray(String str) {
        char[] array = str.toCharArray();
        char[] result = new char[str.length() * 2 + 1];
        int index = 0;
        for (int i = 0; i < result.length; i++) {
            // 偶数位上插入'#'
            result[i] = (i & 1) == 0 ? '#' : array[index++];
        }
        return result;
    }

    public static void main(String[] args) {
        String str = "abc1234321ab";
        System.out.println(longestPalindromeLength(str));
        str = "abcd123321";
        System.out.println(longestPalindromeLength(str));
    }
}
