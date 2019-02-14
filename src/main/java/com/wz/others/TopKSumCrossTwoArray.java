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
