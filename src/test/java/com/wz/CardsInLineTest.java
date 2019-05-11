/**
 * <p>Title: CardsInLineTest</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/5/11</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz;

import com.wz.recursionanddp.CardsInLine;

import java.util.Arrays;

/**
 * <p>每次只能选择最左或最右，得分多者获胜，求出获胜者的得分</p>
 *
 * @author wangzi
 */
public class CardsInLineTest {
    private static int solution(int[] array) {
        // array[i...j] 先拿者得分
        int[][] first = new int[array.length][array.length];
        // array[i...j] 后拿者得分
        int[][] second = new int[array.length][array.length];

        // 动态规划
        // 先拿者有两种选择：拿array[i]剩下array[i+1...j]，或拿array[j]剩下array[i...j-1]
        // 一定是取两种情况下得分最高的，拿过后，对于下一次而言自己是后拿者
        // 对于后拿者，先拿者肯定会把得分低的情况留给他
        for (int j = 0; j < array.length; j++) {
            // 只剩一个，先拿者得到
            first[j][j] = array[j];
            for (int i = j - 1; i > -1; i--) {
                first[i][j] = Math.max(array[i] + second[i + 1][j], array[j] + second[i][j - 1]);
                second[i][j] = Math.min(first[i + 1][j], first[i][j - 1]);
            }
        }

        return Math.max(first[0][array.length - 1], second[0][array.length - 1]);
    }

    public static void main(String[] args) {
        int times = 100;
        boolean result = true;
        for (int i = 0; i < times; i++) {
            int[] array = RandomUtils.genRandomArray();
            if (solution(array) != CardsInLine.winTwo(array)) {
                result = false;
                System.out.println("Error, array:" + Arrays.toString(array));
            }
        }
        if (result) {
            System.out.println("Past");
        }
    }
}
