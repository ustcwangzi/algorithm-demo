/**
 * <p>Title: Topological</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/7/29</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.graph.directed;

/**
 * <p>拓扑排序</p>
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
        if (!finder.hasCycle()){

        }
    }
}
