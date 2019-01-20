/**
 * <p>Title: ReservoirSampling</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/1/19</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.others;

import java.util.Arrays;
import java.util.Random;

/**
 * <p>蓄水池取样算法</p>
 * <p>
 *     有一个机器按自然数序列的方式吐出球(1号球、2号球、3号球...)，现有一个袋子，袋子中最多只能装下K个球，没有其他更多的空间。
 *     设计一种选球方式，使得当机器吐出第N(N>K)号球时，袋子中的球数是K，同时保证从1号球到N号球，每个被选进袋子的概率都是K/N。
 *     解决方案：
 *        1、处理1～k号球时，直接放进袋子
 *        2、处理第i号球时(i>k)，以k/i的概率决定是否将第i号球放进袋子，如果决定不放入袋子，则直接扔掉第i号球；
 *           否则，从袋中的k个球中随机扔掉一个，然后把第i号球放进袋子
 *        3、处理第i+1号球时重复步骤一或步骤二。
 *        证明从1号球到n号球，每个被选进袋子的概率都是k/n：
 *        对于第i号球(1<=i<=k)，在选第k+1号球之前，第i号球留在袋中的概率是1；
 *        在选第k+1号球时，i号球被k+1号球替换的概率 = k+1号球被选中的概率 * i号球被选中替换的概率，即(k/(k+1)) * (1/k) = 1/(k+1)，
 *        则第i号球被保留的概率为1 - (1/(k+1)) = k/(k+1)；这也是从1号球到k+1号球的过程中，i号球留下来的概率；
 *        在选第k+2号球时，i号球被k+2号球替换的概率 = k+2号球被选中的概率 * i号球被选中替换的概率，即(k/(k+2)) * (1/k) = 1/(k+2)，
 *        则第i号球被保留的概率为1 - (1/(k+2)) = (k+1)/(k+2)；那么从1号球到k+2号球的过程中，i号球留下的概率为：
 *        k/(k+1) * (k+1)/(k+2)；
 *        以此类推，在选第N号球时，从1号球到N号球的全部过程中，i号球最终留下来的概率为：
 *        k/(k+1) * (k+1)/(k+2) * (k+2)/(k+3) *...* (N-1)/(N) = k/N。
 *        对于j号球(k<j<=N)，在选第j号球时，第j号球被选中的概率为k/j；
 *        在选第j+1号球时，j号球被j+1号球替换的概率 = j+1号球被选中的概率 * j号球被选中替换的概率，即(k/(j+1)) * (1/k) = 1/(j+1)，
 *        则第j号球被保留的概率为1 - (1/(j+1)) = j/(j+1)；那么从j号球到j+1号球的过程中，j号球留下的概率为：
 *        (k/j) * (j/(j+1))；
 *        在选第j+2号球时，从j号球到j+2号球的过程中，j号球留下来的概率为：
 *        (k/j) * (j/(j+1)) * (j+1)/(j+2) * (N-1)/(N) = k/N。
 *        因此，对于每个球，留下来的概率都是k/N。
 * </p>
 *
 * @author wangzi
 */
public class ReservoirSampling {

    public static int[] getKRandNumbers(int k, int max) {
        if (max < 1 || k < 1) {
            return null;
        }
        int[] result = new int[Math.min(k, max)];
        // 前k个数之间放入袋子
        for (int i = 0; i < result.length; i++) {
            result[i] = i + 1;
        }
        for (int i = k + 1; i < max + 1; i++) {
            // 决定i进不进袋子
            if (rand(i) <= k) {
                // i随机替换掉袋子中的一个
                result[rand(k)- 1] = i;
            }
        }
        return result;
    }

    /**
     * 随机产生1～max
     */
    private static int rand(int max) {
        return new Random().nextInt(max) + 1;
    }

    public static void main(String[] args) {
        int[] result = getKRandNumbers(10, 10000);
        System.out.println(Arrays.toString(result));
    }
}
