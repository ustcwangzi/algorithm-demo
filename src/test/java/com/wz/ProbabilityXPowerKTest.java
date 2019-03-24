/**
 * <p>Title: ProbabilityXPowerKTest</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/3/24</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz;

/**
 * <p>调整[0,x)区间上的数出现的概率</p>
 *
 * @author wangzi
 */
public class ProbabilityXPowerKTest {

    private static double solution(int k) {
        double result = -1;
        // k次调用random()
        // 如果想要返回在[0,x)区间上的数，k次调用的返回值都必须落在[0,x)区间上，否则会返回大于x的数，所以概率为x^k
        for (int i = 0; i < k; i++) {
            result = Math.max(result, Math.random());
        }
        return result;
    }

    public static void main(String[] args) {
        double range = 0.5;
        int times = 5000000;
        int k = 5;
        for (int j = 1; j <= k; j++) {
            int count = 0;
            for (int i = 0; i < times; i++) {
                if (solution(j) < range) {
                    count++;
                }
            }
            double p = (double) count / (double) times;
            System.out.println("range [0," + range + "), k: " + k + ", probability: " + p);
        }
    }
}
