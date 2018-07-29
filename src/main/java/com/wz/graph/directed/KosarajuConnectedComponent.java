/**
 * <p>Title: KosarajuConnectedComponent</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/7/29</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.graph.directed;

import com.wz.utils.GraphUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>计算强联通分量</p>
 * <p>
 *     如果两个顶点是相互可达的，则称它们是强联通的
 *     两个顶点是强联通的当且仅当他们都在一个普通的有向环中
 *     计算强联通分量步骤：
 *     1、对有向图进行反向，并获取它的逆后序排列
 *     2、以逆后序排列的顺序进行深度优先搜索
 *     3、所有在同一个递归dfs()调用中被访问的节点都在同一个强联通分量中
 * </p>
 *
 * @author wangzi
 */
public class KosarajuConnectedComponent {
    /**
     * 标记是否遍历过
     */
    private boolean[] marked;
    /**
     * 某一顶点所属联通分量
     */
    private int[] id;
    /**
     * 联通分量个数
     */
    private int count;

    public KosarajuConnectedComponent(Digraph digraph) {
        this.marked = new boolean[digraph.vertices()];
        this.id = new int[digraph.vertices()];
        DepthFirstOrder dfs = new DepthFirstOrder(digraph.reverse());
        for (int v : dfs.reversePost()) {
            if (!marked[v]) {
                dfs(digraph, v);
                count++;
            }
        }
    }

    private void dfs(Digraph digraph, int v) {
        marked[v] = true;
        id[v] = count;
        for (int w : digraph.adj(v)) {
            if (!marked[w]) {
                dfs(digraph, w);
            }
        }
    }

    public int count() {
        return count;
    }

    public int id(int v) {
        validateVertex(v);
        return id[v];
    }

    public boolean stronglyConnected(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        return id[v] == id[w];
    }

    public List<List<Integer>> components(Digraph digraph) {
        List<List<Integer>> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            list.add(new ArrayList<>());
        }
        for (int v = 0; v < digraph.vertices(); v++) {
            list.get(id(v)).add(v);
        }
        return list;
    }

    private void validateVertex(int v) {
        int length = marked.length;
        if (v < 0 || v >= length) {
            throw new IllegalArgumentException("vertex must between 0 and " + (length - 1));
        }
    }

    public static void main(String[] args) {
        Digraph digraph = GraphUtils.initCycleDigraph();
        KosarajuConnectedComponent component = new KosarajuConnectedComponent(digraph);

        List<List<Integer>> components = component.components(digraph);

        System.out.println(component.count() + " components");
        for (int i = 0; i < components.size(); i++) {
            System.out.print(i + 1 + " component: ");
            for (int v : components.get(i)) {
                System.out.print(v + " ");
            }
            System.out.println();
        }
    }
}
