package com.wz;

import java.util.Stack;

public class ReverseStackUsingRecursiveTest {

    private static void solution(Stack<Integer> stack) {
        if (stack.isEmpty()) {
            return;
        }
        int lastElement = getAndRemoveLastElement(stack);
        solution(stack);
        stack.push(lastElement);
    }

    private static int getAndRemoveLastElement(Stack<Integer> stack) {
        int result = stack.pop();
        if (stack.isEmpty()) {
            return result;
        }
        int lastElement = getAndRemoveLastElement(stack);
        stack.push(result);
        return lastElement;
    }
}
