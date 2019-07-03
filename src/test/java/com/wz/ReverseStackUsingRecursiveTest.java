package com.wz;

import java.util.Stack;

/**
 * <p>使用递归将栈逆置</p>
 *
 * @author wangzi
 */
public class ReverseStackUsingRecursiveTest {

    /**
     * 将栈逆置
     * 以3、2、1为例说明该过程：
     * lastElement=1、lastElement=2、lastElement=3，栈空返回，将3、2、1压入栈中
     */
    private static void solution(Stack<Integer> stack) {
        if (stack.isEmpty()) {
            return;
        }
        int lastElement = getAndRemoveLastElement(stack);
        solution(stack);
        stack.push(lastElement);
    }

    /**
     * 获取并移除栈底元素
     * 以3、2、1为例说明该过程：
     * result=3、result=2、result=1，栈空返回1，lastElement=1，将2、3压入栈中，返回1
     */
    private static int getAndRemoveLastElement(Stack<Integer> stack) {
        int result = stack.pop();
        if (stack.isEmpty()) {
            return result;
        }
        int lastElement = getAndRemoveLastElement(stack);
        stack.push(result);
        return lastElement;
    }

    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        solution(stack);
        while (!stack.isEmpty()) {
            System.out.println(stack.pop());
        }
    }
}
