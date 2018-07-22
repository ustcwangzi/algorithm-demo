/**
 * <p>Title: CycleCheck</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/7/22</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.graph;

import com.wz.utils.GraphUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>检查一个图是否有环</p>
 *
 * @author wangzi
 */
public class CycleCheck {
    /** 标记是否遍历过 */
    private boolean[] marked;
    /** 从起点到一个顶点的已知路径上的最后一个顶点 */
    private int[] edgeTo;
    private List<Integer> cycle;

    public CycleCheck(Graph graph) {
        if (hasSelfLoop(graph) || hasParallelEdges(graph)){
            return;
        }
        this.marked = new boolean[graph.V()];
        this.edgeTo = new int[graph.V()];

        for (int v = 0; v < graph.V(); v++){
            if (!marked[v]){
                dfs(graph, -1, v);
            }
        }
    }

    private boolean hasSelfLoop(Graph graph){
        for (int v = 0; v < graph.V(); v++){
            for (int w : graph.adj(v)){
                if (v == w){
                    cycle = new ArrayList<>();
                    cycle.add(v);
                    cycle.add(v);
                    return true;
                }
            }
        }
        return false;
    }

    private boolean hasParallelEdges(Graph graph){
        this.marked = new boolean[graph.V()];
        for (int v = 0; v < graph.V(); v++) {
            for (int w : graph.adj(v)) {
                if (marked[w]){
                    cycle = new ArrayList<>();
                    cycle.add(v);
                    cycle.add(w);
                    cycle.add(v);
                    return true;
                }
                marked[w] = true;
            }
            for (int w : graph.adj(v)){
                marked[w] = false;
            }
        }
        return false;
    }

    public boolean hasCycle(){
        return cycle != null;
    }

    private void dfs(Graph graph, int u, int v){
        marked[v] = true;
        for (int w : graph.adj(v)){
            if (cycle != null){
                return;
            }
            if (!marked[w]){
                edgeTo[w] = v;
                dfs(graph, v, w);
            }else if (w != u){
                cycle = new ArrayList<>();
                for (int x = v; x != w; x = edgeTo[x]){
                    cycle.add(x);
                }
                cycle.add(w);
                cycle.add(v);
            }
        }
    }

    public Iterable<Integer> cycle(){
        return cycle;
    }

    public static void main(String[] args) {
        Graph graph = GraphUtils.initGraph();
        CycleCheck check = new CycleCheck(graph);

        if (check.hasCycle()){
            for (int v : check.cycle()){
                System.out.print(v + " ");
            }
            System.out.println();
        }else {
            System.out.println("no cycle");
        }
    }
}
