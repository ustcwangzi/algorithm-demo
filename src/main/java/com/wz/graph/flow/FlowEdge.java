/**
 * <p>Title: FlowEdge</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/8/26</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.graph.flow;

/**
 * <p>流量网络中的边</p>
 *
 * @author wangzi
 */
public class FlowEdge {
    /**
     * 起点
     */
    private final int from;
    /**
     * 终点
     */
    private final int to;
    /**
     * 容量
     */
    private final double capacity;
    /**
     * 流量
     */
    private double flow;

    public FlowEdge(int from, int to, double capacity) {
        validateVertex(from);
        validateVertex(to);
        this.from = from;
        this.to = to;
        this.capacity = capacity;
        this.flow = 0;
        validateFlow(flow, capacity);
    }

    public FlowEdge(int from, int to, double capacity, double flow) {
        validateVertex(from);
        validateVertex(to);
        validateFlow(flow, capacity);
        this.from = from;
        this.to = to;
        this.capacity = capacity;
        this.flow = flow;
    }

    public int from() {
        return from;
    }

    public int to() {
        return to;
    }

    public double capacity() {
        return capacity;
    }

    public double flow() {
        return flow;
    }

    public int other(int vertex) {
        if (vertex == from) {
            return to;
        }
        if (vertex == to) {
            return from;
        }
        throw new IllegalArgumentException("invalid vertex");
    }

    /**
     * vertex的剩余容量
     */
    public double residualCapacityTo(int vertex) {
        // 逆向边(实际流量)
        if (vertex == from) {
            return flow;
        }
        // 正向边(剩余容量)
        if (vertex == to) {
            return capacity - flow;
        }
        throw new IllegalArgumentException("invalid vertex");
    }

    /**
     * 将vertex的流量增加delta
     */
    public void addResidualFlowTo(int vertex, double delta) {
        if (delta < 0) {
            throw new IllegalArgumentException("delta must be nonnegative");
        }
        if (vertex == from) {
            flow -= delta;
        } else if (vertex == to) {
            flow += delta;
        } else {
            throw new IllegalArgumentException("invalid vertex");
        }
        validateFlow(flow, capacity);
    }

    @Override
    public String toString() {
        return String.format("%d->%d [%.2f/%.2f]", from, to, flow, capacity);
    }

    private void validateVertex(int v) {
        if (v < 0) {
            throw new IllegalArgumentException("vertex index must be a nonnegative integer");
        }
    }

    private void validateFlow(double flow, double capacity) {
        if (flow < 0) {
            throw new IllegalArgumentException("flow must be nonnegative");
        }
        if (capacity < 0) {
            throw new IllegalArgumentException("capacity must be nonnegative");
        }
        if (flow > capacity) {
            throw new IllegalArgumentException("flow exceeds capacity");
        }
    }

    public static void main(String[] args) {
        FlowEdge edge = new FlowEdge(12, 34, 4.56);
        System.out.println(edge);
    }
}
