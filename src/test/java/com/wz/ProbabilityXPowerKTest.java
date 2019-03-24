/**
 * <p>Title: ProbabilityXPowerKTest</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/3/24</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz;

/**
 * <p></p>
 *
 * @author wangzi
 */
public class ProbabilityXPowerKTest {

    private static double solution(int k) {
        double result = -1;
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
