/**
 * <p>Title: TrieTree</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/12/17</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.string;

/**
 * <p>字典树(前缀树)的实现</p>
 * <p>
 *     字典树又称为前缀树或Trie树，是处理字符串常见的数据结构。假设组成所有单词的字符仅是'a'～'z'，请实现字典树结构，包含以下功能：
 *     添加word，可重复添加；删除word，仅删除一次；查询word是否存在字典树中；获取以pre为前缀的单词数量。
 *     字典树是一种树形结构，优点是利用字符串的公共前缀来节约存储空间，字典树的基本性质如下：
 *     1、根节点没有字符路径，除根节点外，每个节点都被一个字符路径找到
 *     2、从根节点到某一节点，将路径上经过的字符连接起来，为扫过的对应字符串
 *     3、每个节点向下所有的字符路径上的字符都不同。
 *     解决方案：
 *        字典树节点结构使用TrieNode表示，TrieNode中，path表示有多少个单词共用这个节点；end表示有多少个单词以这个节点结尾；
 *        map是一个哈希结构，key代表该节点的一条字符路径，value表示字符路径指向的节点，根据题目说明，map长度为26。
 *        插入操作：
 *        假设word长度为N，从左到右遍历word中的每个字符，并依次从头节点开始根据每个word[i]，找到下一个节点。如果找的过程中节点不存在，
 *        就建立新节点，记为a，并令a.path=1；如果节点存在，记为b，令b.path++；最后一个字符word[N-1]找到的最后一个节点记为e，
 *        令e.path++，e.end++。
 *        查找操作：
 *        从左到右遍历word中的每个字符，并依次从头节点开始根据每个word[i]，找到下一个节点。如果找的过程中节点不存在，直接返回false。
 *        如果能通过最后一个字符word[N-1]找到最后一个节点，记为e，若e.end!=0，说明存在，返回true；若e.end==0，返回false。
 *        删除操作：
 *        先调用search()看word是否存在，不存在直接返回。从左到右遍历word中的每个字符，并依次从头节点开始根据每个word[i]，找到下一个节点。
 *        在找的过程中，把扫过的每一个节点的path减1，如果发现下一个节点path值减完之后已经为0，直接将当前节点的map中删除后续的所有路径，
 *        返回即可。如果扫到最后一个节点，记为e，令e.path--，e.end--。
 *        前缀单词数量：
 *        和查找操作类似，根据pre不断找到节点，假设最后的节点记为e，返回e.path即可。
 * </p>
 *
 * @author wangzi
 */
public class TrieTree {

    /**
     * 字典树节点
     */
    public static class TrieNode {
        // 有多少单词共用该节点
        public int path;
        // 有多少单词以该节点结尾
        public int end;
        // key：字符路径，value：字符路径指向的节点
        public TrieNode[] map;

        public TrieNode() {
            this.path = 0;
            this.end = 0;
            this.map = new TrieNode[26];
        }
    }

    public static class Trie {
        private TrieNode root;

        public Trie() {
            this.root = new TrieNode();
        }

        /**
         * 添加word
         */
        public void insert(String word) {
            if (word == null) {
                return;
            }
            char[] array = word.toCharArray();
            TrieNode node = root;
            int index;
            for (int i = 0; i < array.length; i++) {
                index = array[i] - 'a';
                if (node.map[index] == null) {
                    node.map[index] = new TrieNode();
                }
                node = node.map[index];
                node.path++;
            }
            node.end++;
        }

        /**
         * 查询word
         */
        public boolean search(String word) {
            if (word == null) {
                return false;
            }
            char[] array = word.toCharArray();
            TrieNode node = root;
            int index;
            for (int i = 0; i < array.length; i++) {
                index = array[i] - 'a';
                if (node.map[index] == null) {
                    return false;
                }
                node = node.map[index];
            }
            return node.end != 0;
        }

        /**
         * 删除word
         */
        public void delete(String word) {
            if (!search(word)) {
                return;
            }
            char[] array = word.toCharArray();
            TrieNode node = root;
            int index;
            for (int i = 0; i < array.length; i++) {
                index = array[i] - 'a';
                if (node.map[index].path-- == 1) {
                    node.map[index] = null;
                    return;
                }
                node = node.map[index];
            }
            node.end--;
        }

        /**
         * 以pre为前缀的单词数量
         */
        public int prefixNumber(String pre) {
            if (pre == null) {
                return 0;
            }

            char[] array = pre.toCharArray();
            TrieNode node = root;
            int index;
            for (int i = 0; i < array.length; i++) {
                index = array[i] - 'a';
                if (node.map[index] == null) {
                    return 0;
                }
                node = node.map[index];
            }
            return node.path;
        }
    }

    public static void main(String[] args) {
        Trie trie = new Trie();
        System.out.println(trie.search("zuo"));
        trie.insert("zuo");
        System.out.println(trie.search("zuo"));
        trie.delete("zuo");
        System.out.println(trie.search("zuo"));
        trie.insert("zuo");
        trie.insert("zuo");
        trie.delete("zuo");
        System.out.println(trie.search("zuo"));
        trie.delete("zuo");
        System.out.println(trie.search("zuo"));
        trie.insert("zuoa");
        trie.insert("zuoac");
        trie.insert("zuoab");
        trie.insert("zuoad");
        trie.delete("zuoa");
        System.out.println(trie.search("zuoa"));
        System.out.println(trie.prefixNumber("zuo"));
    }
}
