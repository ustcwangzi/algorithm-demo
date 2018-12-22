/**
 * <p>Title: KTimesOneTime</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/12/22</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.bitoperation;

/**
 * <p>其他数都出现k次的数组中找到只出现一次的数</p>
 *
 * @author wangzi
 */
public class KTimesOneTime {

    public static int onceNum(int[] array, int k) {
        int[] tmp = new int[32];
        for (int cur : array) {
            setExclusiveOr(tmp, cur, k);
        }
        return getNumFromKSysNum(tmp, k);
    }

    private static void setExclusiveOr(int[] tmp, int value, int k) {
        int[] kSysNum = getKSysNumFromNum(value, k);
        for (int i = 0; i < tmp.length; i++) {
            // 无进位相加
            tmp[i] = (tmp[i] + kSysNum[i]) % k;
        }
    }

    /**
     * 将十进制数转成k进制数
     */
    private static int[] getKSysNumFromNum(int value, int k) {
        int[] result = new int[32];
        int index = 0;
        while (value != 0) {
            result[index++] = value % k;
            value = value / k;
        }
        return result;
    }

    /**
     * 将k进制数转成十进制数
     */
    private static int getNumFromKSysNum(int[] tmp, int k) {
        int result = 0;
        for (int i = tmp.length - 1; i != -1; i--) {
            result = result * k + tmp[i];
        }
        return result;
    }

    public static void main(String[] args) {
        int[] array1 = {1, 1, 1, 2, 6, 6, 2, 2, 10, 10, 10, 12, 12, 12, 6, 9};
        System.out.println(onceNum(array1, 3));

        int[] array2 = {-1, -1, -1, -1, -1, 2, 2, 2, 4, 2, 2};
        System.out.println(onceNum(array2, 5));
    }
}
