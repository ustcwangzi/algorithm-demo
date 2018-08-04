/**
 * <p>Title: PrimMST</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/8/4</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.graph.mst;

import com.wz.utils.GraphUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Prim算法的即时实现</p>
 * <p>
 *     要优化LazyPrimMST，可从优先队列中删除失效的边，但其实可以删除更多的边
 *     关键在于，有意义的边只是连接树顶点和非树顶点中权重最小的边
 *     即，不需要在优先队列中保存所有从w到树顶点的边，而只需要保存其中权重最小的那条
 *     在将v添加到树中后检查是否需要更新这条权重最小的边(因为v-w的权重可能更小)
 *     换句话说，只需要在优先队列中保存每个非树顶点w的一条边：它与树中顶点连接起来权重最小
 * </p>
 * <p>
 *     Prim算法的即时实现计算一个含有V个顶点和E条边的联通加权无向图的最小生成树
 *     所需空间与V成正比，所需时间与ElogV成正比
 * </p>
 *
 * @author wangzi
 */
public class PrimMST {
    /**
     * 到达某一顶点的最短路径
     */
    private Edge[] edgeTo;
    /**
     * 到达某一顶点的最小权重
     */
    private double[] distTo;
    /**
     * 顶点是否在最小生成树中
     */
    private boolean[] marked;
    /**
     * 非树顶点与树中顶点连接起来的最小权重
     */
    private Map<Integer, Double> map;

    public PrimMST(EdgeWeightedGraph graph) {
        this.edgeTo = new Edge[graph.vertices()];
        this.distTo = new double[graph.vertices()];
        this.marked = new boolean[graph.vertices()];
        this.map = new HashMap<>();
        for (int v = 0; v < graph.vertices(); v++) {
            this.distTo[v] = Double.MAX_VALUE;
        }
        for (int v = 0; v < graph.vertices(); v++) {
            if (!marked[v]) {
                prim(graph, v);
            }
        }
    }

    private void prim(EdgeWeightedGraph graph, int source) {
        distTo[source] = 0;
        map.put(source, distTo[source]);
        while (!map.isEmpty()) {
            int v = delMin(map);
            scan(graph, v);
        }
    }

    private void scan(EdgeWeightedGraph graph, int v) {
        marked[v] = true;
        for (Edge edge : graph.adj(v)) {
            int w = edge.other(v);
            if (marked[w]) {
                continue;
            }
            if (edge.weight() < distTo[w]) {
                distTo[w] = edge.weight();
                edgeTo[w] = edge;
                map.put(w, distTo[w]);
            }
        }
    }

    private int delMin(Map<Integer, Double> map) {
        int minKey = Integer.MAX_VALUE;
        double minValue = Double.MAX_VALUE;
        for (int key : map.keySet()) {
            if (map.get(key) < minValue) {
                minKey = key;
                minValue = map.get(key);
            }
        }
        map.remove(minKey);
        return minKey;
    }

    public Iterable<Edge> edges() {
        List<Edge> mst = new ArrayList<>();
        for (int v = 0; v < edgeTo.length; v++) {
            Edge e = edgeTo[v];
            if (e != null) {
                mst.add(e);
            }
        }
        return mst;
    }

    public double weight() {
        double weight = 0;
        for (Edge e : edges()) {
            weight += e.weight();
        }
        return weight;
    }

    public static void main(String[] args) {
        EdgeWeightedGraph graph = GraphUtils.initEdgeWeightedGraph();
        PrimMST mst = new PrimMST(graph);
        System.out.println(mst.weight());
        for (Edge edge : mst.edges()) {
            System.out.println(edge);
        }
    }
}
