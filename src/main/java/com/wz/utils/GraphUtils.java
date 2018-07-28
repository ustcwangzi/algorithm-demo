/**
 * <p>Title: GraphUtils</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/7/22</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.utils;

import com.wz.graph.directed.Digraph;
import com.wz.graph.undirected.Graph;

/**
 * <p>图工具类</p>
 *
 * @author wangzi
 */
public class GraphUtils {

    public static Graph initGraph(){
        Graph graph = new Graph(7);
        graph.addEdge(0,1);
        graph.addEdge(0,2);
        graph.addEdge(0,5);
        graph.addEdge(1,2);
        graph.addEdge(2,3);
        graph.addEdge(2,4);
        graph.addEdge(3,4);
        graph.addEdge(3,5);
        return graph;
    }

    public static Digraph initDigraph(){
        Digraph digraph = new Digraph(7);
        digraph.addEdge(0,1);
        digraph.addEdge(0,2);
        digraph.addEdge(0,5);
        digraph.addEdge(1,2);
        digraph.addEdge(2,3);
        digraph.addEdge(2,4);
        digraph.addEdge(3,4);
        digraph.addEdge(3,5);
        digraph.addEdge(6,5);
        return digraph;
    }
}
