/**
 * <p>Title: KMP</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/8/18</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.search.str;

/**
 * <p>KMP字符串查找算法</p>
 * <p>
 *     假设：主字符串为txt，下标为i，模式字符串为pat，下标为j
 *     暴力匹配模式下，两者匹配时，i和j同时前进，当不匹配时，i回退到i-j+1处，j归0
 *     暴力匹配模式丢弃了之前已匹配的字符，直接进行回溯，效率较低
 *     KMP算法思想是：
 *          利用已部分匹配这个有效信息，保持i不回溯，通过修改j，让其回到有效位置
 *          所以，重点是，当某个字符与主串不匹配时，需要知道j要移动的位置
 *     当匹配失败时，j需要移动的位置k，具有该性质：pat[0...k-1] == pat[j-k...j-1]
 *     比如对于ABABAC，可以算出next[]为：-1 0 0 1 2 3
 *     下面重点分析，如何得到失败转移数组next[]
 *          当j为0时，不能再移动，i后移即可，为保持逻辑一致，next[0]初始化为-1
 *          当pat[k] == pat[j]时，有next[j+1] == k + 1 (相同子串在增长)
 *          当pat[k] != pat[j]时，有k == next[k] (回到更小的相同子串中进行比较)
 *     以上关系就可以计算出next[]，但是有个问题：
 *          pat[k] == pat[j]时，直接将next[j+1]赋值为k+1，但此时j+1是失配的，
 *          回退到j后，发现新的j和回退之前的j相等的话，必然也是失配的，需要继续回退
 *     所以可以针对这个作出优化
 * </p>
 * <p>对长度为M的模式字符串和长度为N的文本，KMP算法访问字符不会超过 M+N 个</p>
 *
 * @author wangzi
 */
public class KMP {
    /**
     * 失败转移数组
     */
    private int[] next;
    /**
     * 模式字符串
     */
    private final String pattern;

    public KMP(String pattern) {
        this.pattern = pattern;
        this.next = new int[pattern.length()];

        // 构造next数组
        initNext();
    }

    private void initNext() {
        // j已经在最左侧时，不可能在移动，因此初始化为-1
        next[0] = -1;
        int j = 0, k = -1;
        while (j < pattern.length() - 1) {
            if (k == -1 || pattern.charAt(j) == pattern.charAt(k)) {
                // next[++j] = ++k;
                // 这种情况下，回退后仍然失配，因此需要继续回退
                if (pattern.charAt(++j) == pattern.charAt(++k)) {
                    next[j] = next[k];
                } else {
                    next[j] = k;
                }
            } else {
                k = next[k];
            }
        }
    }

    public int search(String txt) {
        int i = 0, j = 0;
        while (i < txt.length() && j < pattern.length()) {
            if (j == -1 || txt.charAt(i) == pattern.charAt(j)) {
                // j为-1时，只需要移动i，同时j归0；匹配成功时，同时前进
                i++;
                j++;
            } else {
                // 不匹配时，j回到指定位置
                j = next[j];
            }
        }
        // 匹配成功
        if (j == pattern.length()) {
            return i - j;
        }
        // 匹配失败
        return -1;
    }

    public static void main(String[] args) {
        String pattern = "ABABAC";
        String txt = "BCBAABACAABABACAA";
        KMP kmp = new KMP(pattern);

        int offset = kmp.search(txt);
        if (offset == -1) {
            System.out.println("not found");
            return;
        }

        System.out.println(txt);
        for (int i = 0; i < offset; i++) {
            System.out.print(" ");
        }
        System.out.print(pattern);
    }
}
