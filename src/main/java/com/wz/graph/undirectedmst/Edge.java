/**
 * <p>Title: Edge</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/8/4</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.graph.undirectedmst;

/**
 * <p>带权重的边</p>
 *
 * @author wangzi
 */
public class Edge implements Comparable<Edge> {
    /**
     * 一个顶点
     */
    private final int v;
    /**
     * 另一个顶点
     */
    private final int w;
    /**
     * 边的权重
     */
    private final double weight;

    public Edge(int v, int w, double weight) {
        if (v < 0) {
            throw new IllegalArgumentException("vertex index must be a nonnegative integer");
        }
        if (w < 0) {
            throw new IllegalArgumentException("vertex index must be a nonnegative integer");
        }
        if (Double.isNaN(weight)) {
            throw new IllegalArgumentException("Weight is NaN");
        }
        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    public double weight() {
        return weight;
    }

    /**
     * 获取任意一个顶点
     */
    public int either() {
        return v;
    }

    /**
     * 获取另一个顶点
     */
    public int other(int vertex) {
        if (vertex == v) {
            return w;
        } else if (vertex == w) {
            return v;
        }
        throw new IllegalArgumentException("Illegal endpoint");
    }

    @Override
    public int compareTo(Edge other) {
        return Double.compare(this.weight, other.weight);
    }

    @Override
    public String toString() {
        return String.format("%d-%d [%.2f]", v, w, weight);
    }

    public static void main(String[] args) {
        Edge edge = new Edge(12, 34, 9.88);
        System.out.println(edge);
    }
}
