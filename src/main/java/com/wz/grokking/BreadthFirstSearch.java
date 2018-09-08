/**
 * <p>Title: BreadthFirstSearch</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/9/8</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.grokking;

import java.util.*;

/**
 * <p>广度优先搜索</p>
 * <p>运行时间为 O(V+E)，V为顶点数，E为边数</p>
 *
 * @author wangzi
 */
public class BreadthFirstSearch {
    private static Map<Integer, List<Integer>> graph = new HashMap<>();

    private static boolean search(int source, int target) {
        Queue<Integer> searchQueue = new ArrayDeque<>(graph.get(source));
        List<Integer> searched = new ArrayList<>();

        while (!searchQueue.isEmpty()) {
            int now = searchQueue.poll();
            if (!searched.contains(now)) {
                if (now == target) {
                    System.out.println("have search the target: " + target);
                    return true;
                } else {
                    searchQueue.addAll(graph.get(now) == null ? Collections.emptyList() : graph.get(now));
                    searched.add(now);
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        graph.put(0, Arrays.asList(1, 2));
        graph.put(1, Arrays.asList(3));
        graph.put(2, Arrays.asList(4, 5));
        search(0, 5);
    }
}
