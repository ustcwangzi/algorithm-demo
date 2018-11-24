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
 * <p>
 *     给定数组array，array[i]==k代表可以从位置i向右跳1～k个距离。比如array[2]==3代表位置2可以跳到位置3、4或5。
 *     求出从位置0出发，最少跳几次能够到达array最后的位置。
 *     例如，array=[3,2,3,1,1,4]，从位置0跳到位置2，然后从位置2直接跳到位置5，即总共跳2次。
 *     解决过程：
 *     1、jump代表目前跳了多少步，cur代表只跳jump步最远能够达到的位置，next代表如果再多跳一步最远能够达到的位置
 *        初始时，jump=0，cur=0，next=0
 *     2、从左到右遍历array，假设当前遍历到位置i
 *     2.1、如果cur>=i，说明跳jump步可以达到位置i，此时什么也不做
 *     2.2、如果cur<i，说明只跳jump步不能达到位置i，需要多跳一步，令jump++，cur=next，表示多跳一步，
 *          cur更新为跳jump+1步能够达到的位置，即next
 *     2.3、将next更新为max{next, i+array[i]}，代表下一次多跳一步到达的最远位置
 *     3、最终返回jump即可。
 *     以array=[3,2,3,1,1,4]为例，说明整个过程：
 *        i: 0  1  2  3  4  5
 *     jump: 0  1  1  1  2  2
 *      cur: 0  3  3  3  5  5
 *     next: 3  3  5  5  5  9
 * </p>
 * <p>
 *     时间复杂度为O(N)，空间复杂度为O(1)
 * </p>
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
