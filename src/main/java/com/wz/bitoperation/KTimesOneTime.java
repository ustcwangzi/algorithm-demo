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
 * <p>
 *     给定一个整数数组array和一个大于1的整数k，已知array中只有一个数出现了一次，其他数均出现k次，找出这个只出现一次的数。
 *     解决方案：
 *        以下的例子是两个七进制数的无进位相加，即忽略进位的相加，比如：
 *        七进制数a：    6 4 3 2 6 0 1
 *        七进制数b：    3 4 5 0 1 1 1
 *        无进位相加结果：2 1 1 2 0 1 2
 *        可以看出两个七进制数a和b，在i位上无进位相加的结果就是(a(i)+b(i))%7，
 *        同理，两个k进制数c和d，在i位上无进位相加的结果就是(c(i)+d(i))%k。
 *        那么如果k个相同的k进制数进行无进位相加，结果一定是每一位上都是0的k进制数。
 *        因此可以设置一个变量tmp，是一个32位的k进制数，且每一位都是0，然后遍历array，遍历到的整数都转换成k进制数，
 *        然后与tmp进行为进位相加，遍历结束后，再把k进制数转成十进制数，就是最后的结果。
 *        因为k个相同的k进制数为进位相加，结果一定是每一位都是0的k进制数，所以只出现一次的那个数最终会剩下来。
 * </p>
 * <p>时间复杂度为O(N)，空间复杂度为O(1)</p>
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
