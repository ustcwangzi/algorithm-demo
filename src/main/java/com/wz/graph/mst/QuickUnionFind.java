/**
 * <p>Title: UnionFind</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/8/5</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.graph.mst;

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
     * 直接返回联通分量ID
     * 需要union()来确保当p和q处于同一联通分量时，p和q的联通分量ID相同
     */
    public int find(int p) {
        validate(p);
        return parent[p];
    }

    /**
     * 每次都需要遍历整个联通分量ID数组，修改对应的ID，代价较大
     */
    public void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);
        if (rootP == rootQ) {
            return;
        }
        for (int i = 0; i < parent.length; i++){
            if (parent[i] == rootP){
                parent[i] = rootQ;
            }
        }
        count--;
    }

    /**
     * 返回p的根节点的联通分量ID
     * quickUnion()只需要将一个根节点的根节点指向另一个的根节点即可
     */
    public int quickFind(int p) {
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
    public void quickUnion(int p, int q) {
        int rootP = quickFind(p);
        int rootQ = quickFind(q);
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
        return quickFind(p) == quickFind(q);
    }

    private void validate(int p) {
        int n = parent.length;
        if (p < 0 || p >= n) {
            throw new IllegalArgumentException("index " + p + " is not between 0 and " + (n - 1));
        }
    }

    public static void main(String[] args) {
        QuickUnionFind uf = new QuickUnionFind(10);
        uf.quickUnion(0, 5);
        uf.quickUnion(1, 2);
        uf.quickUnion(1, 6);
        uf.quickUnion(2, 7);
        uf.quickUnion(3, 4);
        uf.quickUnion(3, 8);
        uf.quickUnion(4, 9);
        uf.quickUnion(5, 6);
        System.out.println(uf.count);
        uf = new QuickUnionFind(10);
        uf.union(0, 5);
        uf.union(1, 2);
        uf.union(1, 6);
        uf.union(2, 7);
        uf.union(3, 4);
        uf.union(3, 8);
        uf.union(4, 9);
        uf.union(5, 6);
        System.out.println(uf.count);
    }
}
