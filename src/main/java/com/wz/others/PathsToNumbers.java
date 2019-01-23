/**
 * <p>Title: PathsToNumbers</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/1/23</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.others;

import java.util.Arrays;

/**
 * <p>路径数组变为统计数组</p>
 *
 * @author wangzi
 */
public class PathsToNumbers {

    public static void pathsToNumbers(int[] paths) {
        if (paths == null || paths.length == 0) {
            return;
        }
        // citiesPath -> distancesArray
        pathsToDistances(paths);

        // distancesArray -> numberArray
        distanceToNumbers(paths);
    }

    private static void pathsToDistances(int[] paths) {
        // 首都
        int capital = 0;
        for (int i = 0; i < paths.length; i++) {
            if (paths[i] == i) {
                capital = i;
            } else if (paths[i] > -1) {
                int cur = paths[i];
                paths[i] = -1;
                int pre = i;
                // 从cur开始向下一个城市跳，同时记录从哪里跳过来的
                while (paths[cur] != cur) {
                    if (paths[cur] > -1) {
                        // cur的下一个城市
                        int next = paths[cur];
                        // 记录从哪个城市跳过来的
                        paths[cur] = pre;
                        // 继续向下跳
                        pre = cur;
                        cur = next;
                    } else {
                        // 已处理过
                        break;
                    }
                }

                // 跳到了首都的位置，距离设为0，否则直接使用cur距离首都的距离
                int value = paths[cur] == cur ? 0 : paths[cur];
                // 从pre开始回跳，回跳过程中设置距离
                while (paths[pre] != -1) {
                    int lastPre = paths[pre];
                    paths[pre] = --value;
                    pre = lastPre;
                }
                paths[pre] = --value;
            }
        }
        paths[capital] = 0;
    }

    private static void distanceToNumbers(int[] distances) {
        for (int i = 0; i < distances.length; i++) {
            int index = distances[i];
            if (index < 0) {
                distances[i] = 0;
                while (true) {
                    index = -index;
                    if (distances[index] > -1) {
                        // 值是正数，表示当前值代表的是距离为index的城市数量统计
                        distances[index]++;
                        break;
                    } else {
                        // 值是负数，表示当前值代表的是城市index与首都的距离
                        int nextIndex = distances[index];
                        distances[index] = 1;
                        index = nextIndex;
                    }
                }
            }
        }
        // 距离为0的城市只有首都
        distances[0] = 1;
    }

    public static void main(String[] args) {
        int[] paths = {9, 1, 4, 9, 0, 4, 8, 9, 0, 1};
        pathsToNumbers(paths);
        System.out.println(Arrays.toString(paths));
    }
}
