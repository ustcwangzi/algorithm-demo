/**
 * <p>Title: Artist</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/2/21</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.others;

/**
 * <p>画匠问题</p>
 * <p>
 *     给定一个整型数组array，数组中的每个值都为正数，表示完成一幅画作需要的时间，再给定一个整数number表示画匠的数量，
 *     每个画匠只能画连在一起的画作。所有的画家并行工作，请返回完成所有的画作需要的最少时间。
 *     举例：
 *        array=[3, 1, 4]，number=2
 *        最好的分配方式为第一个画匠画3和1，所需时间为4，第二个画匠画4，所需时间为4。
 *        因为并行工作，所以最少时间为4，如果分配方式为第一个画匠画3，所需时间为3，第二个画匠画1和4，所需的时间为5，那么最少时间为5，
 *        显然没有第一种分配方式好，所以返回4。
 *        array=[1, 1, 1, 4, 3]，number=3
 *        最好的分配方式为第一个画匠画前三个1，所需时间为3，第二个画匠画4，所需时间为4，第三个画匠画3，所需时间为3，返回4。
 *     解决方案一：
 *        动态规划。如果只有一个画匠，那么对于这个画匠来说，array[0...j]上的画作最少时间就是array[0...j]的累加和。
 *        如果有两个画匠，有如下几种方案：
 *        1、第一个画匠负责array[0]，第二个画匠负责array[1...j]，所需的时间为max{sum[0], sum[1...j]}；
 *        2、第一个画匠负责array[0...1]，第二个画匠负责array[2...j]，所需的时间为max{sum[0...1], sum[2...j]}；
 *        3、第一个画匠负责array[0...k]，第二个画匠负责array[k+1...j]，所需要的时间为max{sum[0...k], sum[k+1...j]}。
 *        所有情况中所需要时间最少的就是最终的答案。
 *        当画匠数量大于2时，假设dp[i][j]表示i+1个画匠搞定array[0...j]这些画所需要的最少时间，那么有如下几种方案：
 *        1、第1～i个画匠负责array[0]，第i+1个画匠负责array[1...j]，所需的时间为max{dp[i-1][0], sum[1...j]}；
 *        2、第1～i个画匠负责array[0...1]，第i+1个画匠负责array[2...j]，所需的时间为max{dp[i-1][1], sum[2...j]}；
 *        3、第1～i个画匠负责array[0...k]，第i+1个画匠负责array[k+1...j]，所需要的时间为max{dp[i-1][k], sum[k+1...j]}。
 *        哪种情况所需要的时间最少，dp[i][j]的值就等于哪个。
 *        即dp[i][j] = min{ max{dp[i-1][k], sum[k+1...j]}(0<=k<j) }
 *     解决方案二：
 *        动态规划的空间压缩。
 *        方案一中dp[i][j]仅依赖了dp[i-1][...]的值，因为可以使用空间压缩方式实现。
 *     解决方案三：
 *        最优解。如果规定每个画匠的作画时间不能多余limit，那么要几个画匠才能完成呢，这个问题非常简单，从左到右遍历array做累加，
 *        一旦累加和大于limit，则认为当前的画array[i]必须分给下一个画匠，同时让累加和清零，从array[i]开始重新累加。
 *        如果遍历中发现某幅画的时间大于limit，说明即时单独分配一个画匠只画这幅画，也无法在limit内完成，直接返回Integer.MAX_VALUE。
 *        接下来，使用二分法，通过调整limit的大小，看需要的画匠数量是大于给定画匠还是小于给定画匠，然后决定将limit往上或往下调整，
 *        limit一开始为[0,array的累加和]，然后不断二分，缩小范围，最终确定limit的大小。
 * </p>
 * <p>
 *     方案一和方案二的时间复杂度为O(number*N^2)
 *     方案三中，假设array的累加和为S，那么时间复杂度为O(N*logS)
 * </p>
 *
 * @author wangzi
 */
public class Artist {

    public static int solutionOne(int[] array, int number) {
        if (array == null || array.length == 0 || number < 1) {
            throw new RuntimeException("error");
        }

        int[] sumArray = new int[array.length];
        int[][] dp = new int[number][array.length];
        sumArray[0] = array[0];
        dp[0][0] = array[0];

        for (int i = 1; i < sumArray.length; i++) {
            // sum[0...i]
            sumArray[i] = sumArray[i - 1] + array[i];
            // 只有一个画匠，直接时间叠加即可
            dp[0][i] = sumArray[i];
        }
        // i+1个画匠搞定array[0...j]这些画所需要的最少时间
        for (int i = 1; i < dp.length; i++) {
            for (int j = 0; j < dp[0].length; j++) {
                int min = Integer.MAX_VALUE;
                // 前i个画匠负责0～k副画，最后一个画匠负责剩下的，遍历获取最优解
                for (int k = 0; k < j; k++) {
                    min = Math.min(min, Math.max(dp[i-1][k], sumArray[j] - sumArray[k]));
                }
                dp[i][j] = min;
            }
        }
        return dp[number-1][array.length-1];
    }

    /**
     * 动态规划的空间压缩
     */
    public static int solutionTwo(int[] array, int number) {
        if (array == null || array.length == 0 || number < 1) {
            throw new RuntimeException("error");
        }

        int[] sumArray = new int[array.length];
        int[] map = new int[array.length];
        sumArray[0] = array[0];
        map[0] = array[0];

        for (int i = 1; i < sumArray.length; i++) {
            sumArray[i] = sumArray[i - 1] + array[i];
            map[i] = sumArray[i];
        }

        for (int i = 1; i < number; i++) {
            for (int j = map.length - 1; j > i - 1; j--) {
                int min = Integer.MAX_VALUE;
                for (int k = i - 1; k < j; k++) {
                    min = Math.min(min, Math.max(map[k], sumArray[j] - sumArray[k]));
                }
                map[j] = min;
            }
        }
        return map[array.length - 1];
    }

    /**
     * 最优解
     */
    public static int solutionThree(int[] array, int number) {
        if (array == null || array.length == 0 || number < 1) {
            throw new RuntimeException("error");
        }
        // 画匠人数 大于 需要画作个数
        if (array.length < number) {
            int max = Integer.MIN_VALUE;
            for (int cur : array) {
                max = Math.max(max, cur);
            }
            return max;
        }

        int minSum = 0, maxSum = 0;
        for (int cur : array) {
            maxSum += cur;
        }
        // 二分法
        while (minSum < maxSum - 1) {
            int mid = (minSum + maxSum) / 2;
            if (getNeedNumber(array, mid) > number) {
                minSum = mid;
            } else {
                maxSum = mid;
            }
        }
        return maxSum;
    }

    /**
     * 作画时间不能多于limit时，需要多少画匠
     */
    private static int getNeedNumber(int[] array, int limit) {
        int result = 1;
        int stepSum = 0;
        for (int cur : array) {
            if (cur > limit) {
                return Integer.MAX_VALUE;
            }
            stepSum += cur;
            if (stepSum > limit) {
                result++;
                stepSum = cur;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int[] array = {1, 1, 4, 3, 3};
        for (int i = 1; i < array.length + 1; i++) {
            System.out.println(solutionOne(array, i) + " " + solutionTwo(array, i) + " " + solutionThree(array, i));
        }
    }
}
