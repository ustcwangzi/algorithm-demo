/**
 * <p>Title: JumpGame</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/11/24</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.recursionanddynamicprogramming;

/**
 * <p>跳跃游戏</p>
 *
 * @author wangzi
 */
public class JumpGame {
    public static int jump(int[] array) {
        if (array == null || array.length == 0) {
            return 0;
        }
        // 目前跳了多少步
        int jump = 0;
        // 只跳jump步，最远达到的位置
        int cur = 0;
        // 再多跳一步，最远达到的位置
        int next = 0;

        for (int i = 0; i < array.length; i++) {
            if (cur < i) {
                jump++;
                cur = next;
            }
            // 下一次多跳一步达到的最远位置
            next = Math.max(next, i + array[i]);
        }
        return jump;
    }

    public static void main(String[] args) {
        int[] array = {3, 2, 3, 1, 1, 4};
        System.out.println(jump(array));
    }
}
