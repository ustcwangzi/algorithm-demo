/**
 * <p>Title: MaxAbsoluteBetweenLeftAndRight</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/1/20</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.others;

/**
 * <p>最大的leftMax与rightMax之差的绝对值</p>
 * <p>
 *     给定一个长度为N(N>1)的整型数组array，可以划分成左右两部分，左部分为array[0...K]，右部分为array[K+1...N-1]，
 *     K的取值范围为[0...N-2]，求出所有的划分方案中，左部分最大值减去右部分最大值的绝对值中，最大是多少。
 *     解决方案一：
 *        暴力法。在数组的每个位置i都做一次划分，找到array[0...i]的最大值maxLeft，找到array[i+1...N-1]的最大值maxRight，
 *        然后计算两个值相减的绝对值，每次划分这这样求一次，最终得到最大的相减绝对值。
 *     解决方案二：
 *        预处理法。从左到右遍历数组生成leftArray，leftArray[i]表示array[0...i]中的最大值，再从右到左遍历数组生成rightArray，
 *        rightArray[i]表示array[i...N-1]中的最大值，最后一次遍历看哪种划分下两部分最大值的差值绝对值最大。
 *     解决方案三：
 *        最优解。先求出整个数组中的最大值max，因为max是全局最大，所以不管怎么划分，max要么成为左部分最大值，要么成为右部分最大值，
 *        如果max作为左部分最大值，接下来要让右部分的最大值尽量小，当右部分只含有array[N-1]时就是最大值尽量小的时候；
 *        同理，如果max作为右部分最大值，要让左部分的最大值尽量小，当左部分只含有array[0]时就是最大值尽量小的时候。
 * </p>
 * <p>
 *     方案一时间复杂度为O(N^2)，空间复杂度为O(1)
 *     方案二时间复杂度为O(N)，空间复杂度为O(N)
 *     方案三时间复杂度为O(N)，空间复杂度为O(1)
 * </p>
 *
 * @author wangzi
 */
public class MaxAbsoluteBetweenLeftAndRight {

    public static int maxAbsoluteOne(int[] array) {
        if (array == null || array.length < 2) {
            return 0;
        }

        int result = Integer.MIN_VALUE;
        int maxLeft, maxRight;
        for (int i = 0; i < array.length - 1; i++) {
            // 每一种划分下array[0...i]的最大值
            maxLeft = Integer.MIN_VALUE;
            for (int j = 0; j < i + 1; j++) {
                maxLeft = Math.max(array[j], maxLeft);
            }

            // 每一种划分下array[i+1...N-1]的最大值
            maxRight = Integer.MIN_VALUE;
            for (int j = i + 1; j < array.length; j++) {
                maxRight = Math.max(array[j], maxRight);
            }

            // 每一种划分下maxLeft与maxRight的差值
            result = Math.max(result, Math.abs(maxLeft - maxRight));
        }
        return result;
    }

    public static int maxAbsoluteTwo(int[] array) {
        if (array == null || array.length < 2) {
            return 0;
        }
        // array[0...i]中的最大值
        int[] leftArray = new int[array.length];
        leftArray[0] = array[0];
        // array[i...N-1]中的最大值
        int[] rightArray = new int[array.length];
        rightArray[array.length - 1] = array[array.length - 1];

        // 从左到右遍历生成leftArray
        for (int i = 1; i < array.length; i++) {
            leftArray[i] = Math.max(leftArray[i - 1], array[i]);
        }
        // 从右到左遍历生成rightArray
        for (int i = array.length - 2; i >= 0; i--) {
            rightArray[i] = Math.max(rightArray[i + 1], array[i]);
        }

        int result = Integer.MIN_VALUE;
        for (int i = 0; i < array.length - 1; i++) {
            // 以i为划分的情况下，左右两部分最大值的差值
            result = Math.max(result, Math.abs(leftArray[i] - rightArray[i + 1]));
        }
        return result;
    }

    public static int maxAbsoluteThree(int[] array) {
        if (array == null || array.length < 2) {
            return 0;
        }

        int max = Integer.MIN_VALUE;
        for (int cur : array) {
            // 找出数组中的最大元素
            max = Math.max(cur, max);
        }
        // max作为左部分最大值时，右部分只含有array[N-1]是最小的情况
        // max作为右部分最大值时，左部分只含有array[0]是最小的情况
        return max - Math.min(array[0], array[array.length - 1]);
    }

    public static void main(String[] args) {
        int[] array = {2, 7, 3, 1, 1};
        System.out.println(maxAbsoluteOne(array));
        System.out.println(maxAbsoluteTwo(array));
        System.out.println(maxAbsoluteThree(array));
    }
}
