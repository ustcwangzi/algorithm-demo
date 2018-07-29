/**
 * <p>Title: DepthFirstDirectedPaths</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/7/28</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.graph.directed;

import com.wz.utils.GraphUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>使用深度优先搜索查找有向图中的路径</p>
 *
 * @author wangzi
 */
public class DepthFirstDirectedPaths {
    /**
     * 标记是否可达
     */
    private boolean[] marked;
    /**
     * 从起点到一个顶点的已知路径上的最后一个顶点
     */
    private int[] edgeTo;
    /**
     * 起点
     */
    private final int source;

    public DepthFirstDirectedPaths(Digraph digraph, int source) {
        this.marked = new boolean[digraph.vertices()];
        this.edgeTo = new int[digraph.vertices()];
        this.source = source;
        validateVertex(source);
        dfs(digraph, source);
    }

    private void dfs(Digraph digraph, int v) {
        marked[v] = true;
        for (int w : digraph.adj(v)) {
            if (!marked[w]) {
                edgeTo[w] = v;
                dfs(digraph, w);
            }
        }
    }

    public boolean hasPathTo(int v) {
        validateVertex(v);
        return marked[v];
    }

    public Iterable<Integer> pathTo(int v) {
        validateVertex(v);
        if (!hasPathTo(v)) {
            return null;
        }
        List<Integer> path = new ArrayList<>();
        for (int w = v; w != source; w = edgeTo[w]) {
            path.add(w);
        }
        path.add(source);
        Collections.reverse(path);
        return path;
    }

    private void validateVertex(int v) {
        int length = marked.length;
        if (v < 0 || v >= length) {
            throw new IllegalArgumentException("vertex must between 0 and " + (length - 1));
        }
    }

    public static void main(String[] args) {
        Digraph digraph = GraphUtils.initCycleDigraph();
        int source = 3;
        DepthFirstDirectedPaths directedPaths = new DepthFirstDirectedPaths(digraph, source);

        for (int v = 0; v < digraph.vertices(); v++) {
            if (directedPaths.hasPathTo(v)) {
                System.out.printf("%d to %d: ", source, v);
                for (int w : directedPaths.pathTo(v)) {
                    if (w == source) {
                        System.out.print(w);
                    } else {
                        System.out.print("-" + w);
                    }
                }
                System.out.println();
            } else {
                System.out.printf("%d to %d not connected\n", source, v);
            }
        }
    }
}
