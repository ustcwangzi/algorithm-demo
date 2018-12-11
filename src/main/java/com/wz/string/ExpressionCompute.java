/**
 * <p>Title: ParenthesesMatch</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/12/11</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.string;

import java.util.Deque;
import java.util.LinkedList;

/**
 * <p>公式字符串求值</p>
 *
 * @author wangzi
 */
public class ExpressionCompute {

    public static int compute(String str) {
        return value(str.toCharArray(), 0)[0];
    }

    /**
     * 从位置i开始处理，
     * 每次处理后得到两个结果：本次递归的结果、递归到什么位置
     */
    private static int[] value(char[] array, int i) {
        Deque<String> queue = new LinkedList<>();
        int pre = 0;
        int[] result;
        while (i < array.length && array[i] != ')') {
            if (array[i] >= '0' && array[i] <= '9') {
                pre = pre * 10 + array[i++] - '0';
            } else if (array[i] != '(') {
                jointExpression(queue, pre);
                queue.addLast(String.valueOf(array[i++]));
                pre = 0;
            } else {
                // 遇到'('时重复递归
                result = value(array, i + 1);
                // result[0]表示这个递归过程计算的结果
                pre = result[0];
                // result[1]表示这个递归过程遍历到什么位置
                i = result[1] + 1;
            }
        }
        jointExpression(queue, pre);
        return new int[]{calAddOrSub(queue), i};
    }

    /**
     * 表达式拼接，queue中保存的是可计算结果的表达式的每一项
     */
    private static void jointExpression(Deque<String> queue, int num) {
        if (!queue.isEmpty()) {
            String top = queue.pollLast();
            if (top.equals("+") || top.equals("-")) {
                queue.addLast(top);
            } else {
                int cur = Integer.valueOf(queue.pollLast());
                num = top.equals("*") ? (cur * num) : (cur / num);
            }
        }
        queue.addLast(String.valueOf(num));
    }

    /**
     * 计算加减法
     */
    private static int calAddOrSub(Deque<String> queue) {
        int result = 0;
        boolean isAdd = true;
        // 当前字符串
        String cur;
        // 当前字符串表示的数字
        int num;
        while (!queue.isEmpty()) {
            cur = queue.pollFirst();
            if (cur.equals("+")) {
                isAdd = true;
            } else if (cur.equals("-")) {
                isAdd = false;
            } else {
                num = Integer.valueOf(cur);
                result += isAdd ? num : (-num);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        String exp = "3*(4+5)+7";
        System.out.println(compute(exp));

        exp = "10-5*3";
        System.out.println(compute(exp));

        exp = "-3*4";
        System.out.println(compute(exp));
    }
}
