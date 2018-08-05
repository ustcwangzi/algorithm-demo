/**
 * <p>Title: DijkstraSP</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/8/5</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.graph.sp;

import com.wz.utils.GraphUtils;

import java.util.*;

/**
 * <p>Dijkstra算法计算最短路径</p>
 * <p>
 *     每向树中加入一个顶点时，将与之相连的其他顶点加入优先队列
 *     每次从优先队列中取出权重最小的边
 *     即：每次添加的都是离树最近的非树节点
 * </p>
 * <p>
 *     Dijkstra算法计算一个含有V个顶点和E条边的加权有向图的最短路径
 *     所需空间与V成正比，所需时间与ElogV成正比
 * </p>
 *
 * @author wangzi
 */
public class DijkstraSP {
    /**
     * 从起点到一个顶点的已知路径上的最后一条边
     */
    private DirectedEdge[] edgeTo;
    /**
     * 从起点到一个顶点的已知路径权重
     */
    private double[] distTo;
    /**
     * 非树顶点与树中顶点连接起来的最小权重
     */
    private Map<Integer, Double> map;

    public DijkstraSP(WeightedDigraph digraph, int source) {
        for (DirectedEdge e : digraph.edges()) {
            if (e.weight() < 0) {
                throw new IllegalArgumentException("edge " + e + " has negative weight");
            }
        }

        this.distTo = new double[digraph.vertices()];
        this.edgeTo = new DirectedEdge[digraph.vertices()];
        this.map = new HashMap<>();

        validateVertex(source);
        for (int v = 0; v < digraph.vertices(); v++) {
            distTo[v] = Double.POSITIVE_INFINITY;
        }
        distTo[source] = 0;

        map.put(source, distTo[source]);
        while (!map.isEmpty()) {
            int v = delMin(map);
            for (DirectedEdge edge : digraph.adj(v)) {
                relax(edge);
            }
        }
    }

    private void relax(DirectedEdge edge) {
        int v = edge.from(), w = edge.to();
        if (distTo[w] > distTo[v] + edge.weight()) {
            distTo[w] = distTo[v] + edge.weight();
            edgeTo[w] = edge;
            map.put(w, distTo[w]);
        }
    }

    public double distTo(int v) {
        validateVertex(v);
        return distTo[v];
    }

    public boolean hasPathTo(int v) {
        validateVertex(v);
        return distTo[v] < Double.POSITIVE_INFINITY;
    }

    public Iterable<DirectedEdge> pathTo(int v) {
        validateVertex(v);
        if (!hasPathTo(v)) {
            return null;
        }
        List<DirectedEdge> path = new ArrayList<>();
        for (DirectedEdge edge = edgeTo[v]; edge != null; edge = edgeTo[edge.from()]) {
            path.add(edge);
        }
        Collections.reverse(path);
        return path;
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

    private void validateVertex(int v) {
        int length = distTo.length;
        if (v < 0 || v >= length) {
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (length - 1));
        }
    }

    public static void main(String[] args) {
        WeightedDigraph digraph = GraphUtils.initWeightedDigraph();
        int source = 0;
        DijkstraSP sp = new DijkstraSP(digraph, source);

        for (int v = 0; v < digraph.vertices(); v++) {
            if (sp.hasPathTo(v)) {
                System.out.printf("%d to %d [%.2f]:  ", source, v, sp.distTo(v));
                for (DirectedEdge edge : sp.pathTo(v)) {
                    System.out.print(edge + "   ");
                }
                System.out.println();
            } else {
                System.out.printf("%d to %d not connected\n", source, v);
            }
        }
    }
}
