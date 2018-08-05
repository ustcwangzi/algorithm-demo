/**
 * <p>Title: UnionFind</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/8/5</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.unionfind;

import com.wz.utils.UnionFindUtils;

/**
 * <p>动态图连通性</p>
 * <p>
 *     QuickUnionFind中，union时将任意一棵树合并到另一棵上，极端情况下，有可能退化成一个链表
 *     另外，如果将一棵较大的树合并到一棵较小的树上，会产生"畸形树"，WeightedQuickUnionFind对其进行优化
 *     通过size记录联通分量的大小，在union时，总是将较小的一棵合并到较大的一棵树上
 * </p>
 *
 * @author wangzi
 */
public class WeightedQuickUnionFind {
    /**
     * 联通分量ID
     */
    private int[] parent;
    /**
     * 各个根节点对应的分量大小
     */
    private int[] size;
    /**
     * 联通分量数量
     */
    private int count;

    public WeightedQuickUnionFind(int count) {
        if (count < 0) {
            throw new IllegalArgumentException();
        }
        this.count = count;
        this.parent = new int[count];
        this.size = new int[count];
        for (int i = 0; i < count; i++) {
            parent[i] = i;
            size[i] = 1;
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
            p = parent[p];
        }
        return p;
    }

    /**
     * 连接p、q，将size较大的根作为size较小的根的根
     */
    public void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);
        if (rootP == rootQ) {
            return;
        }
        if (size[rootP] < size[rootQ]) {
            parent[rootP] = rootQ;
            size[rootQ] += size[rootP];
        } else {
            parent[rootQ] = rootP;
            size[rootP] += size[rootQ];
        }
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
        WeightedQuickUnionFind uf = UnionFindUtils.initWeightedQuickUnionFind();
        System.out.println(uf.count());
    }
}
