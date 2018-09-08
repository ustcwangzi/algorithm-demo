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
    /**
     * 用Map来表示图
     */
    private static Map<Integer, List<Integer>> graph = new HashMap<>();
    /**
     * 保存已知路径
     */
    private static Map<Integer, Integer> pathMap = new HashMap<>();

    private static boolean search(int source, int target) {
        Queue<Integer> searchQueue = new ArrayDeque<>();
        searchQueue.add(source);
        List<Integer> searched = new ArrayList<>();

        while (!searchQueue.isEmpty()) {
            int v = searchQueue.poll();
            searched.add(v);
            for (int w : graph.get(v)) {
                if (!searched.contains(w)) {
                    pathMap.put(w, v);
                    if (w == target) {
                        System.out.println("have search the target: " + target);
                        return true;
                    }
                    searchQueue.add(w);
                    searched.add(w);
                }
            }
        }
        return false;
    }

    private static void printPath(int source, int target) {
        Stack<Integer> pathStack = new Stack<>();
        pathStack.push(target);
        int w = pathMap.get(target);
        while (w != source) {
            pathStack.push(w);
            w = pathMap.get(w);
        }
        pathStack.push(source);

        while (!pathStack.empty()) {
            int path = pathStack.pop();
            if (path == target) {
                System.out.print(path);
            } else {
                System.out.print(path + " -> ");
            }
        }
    }

    public static void main(String[] args) {
        graph.put(0, Arrays.asList(1, 2));
        graph.put(1, Collections.singletonList(3));
        graph.put(2, Arrays.asList(4, 5));
        graph.put(3, Collections.singletonList(4));
        graph.put(4, Collections.singletonList(5));
        graph.put(5, Collections.emptyList());

        int source = 0, target = 5;
        boolean hasPath = search(source, target);
        if (hasPath) {
            printPath(source, target);
        }
    }
}
