/**
 * <p>Title: UnionFind</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/8/5</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.graph.mst;

import com.wz.utils.UnionFindUtils;

/**
 * <p>动态图连通性</p>
 * <p>
 *     两个节点的根节点相同时，即认定为两者处于同一个联通分量之中
 *     因此在union时，只需要将其中一个节点的根节点的根节点指向另一个节点的根节点即可
 * </p>
 *
 * @author wangzi
 */
public class QuickUnionFind {
    /**
     * 联通分量ID
     */
    private int[] parent;
    /**
     * 联通分量数量
     */
    private int count;

    public QuickUnionFind(int count) {
        if (count < 0) {
            throw new IllegalArgumentException();
        }
        this.count = count;
        this.parent = new int[count];
        for (int i = 0; i < count; i++) {
            parent[i] = i;
        }
    }

    /**
     * 返回p的根节点的联通分量ID
     * union()只需要将一个根节点的根节点指向另一个的根节点即可
     */
    public int find(int p) {
        validate(p);
        // 找到根节点，即链接指向自己的那个
        while (p != parent[p]) {
            parent[p] = parent[parent[p]];
            p = parent[p];
        }
        return p;
    }

    /**
     * 连接p、q，将一个的根作为另一个的根的根即可
     */
    public void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);
        if (rootP == rootQ) {
            return;
        }
        // 也可以是 parent[rootQ] = rootP
        parent[rootP] = rootQ;
        count--;
    }



    public int count() {
        return count;
    }

    /**
     * 两个节点的根节点相同时，它们存在于同一个联通分量之中
     */
    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    private void validate(int p) {
        int n = parent.length;
        if (p < 0 || p >= n) {
            throw new IllegalArgumentException("index " + p + " is not between 0 and " + (n - 1));
        }
    }

    public static void main(String[] args) {
        QuickUnionFind uf = UnionFindUtils.initQuickUnionFind();
        //System.out.println(uf.count());
    }
}
