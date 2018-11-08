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
 *     假设二叉树如TarjanAndDisjointSetsForNCA.png所示，
 *     query[0]=(4,7), query[1]=(7,8), query[2]=(8,9), query[3]=(9,3), query[4]=(6,6), query[5]=(null,5), query[6]=(null,null)
 *     具体过程如下：
 *     1、如果self等于other、self与other有一个为空、self与other都为空，可以直接得到结果
 *        result[4]=6, result[5]=5, result[6]=null
 *     2、不能直接得到结果的，需要生成queryMap和indexMap，queryMap中key代表节点，value是一个链表，代表与key有查询任务的节点
 *        indexMap中key代表节点，value是一个链表，表示获得与key有查询任务的答案所存放的位置
 *             key: 4      7      8      9     3
 *        queryMap: {7}  {4,8}  {7,9}  {8,3}  {9}
 *        indexMap: {0}  {0,1}  {1,2}  {2,3}  {3}
 *     3、接下来处理M条查询，真个过程是左、根、右、再回到根的遍历
 *     3.1、对每个节点生成各自的集合，开始时每个结合的祖先节点设为空
 *     3.2、遍历节点4，属于集合{4}，设置{4}的祖先节点为4，查询任务为7，7属于集合{7}，但{7}的祖先节点为空，说明还未遍历到，暂时不执行该查询
 *     3.3、遍历节点2，属于集合{2}，设置{2}的祖先节点为2，左孩子节点4属于集合{4}，两集合合并为{4,2}，设置{4,2}的祖先节点为2
 *     3.4、遍历节点7，属于集合{7}，设置{7}的祖先节点为7，查询任务为4、8，4属于集合{4,2}，{4,2}的祖先节点为2，说明已遍历，
 *          根据indexMap得到答案应该放在0位置，因此设置result[0]=2，8属于集合{8}，但{8}的祖先节点为空，说明还未遍历到，忽略
 *     3.5、遍历节点5，属于集合{5}，设置{5}的祖先节点为5，左孩子节点7属于集合{7}，两集合合并为{7,5}，设置{7,5}的祖先节点为5
 *     3.6、遍历节点8，属于集合{8}，设置{8}的祖先节点为8，查询任务为7、9，7属于集合{7,5}，{7,5}的祖先节点为5，说明已遍历，
 *          根据indexMap得到答案应该放在1位置，因此设置result[1]=5，9属于集合{9}，但{9}的祖先节点为空，说明还未遍历到，忽略
 *     3.7、从节点5的右子树回到节点5，5属于{7,5}，右孩子节点8属于{8}，两集合合并为{7,5,8}，设置{7,5,8}的祖先节点为5
 *     3.8、从节点2的右子树回到节点2，2属于{2,4}，右孩子节点5属于{7,5,8}，两集合合并为{2,4,7,5,8}，设置{2,4,7,5,8}的祖先节点为2
 *     3.9、遍历节点1，{2,4,7,5,8}与{1}合并为{2,4,7,5,8,1}，设置祖先节点为1
 *     3.10、遍历节点3，属于集合{3}，设置{3}的祖先节点为3，查询任务为9，9还未遍历到，忽略
 *     3.11、遍历节点6，属于集合{6}，设置{6}的祖先节点为6
 *     3.12、遍历节点9，属于集合{9}，设置{9}的祖先节点为9，查询任务为8、3，8属于集合{2,4,7,5,8,1}，其祖先节点为1，说明已遍历，
 *           根据indexMap得到答案应该放在2位置，因此设置result[2]=1，3属于集合{3}，设置result[3]=3
 *     3.13、回到节点6，合并{6}和{9}为{6,9}，祖先节点设为6
 *     3.14、回到节点3，合并{3}和{6,9}为{3,6,9}，祖先节点设为3
 *     3.15、回到节点1，合并{2,4,7,5,8,1}和{3,6,9}为{1,2,3,4,5,6,7,8,9}，祖先节点设为1
 *     3.16、过程结束
 *
 *     遍历到节点A，queryMap能够迅速查到哪些节点与A有查询任务，如果能够得到答案，indexMap还能知道把答案放在result的什么位置
 *     假设A与B直接有查询任务，如果B已遍历过，可以得到答案，在A的链表中删除该任务，如果B未遍历，依然在A的链表中删除该任务，
 *     这个任务会在遍历到B的时候重新被发现，所以遍历到一个节点时，该节点的任务链表会被清空，未解决的任务会在后续过程中被发现并解决。
 *     上述过程中大量出现生成集合、合并集合和根据节点找到所在集合的操作，为提高效率，这里使用了并查集结构
 *
 *        并查集由一群集合构成，比如步骤3.1中对每个节点生成各自的集合，所有集合的全体构成一个并查集{{1},{2},...,{9}}，这些集合可以合并，
 *     如果最终合并成一个大集合，比如步骤3.15，那么此时并查集中有一个元素，这个元素是这个大集合，即{{1,2,...,9}}。
 *        并查集初始化时，每个节点都生成一个只含有自己的集合，集合中只含有一个元素，该元素的father为自己，节点的father信息保存在fatherMap
 *     每个元素除father信息外，还有rank代表一个节点的秩，即该节点下面还有多少层节点，并查集为每个节点秩的更新并不严格，保存在rankMap
 *     当集合有多个节点时，下层节点的father为上层节点，最上层节点的father指向自己，最上层节点又叫集合的代表节点。
 *        在并查集中，查找某个节点属于哪个集合，就是查这个节点所在集合的代表节点，即通过father信息逐渐找到最上面的节点，该节点father为自己。
 *     查找一个节点所在集合的代表节点的过程，不仅仅是单纯的查找，还会把整个查找路径压缩，比如g是下层节点，a是g的最上层节点，从g找到a之后，
 *     会把从a到g这条路径上所有节点的father都是设置为a，经过路径压缩后，路径上每个节点下次查找代表节点时都仅需经过一次移动的过程。
 *        并查集中集合的合并操作时，参数并不是两个集合，而是任意两个节点，记为A和B，根据A和B找到所在集合的代表节点AFather和BFather，
 *     如果AFather==BFather，说明A和B本身就在一个集合中，不用合并；否则，将AFather和BFather中rank较小的挂到rank较大的集合下面。
 *
 *     如果二叉树节点数为N，查询语句的条数为M，整个处理过程的时间复杂度为O(N+M)
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
