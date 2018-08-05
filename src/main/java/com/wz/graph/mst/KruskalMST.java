/**
 * <p>Title: KruskalMST</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/8/5</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.graph.mst;

import com.wz.sort.MinPriorityQueue;

/**
 * <p>最小生成树Kruskal算法</p>
 *
 * @author wangzi
 */
public class KruskalMST {
    /**
     * 最小生成树总权重
     */
    private double weight;
    /**
     * 最小生成树中的边
     */
    private MinPriorityQueue<Edge> pQueue;

    public KruskalMST(EdgeWeightedGraph graph) {
        this.pQueue = new MinPriorityQueue<>();
        for (Edge edge : graph.edges()){
            pQueue.insert(edge);
        }


    }
}
