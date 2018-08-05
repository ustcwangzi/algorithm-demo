/**
 * <p>Title: LazyPrimMST</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/8/4</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.graph.undirectedmst;

import com.wz.sort.MinPriorityQueue;
import com.wz.utils.GraphUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>最小生成树Prim算法的延时实现</p>
 * <p>
 *     每当向树中添加了一条边之后，也向树中添加了一个顶点
 *     要维护包含所有横切边的集合，就要将连接这个顶点和其他所有不在树中的顶点的边加入优先队列
 *     同时，连接新加入树中的顶点和其他已经在树中的顶点的所有边都失效了
 *     延时实现中，并不立即删除失效的边，而是先将其留在优先队列中，等要删除的时候再去检查
 * </p>
 * <p>
 *     Prim算法的延时实现计算一个含有V个顶点和E条边的联通加权无向图的最小生成树
 *     所需空间与E成正比，所需时间与ElogE成正比
 * </p>
 *
 * @author wangzi
 */
public class LazyPrimMST {
    /**
     * 最小生成树总权重
     */
    private double weight;
    /**
     * 最小生成树
     */
    private List<Edge> mst;
    /**
     * 顶点是否在最小生成树中
     */
    private boolean[] marked;
    /**
     * 优先队列存储顶点在树中的边
     */
    private MinPriorityQueue<Edge> pQueue;

    public LazyPrimMST(WeightedGraph graph) {
        this.mst = new ArrayList<>();
        this.pQueue = new MinPriorityQueue<>();
        this.marked = new boolean[graph.vertices()];
        for (int v = 0; v < graph.vertices(); v++) {
            if (!marked[v]) {
                prim(graph, v);
            }
        }
    }

    private void prim(WeightedGraph graph, int source) {
        scan(graph, source);
        while (!pQueue.isEmpty()) {
            // 取出权重最小的边
            Edge minEdge = pQueue.delMin();
            int v = minEdge.either(), w = minEdge.other(v);
            assert marked[v] || marked[w];
            // v和w都已存在树中
            if (marked[v] && marked[w]) {
                continue;
            }
            mst.add(minEdge);
            weight += minEdge.weight();
            if (!marked[v]) {
                scan(graph, v);
            }
            if (!marked[w]) {
                scan(graph, w);
            }
        }
    }

    /**
     * 把其中一个顶点是v的边全部加入优先队列
     */
    private void scan(WeightedGraph graph, int v) {
        assert !marked[v];
        marked[v] = true;
        for (Edge edge : graph.adj(v)) {
            if (!marked[edge.other(v)]) {
                pQueue.insert(edge);
            }
        }
    }

    public Iterable<Edge> edges() {
        return mst;
    }

    public static void main(String[] args) {
        WeightedGraph graph = GraphUtils.initWeightedGraph();
        LazyPrimMST mst = new LazyPrimMST(graph);
        System.out.println(mst.weight);
        for (Edge edge : mst.edges()) {
            System.out.println(edge);
        }
    }
}
