/**
 * <p>Title: MultiplyExceptOwn</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/1/11</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.arrayandmatrix;

import java.util.Arrays;

/**
 * <p>不包含本位置值的累乘结果数组</p>
 * <p>
 *     给定一个整型数组array，获取不包含本位置值的累乘数组。
 *     例如，array=[1,2,3,4]，返回[24,12,8,6]。
 *     解决方案一：
 *        先计算处所有元素的累乘积all，如果数组中不含有0，则结果为result[i]=all/array[i]；如果数组中只含有一个0，假设array[i]==0，
 *        则result[i]=all，其他位置累乘结果全为0；如果数组中0的数量大于1，则所有的结果都为0。
 *     解决方案二：
 *        不使用除法，实现空间复杂度为O(1)的解法：
 *        1、生成两个长度array一样的新数组left[]和right[]，left[]表示从左到右的累乘，right[]表示从右到左的累乘
 *        2、一个位置上除去自己值的乘积，就是自己左边的累乘 * 自己右边的累乘，即result[i] = left[i-1] * right[i+1]
 *        3、最左位置和最右位置的累乘比较特殊，即result[0]=right[1]，result[N-1]=left[N-2]
 *        以上步骤可以得到结果，但是使用了两个辅助数组left[]和right[]，可以通过结果数组result[]复用的方式，来省掉两个辅助数组。
 *        即，先从左到右使用result[]作为辅助数组计算，再从右到左将result[]调整为结果数组。
 * </p>
 * <p>
 *     方案一时间复杂度为O(N)，空间复杂度为O(1)
 *     方案二时间复杂度为O(N)，空间复杂度为O(1)
 * </p>
 *
 * @author wangzi
 */
public class MultiplyExceptOwn {
    public static int[] multiplyOne(int[] array) {
        if (array == null || array.length < 2) {
            return null;
        }
        // 0的个数
        int zeroCount = 0;
        // 所有元素的累乘
        int all = 1;
        for (int cur : array) {
            if (cur != 0) {
                all *= cur;
            } else {
                zeroCount++;
            }
        }

        int[] result = new int[array.length];
        // 不存在0时，result[i]=all/array[i]
        if (zeroCount == 0) {
            for (int i = 0; i < array.length; i++) {
                result[i] = all / array[i];
            }
        }
        // 只有一个0时，值为0的那个位置结果为all，其他均为0
        if (zeroCount == 1) {
            for (int i = 0; i < array.length; i++) {
                if (array[i] == 0) {
                    result[i] = all;
                }
            }
        }
        return result;
    }

    public static int[] multiplyTwo(int[] array) {
        if (array == null || array.length < 2) {
            return null;
        }

        int[] result = new int[array.length];
        result[0] = array[0];
        for (int i = 1; i < array.length; i++) {
            // 此时result[i]为array[0]到array[i]的累乘
            result[i] = result[i - 1] * array[i];
        }

        // tmp用以记录从array[i+1]到array[N-1]的累乘
        int tmp = 1;
        for (int i = array.length - 1; i > 0; i--) {
            // 此时result[i]就是array[0...i-1]的累乘 * array[i+1...N-1]的累乘
            result[i] = result[i - 1] * tmp;
            tmp *= array[i];
        }
        result[0] = tmp;

        return result;
    }

    public static void main(String[] args) {
        int[] array = {1, 2, 3, 4};
        System.out.println(Arrays.toString(multiplyOne(array)));
        System.out.println(Arrays.toString(multiplyTwo(array)));
    }
}
