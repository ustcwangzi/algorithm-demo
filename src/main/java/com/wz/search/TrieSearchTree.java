/**
 * <p>Title: TrieSearchTree</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/8/12</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.search;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>R向单词查找树</p>
 * <p>
 *     在R向单词查找树中，值是从根节点到含有非空值的节点的路径所隐式表示的
 *     例如sea，数据结构即没有保存字符串sea，也没有保存字符s、e、a
 *     事实上，数据结构不会保存任何字符串或字符，而是保存了链接数组和值
 *     Node中保存键相关联的值并用数组保存所有指向其他Node对象的引用
 * </p>
 * <p>R向单词查找树的链表结构和键的插入或删除顺序无关，对任意给定的一组键，其单词查找树唯一</p>
 * <p>R向单词查找树中查找一个键或插入一个键时，访问数组的次数是最长键的长度加一</p>
 * <p>一棵R向单词查找树中的链接总数在 RN 到 RNW 直接，R为字母表大小，W为键的平均长度</p>
 * <p>R向单词查找树适用于较短的键和较小的字母表</p>
 *
 * @author wangzi
 */
public class TrieSearchTree<Value> {
    /**
     * 字符串基数，即所有字符串中不同字符的个数
     * 256为8位的ASCII码
     */
    private static final int RADIX = 256;
    /**
     * 任意字符
     */
    private static final char ANY_CHAR = '.';
    /**
     * 查找树的根节点
     */
    private Node root;
    /**
     * 查找树中键的个数
     */
    private int size;

    private static class Node {
        /**
         * 键所关联的值
         */
        private Object value;
        /**
         * 所有指向其他Node对象的引用
         */
        private Node[] next = new Node[RADIX];
    }

    private Value get(String key) {
        if (key == null) {
            throw new IllegalArgumentException("argument is null");
        }
        Node node = get(root, key, 0);
        return node == null ? null : (Value) node.value;
    }

    /**
     * 从第d个字符所对应的子树开始查找树中与key相关联的值
     */
    private Node get(Node node, String key, int d) {
        if (node == null) {
            return null;
        }
        if (d == key.length()) {
            return node;
        }
        char c = key.charAt(d);
        return get(node.next[c], key, d + 1);
    }

    public boolean contains(String key) {
        if (key == null) {
            throw new IllegalArgumentException("argument is null");
        }
        return get(key) != null;
    }

    public void put(String key, Value value) {
        if (key == null) {
            throw new IllegalArgumentException("argument is null");
        }
        if (value == null) {
            delete(key);
        }
        root = put(root, key, value, 0);
    }

    private Node put(Node node, String key, Value value, int d) {
        if (node == null) {
            node = new Node();
        }
        if (d == key.length()) {
            if (node.value == null) {
                size++;
            }
            node.value = value;
            return node;
        }
        char c = key.charAt(d);
        node.next[c] = put(node.next[c], key, value, d + 1);
        return node;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public Iterable<String> keys() {
        return keysWithPrefix("");
    }

    /**
     * 获取以 prefix 为前缀的字符串
     */
    public Iterable<String> keysWithPrefix(String prefix) {
        List<String> result = new ArrayList<>();
        Node node = get(root, prefix, 0);
        collection(node, new StringBuilder(prefix), result);
        return result;
    }

    /**
     * 收集查找树中的键
     * @param node 当前节点
     * @param prefix 从根节点到该节点的路径上的所有字符
     */
    private void collection(Node node, StringBuilder prefix, List<String> result) {
        if (node == null) {
            return;
        }
        // 访问一个节点时，如果它的值非空，就将和它关联的字符串加入队列
        if (node.value != null) {
            result.add(prefix.toString());
        }
        for (char c = 0; c < RADIX; c++) {
            prefix.append(c);
            collection(node.next[c], prefix, result);
            prefix.deleteCharAt(prefix.length() - 1);
        }
    }

    /**
     * 通配符匹配
     */
    public Iterable<String> keysThatMatch(String pattern) {
        List<String> result = new ArrayList<>();
        collect(root, new StringBuilder(), pattern, result);
        return result;
    }

    /**
     * 与上面的collection()类似，增加了一个参数pattern来指定匹配模式
     * 如果模式中含有通配符，就需要用递归调用处理所有的链接
     * 否则，只需要处理模式中制定的字符的链接即可
     */
    private void collect(Node node, StringBuilder prefix, String pattern, List<String> result) {
        if (node == null) {
            return;
        }
        int d = prefix.length();
        if (d == pattern.length() && node.value != null) {
            result.add(prefix.toString());
        }
        if (d == pattern.length()) {
            return;
        }
        char c = pattern.charAt(d);
        if (c == ANY_CHAR) {
            for (char ch = 0; ch < RADIX; ch++) {
                prefix.append(ch);
                collect(node.next[ch], prefix, pattern, result);
                prefix.deleteCharAt(prefix.length() - 1);
            }
        } else {
            prefix.append(c);
            collect(node.next[c], prefix, pattern, result);
            prefix.deleteCharAt(prefix.length() - 1);
        }
    }

    /**
     * 最长前缀匹配
     */
    public String longestPrefixOf(String query) {
        if (query == null) {
            throw new IllegalArgumentException("argument is null");
        }
        int length = longestPrefixOf(root, query, 0, -1);
        if (length == -1) {
            return null;
        }
        return query.substring(0, length);
    }

    private int longestPrefixOf(Node node, String query, int d, int length) {
        if (node == null) {
            return length;
        }
        if (node.value != null) {
            length = d;
        }
        if (d == query.length()) {
            return length;
        }
        char c = query.charAt(d);
        return longestPrefixOf(node.next[c], query, d + 1, length);
    }

    public void delete(String key) {
        if (key == null) {
            throw new IllegalArgumentException("argument is null");
        }
        root = delete(root, key, 0);
    }

    private Node delete(Node node, String key, int d) {
        if (node == null) {
            return null;
        }
        if (d == key.length()) {
            if (node.value != null) {
                size--;
            }
            node.value = null;
        } else {
            char c = key.charAt(d);
            node.next[c] = delete(node.next[c], key, d + 1);
        }

        if (node.value != null) {
            return node;
        }
        for (int c = 0; c < RADIX; c++) {
            if (node.next[c] != null) {
                return node;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        TrieSearchTree<Integer> tree = new TrieSearchTree<>();
        tree.put("she", 0);
        tree.put("sells", 1);
        tree.put("sea", 2);
        tree.put("shells", 3);
        tree.put("by", 4);
        tree.put("the", 5);
        tree.put("sea", 6);
        tree.put("shore", 7);

        for (String key : tree.keys()){
            System.out.println(key + " " + tree.get(key));
        }
        System.out.println("longestPrefixOf(shellsort): " + tree.longestPrefixOf("shellsort"));
        System.out.println("keysWithPrefix(sh): " + tree.keysWithPrefix("sh"));
        System.out.println("keysThatMatch(.he): " + tree.keysThatMatch(".he"));
    }
}
