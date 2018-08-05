/**
 * <p>Title: WeightedDigraph</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/8/5</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.graph.sp;

import com.wz.graph.Bag;
import com.wz.utils.GraphUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>加权有向图</p>
 *
 * @author wangzi
 */
public class WeightedDigraph {
    private static final String NEWLINE = System.getProperty("line.separator");

    /**
     * 顶点数量
     */
    private final int vertices;
    /**
     * 边的数量
     */
    private int edges;
    /**
     * 邻接表
     */
    private Bag<DirectedEdge>[] adjacent;
    /**
     * 顶点的入度
     */
    private int[] indegree;

    public WeightedDigraph(int vertices) {
        if (vertices < 0) {
            throw new IllegalArgumentException("Number of vertices in a Digraph must be nonnegative");
        }
        this.vertices = vertices;
        this.edges = 0;
        this.indegree = new int[vertices];
        this.adjacent = (Bag<DirectedEdge>[]) new Bag[vertices];
        for (int v = 0; v < vertices; v++) {
            adjacent[v] = new Bag<>();
        }
    }

    public int vertices() {
        return vertices;
    }

    private void validateVertex(int v) {
        if (v < 0 || v >= vertices) {
            throw new IllegalArgumentException("vertex must between 0 and " + (vertices - 1));
        }
    }

    public void addEdge(DirectedEdge edge) {
        int v = edge.from();
        int w = edge.to();
        validateVertex(v);
        validateVertex(w);
        adjacent[v].add(edge);
        indegree[w]++;
        edges++;
    }

    public Iterable<DirectedEdge> adj(int v) {
        validateVertex(v);
        return adjacent[v];
    }

    public Iterable<DirectedEdge> edges() {
        List<DirectedEdge> edges = new ArrayList<>();
        for (int v = 0; v < vertices; v++) {
            for (DirectedEdge edge : adj(v)) {
                edges.add(edge);
            }
        }
        return edges;
    }

    public int inDegree(int v) {
        validateVertex(v);
        return indegree[v];
    }

    public int outDegree(int v) {
        validateVertex(v);
        return adjacent[v].size();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(vertices + " vertices, " + edges + " edges " + NEWLINE);
        for (int v = 0; v < vertices; v++) {
            builder.append(v + ": ");
            for (DirectedEdge edge : adjacent[v]) {
                builder.append(edge + " ");
            }
            builder.append(NEWLINE);
        }
        return builder.toString();
    }

    public static void main(String[] args) {
        WeightedDigraph digraph = GraphUtils.initWeightedDigraph();
        System.out.println(digraph);
    }
}
