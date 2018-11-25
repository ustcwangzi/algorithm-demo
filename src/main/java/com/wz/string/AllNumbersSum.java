/**
 * <p>Title: AllNumbersSum</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/11/25</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.string;

/**
 * <p>字符串中数字子串求和</p>
 * <p>
 *     给定一个字符串str，求出其中全部数字子串所代表的数字之和。
 *     要求：
 *     1、忽略小数点字符，例如"A1.3"，其中包含两个数字1和3
 *     2、连续出现的数字子串视为一个数字，例如"A12B3C"，其中包含两个数字12和3
 *     3、如果紧贴数字子串的左侧出现'-'，当连续出现的'-'数量为奇数时，则将数字视为负数，连续出现的'-'数量为偶数时，则将数字视为正数。
 *        例如"A-1BC--12"，其中包含数字-1和12。
 *     解决过程：
 *     1、result表示目前的累加和，num表示目前收集到的数字，positive表示把num累加到result时，num是正数还是负数，
 *        初始时，result=0，num=0，positive=true
 *     2、从左到右遍历str，假设遍历到的字符为char，根据char的不同有如下处理：
 *     2.1、如果char是'0'～'9'，将char-'0'记为cur，假设之前收集到的数字为num，则将num更新为：num*10 + (positive?cur:-cur)
 *          例如str="-123"，初始时，num=0，positive=true；char='-'时，positive变成false；char='1'时，num变成-1；
 *          char='2'时，num变成-12；char='3'时，num变成-123。
 *     2.2、如果char不是'0'～'9'，做累加，令result+=num，累加后num清零，即num=0。然后看char是否为'-'，如果不是，令positive=true；
 *          如果是'-'，看char的前一个字符是否为'-'，如果也是，则改变positive的符号，即positive=!positive；否则positive=false。
 *     3、累加的时机是放在char不是数字字符的时候，那么如果字符串是以数字字符结尾，会出现最后一个数字没有累加的情况，
 *        因此遍历完成后，令result+=num
 *     4、最后返回result
 * </p>
 * <p>
 *     时间复杂度为O(N)，空间复杂度为O(1)
 * </p>
 *
 * @author wangzi
 */
public class AllNumbersSum {
    public static int numSum(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }

        char[] charArray = str.toCharArray();
        // 最终结果
        int result = 0;
        // 当前收集到的数字
        int num = 0;
        // 记录num累加到result时，num时正数还是负数
        boolean positive = true;
        // 当前遍历的值
        int cur;
        for (int i = 0; i < charArray.length; i++) {
            cur = charArray[i] - '0';
            if (cur < 0 || cur > 9) {
                result += num;
                num = 0;
                if (charArray[i] == '-') {
                    if (i - 1 > -1 && charArray[i - 1] == '-') {
                        positive = !positive;
                    } else {
                        positive = false;
                    }
                } else {
                    positive = true;
                }
            } else {
                num = num * 10 + (positive ? cur : -cur);
            }
        }
        result += num;
        return result;
    }

    public static void main(String[] args) {
        String str = "A-1B--2C-D66E";
        System.out.println(numSum(str));
    }
}
