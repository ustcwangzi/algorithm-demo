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
 *
 * @author wangzi
 */
public class TrieTree {

    public static class TrieNode {
        public int path;
        public int end;
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
