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
 *
 * @author wangzi
 */
public class ConvertStringToCount {
    public static String getCountString(String str) {
        if (str == null || str.length() == 0) {
            return "";
        }

        char[] chStr = str.toCharArray();
        String result = String.valueOf(chStr[0]);
        int num = 1;
        for (int i = 1; i < chStr.length; i++) {
            if (chStr[i] != chStr[i - 1]) {
                result = concat(result, String.valueOf(num), String.valueOf(chStr[i]));
                num = 1;
            } else {
                num++;
            }
        }

        return concat(result, String.valueOf(num), "");
    }

    private static String concat(String s1, String s2, String s3) {
        return s1 + "_" + s2 + (s3.equals("") ? s3 : "_" + s3);
    }

    private static char getCharAt(String str, int index) {
        if (str == null || str.length() == 0) {
            return 0;
        }

        char[] chStr = str.toCharArray();
        boolean stage = true;
        char cur = 0;
        int num = 0;
        int sum = 0;
        for (int i = 0; i < chStr.length; i++) {
            if (chStr[i] == '_') {
                stage = !stage;
            } else if (stage) {
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

        return sum + num > index ? cur : 0;
    }

    public static void main(String[] args) {
        String str = "aaabbadddffc";
        String result = getCountString(str);
        System.out.println(result);
        System.out.println(getCharAt(result, 9));
    }
}
