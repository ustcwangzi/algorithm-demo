/**
 * <p>Title: HanoiStack</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/10/2</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.stackandqueue;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * <p>汉诺塔问题</p>
 * <p>
 *     限制：不能从最左侧的塔直接移动到最右侧，反之亦然，即必须经过中间
 *     解决方案一，递归：
 *        从"左"到"中"需要进行三步：
 *          1、将1～N-1层塔从"左"移到"右"，递归完成
 *          2、将第N层塔从"左"移到"中"
 *          3、再将1～N-1层塔从"右"移到"中"，递归完成
 *        从"右"到"中"、从"中"到"左"、从"中"到"右"与上述过程类似
 *        从"左"到"右"需要进行五步：
 *          1、将1～N-1层塔从"左"移到"右"，递归完成
 *          2、将第N层塔从"左"移到"中"
 *          3、将1～N-1层塔从"右"移到"左"，递归完成
 *          4、将第N层塔从"中"移到"右"
 *          5、将1～N-1层塔从"左"移到"右"，递归完成
 *        从"右"到"左"与上述过程类似
 *      解决方案二，非递归，用栈模拟塔：
 *        因为不能直接从从"左"到"右"或从"右"到"左"，因此有效动作有四个：
 *          L->M、M->L、R->M、M->R，每个动作发生的先决条件时不能违反"小压大"的原则
 *        另外，四个动作后中L->M与M->L互为逆过程、R->M与M->R互为逆过程
 *        如果想走出最少的步数，那么任何两个相邻的动作都不能是互为逆过程的
 *        综上可得到两个结论：
 *          1、第一步一定是L->M，显而易见
 *          2、任何时刻，四个动作中只有一个动作不违反"小压大"和"相邻不可逆"原则
 *        因此，按顺序走下去即可，反正每次只有一个动作满足要求
 * </p>
 *
 * @author wangzi
 */
public class HanoiStack {
    private static final String LEFT = "left";
    private static final String MID = "mid";
    private static final String RIGHT = "right";

    public static class HanoiByRecursive {
        private final int num;

        public HanoiByRecursive(int num) {
            this.num = num;
        }

        public int solve() {
            if (num < 1) {
                return 0;
            }
            return process(num, LEFT, RIGHT);
        }

        private int process(int num, String from, String to) {
            if (num == 1) {
                if (from.equals(MID) || to.equals(MID)) {
                    System.out.println("Move 1 from " + from + " to " + to);
                    return 1;
                } else {
                    System.out.println("Move 1 from " + from + " to " + MID);
                    System.out.println("Move 1 from " + MID + " to " + to);
                    return 2;
                }
            }
            if (from.equals(MID) || to.equals(MID)) {
                String another = (from.equals(LEFT) || to.equals(LEFT)) ? RIGHT : LEFT;
                int step1 = process(num - 1, from, another);
                int step2 = 1;
                System.out.println("Move " + num + " from " + from + " to " + to);
                int step3 = process(num - 1, another, to);
                return step1 + step2 + step3;
            } else {
                int step1 = process(num - 1, from, to);
                int step2 = 1;
                System.out.println("Move " + num + " from " + from + " to " + MID);
                int step3 = process(num - 1, to, from);
                int step4 = 1;
                System.out.println("Move " + num + " from " + MID + " to " + to);
                int step5 = process(num - 1, from, to);
                return step1 + step2 + step3 + step4 + step5;
            }
        }
    }

    public static class HanoiByStack {
        private enum Action {
            // 初始化
            NoAction(0, "初始化"),
            // 从左到中
            LeftToMid(1, "from left to mid"),
            // 从中到左
            MidToLeft(2, "from mid to left"),
            // 从中到右
            MidToRight(3, "from mid to right"),
            // 从右到中
            RightToMid(4, "from right to mid");

            private int action;
            private String desc;

            Action(int action, String desc) {
                this.action = action;
                this.desc = desc;
            }
        }

        /**
         * 互逆动作
         */
        private static Map<Enum, Enum> InvalidActionMap = new HashMap<>(Action.values().length);
        private static Action PreAction = Action.NoAction;
        private final int num;
        private Stack<Integer> leftStack = new Stack<>();
        private Stack<Integer> midStack = new Stack<>();
        private Stack<Integer> rightStack = new Stack<>();

        public HanoiByStack(int num) {
            this.num = num;
            leftStack.push(Integer.MAX_VALUE);
            midStack.push(Integer.MAX_VALUE);
            rightStack.push(Integer.MAX_VALUE);
            for (int i = num; i > 0; i--) {
                leftStack.push(i);
            }
            InvalidActionMap.put(Action.LeftToMid, Action.MidToLeft);
            InvalidActionMap.put(Action.MidToLeft, Action.LeftToMid);
            InvalidActionMap.put(Action.MidToRight, Action.RightToMid);
            InvalidActionMap.put(Action.RightToMid, Action.MidToRight);
        }

        public int solve() {
            int step = 0;
            while (rightStack.size() != num + 1) {
                step += fromStackTotoStack(Action.LeftToMid, leftStack, midStack);
                step += fromStackTotoStack(Action.MidToLeft, midStack, leftStack);
                step += fromStackTotoStack(Action.MidToRight, midStack, rightStack);
                step += fromStackTotoStack(Action.RightToMid, rightStack, midStack);
            }
            return step;
        }

        private static int fromStackTotoStack(Action action, Stack<Integer> fromStack, Stack<Integer> toStack) {
            if (PreAction != InvalidActionMap.get(action) && fromStack.peek() < toStack.peek()) {
                toStack.push(fromStack.pop());
                System.out.println("Move " + toStack.peek() + " " + action.desc);
                PreAction = action;
                return 1;
            }
            return 0;
        }
    }

    public static void main(String[] args) {
        HanoiByRecursive hanoiByRecursive = new HanoiByRecursive(3);
        int step = hanoiByRecursive.solve();
        System.out.println("need " + step + " steps");
        System.out.println("=======================");

        HanoiByStack hanoiByStack = new HanoiByStack(3);
        step = hanoiByStack.solve();
        System.out.println("need " + step + " steps");
    }

}
