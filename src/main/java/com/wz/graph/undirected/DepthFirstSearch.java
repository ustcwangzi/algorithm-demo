/**
 * <p>Title: DepthFirstSearch</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/7/8</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.graph.undirected;

import com.wz.utils.GraphUtils;

/**
 * <p>深度优先搜索</p>
 * <p>
 *     要搜索一个图。只需要用一个递归方法遍历所有顶点，在访问其中一个顶点时：
 *     1、将它标记为已访问；
 *     2、递归的访问它的所有未被标记过的相邻顶点。
 *     如果图是联通的，所有顶点都会被遍历到
 * </p>
 * <p>深度优先搜索标记与起点联通的所有顶点所需时间和顶点的度数之和成正比</p>
 *
 * @author wangzi
 */
public class DepthFirstSearch {
    /** 标记是否遍历过 */
    private boolean[] marked;
    /** 遍历过的节点数量 */
    private int count;

    public DepthFirstSearch(Graph graph, int source) {
        marked = new boolean[graph.vertices()];
        validateVertex(source);
        dfs(graph, source);
    }

    private void dfs(Graph graph, int v){
        count++;
        marked[v] = true;
        for (int w : graph.adj(v)){
            if (!marked[w]){
                dfs(graph, w);
            }
        }
    }

    public boolean marked(int v){
        validateVertex(v);
        return marked[v];
    }

    public int count(){
        return count;
    }

    private void validateVertex(int v) {
        int V = marked.length;
        if (v < 0 || v >= V) {
            throw new IllegalArgumentException("vertex must between 0 and " + (V-1));
        }
    }

    public static void main(String[] args) {
        Graph graph = GraphUtils.initGraph();
        DepthFirstSearch search = new DepthFirstSearch(graph, 1);
        for (int v = 0; v < graph.vertices(); v++){
            if (search.marked(v)){
                System.out.print(v + " ");
            }
        }
        if (search.count != graph.vertices()){
            System.out.println("not connected");
        }else {
            System.out.println("connected");
        }
    }
}
