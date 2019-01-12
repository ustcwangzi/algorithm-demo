/**
 * <p>Title: MinPathValue</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/1/12</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.arrayandmatrix;

import java.util.LinkedList;
import java.util.Queue;

/**
 * <p>最短路径值</p>
 * <p>
 *     给定一个整形矩阵matrix，只含有0和1，0代表无路，1代表有路，每个位置只要不越界，都有上下左右四个方向，
 *     求出从左上角到右下角的最短路径值。
 *     解决方案：
 *        1、生成map矩阵，map[i][j]表示从(0,0)到(i,j)最短的路径值，然后将(0,0)行坐标与列坐标放入行队列rowQueue、列队列colQueue
 *        2、不断从队列弹出位置(row,col)，然后看这个位置的上下左右四个位置哪些在matrix上的值为1，这些都是能走的位置
 *        3、将那些能走的位置设置好各自在map中的值，即map[row][col]+1，同时将这些位置放到rowQueue、colQueue
 *        4、步骤三中，如果一个位置之前已走过，则不要重复走，即若map[row][col]!=0，则表示这个位置之前已走过
 *        5、重复步骤二～四，直到遇到右下角的位置，说明已经到达终点
 * </p>
 * <p>
 *     矩阵大小为N*M，时间复杂度为O(N*M)，空间复杂度为O(N*M)
 * </p>
 *
 * @author wangzi
 */
public class MinPathValue {

    public static int minPathValue(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0 || matrix[0][0] != 1
                || matrix[matrix.length - 1][matrix[0].length - 1] != 1) {
            return 0;
        }

        int result = 0;
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
        return result;
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
        System.out.println(minPathValue(matrix));
    }
}
