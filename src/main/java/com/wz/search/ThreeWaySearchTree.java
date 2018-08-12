/**
 * <p>Title: ThreeWaySearchTree</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/8/12</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.search;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>三向单词查找树</p>
 * <p>
 *     三向单词查找树中，每个节点都含有一个字符、三条链接和一个值
 *     这三条链接分别对应着当前字符小于、大于、等于节点字符的所有键
 *     R向单词查找树中，树的节点含有R条链接，每个非空链接的索引隐式的表示了它所对应的字符
 *     三向单词查找树中，字符是显式的保存在节点中的，只有沿着中间链接前进时才会根据字符找到键
 * </p>
 * <p>三向单词查找树的结构取决于键的插入顺序</p>
 * <p>三向单词查找树适用于非随机的键</p>
 * @author wangzi
 */
public class ThreeWaySearchTree<Value> {
    /**
     * 任意字符
     */
    private static final char ANY_CHAR = '.';
    /**
     * 查找树的根节点
     */
    private Node<Value> root;
    /**
     * 查找树中键的个数
     */
    private int size;

    private static class Node<Value> {
        /**
         * 字符
         */
        private char c;
        /**
         * 左右中三向单词查找树
         */
        private Node<Value> left, mid, right;
        /**
         * 和字符串相关联的值
         */
        private Value value;
    }

    public int size() {
        return size;
    }

    public boolean contains(String key) {
        if (key == null) {
            throw new IllegalArgumentException("argument is null");
        }
        return get(key) != null;
    }

    public Value get(String key) {
        if (key == null) {
            throw new IllegalArgumentException("argument is null");
        }
        if (key.length() == 0) {
            throw new IllegalArgumentException("key must have length >= 1");
        }
        Node<Value> node = get(root, key, 0);
        if (node == null) {
            return null;
        }
        return node.value;
    }

    private Node<Value> get(Node<Value> node, String key, int d) {
        if (node == null) {
            return null;
        }
        if (key.length() == 0) {
            throw new IllegalArgumentException("key must have length >= 1");
        }
        char c = key.charAt(d);
        if (c < node.c) {
            return get(node.left, key, d);
        } else if (c > node.c) {
            return get(node.right, key, d);
        } else if (d < key.length() - 1) {
            return get(node.mid, key, d + 1);
        } else {
            return node;
        }
    }

    public void put(String key, Value value) {
        if (key == null) {
            throw new IllegalArgumentException("argument is null");
        }
        if (!contains(key)) {
            size++;
        }
        root = put(root, key, value, 0);
    }

    private Node<Value> put(Node<Value> node, String key, Value value, int d) {
        char c = key.charAt(d);
        if (node == null) {
            node = new Node<>();
            node.c = c;
        }
        if (c < node.c) {
            node.left = put(node.left, key, value, d);
        } else if (c > node.c) {
            node.right = put(node.right, key, value, d);
        } else if (d < key.length() - 1) {
            node.mid = put(node.mid, key, value, d + 1);
        } else {
            node.value = value;
        }
        return node;
    }

    /**
     * 最长前缀匹配
     */
    public String longestPrefixOf(String query) {
        if (query == null) {
            throw new IllegalArgumentException("argument is null");
        }
        if (query.length() == 0) {
            return null;
        }
        int length = 0;
        Node<Value> node = root;
        int i = 0;
        while (node != null && i < query.length()) {
            char c = query.charAt(i);
            if (c < node.c) {
                node = node.left;
            } else if (c > node.c) {
                node = node.right;
            } else {
                i++;
                if (node.value != null) {
                    length = i;
                }
                node = node.mid;
            }
        }
        return query.substring(0, length);
    }

    /**
     * 前缀匹配
     */
    public Iterable<String> keysWithPrefix(String prefix) {
        if (prefix == null) {
            throw new IllegalArgumentException("argument is null");
        }
        List<String> result = new ArrayList<>();
        Node<Value> node = get(root, prefix, 0);
        if (node == null) {
            return result;
        }
        if (node.value != null) {
            result.add(prefix);
        }
        collect(node.mid, new StringBuilder(prefix), result);
        return result;
    }

    public Iterable<String> keys() {
        List<String> result = new ArrayList<>();
        collect(root, new StringBuilder(), result);
        return result;
    }

    private void collect(Node<Value> node, StringBuilder prefix, List<String> result) {
        if (node == null) {
            return;
        }
        collect(node.left, prefix, result);
        if (node.value != null) {
            result.add(prefix.toString() + node.c);
        }
        collect(node.mid, prefix.append(node.c), result);
        prefix.deleteCharAt(prefix.length() - 1);
        collect(node.right, prefix, result);
    }

    public Iterable<String> keysThatMatch(String pattern) {
        List<String> result = new ArrayList<>();
        collect(root, new StringBuilder(), 0, pattern, result);
        return result;
    }

    private void collect(Node<Value> node, StringBuilder prefix, int i, String pattern, List<String> result) {
        if (node == null) {
            return;
        }
        char c = pattern.charAt(i);
        if (c == ANY_CHAR || c < node.c) {
            collect(node.left, prefix, i, pattern, result);
        }
        if (c == ANY_CHAR || c == node.c) {
            if (i == pattern.length() - 1 && node.value != null) {
                result.add(prefix.toString() + node.c);
            }
            if (i < pattern.length() - 1) {
                collect(node.mid, prefix.append(node.c), i + 1, pattern, result);
                prefix.deleteCharAt(prefix.length() - 1);
            }
        }
        if (c == ANY_CHAR || c > node.c) {
            collect(node.right, prefix, i, pattern, result);
        }
    }

    public static void main(String[] args) {
        ThreeWaySearchTree<Integer> tree = new ThreeWaySearchTree<>();
        tree.put("she", 0);
        tree.put("sells", 1);
        tree.put("sea", 2);
        tree.put("shells", 3);
        tree.put("by", 4);
        tree.put("the", 5);
        tree.put("sea", 6);
        tree.put("shore", 7);

        for (String key : tree.keys()) {
            System.out.println(key + " " + tree.get(key));
        }
        System.out.println("longestPrefixOf(shellsort): " + tree.longestPrefixOf("shellsort"));
        System.out.println("keysWithPrefix(sh): " + tree.keysWithPrefix("sh"));
        System.out.println("keysThatMatch(.he): " + tree.keysThatMatch(".he"));
    }

}
