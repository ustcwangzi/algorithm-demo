/**
 * <p>Title: MinWindowLength</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/12/15</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.string;

/**
 * <p>最小包含子串的长度</p>
 * <p>
 *     给定字符串str和target，求出str的子串中含有target所有字符的最小子串长度。
 *     例如：
 *     str="abcde"，target="ac"，满足条件的子串中"abc"是最短的，因此返回3；
 *     str="12345"，target="344"，最小包含子串不存在，因此返回0。
 *     解决方案：
 *        以str="adabbca"，target="acb"来说明整个过程
 *        生成map，代表对于key字符，str字符串目前还欠target字符串value个；
 *        left：遍历str过程中，str[left...right]表示被框住的子串，left表示子串的左边界；
 *        right：表示被框住子串的右边界；
 *        match：表示对所有字符来说，str[left...right]目前一共欠target多少个；
 *        minLen：最终想要的结果，即最小包含字符的长度。
 *        初始时，left=0，right=0，match=3，minLen=整数最大值，map为遍历target得到的记录：('a',1)('b',1)('c',1)。
 *        1、right==0，str[0]=='a'，把map中key为'a'的value减1，减去后value为0，说明之前大于0，那么str归还了1个'a'，
 *           match也要减1，表示对于多有字符来说，str目前规划了1个。
 *           此时各个变量为：
 *           map：('a',0)('b',1)('c',1)，left=0，right=0，match=2，minLen=Integer.MAX_VALUE
 *        2、right==1，str[1]=='d'，map中没有'd'记录，因此加一条('d',-1)，此时value为-1，说明该字符不是target需要的，match不变。
 *           此时各个变量为：
 *           map：('a',0)('b',1)('c',1)('d',-1)，left=0，right=1，match=2，minLen=Integer.MAX_VALUE
 *        3、right==2，str[2]=='a'，把map中key为'a'的value减1，变为('a',-1)，说明减之前str就不欠target当前字符，match不变。
 *           此时各个变量为：
 *           map：('a',-1)('b',1)('c',1)('d',-1)，left=0，right=2，match=2，minLen=Integer.MAX_VALUE
 *        4、right==3，str[3]=='b'，('b',1)变为('b',0)，减去后value为0，说明归还字符有效，match减1。
 *           此时各个变量为：
 *           map：('a',-1)('b',0)('c',1)('d',-1)，left=0，right=3，match=1，minLen=Integer.MAX_VALUE
 *        5、right==4，str[4]=='b'，('b',0)变为('b',-1)，value为-1，说明归还字符无效，match不变。
 *           此时各个变量为：
 *           map：('a',-1)('b',-1)('c',1)('d',-1)，left=0，right=4，match=1，minLen=Integer.MAX_VALUE
 *        6、right==5，str[5]=='c'，('c',1)变为('c',0)，减去后value为0，说明归还字符有效，match减1。
 *           此时match变为0，说明str把需要归还的字符还完了，被框住的子串str[0...5]肯定包含target所有字符。left开始向右移动。
 *        6.1、left==0，str[0]=='a'，map中对应的记录为('a',-1)，说明即使str拿回该字符，也不会欠target，拿回，('a',-1)变为('a',0)
 *        6.2、left==1，str[1]=='d'，map中对应的记录为('d',-1)，可以拿回，('d',-1)变为('d',0)
 *        6.3、left==2，str[2]=='a'，map中对应的记录为('a',0)，拿回会导致str欠target，不能拿回，left停止移动。
 *             因此当前最小窗口为str[2...5]，minLen更新为4。
 *             此后更小的窗口子串一定不会从left位置开始，而是从left之后的位置开始。
 *             又有str[2]=='a'，令('a',0)变为('a',1)，match++，然后left++，表示现在的str[3...5]又开始欠target字符了。
 *             此时各个变量为：
 *             map：('a',1)('b',-1)('c',0)('d',-1)，left=3，right=5，match=1，minLen=4
 *        7、right==6，str[6]=='a'，('a',1)变为('a',0)，减去后value为0，说明归还字符有效，match减1。
 *           此时match变为0，进入left向右移动的过程。
 *        7.1、left==3，str[3]=='b'，map中对应的记录为('b',-1)，可以拿回，('b',-1)变为('b',0)
 *        7.2、left==4，str[4]=='b'，map中对应的记录为('b',0)，不能拿回，left停止移动
 *             因此当前最小窗口为str[4...6]，minLen更新为3。
 *             与步骤6.3类似，str[4]=='b'，令('b',0)变为('b',1)，match++，left++，表示现在的str[5...6]又开始欠target字符了。
 *             此时各个变量为：
 *             map：('a',0)('b',1)('c',0)('d',-1)，left=5，right=6，match=1，minLen=3
 *        8、right==7，遍历结束。
 *        如果结束时minLen依然是Integer.MAX_VALUE，说明未出现过符合条件的窗口，返回0即可，否则返回minLen。
 * </p>
 *
 * <p>时间复杂度为O(N)</p>
 *
 * @author wangzi
 */
public class MinWindowLength {

    public static int minLength(String str, String target) {
        if (str == null || target == null || str.length() < target.length()) {
            return 0;
        }

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
        int match = targetArray.length;
        // 最终结果
        int minLen = Integer.MAX_VALUE;
        while (right < strArray.length) {
            map[strArray[right]]--;
            if (map[strArray[right]] >= 0) {
                match--;
            }
            if (match == 0) {
                // match为0时，left右移
                while (map[strArray[left]] < 0) {
                    map[strArray[left++]]++;
                }
                minLen = Math.min(minLen, right - left + 1);
                match++;
                map[strArray[left++]]++;
            }
            right++;
        }
        return minLen == Integer.MAX_VALUE ? 0 : minLen;
    }

    public static void main(String[] args) {
        System.out.println(minLength("adabbca", "acb"));
    }
}
