/**
 * <p>Title: PostOffice</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/2/22</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.others;

/**
 * <p>邮局选址问题</p>
 * <p>
 *     一条直线上有居民点，邮局只能建在居民点上。给定一个有序整型数组array，每个值表示居民点的一维坐标，再给定一个正数number，表示邮局数量。
 *     选择number个居民点建立number个邮局，使得所有的居民点到邮局的总距离最短，返回最短的总距离。
 *     例如：
 *        array=[1,2,3,4,5,1000]，number=2
 *        第一个邮局建在3位置，第二个邮局建在1000位置，此时1位置到邮局距离2，2位置到邮局距离1，3位置到邮局距离0，4位置到邮局距离1，
 *        5位置到邮局距离2，1000位置到邮局距离0，这种方案下总距离为6，其他方案的总距离都不比这个更小，所以返回6。
 *     解决方案：
 *        动态规划。
 *        首先解决一个问题，如果在array[0...j]上只能建立一个邮局，最短总距离是多少？很显然，如果居民点有奇数个，
 *        邮局建在最中间的那个居民点可以使总距离最短，如果居民点有偶数个，中点有两个，邮局建在哪个都可以让总距离最短。
 *
 *        生成规模为N×M的矩阵w，w[i][j]表示如果在array[i...j]上只能建立一个邮局，最短的总距离。w[i][j]的值可以通过如下计算得到：
 *        w[i][j] = w[i][j-1] + array[j] - array[(i + j) / 2]
 *        1、如果array[i...j-1]有奇数个点，那么中间是array[(i+j-1)/2]，加上array[j]后，array[i...j]有偶数个点，
 *           第一个中点是array[(i+j)/2]，此时array[(i+j-1)/2]和array[(i+j)/2]其实是同一个位置。
 *           比如array[i...j-1]=[4,15,26]，中点是15，array[i...j]=[4,15,26,37]，第一个中点是15，
 *           所以此时w[i][j]比w[i][j-1]多出来的距离就是array[j]到array[(i+j)/2]的距离。
 *        2、如果array[i...j-1]有偶数个点，中间点有两个，无论选哪个，w[i][j-1]的值都是一样的，加上array[j]后，array[i...j]有奇数个点，
 *           中间点是array[(i+j)/2]，此时array[i...j-1]的第二个中点和array[(i+j)/2]其实是同一个位置。
 *           比如array[i...j-1]=[4,15,26,37]，第二个中点是26，array[i...j]=[4,15,26,37,53]，中点是26，
 *           所以此时w[i][j]比w[i][j-1]多出来的距离还是array[j]到array[(i+j)/2]的距离。
 *
 *        有了w矩阵后，接下来进行动态规划过程。假设dp[i][j]表示如果在array[0...j]上建立i+1个邮局的最短总距离。
 *        所以dp[0][j]的值表示如果在array[0...j]上建立一个邮局最短的总距离，很明显，就是w[0][j]。
 *        当可以建立不止一个邮局时，情况如下：
 *        1、前i个邮局负责array[0]，最后一个邮局负责array[1...j]，总距离为dp[i-1][0] + w[1][j]
 *        2、前i个邮局负责array[0...1]，最后一个邮局负责array[2...j]，总距离为dp[i-1][1] + w[2][j]
 *        ...
 *        3、前i个邮局负责array[0...k]，最后一个邮局负责array[k+1...j]，总距离为dp[i-1][k] + w[k+1][j]
 *        所有方案中，选一个距离最短的即可，所以dp[i][j] = min{ dp[i-1][k] + w[k+1][j] (0<=k<N) }
 * </p>
 * <p>
 *     时间复杂度为O(number*N^2)
 * </p>
 *
 * @author wangzi
 */
public class PostOffice {

    public static int minDistance(int[] array, int number) {
        if (array == null || number < 1 || array.length <= number) {
            return 0;
        }

        // array[i...j]上只建立一个邮局，最短的总距离
        int[][] w = new int[array.length + 1][array.length + 1];
        for (int i = 0; i < array.length; i++) {
            for (int j = i + 1; j < array.length; j++) {
                w[i][j] = w[i][j - 1] + array[j] - array[(i + j) / 2];
            }
        }

        int[][] dp = new int[number][array.length];
        // array[0...j]上建立一个邮局最短的总距离
        for (int j = 0; j < array.length; j++) {
            dp[0][j] = w[0][j];
        }
        // array[0...j]上建立i+1个邮局的最短总距离
        for (int i = 1; i < number; i++) {
            for (int j = i + 1; j < array.length; j++) {
                dp[i][j] = Integer.MAX_VALUE;
                // 0～j逐个尝试最优解
                for (int k = 0; k <= j; k++) {
                    // 前i个邮局负责array[0...k]，最后一个邮局负责array[k+1...j]
                    dp[i][j] = Math.min(dp[i][j], dp[i - 1][k] + w[k + 1][j]);
                }
            }
        }
        return dp[number - 1][array.length - 1];
    }

    public static void main(String[] args) {
        int[] array = {-2, -1, 0, 1, 2, 1000};
        for (int i = 1; i < array.length + 1; i++) {
            System.out.println(minDistance(array, i));
        }
    }
}
