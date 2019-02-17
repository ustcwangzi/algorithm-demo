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
 * <p>
 *     问题一：
 *        给定一个字符串str，求出str的最长回文子串的长度。
 *     问题二：
 *        给定一个字符串str，想通过添加字符的方式使得str整体变成回文串，只能在尾部添加字符，求出添加的最短字符串。
 *     问题一解决方案：
 *        最简单的方法是从左到右遍历字符串，遍历到每个字符时，检查以这个字符为中心能够产生多大的回文子串，同时注意奇回文和偶回文的情况，
 *        比如"121"是奇回文，"1221"是偶回文，这种方法明显的缺点是之前遍历的字符完全无法指导后面的遍历过程，即对于每个字符来说，
 *        都需要从自己的位置出发，向左右两个方向检查，总的时间复杂度为O(N*N)。Manacher算法可以做到O(N)的时间复杂度。过程如下：
 *        1、因为奇回文和偶回文在判断时比较麻烦，所以先对str进行处理，把str开始、结尾和中间都插入特殊字符'#'来得到一个新的字符串数组，
 *           比如"aaaba"，处理后为"#a#a#a#b#a#"，再比如"aa"，处理后为"#a#a#"，这样原来的字符串长度不管是奇数还是偶数，
 *           最终转换都能够得到一个长度为奇数的字符串，假设处理后的字符数组为manacherArray；
 *        2、需要用一个辅助数组radiusArray[i]表示以字符manacherArray[i]为中心的最长回文字串的最右字符到manacherArray[i]的长度，
 *           称之为回文半径数组，比如"#a#a#a#b#a#"的radiusArray为"1,2,3,4,3,2,1,4,1,2,1"；
 *           radiusArray数组有个性质，即radiusArray[i]-1 正好是该回文子串在原字符串中的长度，因为转换得到的字符串中，
 *           所有的回文字串的长度都为奇数，那么对于以manacherArray[i]为中心的最长回文字串，其长度就为2*radiusArray[i]-1，
 *           经过观察可知，manacherArray中所有的回文子串，其中分隔符的数量一定比其他字符的数量多1，也就是有radiusArray[i]个分隔符，
 *           剩下radiusArray[i]-1个字符来自原字符串，所以该回文串在原字符串中的长度就为radiusArray[i]-1；
 *           这样，原问题就转化为了求所有的radiusArray[i]
 *        3、从左往右依次计算radiusArray[i]，当计算radiusArray[i]时，radiusArray[j](0<=j<i)已经计算完毕。
 *           right表示最右回文右边界，指的是这个位置及之前的位置的回文子串，所到达的最右边的地方。
 *           center表示最右回文右边界right的对称中心，指的是最右回文右边界right的中心点。
 *           当遍历到i位置时，主要分为两种情况：
 *           ...j... ...center... ...i... ...right...
 *        3.1、i <= right，此时i关于对称中心center的对称位置j为2*center-i，
 *             如果radiusArray[j] < right-i，说明以j为中心的回文子串一定在以center为中心的回文子串内部，又且j和i关于center对称，
 *             所以radiusArray[i]=radiusArray[j]；
 *             如果radiusArray[j] >= right-i，说明以i为中心的回文串可能会延伸到right之外，而这部分还没有进行匹配，
 *             此时这部分需要一个一个进行匹配，直到发生失配，从而更新right、center以及radiusArray[i]；
 *        3.2、i > right，说明对于中点为i的回文串还一点都没有匹配，这个时候，就只能老老实实地一个一个匹配了，
 *             匹配完成后要更新right、center以及radiusArray[i]。
 * </p>
 *
 * @author wangzi
 */
public class ManacherAlgorithm {

    /**
     * 最长回文子串的长度
     */
    public static int longestPalindromeLength(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        char[] manacherArray = manacherArray(str);
        // 回文半径数组，radiusArray[i]-1 正好是该回文子串在原字符串中的长度
        int[] radiusArray = new int[manacherArray.length];
        // 最右回文右边界
        int right = -1;
        // 最右回文右边界right的对称中心
        int center = -1;
        // 最大回文半径
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < manacherArray.length; i++) {
            // i以center为中心的对称点为 2*center-i
            radiusArray[i] = right > i ? Math.min(radiusArray[2 * center - i], right - i) : 1;
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

    /**
     * 末尾添加最短字符串变成回文串
     */
    public static String shortestEnd(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        char[] manacherArray = manacherArray(str);
        // 回文半径数组，radiusArray[i]-1 正好是该回文子串在原字符串中的长度
        int[] radiusArray = new int[manacherArray.length];
        // 最右回文右边界
        int right = -1;
        // 最右回文右边界right的对称中心
        int center = -1;
        // 最右回文右边界到达最后位置时的回文半径
        int maxContainsEndRadius = -1;
        for (int i = 0; i < manacherArray.length; i++) {
            // i以center为中心的对称点为 2*center-i
            radiusArray[i] = right > i ? Math.min(radiusArray[2 * center - i], right - i) : 1;
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
            // 最右回文边界已到达最后位置
            if (right == manacherArray.length) {
                maxContainsEndRadius = radiusArray[i];
                break;
            }
        }
        char[] result = new char[str.length() - maxContainsEndRadius + 1];
        for (int i = 0; i < result.length; i++) {
            // 将之前不是最长回文子串的部分逆序
            result[result.length - 1 - i] = manacherArray[i * 2 + 1];
        }
        return String.valueOf(result);
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
        System.out.println(shortestEnd(str));
        str = "abcd123321";
        System.out.println(longestPalindromeLength(str));
        System.out.println(shortestEnd(str));
    }
}
