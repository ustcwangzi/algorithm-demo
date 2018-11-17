/**
 * <p>Title: HanoiTower</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/11/17</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.recursionanddynamicprogramming;

/**
 * <p>汉诺塔问题</p>
 * <p>
 *     问题一：
 *        经典的汉诺塔问题，即将n个圆盘从左柱移到右柱，可以使用中间的柱子，打印出最优路径
 *     方案：
 *        假设有from、mid和to三个柱子，将from上的圆盘1～i完全移动到to，最优过程为：
 *        1、将圆盘1～i-1从from移动到mid
 *        2、将圆盘i从from移动到to
 *        3、将圆盘1～i-1从mid移动到to
 *     问题二：
 *        给定一个数组，其中只含有1、2和3，分别代表当前圆盘处在左柱、中柱和右柱，array[i]的值代表第i+1个圆盘的位置，
 *        如果array代表的状态是汉诺塔问题最优路径移动过程中出现的状态，则返回这种状态是最优路径中的第几步，否则返回-1。
 *     方案：
 *        假设将from上的圆盘1～i完全移动到to最少步骤数为S(i)，根据问题一的解答过程可知：S(i) = S(i-1) + 1 + S(i-1)
 *        S(1) = 1，因此 S(i)+1 = 2*(S(i-1)+1)，根据等比数列求和公式得到 S(i)+1 = 2^i，即 S(i) = 2^i - 1
 *        对于数组array来说，array[N-1]表示最大圆盘N在哪个柱子上，情况有以下三种：
 *        1、圆盘N在左柱上，说明步骤一或者已完成，或者还未完成，需要考查圆盘1～N-1的情况
 *        2、圆盘N在右柱上，说明步骤一已完成，走了2^(N-1) -1 步，步骤二也已完成，走了1步，共走了2^(N-1)步，
 *           剩下的步骤需要考查圆盘1～N-1的情况
 *        3、圆盘N在中柱上，最优步骤不可能出现这种情况，直接返回-1。
 *        所以整个过程可以总结为：对于圆盘1～i来说，如果目标为从from到to，那么情况有三种：
 *        1、圆盘i在from上，需要继续考察1～i-1的情况，圆盘1～i-1的目标为从from到mid
 *        2、圆盘i在to上，说明走完了2^(i-1)步，剩下的步骤需要考察1～i-1的情况，圆盘1～i-1的目标为从mid到to
 *        3、圆盘i在mid上，直接返回-1。
 *        整个过程见stepOne()，stepTwo()为该过程的非递归形式。
 * </p>
 * <p>
 *     问题二，stepOne()时间复杂度为O(N)、空间复杂度为O(N)，stepTwo()时间复杂度为O(N)、空间复杂度为O(1)
 * </p>
 *
 * @author wangzi
 */
public class HanoiTower {
    public static void hanoi(int n) {
        if (n > 0) {
            hanoi(n, "left", "mid", "right");
        }
    }

    private static void hanoi(int n, String from, String mid, String to) {
        if (n == 1) {
            System.out.println("move from " + from + " to " + to);
        } else {
            hanoi(n - 1, from, to, mid);
            hanoi(1, from, mid, to);
            hanoi(n - 1, mid, from, to);
        }
    }

    public static int stepOne(int[] array) {
        if (array == null || array.length == 0) {
            return -1;
        }
        return stepOne(array, array.length - 1, 1, 2, 3);
    }

    private static int stepOne(int[] array, int i, int from, int mid, int to) {
        if (i == -1) {
            return 0;
        }
        if (array[i] != from && array[i] != to) {
            return -1;
        }
        if (array[i] == from) {
            return stepOne(array, i - 1, from, to, mid);
        } else {
            int result = stepOne(array, i - 1, mid, from, to);
            if (result == -1) {
                return -1;
            }
            return 1 << i + result;
        }
    }

    public static int stepTwo(int[] array) {
        if (array == null || array.length == 0) {
            return -1;
        }
        int from = 1, mid = 2, to = 3;
        int i = array.length - 1;
        int result = 0, tmp = 0;
        while (i >= 0) {
            if (array[i] != from && array[i] != to) {
                return -1;
            }
            if (array[i] == to) {
                result += 1 << i;
                tmp = from;
                from = mid;
            } else {
                tmp = to;
                to = mid;
            }
            mid = tmp;
            i--;
        }
        return result;
    }

    public static void main(String[] args) {
        hanoi(4);

        int[] array = {3, 3, 2, 1};
        System.out.println(stepOne(array));
        System.out.println(stepTwo(array));
    }
}
