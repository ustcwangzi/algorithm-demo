/**
 * <p>Title: Graph</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/7/8</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.graph.undirected;

import com.wz.graph.Bag;
import com.wz.utils.GraphUtils;

/**
 * <p>使用邻接表表示无向图</p>
 * <p>
 *     使用一个以顶点为索引的列表数组，其中每个元素都是和该顶点相邻的顶点列表
 *     它将每个顶点的所有相邻顶点都保存在该顶点对应的元素所指向的一张链表中
 * </p>
 *
 * @author wangzi
 */
public class Graph {
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
    private Bag<Integer>[] adjacent;

    public Graph(int vertices) {
        if (vertices < 0) {
            throw new IllegalArgumentException("Number of vertices must be positive");
        }
        this.vertices = vertices;
        this.edges = 0;
        this.adjacent = (Bag<Integer>[]) new Bag[vertices];
        for (int v = 0; v < vertices; v++) {
            adjacent[v] = new Bag<>();
        }
    }

    public int vertices() {
        return vertices;
    }

    public int edges() {
        return edges;
    }

    private void validateVertex(int v) {
        if (v < 0 || v >= vertices) {
            throw new IllegalArgumentException("vertex must between 0 and " + (vertices - 1));
        }
    }

    /**
     * 增加边v-w
     */
    public void addEdge(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        edges++;
        adjacent[v].add(w);
        adjacent[w].add(v);
    }

    /**
     * 获取和v相邻的顶点列表
     */
    public Iterable<Integer> adj(int v) {
        validateVertex(v);
        return adjacent[v];
    }

    public int degree(int v) {
        validateVertex(v);
        return adjacent[v].size();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(vertices + " vertices, " + edges + " edges " + NEWLINE);
        for (int v = 0; v < vertices; v++) {
            builder.append(v + ": ");
            for (int w : adjacent[v]) {
                builder.append(w + " ");
            }
            builder.append(NEWLINE);
        }
        return builder.toString();
    }

    public static void main(String[] args) {
        Graph graph = GraphUtils.initGraph();
        System.out.println(graph);
    }
}
