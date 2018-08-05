/**
 * <p>Title: BellmanFordSP</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/8/5</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.graph.sp;

import com.wz.utils.GraphUtils;

import java.util.*;

/**
 * <p>BellmanFord算法计算最短路径</p>
 * <p>
 *     Dijkstra算法不能处理权重为负的情况，因为Dijkstra算法是贪心算法
 *     它必须有把握：当前确定的路径都是到各顶点的最短路径，并能根据这些路径确定下一步的最短路径
 *     而如果后面有负权值，它并不一定能确定当前已知路径中就一定有最短路径
 *     BellmanFord算法能够处理权重为负的情况，但不能存在负权重环
 *     每次被成功relax的边所指向的顶点加入队列中，以便下一轮的处理
 * </p>
 * <p>
 *     Dijkstra算法计算一个含有V个顶点和E条边的加权有向图的最短路径
 *     所需空间与V成正比，所需时间与EV成正比
 * </p>
 *
 * @author wangzi
 */
public class BellmanFordSP {
    /**
     * 从起点到一个顶点的已知路径上的最后一条边
     */
    private DirectedEdge[] edgeTo;
    /**
     * 从起点到一个顶点的已知最短路径权重
     */
    private double[] distTo;
    /**
     * 顶点是否存在队列中
     */
    private boolean[] onQueue;
    /**
     * 正在被处理的顶点
     */
    private Queue<Integer> queue;
    /**
     * relax()的调用次数
     */
    private int cost;
    /**
     * 是否存在负权重环
     */
    private Iterable<DirectedEdge> cycle;

    public BellmanFordSP(WeightedDigraph digraph, int source) {
        this.distTo = new double[digraph.vertices()];
        this.edgeTo = new DirectedEdge[digraph.vertices()];
        this.onQueue = new boolean[digraph.vertices()];
        this.queue = new LinkedList<>();
        for (int v = 0; v < digraph.vertices(); v++) {
            distTo[v] = Double.POSITIVE_INFINITY;
        }

        distTo[source] = 0;
        queue.add(source);
        onQueue[source] = true;
        while (!queue.isEmpty() && !hasNegativeCycle()) {
            int v = queue.poll();
            onQueue[v] = false;
            relax(digraph, v);
        }
    }

    private void relax(WeightedDigraph digraph, int v) {
        for (DirectedEdge edge : digraph.adj(v)) {
            int w = edge.to();
            if (distTo[w] > distTo[v] + edge.weight()) {
                distTo[w] = distTo[v] + edge.weight();
                edgeTo[w] = edge;
                if (!onQueue[w]) {
                    queue.add(w);
                    onQueue[w] = true;
                }
            }
            if (cost++ % digraph.vertices() == 0) {
                findNegativeCycle();
                if (hasNegativeCycle()) {
                    return;
                }
            }
        }
    }

    public boolean hasNegativeCycle() {
        return cycle != null;
    }

    public Iterable<DirectedEdge> negativeCycle() {
        return cycle;
    }

    private void findNegativeCycle() {
        int length = edgeTo.length;
        WeightedDigraph digraph = new WeightedDigraph(length);
        for (int v = 0; v < length; v++) {
            if (edgeTo[v] != null) {
                digraph.addEdge(edgeTo[v]);
            }
        }

        WeightedDirectedCycle finder = new WeightedDirectedCycle(digraph);
        cycle = finder.cycle();
    }

    public double distTo(int v) {
        validateVertex(v);
        if (hasNegativeCycle()) {
            throw new UnsupportedOperationException("Negative cost cycle exists");
        }
        return distTo[v];
    }

    public boolean hasPathTo(int v) {
        validateVertex(v);
        return distTo[v] < Double.POSITIVE_INFINITY;
    }

    public Iterable<DirectedEdge> pathTo(int v) {
        validateVertex(v);
        if (hasNegativeCycle()) {
            throw new UnsupportedOperationException("Negative cost cycle exists");
        }
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

    private void validateVertex(int v) {
        int length = distTo.length;
        if (v < 0 || v >= length) {
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (length - 1));
        }
    }

    public static void main(String[] args) {
        WeightedDigraph digraph = GraphUtils.initWeightedNegativeDigraph();
        int source = 0;
        BellmanFordSP sp = new BellmanFordSP(digraph, source);

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
