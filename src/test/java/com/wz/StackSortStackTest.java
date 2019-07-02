package com.wz;

import java.util.Stack;

/**
 * <p>用一个栈实现另一个栈的排序</p>
 *
 * @author wangzi
 */
public class StackSortStackTest {
    /**
     * 要想stack从上到下为正序，那么help中必须从上到下为逆序
     * 从stack弹出元素cur，若cur小于help的栈顶元素，则从help弹出元素压入stack，直到help为空或cur大于等于help的栈顶元素
     * 将cur压入help，直到stack为空，此时help中从上到下为逆序，直接将help的元素弹出压入stack即可
     */
    private static void solution(Stack<Integer> stack) {
        Stack<Integer> help = new Stack<>();
        while (!stack.isEmpty()) {
            int cur = stack.pop();
            // 要求新加入的元素不能小于栈顶元素
            while (!help.isEmpty() && help.peek() > cur) {
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
        stack.push(1);
        stack.push(2);
        stack.push(3);

        solution(stack);
        while (!stack.isEmpty()) {
            System.out.print(stack.pop() + " ");
        }
    }
}
