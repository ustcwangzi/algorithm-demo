/**
 * <p>Title: ReservoirSamplingTest</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/3/30</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz;

import java.util.Arrays;
import java.util.Random;

/**
 * <p>蓄水池取样，保证每个元素加入的概率相等</p>
 *
 * @author wangzi
 */
public class ReservoirSamplingTest {
    private static int[] solution(int k, int max) {
        if (max < 1 || k < 1) {
            return null;
        }
        int[] result = new int[Math.min(k, max)];
        // 前k个直接放入
        for (int i = 0; i < result.length; i++) {
            result[i] = i + 1;
        }
        for (int i = k + 1; i < max + 1; i++) {
            // 决定i是否加入
            if (rand(i) <= k) {
                // i加入时，替换掉已有的哪个位置
                result[rand(k) - 1] = i;
            }
        }
        return result;
    }

    private static int rand(int max) {
        // 随机产生[1...max]
        return new Random().nextInt(max) + 1;
    }

    public static void main(String[] args) {
        int times = 100;
        for (int i = 0; i < times; i++) {
            System.out.println(Arrays.toString(solution(i + 1, RandomUtils.genRandomNumber())));
        }
    }
}
