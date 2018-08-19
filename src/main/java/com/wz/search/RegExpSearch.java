/**
 * <p>Title: RegExpSearch</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/8/19</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.search;

import com.wz.graph.Bag;
import com.wz.graph.directed.Digraph;
import com.wz.graph.directed.DirectedDFS;

import java.util.*;


/**
 * <p>正则匹配</p>
 * <p>
 *     正则匹配与普通的字符串匹配不同点在于下一个状态不是固定的，可能会存在多个有效状态
 *     因此需要构造无限状态机(NFA)来进行相关操作，而构造NFA可以使用有向图实现
 *     具体过程为：
 *     根据正则表达式，构造一个有所有转换组成的有向图
 *     然后通过检查有向图的可达性，判断是否匹配
 * </p>
 *
 * @author wangzi
 */
public class RegExpSearch {
    /**
     * 正则表达式
     */
    private final String regexp;

    public RegExpSearch(String regexp) {
        this.regexp = regexp;
    }

    public Iterable<String> match(Collection<String> txt) {
        List<String> result = new ArrayList<>();
        NFA nfa = new NFA(regexp);
        for (String str : txt) {
            if (nfa.recognizes(str)) {
                result.add(str);
            }
        }
        return result;
    }

    private static class NFA {
        private Digraph digraph;
        private final String regexp;

        public NFA(String regexp) {
            this.regexp = regexp;
            this.digraph = new Digraph(regexp.length() + 1);
            // 构造NFA
            Deque<Integer> ops = new LinkedList<>();
            for (int i = 0; i < regexp.length(); i++) {
                int lp = i;
                if (regexp.charAt(i) == '(' || regexp.charAt(i) == '|') {
                    ops.addFirst(i);
                } else if (regexp.charAt(i) == ')') {
                    int or = ops.pollFirst();
                    if (regexp.charAt(or) == '|') {
                        lp = ops.pollFirst();
                        digraph.addEdge(lp, or + 1);
                        digraph.addEdge(or, i);
                    } else if (regexp.charAt(or) == '(') {
                        lp = or;
                    }
                }

                if (i < regexp.length() - 1 && regexp.charAt(i + 1) == '*') {
                    digraph.addEdge(lp, i + 1);
                    digraph.addEdge(i + 1, lp);
                }
                if (regexp.charAt(i) == '(' || regexp.charAt(i) == '*' || regexp.charAt(i) == ')') {
                    digraph.addEdge(i, i + 1);
                }
            }
            if (ops.size() != 0) {
                throw new IllegalArgumentException("Invalid regular expression");
            }
        }

        public boolean recognizes(String txt) {
            DirectedDFS dfs = new DirectedDFS(digraph, 0);
            Bag<Integer> pc = new Bag<>();
            for (int v = 0; v < digraph.vertices(); v++) {
                if (dfs.marked(v)) {
                    pc.add(v);
                }
            }

            // 检查NFA能否匹配文本
            for (int i = 0; i < txt.length(); i++) {
                if (txt.charAt(i) == '*' || txt.charAt(i) == '|' || txt.charAt(i) == '(' || txt.charAt(i) == ')') {
                    throw new IllegalArgumentException("text contains the metacharacter '" + txt.charAt(i) + "'");
                }

                Bag<Integer> match = new Bag<>();
                for (int v : pc) {
                    if (v == regexp.length()) {
                        continue;
                    }
                    if ((regexp.charAt(v) == txt.charAt(i)) || regexp.charAt(v) == '.') {
                        match.add(v + 1);
                    }
                }

                dfs = new DirectedDFS(digraph, match);
                pc = new Bag<>();
                for (int v = 0; v < digraph.vertices(); v++) {
                    if (dfs.marked(v)) {
                        pc.add(v);
                    }
                }

                if (pc.size() == 0) {
                    return false;
                }
            }

            for (int v : pc) {
                if (v == regexp.length()) {
                    return true;
                }
            }
            return false;
        }
    }

    public static void main(String[] args) {
        List<String> txt = new ArrayList<>();
        txt.add("AC");
        txt.add("AD");
        txt.add("AAA");
        txt.add("ABD");
        txt.add("BABAAA");
        txt.add("AAAABD");
        txt.add("BABBAAA");

        RegExpSearch search = new RegExpSearch("(A*B|AC)D");
        for (String str : search.match(txt)) {
            System.out.println(str);
        }
    }
}
