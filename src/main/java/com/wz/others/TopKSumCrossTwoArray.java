/**
 * <p>Title: TopKSumCrossTwoArray</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/2/14</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.others;

import java.util.*;

/**
 * <p>两个有序数组间相加和的TopK</p>
 * <p>
 *     给定两个有序数组self和other，再给定一个整数k，获取来自self和other的两个数相加和最大的前k个，两个数必须分别来自两个数组。
 *     例如：
 *        self=[1,2,3,4,5]，other=[3,5,7,9,11]，k=4，结果为[16,15,14,14]。
 *     解决方案：
 *        使用大根堆结构。假设self的长度是M，other的长度是N。因为是排序数组，self中最后一个数加上other中最后一个数一定就是最大的相加和。
 *        将这个数压入大根堆中。然后从大根堆中弹出一个堆顶，此时这个堆顶一定是(M-1, N-1)位置的和，表示获得一个最大相加和。
 *        然后，将两个相邻位置的和再放入堆中，即位置(M-1,N-2)和(M-2, N-1)，因为除(M-1, N-1)位置的和外，
 *        最大的相加和一定在位置(M-1,N-2)和(M-2, N-1)中产生。
 *        重新调整大根堆，然后继续弹出，继续将弹出元素的两个相邻位置添加到堆中，直到弹出的元素达到K个。
 *        注意，要利用哈希表来防止同一个位置重复进堆的情况。
 * </p>
 * <p>
 *     时间复杂度为O(k*logk)
 * </p>
 *
 * @author wangzi
 */
public class TopKSumCrossTwoArray {

    private static class HeapNode {
        public int row;
        public int col;
        public int value;

        public HeapNode(int row, int col, int value) {
            this.row = row;
            this.col = col;
            this.value = value;
        }
    }

    public static int[] topKSum(int[] self, int[] other, int topK) {
        if (self == null || other == null || self.length == 0 || other.length == 0 || topK < 1) {
            return null;
        }

        topK = Math.min(topK, self.length * other.length);
        int headRow = self.length - 1;
        int headCol = other.length - 1;
        // 上方位置
        int upperRow, upperCol;
        // 左方位置
        int leftRow, leftCol;

        // 利用PriorityQueue实现大根堆
        Queue<HeapNode> maxHeap = new PriorityQueue<>(topK + 1,
                (HeapNode o1, HeapNode o2) -> (Integer.compare(o2.value, o1.value)));

        maxHeap.add(new HeapNode(headRow, headCol, self[headRow] + other[headCol]));
        // 记录已添加过的位置
        Set<String> positionSet = new HashSet<>();
        int[] result = new int[topK];
        int index = 0;
        while (index != topK) {
            HeapNode head = maxHeap.poll();
            result[index++] = head.value;
            headRow = head.row;
            headCol = head.col;

            upperRow = headRow - 1;
            upperCol = headCol;
            if (headRow != 0 && !positionSet.contains(String.valueOf(upperRow + "_" + upperCol))) {
                maxHeap.add(new HeapNode(upperRow, upperCol, self[upperRow] + other[upperCol]));
                positionSet.add(String.valueOf(upperCol + "_" + upperCol));
            }

            leftRow = headRow;
            leftCol = headCol - 1;
            if (headCol != 0 && !positionSet.contains(String.valueOf(leftRow + "_" + leftCol))) {
                maxHeap.add(new HeapNode(leftRow, leftCol, self[leftRow] + other[leftCol]));
                positionSet.add(String.valueOf(leftRow + "_" + leftCol));
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int[] self = {1, 2, 3, 4, 5};
        int[] other = {3, 5, 7, 9, 11};
        for (int i = 1; i <= self.length + other.length; i++) {
            System.out.println("top" + i + ": " + Arrays.toString(topKSum(self, other, i)));
        }
    }
}
