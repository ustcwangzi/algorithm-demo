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
 * <p>
 *     给定一个字符串str，str表示一个公式，公式里可能有整数、加减乘除符号和左右括号，求出公式的计算结果。
 *     说明：
 *     1、给定的字符串一定是正确的公式，即不需要对str做公式有效性检查
 *     2、如果是负数，就需要用括号括起来，比如"4*(-3)"，但如果负数作为公式的开头或括号部分的开头，则可以没有括号，比如"-4*3"、"(-3*4)"
 *     3、不用考虑计算过程中发生溢出的情况。
 *     解决方案：
 *        1、从左到右遍历str，开始遍历或遇到'('时，就进行递归，当遍历完str或遇到')'时，递归过程结束。
 *           比如"3*(4+5)+7"，一开始遍历就进入递归过程value(str, 0)；在递归中继续遍历str，当遇到'('时，进入递归value(str, 3)；
 *           在递归中继续遍历str，当遇到')'时，递归value(str, 3)结束，并向上层的递归value(str, 0)返回两个结果，
 *           一个是value(str, 3)遍历过的公式字符子串的结果，即"4+5"==9，另一个是value(str, 3)遍历到的位置，即字符')'的位置6；
 *           value(str, 0)收到结果后，既可知道value(str, 3)的处理结果，又可知道自己下一步该从位置6的下一个位置开始继续遍历。
 *        2、在递归过程中遇到'('就交给下一层的递归过程处理，自己只用接收'('和')'直接的子串计算结果，所以对于所有的递归过程来说，
 *           可以看作计算的公式都是不含有'('和')'的。比如对于递归value(str, 0)，实际上计算的公式是"3*9+7"，"(4+5)"交给value(str, 3)，
 *           拿到结果9后，再从'+'继续即可。
 * </p>
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
