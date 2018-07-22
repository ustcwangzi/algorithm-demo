/**
 * <p>Title: DepthFirstPaths</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/7/8</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.graph;


import com.wz.utils.GraphUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>使用深度优先搜索查找图中的路径</p>
 * <p>
 *     edgeTo数组可以找到每个与source联通的顶点回到source的路径
 *     由边v-w第一次访问w时，将edgeTo[w]设为v来记住这条路径
 *     即v-w是从source到w的路径上最后一条已知的边
 *     这样，搜索的结果是一棵以source为根节点的树，使用pathTo遍历整棵树
 * </p>
 *
 * <p>使用深度优先搜索得到从给定起点到任意顶点的路径所需时间与路径长度成正比</p>
 *
 * @author wangzi
 */
public class DepthFirstPaths {
    /** 标记是否遍历过 */
    private boolean[] marked;
    /** 从起点到一个顶点的已知路径上的最后一个顶点 */
    private int[] edgeTo;
    /** 起点 */
    private final int source;

    public DepthFirstPaths(Graph graph, int source) {
        this.marked = new boolean[graph.V()];
        this.edgeTo = new int[graph.V()];
        this.source = source;
        validateVertex(source);
        dfs(graph, source);
    }

    private void dfs(Graph graph, int v){
        marked[v] = true;
        for (int w : graph.adj(v)){
            if (!marked[w]){
                edgeTo[w] = v;
                dfs(graph, w);
            }
        }
    }

    public boolean hasPathTo(int v){
        validateVertex(v);
        return marked[v];
    }

    /**
     * 找到source到它联通的所有顶点的路径
     */
    public Iterable<Integer> pathTo(int v){
        validateVertex(v);
        if (!hasPathTo(v)){
            return null;
        }

        List<Integer> path = new ArrayList<>();
        for (int w = v; w != source; w = edgeTo[w]){
            path.add(w);
        }
        path.add(source);
        Collections.reverse(path);
        return path;
    }

    private void validateVertex(int v) {
        int length = marked.length;
        if (v < 0 || v >= length) {
            throw new IllegalArgumentException("vertex must between 0 and " + (length-1));
        }
    }

    public static void main(String[] args) {
        Graph graph = GraphUtils.initGraph();
        int source = 0;
        DepthFirstPaths depthFirstPaths = new DepthFirstPaths(graph, source);

        for (int v = 0; v < graph.V(); v++){
            if (depthFirstPaths.hasPathTo(v)){
                System.out.printf("%d to %d: ", source, v);
                for (int w : depthFirstPaths.pathTo(v)){
                    if (w == source){
                        System.out.print(w);
                    }else {
                        System.out.print("-" + w);
                    }
                }
                System.out.println();
            }else {
                System.out.printf("%d to %d not connected\n", source, v);
            }
        }
    }
}
