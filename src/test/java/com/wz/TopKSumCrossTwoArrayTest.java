/**
 * <p>Title: TopKSumCrossTwoArrayTest</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/3/2</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz;

import com.wz.others.TopKSumCrossTwoArray;

import java.util.*;

/**
 * <p>两个有序数组间相加和的TopK</p>
 *
 * @author wangzi
 */
public class TopKSumCrossTwoArrayTest {
    private static class Node {
        public int row;
        public int col;
        public int value;

        public Node(int row, int col, int value) {
            this.row = row;
            this.col = col;
            this.value = value;
        }
    }

    private static int[] solution(int[] self, int[] other, int k) {
        k = Math.min(self.length * other.length, k);
        int[] result = new int[k];
        int row = self.length - 1, col = other.length - 1;
        int nextRow, nextCol;
        // 大根堆
        Queue<Node> maxHeap = new PriorityQueue<>(k, (Node o1, Node o2) -> (Integer.compare(o2.value, o1.value)));
        // 最大的和一定是(m-1,n-1)
        maxHeap.add(new Node(row, col, self[row] + other[col]));
        Set<String> set = new HashSet<>();
        int index = 0;
        while (index < k) {
            // 次大的和一定在(m-1,n-2)与(m-2,n-1)中产生
            Node head = maxHeap.poll();
            result[index++] = head.value;
            nextRow = head.row;
            nextCol = head.col - 1;
            if (nextCol > -1 && !set.contains(nextRow + "_" + nextCol)) {
                maxHeap.add(new Node(nextRow, nextCol, self[nextRow] + other[nextCol]));
                set.add(nextRow + "_" + nextCol);
            }
            nextRow = head.row - 1;
            nextCol = head.col;
            if (nextRow > -1 && !set.contains(nextRow + "_" + nextCol)) {
                maxHeap.add(new Node(nextRow, nextCol, self[nextRow] + other[nextCol]));
                set.add(nextRow + "_" + nextCol);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int[] self = {1, 2, 3, 4, 5};
        int[] other = {3, 5, 7, 9, 11};
        for (int i = 1; i <= self.length + other.length; i++) {
            System.out.println("top" + i + ": " + Arrays.toString(solution(self, other, i)));
            System.out.println("top" + i + ": " + Arrays.toString(TopKSumCrossTwoArray.topKSum(self, other, i)));
        }
    }
}
