package com.wz;

import com.wz.recursionanddp.JumpGame;

import java.util.Arrays;

/**
 * <p>元素值代表可跳最远距离，最少几步能够跳到终点</p>
 *
 * @author wangzi
 */
public class JumpGameTest {
    private static int solution(int[] array) {
        // 当前已跳步数
        int result = 0;
        // 当前步数下能够到达的最远位置
        int cur = 0;
        // 再多跳一步能够到达的最远位置
        int next = 0;

        for (int i = 0; i < array.length; i++) {
            // 不能到达i时，再多跳一步
            if (cur < i) {
                result++;
                cur = next;
            }
            next = Math.max(next, i + array[i]);
        }
        return result;
    }

    public static void main(String[] args) {
        int times = 100;
        boolean result = true;
        for (int i = 0; i < times; i++) {
            int[] array = RandomUtils.genRandomArray();
            if (solution(array) != JumpGame.jump(array)) {
                result = false;
                System.out.println("Error, array:" + Arrays.toString(array));
            }
        }
        if (result) {
            System.out.println("Past");
        }
    }
}
