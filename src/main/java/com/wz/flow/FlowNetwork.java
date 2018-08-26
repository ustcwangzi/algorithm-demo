/**
 * <p>Title: FlowNetwork</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/8/26</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.flow;

import com.wz.graph.Bag;
import com.wz.utils.FlowUtils;

/**
 * <p>流量网络</p>
 *
 * @author wangzi
 */
public class FlowNetwork {
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
    private Bag<FlowEdge>[] adjacent;

    public FlowNetwork(int vertices) {
        if (vertices < 0) {
            throw new IllegalArgumentException("vertex index must be a nonnegative integer");
        }
        this.vertices = vertices;
        this.edges = 0;
        this.adjacent = (Bag<FlowEdge>[]) new Bag[vertices];
        for (int v = 0; v < vertices; v++) {
            adjacent[v] = new Bag<>();
        }
    }

    public int vertices() {
        return vertices;
    }

    public void addEdge(FlowEdge edge) {
        validateVertex(edge.from());
        validateVertex(edge.to());
        adjacent[edge.from()].add(edge);
        adjacent[edge.to()].add(edge);
        edges++;
    }

    public Iterable<FlowEdge> adjacent(int vertex) {
        validateVertex(vertex);
        return adjacent[vertex];
    }

    public Iterable<FlowEdge> edges() {
        Bag<FlowEdge> list = new Bag<>();
        for (int v = 0; v < vertices; v++) {
            for (FlowEdge e : adjacent(v)) {
                if (e.to() != v) {
                    list.add(e);
                }
            }
        }
        return list;
    }

    private void validateVertex(int v) {
        if (v < 0 || v >= vertices) {
            throw new IllegalArgumentException("vertex is not between 0 and " + (vertices - 1));
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(vertices + " vertices, " + edges + " edges " + NEWLINE);
        for (int v = 0; v < vertices; v++) {
            builder.append(v + ": ");
            for (FlowEdge edge : adjacent[v]) {
                if (edge.to() != v) {
                    builder.append(edge + " ");
                }
            }
            builder.append(NEWLINE);
        }
        return builder.toString();
    }

    public static void main(String[] args) {
        FlowNetwork network = FlowUtils.initFlowNetwork();
        System.out.println(network);
    }
}
