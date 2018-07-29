/**
 * <p>Title: DepthFirstOrder</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/7/29</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.graph.directed;

import com.wz.utils.GraphUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>有向图中基于深度优先搜索的顶点排序</p>
 * <p>
 *     前序：在递归调用之前将顶点加入队列
 *     后序：在递归调用之后将顶点加入队列
 *     逆后序：在递归调用之后将顶点压入栈
 * </p>
 *
 * @author wangzi
 */
public class DepthFirstOrder {
    /**
     * 标识顶点是否遍历过
     */
    private boolean[] marked;
    /**
     * 后续位置
     */
    private int[] pre;
    /**
     * 前序位置
     */
    private int[] post;
    /**
     * 前序排列
     */
    private List<Integer> preOrder;
    /**
     * 后序排列
     */
    private List<Integer> postOrder;
    /**
     * 前序顶点个数
     */
    private int preCounter;
    /**
     * 后序顶点个数
     */
    private int postCounter;

    public DepthFirstOrder(Digraph digraph) {
        this.marked = new boolean[digraph.vertices()];
        this.pre = new int[digraph.vertices()];
        this.post = new int[digraph.vertices()];
        this.preOrder = new ArrayList<>();
        this.postOrder = new ArrayList<>();
        for (int v = 0; v < digraph.vertices(); v++) {
            if (!marked[v]) {
                dfs(digraph, v);
            }
        }

        assert check();
    }

    private void dfs(Digraph digraph, int v) {
        marked[v] = true;
        pre[v] = preCounter++;
        preOrder.add(v);
        for (int w : digraph.adj(v)) {
            if (!marked[w]) {
                dfs(digraph, w);
            }
        }
        postOrder.add(v);
        post[v] = postCounter++;
    }

    public int pre(int v) {
        validateVertex(v);
        return pre[v];
    }

    public int post(int v) {
        validateVertex(v);
        return post[v];
    }

    public Iterable<Integer> pre() {
        return preOrder;
    }

    public Iterable<Integer> post() {
        return postOrder;
    }

    public Iterable<Integer> reversePost() {
        List<Integer> reversePost = new ArrayList<>(postOrder);
        Collections.reverse(reversePost);
        return reversePost;
    }

    private boolean check() {
        int r = 0;
        for (int v : post()) {
            if (post(v) != r) {
                return false;
            }
            r++;
        }

        r = 0;
        for (int v : pre()) {
            if (pre(v) != r) {
                return false;
            }
            r++;
        }

        return true;
    }

    private void validateVertex(int v) {
        int length = marked.length;
        if (v < 0 || v >= length) {
            throw new IllegalArgumentException("vertex must between 0 and " + (length - 1));
        }
    }

    public static void main(String[] args) {
        Digraph digraph = GraphUtils.initDigraph();
        DepthFirstOrder order = new DepthFirstOrder(digraph);

        System.out.println("   v   pre  post");
        for (int v = 0; v < digraph.vertices(); v++) {
            System.out.printf("%4d %4d %4d\n", v, order.pre(v), order.post(v));
        }

        System.out.print("PreOrder:");
        for (int v : order.pre()) {
            System.out.print(v + " ");
        }
        System.out.println();

        System.out.print("PostOrder:");
        for (int v : order.post()) {
            System.out.print(v + " ");
        }
        System.out.println();

        System.out.print("Reverse postOrder:");
        for (int v : order.reversePost()) {
            System.out.print(v + " ");
        }
    }
}
