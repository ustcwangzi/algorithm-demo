/**
 * <p>Title: Digraph</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/7/28</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.graph.directed;

import com.wz.graph.Bag;
import com.wz.utils.GraphUtils;

/**
 * <p>使用邻接表表示有向图</p>
 *
 * @author wangzi
 */
public class Digraph {
    private static final String NEWLINE = System.getProperty("line.separator");

    /** 顶点数量 */
    private final int vertices;
    /** 边的数量 */
    private int edges;
    /** 邻接表 */
    private Bag<Integer>[] adjacent;
     /** 顶点的入度 */
    private int[] indegree;

    public Digraph(int vertices) {
        if (vertices < 0){
            throw new IllegalArgumentException("Number of vertices must be positive");
        }
        this.vertices = vertices;
        this.edges = 0;
        this.indegree = new int[vertices];
        this.adjacent = (Bag<Integer>[]) new Bag[vertices];
        for (int v = 0; v < vertices; v++){
            adjacent[v] = new Bag<>();
        }
    }

    public int vertices(){
        return vertices;
    }

    public int edges(){
        return edges;
    }

    private void validateVertex(int v) {
        if (v < 0 || v >= vertices) {
            throw new IllegalArgumentException("vertex must between 0 and " + (vertices-1));
        }
    }

    public void addEdge(int v, int w){
        validateVertex(v);
        validateVertex(w);
        adjacent[v].add(w);
        indegree[w]++;
        edges++;
    }

    public Iterable<Integer> adj(int v){
        validateVertex(v);
        return adjacent[v];
    }

    public int inDegree(int v) {
        validateVertex(v);
        return indegree[v];
    }

    public int outDegree(int v) {
        validateVertex(v);
        return adjacent[v].size();
    }

    public Digraph reverse() {
        Digraph reverse = new Digraph(vertices);
        for (int v = 0; v < vertices; v++) {
            for (int w : adj(v)) {
                reverse.addEdge(w, v);
            }
        }
        return reverse;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(vertices + " vertices, " + edges + " edges " + NEWLINE);
        for (int v = 0; v < vertices; v++){
            builder.append(v + ": ");
            for (int w : adjacent[v]){
                builder.append(w + " ");
            }
            builder.append(NEWLINE);
        }
        return builder.toString();
    }

    public static void main(String[] args) {
        Digraph digraph = GraphUtils.initDigraph();
        System.out.println(digraph);
    }
}
