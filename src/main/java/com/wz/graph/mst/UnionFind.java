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
 *     两个节点的联通分量ID相同时，即认定为两者处于同一个联通分量之中
 *     因此在union时，需要遍历这个联通分量ID数组，修改联通分量ID
 * </p>
 *
 * @author wangzi
 */
public class UnionFind {
    /**
     * 联通分量ID
     */
    private int[] parent;
    /**
     * 联通分量数量
     */
    private int count;

    public UnionFind(int count) {
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


    public int count() {
        return count;
    }

    /**
     * 两个节点的联通分量ID相同时，它们存在于同一个联通分量之中
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
        UnionFind uf = UnionFindUtils.initUnionFind();
        System.out.println(uf.count());
    }
}
