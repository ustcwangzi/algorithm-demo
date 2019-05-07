/**
 * <p>Title: RemoveKZeros</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/11/25</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.string;

/**
 * <p>去掉字符串中连续出现k个0的子串</p>
 * <p>
 *     给定一个字符串str和一个整数k，如果str中正好有连续的k个'0'字符，则将这k个'0'字符去除，返回处理后的字符串。
 *     例如，str="A00B"，k=2，返回"AB"
 *     例如，str="A0000B000"，k=3，返回"A0000B"
 *     解决过程：
 *     1、count表示目前连续'0'的数量，start表示连续'0'出现的开始位置，初始时count=0，start=-1
 *     2、从左到右遍历字符串str，假设当前遍历到i位置的字符为char，根据char的不同做以下处理：
 *     2.1、如果char是字符'0'，则令start = start==-1?i:start，count++
 *     2.2、如果char不是字符'0'，判断count是否等于k，若等于，从start开始去除'0'；若不等于，则不能去除。最后令count=0，start=-1
 *     3、去除连续'0'的时机是放在char不是字符'0'的时候，那么如果str以字符'0'结尾，可能出现最后一组连续k个'0'的情况，
 *        所以在遍历结束后，再检查一下count是否等于k，若等于，从start开始去除'0'。
 * </p>
 * <p>
 *     时间复杂度为O(N)，空间复杂度为O(1)
 * </p>
 *
 * @author wangzi
 */
public class RemoveKZeros {
    public static String remove(String str, int k) {
        if (str == null || str.length() == 0 || k < 1) {
            return str;
        }

        char[] charArray = str.toCharArray();
        // 目前连续'0'的数量与开始位置
        int count = 0, start = -1;
        for (int i = 0; i < charArray.length; i++) {
            if (charArray[i] == '0') {
                count++;
                start = start == -1 ? i : start;
            } else {
                if (count == k) {
                    while (count-- != 0) {
                        charArray[start++] = 0;
                    }
                }
                count = 0;
                start = -1;
            }
        }
        if (count == k) {
            while (count-- != 0) {
                charArray[start++] = 0;
            }
        }
        return String.valueOf(charArray);
    }

    public static void main(String[] args) {
        String str = "000A00B0000C0D000";
        System.out.println(remove(str, 3));
    }
}
