/**
 * <p>Title: StackSortStack</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/10/2</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.stackandqueue;

import java.util.Stack;

/**
 * <p>用一个栈实现另一个栈的排序</p>
 * <p>
 *     过程：
 *          1、从stack弹出元素记为cur
 *          2、若cur<=help的栈顶元素，将cur直接压入help
 *          3、若cur>help的栈顶元素，将help的元素逐一弹出压入stack，直到cur<=help栈顶元素，再将cur压入help
 *     一直执行以上操作，知道stack为空，最后将help的元素逐一压入stack，完成排序
 *     假设初识时，stack从栈顶到栈底依次为1、2、3，则执行过程为：
 *          1、cur为1，直接压入help，stack剩下2、3，
 *          2.1、cur为2，大于help栈顶元素1，将1压入stack，stack剩余1、3
 *          2.2、然后将2压入help
 *          3、cur为1，小于help栈顶元素2，将2压入help，stack剩余3，help中为1、2
 *          4.1、cur为3，大于help栈顶元素1，将1压入stack，stack剩余1
 *          4.2、cur为3，大于help栈顶元素2，将2压入stack，stack剩余2、1
 *          4.3、然后将3压入help
 *          5、cur为2，小于help栈顶元素3，将2压入help，stack剩余1
 *          6、cur为1，小于help栈顶元素3，将1压入help，stack为空
 * </p>
 *
 * @author wangzi
 */
public class StackSortStack {
    public static void sort(Stack<Integer> stack) {
        Stack<Integer> help = new Stack<>();
        while (!stack.isEmpty()) {
            int cur = stack.pop();
            while (!help.isEmpty() && help.peek() < cur) {
                stack.push(help.pop());
            }
            help.push(cur);
        }
        while (!help.isEmpty()) {
            stack.push(help.pop());
        }
    }

    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>();
        stack.push(3);
        stack.push(2);
        stack.push(1);

        sort(stack);
        while (!stack.isEmpty()) {
            System.out.println(stack.pop());
        }
    }
}
