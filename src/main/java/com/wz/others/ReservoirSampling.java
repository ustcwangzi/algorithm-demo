/**
 * <p>Title: ReservoirSampling</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/1/19</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.others;

import java.util.Arrays;

/**
 * <p>蓄水池取样算法</p>
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
                result[rand(k) - 1] = i;
            }
        }
        return result;
    }

    private static int rand(int max) {
        return (int) (Math.random() * max) + 1;
    }

    public static void main(String[] args) {
        int[] result = getKRandNumbers(10, 10000);
        System.out.println(Arrays.toString(result));
    }
}
