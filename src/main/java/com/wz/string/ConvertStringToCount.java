/**
 * <p>Title: ConvertStringToCount</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/12/3</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.string;

/**
 * <p>字符串的统计字符串</p>
 * <p>
 *     问题一：
 *        给定字符串str，返回str的统计字符串，例如"aaabbadddffc"的统计字符串为"a_3_b_2_a_1_d_3_f_2_c_1"。
 *     解决方案：
 *        1、字符串为空时，统计字符串不存在
 *        2、字符串不为空，result表示其统计字符串，num表示当前字符的数量，初始时result只包含str的起始字符str[0]，同时num=1
 *        3、从str[1]开始从左到右遍历str，假设当前遍历到i位置，如果str[i]==str[i-1]，说明当前连续字符(str[i-1])还未结束，
 *           令num++，然后继续遍历下一个字符。如果str[i]!=str[i-1]，说明当前连续字符(str[i-1])已结束，
 *           令result=result+"_"+num+"_"+str[i]，同时num=1，继续遍历下一个字符。
 *           以"aaabbadddffc"为例，开始时result="a"，num=1，遍历str[1~2]时，'a'处于连续状态，num增加到3，遍历str[3]时，出现字符'b'，
 *           令result="a_3_b"，num=1；遍历str[4]，'b'处于连续状态，num增加到2，遍历str[5]时，出现字符'b'，result="a_3_b_2_a"，
 *           num=1。以此类推，直到遍历最后一个字符时，result="a_3_b_2_a_1_d_3_f_2_c"，num=1。
 *        4、步骤三中，发现新字符时再将该字符连续出现的次数放在result后面，所以遍历结束时，最后一个字符的次数还未放入，需要单独加入。
 *     问题二：
 *        给定统计字符串str，再给定整数值index，返回str所代表的原始字符串上第index个字符，例如"a_1_b_100"的原始字符第50个字符为'b'。
 *     解决方案：
 *        1、stage为true表示目前处在遇到字符阶段，stage为false表示目前处在遇到字符统计阶段，cur表示在上一个遇到字符阶段时遇到的字符，
 *           num表示上一个遇到字符统计阶段时字符出现的次数，sum表示目前遍历到的位置相当于愿字符串中的什么位置，
 *           初始时，stage=true，cur=0(空字符)，num=0，sum=0
 *        2、从左到右遍历str，以"a_100_b_2_c_4"，index=105，说明这一过程。遍历完str[0]='a'后，cur='a'。遇到'_'说明该转阶段了，
 *           stage=!stage；遇到str[2]='1'时，num=1；遇到str[3]='0'时，num=10；遇到str[4]='0'时，num=100；遇到str[6]='b'时，
 *           出现了新字符，令sum+=num(即sum=100)，发现sum<index，说明需要继续遍历，cur='b'，num=0，因为'a'的统计已完成，现在num开始
 *           表示'b'的连续数量。即没遇到一个新字符，把上一个已完成的统计数量num加到sum上，再看sum是否达到index，达到则返回上一个字符cur。
 *        3、每个字符的统计都在遇到新字符时加到sum上，所以当遍历完成时，最后一个字符的统计并不会加到sum上，需要单独加入。
 * </p>
 *
 * @author wangzi
 */
public class ConvertStringToCount {
    /**
     * 字符串转为统计字符串
     */
    public static String getCountString(String str) {
        if (str == null || str.length() == 0) {
            return "";
        }

        char[] chStr = str.toCharArray();
        String result = String.valueOf(chStr[0]);
        // 当前字符串的数量
        int num = 1;
        for (int i = 1; i < chStr.length; i++) {
            // 发现新字符时，拼接
            if (chStr[i] != chStr[i - 1]) {
                result = concat(result, String.valueOf(num), String.valueOf(chStr[i]));
                num = 1;
            } else {
                num++;
            }
        }

        // 最后一个位置也要拼接
        return concat(result, String.valueOf(num), "");
    }

    private static String concat(String s1, String s2, String s3) {
        return s1 + "_" + s2 + (s3.equals("") ? s3 : "_" + s3);
    }

    /**
     * 根据统计字符串返回原字符串第index位置的字符
     */
    public static char getCharAt(String str, int index) {
        if (str == null || str.length() == 0) {
            return 0;
        }

        char[] chStr = str.toCharArray();
        // 目前处在遇到字符阶段:true ，目前处在遇到字符统计阶段:false
        boolean stage = true;
        // 上一个遇到字符阶段时遇到的字符
        char cur = 0;
        // 上一个遇到字段统计阶段时，字符出现的数量
        int num = 0;
        // 目前遍历到的位置相当于原字符串中的什么位置
        int sum = 0;
        for (int i = 0; i < chStr.length; i++) {
            // 遇到'_'说明转阶段了
            if (chStr[i] == '_') {
                stage = !stage;
            } else if (stage) {
                // 出现新字符
                sum += num;
                if (sum > index) {
                    return cur;
                }
                num = 0;
                cur = chStr[i];
            } else {
                num = num * 10 + chStr[i] - '0';
            }
        }

        // 判断最后一个位置
        return sum + num > index ? cur : 0;
    }

    public static void main(String[] args) {
        String str = "aaabbadddffc";
        String result = getCountString(str);
        System.out.println(result);
        System.out.println(getCharAt(result, 9));
    }
}
