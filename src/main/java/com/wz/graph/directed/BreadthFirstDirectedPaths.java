/**
 * <p>Title: BreadthFirstDirectedPaths</p>
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
 * <p>使用广度优先搜索查找有向图中的路径</p>
 *
 * @author wangzi
 */
public class BreadthFirstDirectedPaths {
    private static final int INFINITY = Integer.MAX_VALUE;
    /**
     * 标记是否遍历过
     */
    private boolean[] marked;
    /**
     * 从起点到一个顶点的已知路径上的最后一个顶点
     */
    private int[] edgeTo;
    /**
     * 从起点到一个顶点的已知路径长度
     */
    private int[] distTo;
    /**
     * 起点
     */
    private final int source;

    public BreadthFirstDirectedPaths(Digraph digraph, int source) {
        this.marked = new boolean[digraph.vertices()];
        this.distTo = new int[digraph.vertices()];
        this.edgeTo = new int[digraph.vertices()];
        this.source = source;
        validateVertex(source);
        bfs(digraph);
    }

    private void bfs(Digraph digraph) {
        List<Integer> path = new ArrayList<>();
        for (int v = 0; v < digraph.vertices(); v++) {
            distTo[v] = INFINITY;
        }
        distTo[source] = 0;
        marked[source] = true;
        path.add(source);

        for (int i = 0; i < path.size(); i++) {
            int v = path.get(i);
            for (int w : digraph.adj(v)) {
                if (!marked[w]) {
                    edgeTo[w] = v;
                    distTo[w] = distTo[v] + 1;
                    marked[w] = true;
                    path.add(w);
                }
            }
        }
    }

    public boolean hasPathTo(int v) {
        validateVertex(v);
        return marked[v];
    }

    public int distTo(int v) {
        validateVertex(v);
        return distTo[v];
    }

    public Iterable<Integer> pathTo(int v) {
        validateVertex(v);
        if (!hasPathTo(v)) {
            return null;
        }

        List<Integer> path = new ArrayList<>();
        for (int w = v; distTo[w] != 0; w = edgeTo[w]) {
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
        BreadthFirstDirectedPaths directedPaths = new BreadthFirstDirectedPaths(digraph, source);

        for (int v = 0; v < digraph.vertices(); v++) {
            if (directedPaths.hasPathTo(v)) {
                System.out.printf("%d to %d (%d): ", source, v, directedPaths.distTo(v));
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
