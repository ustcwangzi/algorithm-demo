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
 *
 * @author wangzi
 */
public class Artist {

    /**
     * 动态规划的空间压缩
     */
    public static int solutionOne(int[] array, int number) {
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
     * 四边形不等式
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

        int[] cands = new int[array.length];
        for (int i = 1; i < number; i++) {
            for (int j = map.length - 1; j > i - 1; j--) {
                int minPar = cands[j];
                int maxPar = j == map.length - 1 ? j : cands[j + 1];
                int min = Integer.MAX_VALUE;
                for (int k = minPar; k < maxPar + 1; k++) {
                    int cur = Math.max(map[k], sumArray[j] - sumArray[k]);
                    if (cur <= min) {
                        min = cur;
                        cands[j] = k;
                    }
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
}
