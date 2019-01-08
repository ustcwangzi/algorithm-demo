/**
 * <p>Title: SubArrayMaxProduct</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/1/8</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.arrayandmatrix;

/**
 * <p>子数组的最大累乘积</p>
 * <p>
 *     给定一个double类型的数组array，其中的元素可正、可负、可0，获取子数组的最大累乘积。
 *     例如，array=[-2.5,4,0,3,0.5,8,-1]，子数组[3,0.5,8]累乘可获取最大的累乘积12，返回12。
 *     解决方案：
 *        所有的子数组都会以某个位置结束，因此求出以每个位置结尾的子数组最大的累乘积，在这么多的累乘积中最大的那个就是最终的结果。
 *        假设以array[i-1]结尾的最小累乘积为min，以array[i-1]结尾的最大累乘积为max，那么以array[i]结尾的最大累乘积只有以下三种可能：
 *        1、max*array[i]，max既然表示以array[i-1]结尾的最大累乘积，那么当然有可能以array[i]结尾的最大累乘积是max*array[i]，
 *           例如[3,4,5]在计算到5的时候；
 *        2、min*array[i]，min既然表示以array[i-1]结尾的最小累乘积，当然有可能min是负数，而如果array[i]也是负数，两个负数相乘的结果
 *           也可能是很大，例如[-2,3,-4]在计算到-4的时候；
 *        3、仅是array[i]，以array[i]结尾的最大累乘积不一定非要包含array[i]之前的数，例如[0.1,0.1,100]在计算到100的时候。
 *        这三种可能的值中最大的那个就最为以array[i]结尾的最大累乘积，最小的最为最小累乘积，然后继续计算以array[i+1]结尾的时候，直到结束。
 * </p>
 * <p>
 *     时间复杂度为O(N)，空间复杂度为O(1)
 * </p>
 *
 * @author wangzi
 */
public class SubArrayMaxProduct {
    public static double maxProduct(double[] array) {
        if (array == null || array.length == 0) {
            return 0;
        }

        double max = array[0], min = array[0], result = array[0];
        double maxEnd, minEnd;
        for (int i = 1; i < array.length; i++) {
            // 以array[i-1]结尾的最大乘积 * array[i]
            maxEnd = max * array[i];
            // 以array[i-1]结尾的最小乘积 * array[i]
            minEnd = min * array[i];
            // 以array[i]结尾的最大乘积
            max = Math.max(Math.max(maxEnd, minEnd), array[i]);
            // 以array[i]结尾的最小乘积
            min = Math.min(Math.min(maxEnd, minEnd), array[i]);
            // 结果更新
            result = Math.max(result, max);
        }
        return result;
    }

    public static void main(String[] args) {
        double[] array = {-2.5, 4, 0, 3, 0.5, 8, -1};
        System.out.println(maxProduct(array));
    }
}
