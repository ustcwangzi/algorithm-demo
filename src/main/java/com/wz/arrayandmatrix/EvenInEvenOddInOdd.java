/**
 * <p>Title: EvenInEvenOddInOdd</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/1/5</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.arrayandmatrix;

import java.util.Arrays;

/**
 * <p>奇数位置都是奇数或者偶数位置都是偶数</p>
 * <p>
 *     给定一个长度不小于2的数组array，请对其进行调整，要么让所有偶数位置都是位置，要么让所有奇数位置都是奇数。
 *     解决方案：
 *        1、even表示目前array最左边的偶数下标，odd表示目前array最左边的奇数下标，初始时even=0，odd=1。
 *        2、不断检查array的最后一个数，即array[N-1]
 *        2.1、如果array[N-1]是偶数，交换array[N-1]与array[even]，然后令even=even+2
 *        2.1、如果array[N-1]是奇数，交换array[N-1]与array[odd]，然后令odd=odd+2
 *        3、重复步骤二，直到even或odd大于等于N。
 *        以[1,8,3,2,4,6]为例，说明以上过程：
 *        even=0，odd=1，end=6
 *        1、end=6为偶数，交换array[end]与array[even=0]，数组变成[6,8,3,2,4,1]，even=even+2=2；
 *        2、end=1为奇数，交换array[end]与array[odd=1]，数组变成[6,1,3,2,4,8]，odd=odd+2=3；
 *        3、end=8为偶数，交换array[end]与array[even=2]，数组变成[6,1,8,2,4,3]，even=even+2=4；
 *        4、end=3为奇数，交换array[end]与array[odd=3]，数组变成[6,1,8,3,4,2]，odd=odd+2=5；
 *        5、end=2为偶数，交换array[end]与array[even=4]，数组变成[6,1,8,3,2,4]，even=even+2=6；
 *        此时even等于数组长度，说明偶数位置已经都是偶数，过程结束。
 * </p>
 * <p>
 *     时间复杂度为O(N)，空间复杂度为O(1)
 * </p>
 *
 * @author wangzi
 */
public class EvenInEvenOddInOdd {

    public static void modify(int[] array) {
        if (array == null || array.length < 2) {
            return;
        }
        int even = 0, odd = 1;
        int end = array.length - 1;
        while (even <= end && odd <= end) {
            if ((array[end] & 1) == 0) {
                // array[end]是偶数，与even交换
                swap(array, end, even);
                even += 2;
            } else {
                // array[end]是奇数，与odd交换
                swap(array, end, odd);
                odd += 2;
            }
        }
    }

    private static void swap(int[] array, int self, int other) {
        int tmp = array[self];
        array[self] = array[other];
        array[other] = tmp;
    }

    public static void main(String[] args) {
        int[] array = {1, 8, 3, 2, 4, 6};
        modify(array);
        System.out.println(Arrays.toString(array));
    }
}
