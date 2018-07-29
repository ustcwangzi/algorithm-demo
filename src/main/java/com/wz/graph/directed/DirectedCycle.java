/**
 * <p>Title: DirectedCycle</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/7/29</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.graph.directed;

import com.wz.utils.GraphUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>寻找有向环</p>
 * <p>
 *     深度优先搜索中，系统维护的递归调用的栈表示的是当前正在遍历的有向路径
 *     一旦找到一条边 v->w 且 w 已经在栈中了，表明此时形成了回路，即存在环
 *     执行dfs(graph, v)时，查找的是一条由起点到v的有向路径
 *     使用数组onStack[]维护了这条路径，以标记递归调用的栈上的所有顶点
 * </p>
 *
 * @author wangzi
 */
public class DirectedCycle {
    /**
     * 标识顶点是否遍历过
     */
    private boolean[] marked;
    /**
     * 从起点到一个顶点的已知路径上的最后一个顶点
     */
    private int[] edgeTo;
    /**
     * 递归调用的栈上的所用顶点
     */
    private boolean[] onStack;
    /**
     * 有向环上的顶点
     */
    private List<Integer> cycle;

    public DirectedCycle(Digraph digraph) {
        this.marked = new boolean[digraph.vertices()];
        this.onStack = new boolean[digraph.vertices()];
        this.edgeTo = new int[digraph.vertices()];
        for (int v = 0; v < digraph.vertices(); v++) {
            if (!marked[v] && cycle == null) {
                dfs(digraph, v);
            }
        }
    }

    private void dfs(Digraph digraph, int v) {
        marked[v] = true;
        // 递归调用开始时将其设为true
        onStack[v] = true;
        for (int w : digraph.adj(v)) {
            // 已找到有向环
            if (cycle != null) {
                return;
            }
            if (!marked[w]) {
                edgeTo[w] = v;
                dfs(digraph, w);
            } else if (onStack[w]) {
                cycle = new ArrayList<>();
                for (int x = v; x != w; x = edgeTo[x]) {
                    cycle.add(x);
                }
                cycle.add(w);
                cycle.add(v);
                assert check();
            }
        }
        // 递归调用结束时将其设为false
        onStack[v] = false;
    }

    public boolean hasCycle() {
        return cycle != null;
    }

    public Iterable<Integer> cycle() {
        return cycle;
    }

    private boolean check() {
        if (hasCycle()) {
            int first = -1, last = -1;
            for (int v : cycle()) {
                if (first == -1) {
                    first = v;
                }
                last = v;
            }
            if (first != last) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Digraph digraph = GraphUtils.initCycleDigraph();
        DirectedCycle finder = new DirectedCycle(digraph);
        if (finder.hasCycle()) {
            System.out.print("Directed cycle: ");
            for (int v : finder.cycle()) {
                System.out.print(v + " ");
            }
            System.out.println();
        } else {
            System.out.println("No directed cycle");
        }
        System.out.println();
    }
}
