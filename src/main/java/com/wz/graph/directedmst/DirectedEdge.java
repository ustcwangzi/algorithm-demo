/**
 * <p>Title: DirectedEdge</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/8/5</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.graph.directedmst;

/**
 * <p>加权有向边</p>
 *
 * @author wangzi
 */
public class DirectedEdge implements Comparable<DirectedEdge> {
    /**
     * 起点
     */
    private final int from;
    /**
     * 终点
     */
    private final int to;
    /**
     * 权重
     */
    private final double weight;

    public DirectedEdge(int from, int to, double weight) {
        if (from < 0) {
            throw new IllegalArgumentException("vertex index must be a nonnegative integer");
        }
        if (to < 0) {
            throw new IllegalArgumentException("vertex index must be a nonnegative integer");
        }
        if (Double.isNaN(weight)) {
            throw new IllegalArgumentException("Weight is NaN");
        }
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    public int from() {
        return from;
    }

    public int to() {
        return to;
    }

    public double weight() {
        return weight;
    }

    @Override
    public String toString() {
        return String.format("%d->%d [%.2f]", from, to, weight);
    }

    @Override
    public int compareTo(DirectedEdge other) {
        return Double.compare(this.weight, other.weight);
    }

    public static void main(String[] args) {
        DirectedEdge edge = new DirectedEdge(12, 34, 98.88);
        System.out.println(edge);
    }
}
