/**
 * <p>Title: ReverseStackUsingRecursive</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/10/1</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.stackandqueue;

import java.util.Stack;

/**
 * <p>使用递归将栈逆置</p>
 * <p>
 *     假设当前stack从栈顶到栈底依次为3、2、1
 *     getAndRemoveLastElement()调用过程为：
 *     ----------------------------------------------------------
 *          result=3   ->  result=2   ->   result=1 ->
 *          将3重新压栈 <- 将2重新压栈 <- 栈已空，不压入1，返回1 <-
 *     ----------------------------------------------------------
 *     一次递归调用后，stack从栈顶到栈底依次为3、2，已移除1
 *     将getAndRemoveLastElement()简记为get()，reverse()调用过程为：
 *     -----------------------------------------------------------------------------------
 *          lastElement=get(s)=1 -> lastElement=get(s)=2 -> lastElement=get(s)=3 ->
 *              将1重新压栈       <-    将2重新压栈        <- 将3重新压栈 <- 栈已空，返回 <-
 *     -----------------------------------------------------------------------------------
 *     递归调用完成后，stack从栈顶到栈底依次为3，2，1，完成逆置
 * </p>
 *
 * @author wangzi
 */
public class ReverseStackUsingRecursive {
    /**
     * 逆置栈
     */
    public static void reverse(Stack<Integer> stack) {
        if (stack.isEmpty()) {
            return;
        }
        int lastElement = getAndRemoveLastElement(stack);
        reverse(stack);
        stack.push(lastElement);
    }

    /**
     * 获取并移除栈底元素
     */
    public static int getAndRemoveLastElement(Stack<Integer> stack) {
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
        reverse(stack);
        while (!stack.isEmpty()) {
            System.out.println(stack.pop());
        }
    }
}
