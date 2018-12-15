/**
 * <p>Title: FindNewTypeChar</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/12/15</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.string;

/**
 * <p>找到指定位置的新类型字符</p>
 *
 * @author wangzi
 */
public class FindNewTypeChar {

    public static String getNewChar(String str, int k) {
        if (str == null || str.length() == 0 || k < 0 || k >= str.length()) {
            return "";
        }

        char[] array = str.toCharArray();
        int upperNum = 0;
        for (int i = k - 1; i >= 0; i--) {
            if (array[i] < 'A' || array[i] > 'Z') {
                break;
            }
            upperNum++;
        }
        // 奇数
        if ((upperNum & 1) == 1) {
            return str.substring(k - 1, k + 1);
        }
        if (array[k] >= 'A' && array[k] <= 'Z') {
            return str.substring(k, k + 2);
        }
        return String.valueOf(array[k]);
    }

    public static void main(String[] args) {
        String str = "aaABCDEcBCg";
        System.out.println(getNewChar(str, 7));
        System.out.println(getNewChar(str, 4));
        System.out.println(getNewChar(str, 10));
    }
}
