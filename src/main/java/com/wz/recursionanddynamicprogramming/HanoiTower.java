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
