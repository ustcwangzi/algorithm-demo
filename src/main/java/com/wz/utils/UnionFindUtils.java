/**
 * <p>Title: UnionFindUtils</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/8/5</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.utils;

import com.wz.graph.mst.QuickUnionFind;
import com.wz.graph.mst.UnionFind;

/**
 * <p></p>
 *
 * @author wangzi
 */
public class UnionFindUtils {
    public static UnionFind initUnionFind(){
        UnionFind unionFind = new UnionFind(10);
        unionFind.union(0, 5);
        unionFind.union(1, 2);
        unionFind.union(1, 6);
        unionFind.union(2, 7);
        unionFind.union(3, 4);
        unionFind.union(3, 8);
        unionFind.union(4, 9);
        unionFind.union(5, 6);
        return unionFind;
    }

    public static QuickUnionFind initQuickUnionFind(){
        QuickUnionFind unionFind = new QuickUnionFind(10);
        unionFind.union(0, 5);
        unionFind.union(1, 2);
        unionFind.union(1, 6);
        unionFind.union(2, 7);
        unionFind.union(3, 4);
        unionFind.union(3, 8);
        unionFind.union(4, 9);
        unionFind.union(5, 6);
        return unionFind;
    }
}
