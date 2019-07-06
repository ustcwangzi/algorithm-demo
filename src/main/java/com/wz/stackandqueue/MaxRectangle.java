/**
 * <p>Title: MaxRectangle</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/10/4</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.stackandqueue;

import java.util.Stack;

/**
 * <p>最大子矩阵</p>
 * <p>
 *     给定一个整形矩阵map，其中的值只有0和1，求其中全是1的的矩形中最大的矩形大小
 *     解决过程：
 *        第一步、以每一行做切割，统计以当前行为底时，每个位置往上的1的数量，存在height[]中
 *              eg. map = {{1, 0, 1, 1}, {1, 1, 1, 1}, {1, 1, 1, 0}}
 *              以第一行切割后，height={1,0,1,1}
 *              以第二行切割后，height={2,1,2,2}
 *              以第三行切割后，height={3,2,3,0}
 *         第二步、对于每次切割，都利用height[]计算出以该行为底时，最大的矩形面积，最后可得最大的那个
 *      对于height[]可理解为一个直方图，步骤二的实质是在一个大的直方图中求最大的矩形面积，
 *      每个值为柱子高度，如果能够求出每个柱子扩展出去的最大矩形，那么其中最大的那个就是我们想找的，
 *      如{3,2,3,0}，第一个柱子向左向右均无法扩展，最大面积为3；第二个柱子可向左向右都扩展1，最大面积为2*3=6
 *      每个柱子最大能扩多大，实质就是找到这个柱子左边和右边刚比它小的的位子位置在哪里，可用栈来实现
 *      栈中从栈顶到栈底所代表的值是依次递减的，且无重复值
 *      以下过程将利用height[]快速实现求最大的矩形面积：
 *         1、生成一个栈stack，从左到右遍历height[]，如果stack为空或当前值大于栈顶元素代表的值，则入栈，
 *            即height[i]>height[stack.peek()]时i入栈
 *         2、如果当前值小于等于栈顶元素代表的值，则不断弹出栈顶元素，直到stack为空或当前值大于栈顶元素代表的值
 *            即height[i]<=height[stack.peek()]时，stack.pop()，最后i入栈
 *         2.1、假设当前弹出j，弹出后新栈顶为k，此时计算j向左向右最远扩到哪里
 *         2.2、向右，若height[j]>height[i]，则向右最远扩到i-1，因为j之所以弹出是遇到了第一个比height[j]小的
 *              若height[j]==height[i]，说明j和i扩出的最大矩形是同一个，不再计算j能扩出的最大面积，等i弹出再计算
 *         2.3、向左，最远能扩到k+1处，height[k+1...j-1]中不存在<=height[k]的值，否则k之前就已经从栈中弹出了
 *         2.4、综上所述，j能扩出来的最大矩形为(i-k-1)*height[j]
 *      以{3,4,5,4,3,6}为例，具体说明以上过程：
 *         1、height[0]=3，栈空，直接入栈，stack={0}
 *         2、height[1]=4，4>height[0]，入栈，stack={0,1}
 *         3、height[2]=5，5>height[1]，入栈，stack={0,1,2}
 *         4.1、height[3]=4，4<height[2]，2弹出(j=2)，栈顶为1(k=1)，2面积为(3-1-1)*5=5，stack={0,1}
 *         4.2、height[3]=4，4<height[1]，1弹出(j=1)，栈顶为0(k=0)，1面积为(3-0-1)*4=8，stack={0}
 *         4.3、height[3]=4，4>height[0]，3入栈，stack={0,3}
 *         5.1、height[4]=3，3<height[3]，3弹出(j=3)，栈顶为0(k=0)，3面积为(4-0-1)*4=12，stack={0}
 *         5.2、height[4]=3，3==height[0]，0弹出(j=0)，栈空(k=-1)，0面积为(4+1-1)*3=12，stack={}
 *         5.3、height[4]=3，栈空，直接入栈，stack={4}
 *         6、height[5]=6，6>height[4]，入栈，stack={4,5}
 *         7、遍历结束，stack中仍有未经过扩展的元素，此时i为height.length，无法再向右扩展
 *         7.1、height[6]极小，5弹出(j=5)，栈顶为4(k=4)，5面积为(6-4-1)*6=6，stack={4}
 *         7.2、height[6]极小，4弹出(j=4)，栈空(k=-1)，4面积为(6+1-1)*3=18，stack={}
 *         8、其中1的面积和0的面积均在计算时偏小，但在3和4弹出时能够重新计算得到正确值
 *      stack中任何一个元素仅进出栈1次，所以时间复杂度为O(M)，做了N次切割，因此总时间复杂度为O(N*M)
 * </p>
 *
 * @author wangzi
 */
public class MaxRectangle {

    public static int maxRecSize(int[][] map) {
        if (map == null || map.length == 0 || map[0].length == 0) {
            return 0;
        }

        int maxArea = 0;
        int[] height = new int[map[0].length];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                height[j] = map[i][j] == 0 ? 0 : height[j] + 1;
            }
            // 每一行为底时的最大矩形面积
            maxArea = Math.max(maxRecFromBottom(height), maxArea);
        }
        return maxArea;
    }

    private static int maxRecFromBottom(int[] height) {
        if (height == null || height.length == 0) {
            return 0;
        }

        int maxArea = 0;
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < height.length; i++) {
            // 大于栈顶才入栈，否则出栈，并计算矩形面试
            while (!stack.isEmpty() && height[i] <= height[stack.peek()]) {
                int j = stack.pop();
                int k = stack.isEmpty() ? -1 : stack.peek();
                int curArea = (i - k - 1) * height[j];
                maxArea = Math.max(maxArea, curArea);
            }
            stack.push(i);
        }

        while (!stack.isEmpty()) {
            int j = stack.pop();
            int k = stack.isEmpty() ? -1 : stack.peek();
            int curArea = (height.length - k - 1) * height[j];
            maxArea = Math.max(maxArea, curArea);
        }

        return maxArea;
    }

    public static void main(String[] args) {
        int[][] map = {{1, 0, 1, 1}, {1, 1, 1, 1}, {1, 1, 1, 0}};
        System.out.println(maxRecSize(map));
    }
}
