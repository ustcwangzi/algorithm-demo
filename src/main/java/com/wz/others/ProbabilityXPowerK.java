/**
 * <p>Title: ProbabilityXPowerK</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/1/22</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.others;

/**
 * <p>调整[0,x)区间上的数出现的概率</p>
 *
 * @author wangzi
 */
public class ProbabilityXPowerK {

    public static double randXPowerK(int k) {
        if (k < 1) {
            return 0;
        }

        double result = -1;
        // k次调用random()
        for (int i = 0; i < k; i++) {
            result = Math.max(result, Math.random());
        }
        return result;
    }

    public static void main(String[] args) {
        double range = 0.5;
        int times = 5000000;
        int count = 0;
        for (int i = 0; i < times; i++) {
            if (randXPowerK(2) < range) {
                count++;
            }
        }
        double p = (double) count / (double) times;
        System.out.println("range [0," + range + "), probability: " + p);
    }
}
