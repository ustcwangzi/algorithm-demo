/**
 * <p>Title: GraphUtils</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/7/22</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.utils;

import com.wz.graph.directed.Digraph;
import com.wz.graph.shortestpath.DirectedEdge;
import com.wz.graph.shortestpath.WeightedDigraph;
import com.wz.graph.undirected.Graph;
import com.wz.graph.mst.Edge;
import com.wz.graph.mst.WeightedGraph;

/**
 * <p>图工具类</p>
 *
 * @author wangzi
 */
public class GraphUtils {

    public static Graph initGraph() {
        Graph graph = new Graph(7);
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(0, 5);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(2, 4);
        graph.addEdge(3, 4);
        graph.addEdge(3, 5);
        return graph;
    }

    public static Digraph initCycleDigraph() {
        Digraph digraph = new Digraph(13);
        digraph.addEdge(0, 1);
        digraph.addEdge(0, 5);
        digraph.addEdge(2, 0);
        digraph.addEdge(2, 3);
        digraph.addEdge(3, 2);
        digraph.addEdge(3, 5);
        digraph.addEdge(4, 2);
        digraph.addEdge(4, 3);
        digraph.addEdge(5, 4);
        digraph.addEdge(6, 0);
        digraph.addEdge(6, 4);
        digraph.addEdge(6, 9);
        digraph.addEdge(7, 6);
        digraph.addEdge(7, 8);
        digraph.addEdge(8, 7);
        digraph.addEdge(8, 9);
        digraph.addEdge(9, 10);
        digraph.addEdge(9, 11);
        digraph.addEdge(10, 12);
        digraph.addEdge(11, 4);
        digraph.addEdge(11, 12);
        digraph.addEdge(12, 9);
        return digraph;
    }

    public static Digraph initDigraph() {
        Digraph digraph = new Digraph(13);
        digraph.addEdge(0, 1);
        digraph.addEdge(0, 5);
        digraph.addEdge(0, 6);
        digraph.addEdge(2, 0);
        digraph.addEdge(2, 3);
        digraph.addEdge(3, 5);
        digraph.addEdge(5, 4);
        digraph.addEdge(6, 4);
        digraph.addEdge(6, 9);
        digraph.addEdge(7, 6);
        digraph.addEdge(8, 7);
        digraph.addEdge(9, 10);
        digraph.addEdge(9, 11);
        digraph.addEdge(9, 12);
        digraph.addEdge(11, 12);
        return digraph;
    }

    public static WeightedGraph initWeightedGraph() {
        WeightedGraph weightedGraph = new WeightedGraph(8);
        weightedGraph.addEdge(new Edge(0, 2, 26));
        weightedGraph.addEdge(new Edge(0, 4, 38));
        weightedGraph.addEdge(new Edge(0, 6, 58));
        weightedGraph.addEdge(new Edge(0, 7, 16));
        weightedGraph.addEdge(new Edge(1, 2, 36));
        weightedGraph.addEdge(new Edge(1, 3, 29));
        weightedGraph.addEdge(new Edge(1, 5, 32));
        weightedGraph.addEdge(new Edge(1, 7, 19));
        weightedGraph.addEdge(new Edge(2, 3, 17));
        weightedGraph.addEdge(new Edge(2, 6, 40));
        weightedGraph.addEdge(new Edge(2, 7, 34));
        weightedGraph.addEdge(new Edge(3, 6, 52));
        weightedGraph.addEdge(new Edge(4, 5, 35));
        weightedGraph.addEdge(new Edge(4, 6, 93));
        weightedGraph.addEdge(new Edge(4, 7, 37));
        weightedGraph.addEdge(new Edge(5, 7, 28));
        return weightedGraph;
    }

    public static WeightedDigraph initWeightedDigraph() {
        WeightedDigraph digraph = new WeightedDigraph(8);
        digraph.addEdge(new DirectedEdge(0, 2, 26));
        digraph.addEdge(new DirectedEdge(0, 4, 38));
        digraph.addEdge(new DirectedEdge(1, 3, 29));
        digraph.addEdge(new DirectedEdge(2, 7, 34));
        digraph.addEdge(new DirectedEdge(3, 6, 52));
        digraph.addEdge(new DirectedEdge(4, 5, 35));
        digraph.addEdge(new DirectedEdge(4, 7, 37));
        digraph.addEdge(new DirectedEdge(5, 1, 32));
        digraph.addEdge(new DirectedEdge(5, 4, 35));
        digraph.addEdge(new DirectedEdge(5, 7, 28));
        digraph.addEdge(new DirectedEdge(6, 0, 58));
        digraph.addEdge(new DirectedEdge(6, 2, 40));
        digraph.addEdge(new DirectedEdge(6, 4, 93));
        digraph.addEdge(new DirectedEdge(7, 3, 39));
        digraph.addEdge(new DirectedEdge(7, 5, 28));
        return digraph;
    }

    public static WeightedDigraph initWeightedNegativeDigraph() {
        WeightedDigraph digraph = new WeightedDigraph(8);
        digraph.addEdge(new DirectedEdge(0, 2, 26));
        digraph.addEdge(new DirectedEdge(0, 4, 38));
        digraph.addEdge(new DirectedEdge(1, 3, 29));
        digraph.addEdge(new DirectedEdge(2, 7, 34));
        digraph.addEdge(new DirectedEdge(3, 6, 52));
        digraph.addEdge(new DirectedEdge(4, 5, 35));
        digraph.addEdge(new DirectedEdge(4, 7, 37));
        digraph.addEdge(new DirectedEdge(5, 1, 32));
        digraph.addEdge(new DirectedEdge(5, 4, 35));
        digraph.addEdge(new DirectedEdge(5, 7, 28));
        digraph.addEdge(new DirectedEdge(6, 0, -140));
        digraph.addEdge(new DirectedEdge(6, 2, -120));
        digraph.addEdge(new DirectedEdge(6, 4, -125));
        digraph.addEdge(new DirectedEdge(7, 3, 39));
        digraph.addEdge(new DirectedEdge(7, 5, 28));
        return digraph;
    }
}
