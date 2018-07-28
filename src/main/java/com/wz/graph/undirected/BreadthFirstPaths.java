/**
 * <p>Title: BreadthFirstPaths</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/7/22</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.graph.undirected;


import com.wz.utils.GraphUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>使用广度优先搜索查找图中的路径</p>
 * <p>
 *     深度优先搜索在单点最短路径上没什么作为，因为它遍历整个图的顺序和找到最短路径的目标没任何关系
 *     相比之下，广度优先搜索正是为了这个目标出现的
 *     要找到从source到v的最短路径，从source开始，在所有由一条边就可以到达的顶点中寻找v
 *     如果找不到就继续在与source距离两条边的所有顶点中寻找v，如此进行下去
 *     在深度优先搜索中，使用栈(递归)来支持搜索，从待搜索的路径中选择最晚遇到的那条
 *     在广度优先搜索中，需要按照与source的距离为顺序来遍历所有顶点，使用队列来代替栈即可，从待搜索的路径中选择最早遇到的那条
 *     具体实现：
 *     使用一个队列来保存所有已经被标记过但其邻接表还未被检查过的顶点
 *     先将其加入队列，然后重复以下步骤直到队列为空：
 *     1、取队列中的下一个顶点v并标记它
 *     2、将与v相邻的所有未被标记过的顶点加入队列
 *     和深度优先搜索一样，其结果也是一个数组edgeTo[]，也是一棵以source为根节点的树
 *     它表示了source到每个与source相通的顶点的最短路径
 * </p>
 *
 * <p>
 *     深度优先搜索和广度优先搜索的对比：
 *     两种搜索都是先将起点存到数据结构中，然后重复以下步骤直到数据结构被清空：
 *     1、取其中的下一个顶点v并标记它
 *     2、将v的所有相邻并且未被标记的顶点加入数据结构
 *     不同之处在于用数据结构中获取下一个顶点的规则：
 *     对于广度优先搜索来说，取的是最早加入的顶点
 *     对于深度优先搜索来说，取的是最晚加入的顶点
 * </p>
 *
 * <p>广度优先搜索所需时间在最坏情况下和V+E成正比</p>
 *
 * @author wangzi
 */
public class BreadthFirstPaths {
    private static final int INFINITY = Integer.MAX_VALUE;
    /** 标记是否遍历过 */
    private boolean[] marked;
    /** 从起点到一个顶点的已知路径上的最后一个顶点 */
    private int[] edgeTo;
    /** 从起点到一个顶点的已知路径长度 */
    private int[] distTo;
    /** 起点 */
    private final int source;

    public BreadthFirstPaths(Graph graph, int source) {
        this.marked = new boolean[graph.V()];
        this.edgeTo = new int[graph.V()];
        this.distTo = new int[graph.V()];
        this.source = source;
        bfs(graph);

        assert check(graph);
    }

    private void bfs(Graph graph){
        List<Integer> path = new ArrayList<>();
        for (int v = 0; v < graph.V(); v++){
            distTo[v] = INFINITY;
        }
        distTo[source] = 0;
        marked[source] = true;
        path.add(source);

        for (int i = 0; i < path.size(); i++) {
            int v = path.get(i);
            for (int w : graph.adj(v)){
                if (!marked[w]){
                    edgeTo[w] = v;
                    distTo[w] = distTo[v] + 1;
                    marked[w] = true;
                    path.add(w);
                }
            }
        }
    }

    public boolean hasPathTo(int v){
        validateVertex(v);
        return marked[v];
    }

    public int distTo(int v) {
        validateVertex(v);
        return distTo[v];
    }

    public Iterable<Integer> pathTo(int v){
        validateVertex(v);
        if (!hasPathTo(v)){
            return null;
        }

        List<Integer> path = new ArrayList<>();
        for (int w = v; distTo[w] != 0; w = edgeTo[w]){
            path.add(w);
        }
        path.add(source);
        Collections.reverse(path);
        return path;
    }

    private boolean check(Graph graph){
        if (distTo[source] != 0){
            return false;
        }

        for (int v = 0; v < graph.V(); v++){
            for (int w : graph.adj(v)){
                if (hasPathTo(v) != hasPathTo(w)){
                    return false;
                }
                if (hasPathTo(v) && (distTo[w] > distTo[v] + 1)){
                    return false;
                }
            }
        }

        for (int v = 0; v < graph.V(); v++){
            if (!hasPathTo(v) || v == source){
                continue;
            }
            int w = edgeTo[v];
            if (distTo[v] != distTo[w] + 1){
                return false;
            }
        }

        return true;
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
        BreadthFirstPaths breadthPaths = new BreadthFirstPaths(graph, source);

        for (int v = 0; v < graph.V(); v++){
            if (breadthPaths.hasPathTo(v)){
                System.out.printf("%d to %d (%d): ", source, v, breadthPaths.distTo(v));
                for (int w : breadthPaths.pathTo(v)){
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
