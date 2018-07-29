/**
 * <p>Title: DirectedDFS</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/7/28</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.graph.directed;

import com.wz.utils.GraphUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>深度优先遍历计算有向图的可达性</p>
 *
 * @author wangzi
 */
public class DirectedDFS {
    /**
     * 标识顶点是否可达
     */
    private boolean[] marked;
    /**
     * 可达顶点个数
     */
    private int count;

    /**
     * 从一个顶点开始能够到达哪些顶点
     */
    public DirectedDFS(Digraph digraph, int source) {
        this.marked = new boolean[digraph.vertices()];
        validateVertex(source);
        dfs(digraph, source);
    }

    /**
     * 从一组顶点开始能够达到哪些顶点
     */
    public DirectedDFS(Digraph digraph, Iterable<Integer> sources) {
        this.marked = new boolean[digraph.vertices()];
        validateVertex(sources);
        for (int source : sources) {
            if (!marked[source]) {
                dfs(digraph, source);
            }
        }
    }

    private void dfs(Digraph digraph, int v) {
        count++;
        marked[v] = true;
        for (int w : digraph.adj(v)) {
            if (!marked[w]) {
                dfs(digraph, w);
            }
        }
    }

    /**
     * 某一顶点是否从source可达
     */
    public boolean marked(int v) {
        validateVertex(v);
        return marked[v];
    }

    /**
     * 从source可达顶点数
     */
    public int count() {
        return count;
    }

    private void validateVertex(int v) {
        int length = marked.length;
        if (v < 0 || v >= length) {
            throw new IllegalArgumentException("vertex must between 0 and " + (length - 1));
        }
    }

    private void validateVertex(Iterable<Integer> vertices) {
        if (vertices == null) {
            throw new IllegalArgumentException("argument is null");
        }
        int length = marked.length;
        for (int v : vertices) {
            if (v < 0 || v >= length) {
                throw new IllegalArgumentException("vertex must between 0 and " + (length - 1));
            }
        }
    }

    public static void main(String[] args) {
        Digraph digraph = GraphUtils.initDigraph();
        DirectedDFS dfs = new DirectedDFS(digraph, 2);
        for (int v = 0; v < digraph.vertices(); v++) {
            if (dfs.marked(v)) {
                System.out.print(v + " ");
            }
        }
        System.out.println();

        List<Integer> sources = new ArrayList<>();
        sources.add(1);
        sources.add(6);
        dfs = new DirectedDFS(digraph, sources);
        for (int v = 0; v < digraph.vertices(); v++) {
            if (dfs.marked(v)) {
                System.out.print(v + " ");
            }
        }
    }
}
