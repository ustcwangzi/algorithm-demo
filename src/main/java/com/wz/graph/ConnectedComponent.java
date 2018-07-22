/**
 * <p>Title: ConnectedComponent</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/7/22</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.graph;

import com.wz.utils.GraphUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>联通分量</p>
 *
 * @author wangzi
 */
public class ConnectedComponent {
    /** 标记是否遍历过 */
    private boolean[] marked;
    /** 某一顶点所属联通分量 */
    private int[] id;
    /** 某一联通分量中顶点的个数 */
    private int[] size;
    /** 联通分量个数 */
    private int count;

    public ConnectedComponent(Graph graph) {
        this.marked = new boolean[graph.V()];
        this.id = new int[graph.V()];
        this.size = new int[graph.V()];

        for (int v = 0; v < graph.V(); v++){
            if (!marked[v]){
                dfs(graph, v);
                count++;
            }
        }
    }

    private void dfs(Graph graph, int v){
        marked[v] = true;
        id[v] = count;
        size[count]++;
        for (int w : graph.adj(v)) {
            if (!marked[w]){
                dfs(graph, w);
            }
        }
    }

    public int id(int v) {
        validateVertex(v);
        return id[v];
    }

    public int size(int v) {
        validateVertex(v);
        return size[id[v]];
    }

    public boolean connected(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        return id(v) == id(w);
    }

    public List<List<Integer>> components(Graph graph){
        List<List<Integer>> list = new ArrayList<>();
        for (int i = 0; i < count; i++){
            list.add(new ArrayList<>());
        }
        for (int v = 0; v < graph.V(); v++){
            list.get(id(v)).add(v);
        }
        return list;
    }

    private void validateVertex(int v) {
        int length = marked.length;
        if (v < 0 || v >= length) {
            throw new IllegalArgumentException("vertex must between 0 and " + (length-1));
        }
    }

    public static void main(String[] args) {
        Graph graph = GraphUtils.initGraph();
        ConnectedComponent component = new ConnectedComponent(graph);
        List<List<Integer>> components = component.components(graph);

        System.out.println(components.size() + " components");
        for (int i = 0; i < components.size(); i++){
            System.out.print( i + 1 + " component: ");
            for (int v : components.get(i)){
                System.out.print(v + " ");
            }
            System.out.println();
        }
    }
}
