/**
 * <p>Title: TransitiveClosure</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/7/29</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.graph.directed;

import com.wz.utils.GraphUtils;

/**
 * <p>顶点对的可达性</p>
 *
 * @author wangzi
 */
public class TransitiveClosure {
    /**
     * 顶点可达
     */
    private DirectedDFS[] tc;

    public TransitiveClosure(Digraph digraph) {
        this.tc = new DirectedDFS[digraph.vertices()];
        for (int v = 0; v < digraph.vertices(); v++) {
            tc[v] = new DirectedDFS(digraph, v);
        }
    }

    public boolean reachable(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        return tc[v].marked(w);
    }

    private void validateVertex(int v) {
        int length = tc.length;
        if (v < 0 || v >= length) {
            throw new IllegalArgumentException("vertex must between 0 and " + (length - 1));
        }
    }

    public static void main(String[] args) {
        Digraph digraph = GraphUtils.initCycleDigraph();
        TransitiveClosure closure = new TransitiveClosure(digraph);
        System.out.print("   ");
        for (int v = 0; v < digraph.vertices(); v++) {
            System.out.printf("%3d", v);
        }
        System.out.println();

        for (int v = 0; v < digraph.vertices(); v++) {
            System.out.printf("%3d", v);
            for (int w = 0; w < digraph.vertices(); w++) {
                if (closure.reachable(v, w)) {
                    System.out.print("  T");
                } else {
                    System.out.print("  F");
                }
            }
            System.out.println();
        }
    }
}
