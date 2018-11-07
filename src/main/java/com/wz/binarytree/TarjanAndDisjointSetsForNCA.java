/**
 * <p>Title: TarjanAndDisjointSetsForNCA</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/10/21</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.binarytree;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * <p>Tarjan算法与并查集解决二叉树节点间最近公共祖先的批量查询</p>
 * <p>
 *
 * </p>
 *
 * @author wangzi
 */
public class TarjanAndDisjointSetsForNCA {
    private static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    private static class Query {
        public Node self;
        public Node other;

        public Query(Node self, Node other) {
            this.self = self;
            this.other = other;
        }
    }

    /**
     * 并查集
     */
    private static class DisjointSets {
        /**
         * 节点的上层节点
         */
        public Map<Node, Node> fatherMap;
        /**
         * 节点的秩，即节点下面还有多少层，更新不严格
         */
        public Map<Node, Integer> rankMap;

        public DisjointSets() {
            this.fatherMap = new HashMap<>();
            this.rankMap = new HashMap<>();
        }

        /**
         * 并查集初始化
         */
        public void makeSets(Node head) {
            fatherMap.clear();
            rankMap.clear();
            preOrderMake(head);
        }

        private void preOrderMake(Node head) {
            if (head == null) {
                return;
            }
            fatherMap.put(head, head);
            rankMap.put(head, 0);
            preOrderMake(head.left);
            preOrderMake(head.right);
        }

        /**
         * 查找一个节点属于哪个集合，即这个节点所在集合的代表节点
         */
        public Node findFather(Node node) {
            Node father = fatherMap.get(node);
            if (father != node) {
                father = findFather(father);
            }
            fatherMap.put(node, father);
            return father;
        }

        /**
         * 两个集合进行合并
         * 找到两个节点所在集合的代表节点进行合并
         */
        public void union(Node aNode, Node bNode) {
            if (aNode == null || bNode == null) {
                return;
            }
            Node aFather = findFather(aNode);
            Node bFather = findFather(bNode);
            if (aFather != bFather) {
                int aFrank = rankMap.get(aFather);
                int bFrank = rankMap.get(bFather);
                // 将层数小的集合挂在层数大的集合下面
                if (aFrank < bFrank) {
                    fatherMap.put(aFather, bFather);
                } else if (aFrank > bFrank) {
                    fatherMap.put(bFather, aFather);
                } else {
                    fatherMap.put(bFather, aFather);
                    rankMap.put(aFather, aFrank + 1);
                }
            }
        }
    }

    public static class Tarjan {
        /**
         * 节点key与哪些节点之间有查询任务
         */
        private Map<Node, LinkedList<Node>> queryMap;
        /**
         * 节点key的查询任务获得的结果存放的位置
         */
        private Map<Node, LinkedList<Integer>> indexMap;
        /**
         * 节点key所在集合的祖先节点
         */
        private Map<Node, Node> ancestorMap;
        /**
         * 并查集
         */
        private DisjointSets sets;

        public Tarjan() {
            queryMap = new HashMap<>();
            indexMap = new HashMap<>();
            ancestorMap = new HashMap<>();
            sets = new DisjointSets();
        }

        public Node[] query(Node head, Query[] queries) {
            Node[] results = new Node[queries.length];
            setQueries(queries, results);
            sets.makeSets(head);
            setResults(head, results);
            return results;
        }

        private void setQueries(Query[] queries, Node[] results) {
            Node self, other;
            for (int i = 0; i < results.length; i++) {
                self = queries[i].self;
                other = queries[i].other;
                if (self == other || self == null || other == null) {
                    results[i] = self == null ? other : self;
                } else {
                    if (!queryMap.containsKey(self)) {
                        queryMap.put(self, new LinkedList<>());
                        indexMap.put(self, new LinkedList<>());
                    }
                    if (!queryMap.containsKey(other)) {
                        queryMap.put(other, new LinkedList<>());
                        indexMap.put(other, new LinkedList<>());
                    }

                    queryMap.get(self).add(other);
                    indexMap.get(self).add(i);
                    queryMap.get(other).add(self);
                    indexMap.get(other).add(i);
                }
            }
        }

        private void setResults(Node head, Node[] results) {
            if (head == null) {
                return;
            }
            setResults(head.left, results);
            sets.union(head.left, head);
            ancestorMap.put(sets.findFather(head), head);

            setResults(head.right, results);
            sets.union(head.right, head);
            ancestorMap.put(sets.findFather(head), head);

            LinkedList<Node> nodeList = queryMap.get(head);
            LinkedList<Integer> indexList = indexMap.get(head);
            Node node, nodeFather;
            int index;
            while (nodeList != null && !nodeList.isEmpty()) {
                node = nodeList.poll();
                index = indexList.poll();
                nodeFather = sets.findFather(node);
                if (ancestorMap.containsKey(nodeFather)) {
                    results[index] = ancestorMap.get(nodeFather);
                }
            }
        }

        public static void main(String[] args) {
            Node head = new Node(1);
            head.left = new Node(2);
            head.right = new Node(3);
            head.left.left = new Node(4);
            head.left.right = new Node(5);
            head.right.left = new Node(6);
            head.right.right = new Node(7);
            head.right.right.left = new Node(8);

            Query[] queries = new Query[7];
            queries[0] = new Query(head.left.right, head.right.left);
            queries[1] = new Query(head.left.left, head.left);
            queries[2] = new Query(head.right.left, head.right.right.left);
            queries[3] = new Query(head.left.left, head.right.right);
            queries[4] = new Query(head.right.right, head.right.right.left);
            queries[5] = new Query(head, head);
            queries[6] = new Query(head.left, head.right.right.left);

            Node[] results = new Tarjan().query(head, queries);

            for (int i = 0; i < results.length; i++) {
                System.out.print("self: " + queries[i].self.value);
                System.out.print(", other: " + queries[i].other.value);
                System.out.print(", ancestor: " + (results[i] == null ? null : results[i].value));
                System.out.println();
            }
        }
    }
}
