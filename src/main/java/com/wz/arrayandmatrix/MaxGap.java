/**
 * <p>Title: MaxGap</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/1/13</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.arrayandmatrix;

/**
 * <p>数组排序后相邻数的最大差值</p>
 * <p>
 *     给定一个整型数组array，获取排序后的相邻两个数的最大差值。
 *     解决方案：
 *        直接使用排序实现，时间复杂度为O(N*logN)，利用桶排序的思想(不直接进行桶排序)，可以做到空间复杂度为O(N)。
 *        遍历array获取最大值和最小值，记为max和min，如果array长度为N，准备N+1个桶，把max单独放在第N+1号桶中，
 *        array中在[min,max)范围上的数放在1～N号桶里，对于1～N号桶的每个桶来说，负责的区间大小为(max-min)/N。
 *        比如长度为10的数组array中，最大值为110，最小值为10，那么就准备11个桶，array中等于110的数放在11号桶，
 *        区间[10,20)的数放在1号桶、区间[20,30)的数放在2号桶...，区间[100,110)的数放在10号桶。
 *        那么如果一个数为num，它应该分配进的桶号为(num-min)*length/(max-min)。
 *        array共有N个数，min一定会放进1号桶，max一定会放在最后一个桶，所以如果把所有数放在N+1个桶，必然有一个桶是空的。
 *        如果array经过排序后，相邻的数可能会分在同一个桶中，也可能不在同一个桶中。在同一个桶中任何两个数的差值不会大于区间值，
 *        而在空桶左右两边不为空的桶里，相邻数的差值肯定大于区间值，所以产生最大差值的两个相邻数肯定来自于不同的桶。
 *        只要计算出桶之间数的间距，也就是只要记录每个桶的最大值和最小值，最大差值只能来自某个非空桶的最小值减去前一个非空桶的最大值。
 * </p>
 * <p>
 *     时间复杂度为O(N)，空间复杂度为O(N)
 * </p>
 *
 * @author wangzi
 */
public class MaxGap {
    public static int maxGap(int[] array) {
        if (array == null || array.length < 2) {
            return 0;
        }
        int length = array.length;
        int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
        for (int cur : array) {
            min = Math.min(min, cur);
            max = Math.max(max, cur);
        }
        if (max == min) {
            return 0;
        }

        // 桶中是否有元素
        boolean[] hasNum = new boolean[length + 1];
        // 每个桶中的最大值
        int[] maxArray = new int[length + 1];
        // 每个桶中的最小值
        int[] minArray = new int[length + 1];
        int bucket;
        for (int cur : array) {
            // 桶号
            bucket = bucketNum(cur, length, min, max);
            minArray[bucket] = hasNum[bucket] ? Math.min(minArray[bucket], cur) : cur;
            maxArray[bucket] = hasNum[bucket] ? Math.max(maxArray[bucket], cur) : cur;
            hasNum[bucket] = true;
        }

        int result = 0;
        // 上一个桶中的最大值
        int lastMax = maxArray[0];
        int index = 1;
        for (; index <= length; index++) {
            if (hasNum[index]) {
                result = Math.max(result, minArray[index] - lastMax);
                lastMax = maxArray[index];
            }
        }

        return result;
    }

    private static int bucketNum(long num, int length, long min, long max) {
        return (int) ((num - min) * length / (max - min));
    }

    public static void main(String[] args) {
        int[] array = {11, 10, 9, 3, 1, 12};
        System.out.println(maxGap(array));
    }
}
