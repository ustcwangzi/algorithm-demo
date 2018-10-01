/**
 * <p>Title: GetMinStack</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/10/1</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.stackandqueue;

import java.util.Stack;

/**
 * <p>有getMin()功能的栈</p>
 * <p>
 *     使用stackMin来保存stackData每一步的最小值，时间复杂度都是O(1)、空间复杂度都是O(n)
 *     方案一压栈时只有当前值<=stackMin栈顶元素才将其同时压入stackMin
 *     方案二压栈时如果当前值>stackMin栈顶元素，则将stackMin栈顶元素再次压栈
 *     区别是方案一stackMin压栈时稍微节省空间，但是弹出操作稍费时间
 *          方案二stackMin压栈时稍费空间，但是弹出操作稍微节省时间
 * </p>
 *
 * @author wangzi
 */
public class GetMinStack {
    public static class MyStackOne {
        /**
         * 保存当前栈中元素
         */
        private Stack<Integer> stackData;
        /**
         * 保存每一步的最小值
         */
        private Stack<Integer> stackMin;

        public MyStackOne() {
            stackData = new Stack<>();
            stackMin = new Stack<>();
        }

        /**
         * 元素压栈时，如果stackMin为空，或者元素<=stackMin栈顶元素，则将元素同时压入stackMin
         * 保证stackMin栈顶元素已知是当前stackData中的最小值
         */
        public void push(int element) {
            if (stackMin.isEmpty() || element <= getMin()) {
                stackMin.push(element);
            }
            stackData.push(element);
        }

        /**
         * 出栈从stackData弹出元素value，如果value==stackMin栈顶元素，则同时弹出stackMin栈顶元素
         * 因为stackMin栈顶元素即时stackMin中的最小值也是当前stackData最小值，所以value只可能>=stackMin的栈顶元素
         */
        public int pop() {
            if (stackData.isEmpty()) {
                throw new RuntimeException("stack is empty");
            }
            int value = stackData.pop();
            if (value == getMin()) {
                stackMin.pop();
            }
            return value;
        }

        public int getMin() {
            if (stackMin.isEmpty()) {
                throw new RuntimeException("stack is empty");
            }
            return stackMin.peek();
        }
    }

    public static class MyStackTwo {
        /**
         * 保存当前栈中元素
         */
        private Stack<Integer> stackData;
        /**
         * 保存每一步的最小值
         */
        private Stack<Integer> stackMin;

        public MyStackTwo() {
            stackData = new Stack<>();
            stackMin = new Stack<>();
        }

        /**
         * 元素压栈时，如果stackMin为空，或者元素<=stackMin栈顶元素，则将元素同时压入stackMin
         * 否则将stackMin栈顶元素再次压栈
         */
        public void push(int element) {
            if (stackMin.isEmpty() || element <= getMin()) {
                stackMin.push(element);
            } else {
                int minElement = stackMin.peek();
                stackMin.push(minElement);
            }
            stackData.push(element);
        }

        /**
         * 出栈同时从stackData和stackMin弹出元素
         */
        public int pop() {
            if (stackData.isEmpty()) {
                throw new RuntimeException("stack is empty");
            }
            stackMin.pop();
            return stackData.pop();
        }

        public int getMin() {
            if (stackMin.isEmpty()) {
                throw new RuntimeException("stack is empty");
            }
            return stackMin.peek();
        }
    }

    public static void main(String[] args) {
        MyStackOne stackOne = new MyStackOne();
        stackOne.push(3);
        System.out.println(stackOne.getMin());
        stackOne.push(4);
        System.out.println(stackOne.getMin());
        stackOne.push(1);
        System.out.println(stackOne.getMin());
        System.out.println(stackOne.pop());
        System.out.println(stackOne.getMin());

        System.out.println("==========");

        MyStackTwo stackTwo = new MyStackTwo();
        stackTwo.push(3);
        System.out.println(stackTwo.getMin());
        stackTwo.push(4);
        System.out.println(stackTwo.getMin());
        stackTwo.push(1);
        System.out.println(stackTwo.getMin());
        System.out.println(stackTwo.pop());
        System.out.println(stackTwo.getMin());
    }

}
