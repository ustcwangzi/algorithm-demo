/**
 * <p>Title: KruskalMST</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/8/5</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.graph.undirectedmst;

import com.wz.sort.MinPriorityQueue;
import com.wz.unionfind.WeightedQuickUnionFind;
import com.wz.utils.GraphUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>最小生成树Kruskal算法</p>
 * <p>
 *     每次寻找权值最小并且不会和已构建的树产生环的边
 * </p>
 * <p>
 *     Kruskal算法计算一个含有V个顶点和E条边的联通加权无向图的最小生成树
 *     所需空间与E成正比，所需时间与ElogE成正比
 * </p>
 *
 * @author wangzi
 */
public class KruskalMST {
    /**
     * 最小生成树总权重
     */
    private double weight;
    /**
     * 最小生成树
     */
    private List<Edge> mst;

    public KruskalMST(EdgeWeightedGraph graph) {
        this.mst = new ArrayList<>();
        MinPriorityQueue<Edge> pQueue = new MinPriorityQueue<>();
        for (Edge edge : graph.edges()) {
            pQueue.insert(edge);
        }

        WeightedQuickUnionFind unionFind = new WeightedQuickUnionFind(graph.vertices());
        while (!pQueue.isEmpty() && mst.size() < graph.vertices() - 1) {
            Edge edge = pQueue.delMin();
            int v = edge.either();
            int w = edge.other(v);
            if (!unionFind.connected(v, w)) {
                unionFind.union(v, w);
                mst.add(edge);
                weight += edge.weight();
            }
        }
    }

    public Iterable<Edge> edges() {
        return mst;
    }

    public double weight() {
        return weight;
    }

    public static void main(String[] args) {
        EdgeWeightedGraph graph = GraphUtils.initEdgeWeightedGraph();
        KruskalMST mst = new KruskalMST(graph);
        System.out.println(mst.weight());
        for (Edge edge : mst.edges()) {
            System.out.println(edge);
        }
    }
}
