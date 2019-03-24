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
 * <p>
 *     Math.random()等概率随机返回[0,1)区间上的数，那么我们知道在[0,x)区间上的数出现的概率为x(0<x<=1)。
 *     给定一个大于0的整数k，请使用Math.random()实现一个函数依然返回[0,1)区间上的数，但是在[0,x)区间上的数出现的概率为x^k(0<x<=1)。
 *     解决方案：
 *        要实现[0,x)区间上的数出现的概率为x^2，只需要调用两次Math.random()，返回最大的那个数即可，因为如果想要返回[0,x)区间上的数，
 *        两次调用Math.random()的返回值都必须落在[0,x)区间上，否则最后的返回值会大于x，因此概率为x^2。
 *        同理，[0,x)区间上的数出现的概率为x^k，只需要调用k次Math.random()，返回最大的那个数即可。如果想要返回在[0, x)区间上的数，
 *        k次调用Math.random()的返回值都必须落在[0,x)区间上，否则会返回大于x的数，所以概率为x^k。
 * </p>
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
