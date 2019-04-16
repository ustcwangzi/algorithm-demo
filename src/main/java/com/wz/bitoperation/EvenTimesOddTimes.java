/**
 * <p>Title: EvenTimesOddTimes</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/12/22</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.bitoperation;

import java.util.Arrays;

/**
 * <p>其他数出现偶数次的数组中找到出现奇数次的数</p>
 * <p>
 *     问题一：
 *        给定一个整数数组array，其中只有一个数出现奇数次，其他数出现偶数次，找出这个数。
 *     问题二：
 *        有两个数出现奇数次，其他数出现偶数次，找出这两个数。
 *     问题一解答：
 *        n与0异或为n，n与n异或为0。所以可以使用tmp依次与数组中每次数进行异或，初始时tmp为0，
 *        因为异或操作满足交换律和结合律，因此数组中数字出现的顺序不影响最终结果，tmp依次与数组中每次数进行异或，
 *        出现偶数次的数都会被异或掉，即结果为0，再去与出现奇数次的数异或，最后的结果就是出现奇数次的数。
 *     问题二解答：
 *        假设a和b出现了奇数次，那么最终的tmp就是a^b，即最终结果不为0，在tmp中找到不为0的bit位，假设为第k位，
 *        那么a和b的第k位肯定一个是0，另一个是1。接下来用另一个变量one与数组中第k位上是1的数进行异或，
 *        结束后，one就是a和b中的一个，而tmp^one就是另一个。
 * </p>
 * <p>时间复杂度为O(N)，空间复杂度为O(1)</p>
 *
 * @author wangzi
 */
public class EvenTimesOddTimes {

    public static int findOneOddTimesNum(int[] array) {
        int tmp = 0;
        for (int cur : array) {
            tmp ^= cur;
        }
        return tmp;
    }

    public static int[] printTwoOddTimesNum(int[] array) {
        int[] result = new int[2];
        int tmp = 0, one = 0;
        for (int cur : array) {
            tmp ^= cur;
        }

        // 第k位为1
        int rightOne = tmp & (~tmp + 1);
        for (int cur : array) {
            // 只与第k位是1的异或，结束后one就是两个数中的一个
            if ((cur & rightOne) != 0) {
                one ^= cur;
            }
        }

        result[0] = one;
        result[1] = tmp ^ one;
        return result;
    }

    public static void main(String[] args) {
        int[] arr1 = {3, 3, 2, 3, 1, 1, 1, 3, 1, 1, 1};
        System.out.println(findOneOddTimesNum(arr1));

        int[] arr2 = {4, 3, 4, 2, 2, 2, 4, 1, 1, 1, 3, 3, 1, 1, 1, 4, 2, 2};
        System.out.println(Arrays.toString(printTwoOddTimesNum(arr2)));
    }
}
