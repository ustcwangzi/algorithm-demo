/**
 * <p>Title: Topological</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/7/29</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.graph.directed;

import com.wz.utils.GraphUtils;

/**
 * <p>拓扑排序</p>
 * <p>
 *     一个有向无环图的拓扑排序就是所有顶点的逆后序排列
 * </p>
 *
 * @author wangzi
 */
public class Topological {
    /**
     * 顶点的拓扑顺序
     */
    private Iterable<Integer> order;
    /**
     * 顶点在拓扑顺序中的位置
     */
    private int[] rank;

    public Topological(Digraph digraph) {
        DirectedCycle finder = new DirectedCycle(digraph);
        if (!finder.hasCycle()) {
            DepthFirstOrder dfs = new DepthFirstOrder(digraph);
            order = dfs.reversePost();
            rank = new int[digraph.vertices()];
            int i = 0;
            for (int v : order) {
                rank[v] = i++;
            }
        }
    }

    public Iterable<Integer> order() {
        return order;
    }

    public boolean hasOrder() {
        return order != null;
    }

    public int rank(int v) {
        validateVertex(v);
        if (hasOrder()) {
            return rank[v];
        }
        return -1;
    }

    private void validateVertex(int v) {
        int length = rank.length;
        if (v < 0 || v >= length) {
            throw new IllegalArgumentException("vertex must between 0 and " + (length - 1));
        }
    }

    public static void main(String[] args) {
        Digraph digraph = GraphUtils.initDigraph();
        Topological topological = new Topological(digraph);
        for (int v : topological.order()) {
            System.out.print(v + " ");
        }
    }
}
