/**
 * <p>Title: FordFulkerson</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/8/26</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.graph.flow;


import com.wz.utils.GraphUtils;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * <p>Ford-Fulkerson最大流量算法</p>
 * <p>
 *     剩余网络：
 *          如果从v到w的边e为空(即flow为0)，剩余网络中就只有一条容量为capacity的边v->w与之对应；
 *          如果该边饱和(即flow等于capacity)，剩余网络就只有一条容量为flow的边w->v与之对应；
 *          如果既不为空也不饱和，那么剩余网络中将同时含有相应容量的v->w和w->v
 *     剩余网络中，与流量对应的边的方向和流量本身相反，正向边表示的是剩余的容量，逆向边表示的是实际流量
 *     寻找最大流量就是处理剩余网络，检查所有剩余的容量并修正流量配置
 *     具体过程：
 *          在剩余网络中寻找最短增广路径，找出路径上的瓶颈容量并增大该路径上的流量，
 *          重复以上过程直至不再存在从起点到终点的增广路径为止
 * </p>
 *
 * @author wangzi
 */
public class FordFulkerson {
    /**
     * 顶点数量
     */
    private final int vertices;
    /**
     * 剩余网络中是否存在从s到v的路径
     */
    private boolean[] marked;
    /**
     * 从s到v的最短路径(最大流量)上的最后一条边
     */
    private FlowEdge[] edgeTo;
    /**
     * 当前最大流量
     */
    private double value;

    public FordFulkerson(FlowNetwork network, int s, int t) {
        this.vertices = network.vertices();
        validateVertex(s);
        validateVertex(t);
        if (s == t) {
            throw new IllegalArgumentException("Source equals sink");
        }

        value = excess(network, t);
        // 利用所有存在的增广路径，找出从s到t的最大流量配置
        while (hasAugmentingPath(network, s, t)) {
            double bottle = Double.POSITIVE_INFINITY;
            // 计算当前的瓶颈容量
            for (int v = t; v != s; v = edgeTo[v].other(v)) {
                bottle = Math.min(bottle, edgeTo[v].residualCapacityTo(v));
            }
            // 增加流量
            for (int v = t; v != s; v = edgeTo[v].other(v)) {
                edgeTo[v].addResidualFlowTo(v, bottle);
            }
            value += bottle;
        }
    }

    public double value() {
        return value;
    }

    /**
     * 已存在的流量
     */
    private double excess(FlowNetwork network, int v) {
        double excess = 0;
        for (FlowEdge edge : network.adjacent(v)) {
            if (v == edge.from()) {
                excess -= edge.flow();
            } else {
                excess += edge.flow();
            }
        }
        return excess;
    }

    /**
     * 在剩余网络中通过广度优先搜索寻找增广路径
     */
    private boolean hasAugmentingPath(FlowNetwork network, int s, int t) {
        this.edgeTo = new FlowEdge[network.vertices()];
        this.marked = new boolean[network.vertices()];
        Queue<Integer> queue = new ArrayDeque<>();

        queue.add(s);
        marked[s] = true;
        while (!queue.isEmpty() && !marked[t]) {
            int v = queue.poll();
            for (FlowEdge edge : network.adjacent(v)) {
                int w = edge.other(v);
                if (edge.residualCapacityTo(w) > 0 && !marked[w]) {
                    edgeTo[w] = edge;
                    marked[w] = true;
                    queue.add(w);
                }
            }
        }
        return marked[t];
    }

    private void validateVertex(int v) {
        if (v < 0 || v >= vertices) {
            throw new IllegalArgumentException("vertex is not between 0 and " + (vertices - 1));
        }
    }

    public static void main(String[] args) {
        FlowNetwork network = GraphUtils.initFlowNetwork();
        int s = 0, t = network.vertices() - 1;
        FordFulkerson maxFlow = new FordFulkerson(network, s, t);
        System.out.println("Max flow from " + s + " to " + t);
        for (int v = 0; v < network.vertices(); v++) {
            for (FlowEdge edge : network.adjacent(v)) {
                if (v == edge.from() && edge.flow() > 0) {
                    System.out.println(edge);
                }
            }
        }
        System.out.println("Max flow is " + maxFlow.value());
    }
}
