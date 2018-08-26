/**
 * <p>Title: FordFulkerson</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/8/26</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.flow;

/**
 * <p></p>
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

    public FordFulkerson(int vertices) {
        this.vertices = vertices;
    }
}
