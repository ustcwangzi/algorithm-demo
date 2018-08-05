/**
 * <p>Title: WeightedDirectedCycle</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/8/5</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.graph.sp;

import com.wz.utils.GraphUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>加权有向图环路检测</p>
 *
 * @author wangzi
 */
public class WeightedDirectedCycle {
    /**
     * 标识顶点是否遍历过
     */
    private boolean[] marked;
    /**
     * 从起点到一个顶点的已知路径上的最后一条边
     */
    private DirectedEdge[] edgeTo;
    /**
     * 递归调用的栈上的所用顶点
     */
    private boolean[] onStack;
    /**
     * 有向环上的边
     */
    private List<DirectedEdge> cycle;

    public WeightedDirectedCycle(WeightedDigraph digraph) {
        this.marked = new boolean[digraph.vertices()];
        this.onStack = new boolean[digraph.vertices()];
        this.edgeTo = new DirectedEdge[digraph.vertices()];
        for (int v = 0; v < digraph.vertices(); v++) {
            if (!marked[v]) {
                dfs(digraph, v);
            }
        }
    }

    private void dfs(WeightedDigraph digraph, int v) {
        onStack[v] = true;
        marked[v] = true;
        for (DirectedEdge edge : digraph.adj(v)) {
            int w = edge.to();
            if (cycle != null) {
                return;
            }
            if (!marked[w]) {
                edgeTo[w] = edge;
                dfs(digraph, w);
            } else if (onStack[w]) {
                cycle = new ArrayList<>();
                DirectedEdge tmp = edge;
                while (tmp.from() != w) {
                    cycle.add(tmp);
                    tmp = edgeTo[tmp.from()];
                }
                cycle.add(tmp);
                return;
            }
        }
        onStack[v] = false;
    }

    public boolean hasCycle() {
        return cycle != null;
    }

    public Iterable<DirectedEdge> cycle() {
        return cycle;
    }

    public static void main(String[] args) {
        WeightedDigraph digraph = GraphUtils.initWeightedDigraph();
        WeightedDirectedCycle directedCycle = new WeightedDirectedCycle(digraph);
        System.out.println(directedCycle.hasCycle());
        for (DirectedEdge edge : directedCycle.cycle()) {
            System.out.print(edge + "  ");
        }
    }
}
