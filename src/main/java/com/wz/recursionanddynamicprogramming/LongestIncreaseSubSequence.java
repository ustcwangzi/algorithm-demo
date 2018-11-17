/**
 * <p>Title: LongestIncreaseSubSequence</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/11/17</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.recursionanddynamicprogramming;

import java.util.Arrays;

/**
 * <p>最长递增子序列</p>
 * <p>
 *     给定数组array，查找array的最长递增子序列。
 *     eg. array={2, 1, 5, 3, 6, 4, 8, 9, 7}，返回[1, 3, 4, 8, 9]
 *     方案一：
 *        1、生成长度为N的数组result，result[i]表示以array[i]为结尾的情况下，array[0...i]中的最长递增子序列长度
 *        2、对于array[0]，result[0]=1，接下来从左到右依次计算以每个位置的数结尾情况下，最长递增子序列长度
 *        3、计算以array[i]结尾情况下的最长递增子序列长度，即result[i]，此时最长递增子序列以array[i]结尾，那么在array[0...i-1]中
 *           所有比array[i]小的数都可以作为倒数第二个数，在这些数中选择那个以该数结尾的最长递增子序列长度最大的，即
 *           result[i] = max{result[j] + 1} (0<=j<i, array[j]<array[i])，如果array[0...i-1]中所有数都不比array[i]小，
 *           则令result[i]=1，说明以array[i]结尾情况下的最长递增子序列只包含array[i]
 *        eg. array={2, 1, 5, 3, 6, 4, 8, 9, 7}
 *              i   :0  1  2  3  4  5  6  7  8
 *            result:1  1  2  2  3  3  4  5  4
 *        然后，根据求出的result数组得到最长递增子序列
 *        1、遍历result数组，找到最大值以及位置，本例中最大值为5，位置为7，说明最长递增子序列长度为5，并且以array[7]结尾
 *        2、从array数组的位置7开始从右到左遍历，如果某位置i，既有array[i]<array[7]又有result[i]=result[7]-1，
 *           说明array[i]是作为最长递增子序列倒数第二个数，本例中为array[6]
 *        3、从array数组的位置6开始从右到左遍历，按照同样的过程找到倒数第三个数
 *        4、重复以上过程，直到所有数都找到
 *     方案二：
 *        利用二分查找对生成result数组进行优化
 *        生成长度为N的数组ends，初识时ends[0]=array[0]，整数partition，初识时partition=0，从左到右遍历array数组的过程中，
 *        求解result的过程需要使用ends数组和partition变量。遍历过程中，ends[0...partition]为有效区域，ends其他部分为无效区域。
 *        对于有效区域上的位置b，如果ends[b]=c，则表示遍历到目前为止，所有长度为b+1的递增序列中，最小的结尾数是c，无效区域不用考虑。
 *        比如array={2, 1, 5, 3, 6, 4, 8, 9, 7}，初始化时result[0]=1，ends[0]=2，partition=0
 *        1、array[1]=1，ends=[1]，result=[1, 1]
 *        2、array[2]=5，ends=[1, 5]，result=[1, 1, 2]
 *        3、array[3]=3，ends=[1, 3]，result=[1, 1, 2, 2]
 *        4、array[4]=6，ends=[1, 3, 6]，result=[1, 1, 2, 2, 3]
 *        5、array[5]=4，ends=[1, 3, 4]，result=[1, 1, 2, 2, 3, 3]
 *        6、array[6]=8，ends=[1, 3, 4, 8]，result=[1, 1, 2, 2, 3, 3, 4]
 *        7、array[7]=9，ends=[1, 3, 4, 8, 9]，result=[1, 1, 2, 2, 3, 3, 4, 5]
 *        8、array[8]=7，ends=[1, 3, 4, 7, 9]，result=[1, 1, 2, 2, 3, 3, 4, 5, 4]
 * <p>
 *     方案一计算result数组时间复杂度为O(N*N)，根据result数组得到最长递增子序列时间复杂度为O(N)，所以整个过程的时间复杂度为O(N*N)
 *     方案一计算result数组时间复杂度为O(N*logN)，整个过程的时间复杂度为O(N*logN)
 * </p>
 *
 * @author wangzi
 */
public class LongestIncreaseSubSequence {

    public static int[] lisOne(int[] array) {
        if (array == null || array.length == 0) {
            return null;
        }

        int[] result = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = 1;
            // array[0...i-1]中所有比array[i]小的数中，选择那个以该数结尾的最长递增子序列最大的
            for (int j = 0; j < i; j++) {
                if (array[i] > array[j]) {
                    result[i] = Math.max(result[i], result[j] + 1);
                }
            }
        }

        return generateLIS(array, result);
    }

    private static int[] generateLIS(int[] array, int[] result) {
        int length = 0;
        int index = 0;
        // 找到最长递增子序列的长度和最后一个字符位置
        for (int i = 0; i < result.length; i++) {
            if (result[i] > length) {
                length = result[i];
                index = i;
            }
        }

        int[] lis = new int[length];
        lis[--length] = array[index];
        // 从最后一个字符位置开始逆序还原决策路径
        for (int i = index; i >= 0; i--) {
            if (array[i] < array[index] && result[i] == result[index] - 1) {
                lis[--length] = array[i];
                index = i;
            }
        }
        return lis;
    }

    public static int[] lisTwo(int[] array) {
        if (array == null || array.length == 0) {
            return null;
        }

        int[] result = new int[array.length];
        int[] ends = new int[array.length];
        ends[0] = array[0];
        result[0] = 1;

        int partition = 0;
        int left, mid, right;
        for (int i = 1; i < array.length; i++) {
            left = 0;
            right = partition;
            // 二分查找法寻找array[0...partition]中最左边大于或等于array[i]的数
            while (left <= right) {
                mid = (left + right) / 2;
                if (array[i] > ends[mid]) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
            partition = Math.max(partition, left);
            ends[left] = array[i];
            result[i] = left + 1;
        }

        return generateLIS(array, result);
    }

    public static void main(String[] args) {
        int[] array = {2, 1, 5, 3, 6, 4, 8, 9, 7};
        System.out.println(Arrays.toString(lisOne(array)));
        System.out.println(Arrays.toString(lisTwo(array)));
    }
}
