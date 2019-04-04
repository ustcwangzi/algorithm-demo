package com.wz;

import com.wz.arrayandmatrix.MinPathValue;

import java.util.LinkedList;
import java.util.Queue;

/**
 * <p>矩阵matrix，只含有0和1，0代表无路，1代表有路，求出从左上角到右下角的最短路径值</p>
 *
 * @author wangzi
 */
public class MinPathValueTest {
    private static int solution(int[][] matrix) {
        int[][] map = new int[matrix.length][matrix[0].length];
        Queue<Integer> rowQueue = new LinkedList<>();
        Queue<Integer> colQueue = new LinkedList<>();
        map[0][0] = 1;
        rowQueue.add(0);
        colQueue.add(0);

        int row, col;
        while (!rowQueue.isEmpty()) {
            row = rowQueue.poll();
            col = colQueue.poll();
            if (row == matrix.length - 1 && col == matrix[0].length - 1) {
                return map[row][col];
            }

            // 向上
            walkTo(matrix, map, rowQueue, colQueue, map[row][col], row - 1, col);
            // 向下
            walkTo(matrix, map, rowQueue, colQueue, map[row][col], row + 1, col);
            // 向左
            walkTo(matrix, map, rowQueue, colQueue, map[row][col], row, col - 1);
            // 向右
            walkTo(matrix, map, rowQueue, colQueue, map[row][col], row, col + 1);
        }
        return 0;
    }

    private static void walkTo(int[][] matrix, int[][] map, Queue<Integer> rowQueue, Queue<Integer> colQueue,
                               int pre, int row, int col) {
        if (row < 0 || col < 0 || row == matrix.length || col == matrix[0].length
                || matrix[row][col] != 1 || map[row][col] != 0) {
            return;
        }
        map[row][col] = pre + 1;
        rowQueue.add(row);
        colQueue.add(col);
    }

    public static void main(String[] args) {
        int[][] matrix = {
                {1, 0, 1, 1, 1, 0, 1, 1, 1},
                {1, 0, 1, 0, 1, 0, 1, 0, 1},
                {1, 0, 1, 0, 1, 0, 1, 0, 1},
                {1, 0, 1, 0, 1, 0, 1, 0, 1},
                {1, 0, 1, 0, 1, 0, 1, 0, 1},
                {1, 0, 1, 0, 1, 0, 1, 0, 1},
                {1, 0, 1, 0, 1, 0, 1, 0, 1},
                {1, 0, 1, 0, 1, 0, 1, 0, 1},
                {1, 1, 1, 0, 1, 1, 1, 0, 1}};
        if (solution(matrix) != MinPathValue.minPathValue(matrix)) {
            System.out.println("Error");
        } else {
            System.out.println("Past");
        }
    }
}
