/**
 * <p>Title: EdgeWeightedGraph</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/8/4</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.graph.mst;

import com.wz.graph.Bag;
import com.wz.utils.GraphUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>加权无向图</p>
 *
 * @author wangzi
 */
public class EdgeWeightedGraph {
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
    private Bag<Edge>[] adjacent;

    public EdgeWeightedGraph(int vertices) {
        if (vertices < 0) {
            throw new IllegalArgumentException("Number of vertices must be nonnegative");
        }
        this.vertices = vertices;
        this.edges = 0;
        this.adjacent = (Bag<Edge>[]) new Bag[vertices];
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

    public void addEdge(Edge edge) {
        int v = edge.either();
        int w = edge.other(v);
        validateVertex(v);
        validateVertex(w);
        adjacent[v].add(edge);
        adjacent[w].add(edge);
        edges++;
    }

    public Iterable<Edge> adj(int v) {
        validateVertex(v);
        return adjacent[v];
    }

    public int degree(int v) {
        validateVertex(v);
        return adjacent[v].size();
    }

    public Iterable<Edge> edges() {
        List<Edge> edges = new ArrayList<>();
        for (int v = 0; v < vertices; v++) {
            int selfLoops = 0;
            for (Edge edge : adj(v)) {
                if (edge.other(v) > v) {
                    edges.add(edge);
                } else if (edge.other(v) == v) {
                    // 自环的边只添加一次
                    if (selfLoops % 2 == 0) {
                        edges.add(edge);
                    }
                    selfLoops++;
                }
            }
        }
        return edges;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(vertices + " vertices, " + edges + " edges " + NEWLINE);
        for (int v = 0; v < vertices; v++) {
            builder.append(v + ": ");
            for (Edge edge : adjacent[v]) {
                builder.append(edge + " ");
            }
            builder.append(NEWLINE);
        }
        return builder.toString();
    }

    public static void main(String[] args) {
        EdgeWeightedGraph weightedGraph = GraphUtils.initEdgeWeightedGraph();
        System.out.println(weightedGraph);
    }
}
