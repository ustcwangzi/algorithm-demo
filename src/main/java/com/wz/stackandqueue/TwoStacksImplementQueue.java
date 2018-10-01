/**
 * <p>Title: TwoStacksImplementQueue</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/10/1</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.stackandqueue;

import java.util.Stack;

/**
 * <p>两个栈实现队列</p>
 * <p>
 *     栈是先进后出，而队列是先进先出，压栈时数据压入stackPush，
 *     把stackPush中的数据再压入stackPop中，即可实现先进先出
 *     需要遵循两点：
 *     1：stackPush往stackPop中压入元素，必须一次性把stackPush的元素全部压入
 *     2：stackPop不为空时，stackPush绝不能向stackPush压入元素
 * </p>
 *
 * @author wangzi
 */
public class TwoStacksImplementQueue {
    /**
     * 用于压栈
     */
    private Stack<Integer> stackPush;
    /**
     * 用于出栈
     */
    private Stack<Integer> stackPop;

    public TwoStacksImplementQueue() {
        this.stackPush = new Stack<>();
        this.stackPop = new Stack<>();
    }

    public void add(int element) {
        stackPush.push(element);
    }

    public int poll() {
        if (stackPop.isEmpty() && stackPush.isEmpty()) {
            throw new RuntimeException("queue is empty");
        }
        if (stackPop.isEmpty()) {
            while (!stackPush.isEmpty()) {
                stackPop.push(stackPush.pop());
            }
        }
        return stackPop.pop();
    }

    public int peek() {
        if (stackPop.isEmpty() && stackPush.isEmpty()) {
            throw new RuntimeException("queue is empty");
        }
        if (stackPop.isEmpty()) {
            while (!stackPush.isEmpty()) {
                stackPop.push(stackPush.pop());
            }
        }
        return stackPop.peek();
    }

    public static void main(String[] args) {
        TwoStacksImplementQueue queue = new TwoStacksImplementQueue();
        queue.add(1);
        queue.add(2);
        queue.add(3);
        System.out.println(queue.peek());
        System.out.println(queue.poll());
        System.out.println(queue.peek());
        System.out.println(queue.poll());
        System.out.println(queue.peek());
        System.out.println(queue.poll());
    }
}
