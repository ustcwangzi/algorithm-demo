/**
 * <p>Title: RabinKarp</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/8/19</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.search;

import java.math.BigInteger;
import java.util.Random;

/**
 * <p>RabinKarp指纹字符串查找算法</p>
 * <p>
 *     计算模式字符串的散列值，然后用相同的散列函数计算文本中所有可能的M个字符的自字符串散列值
 *     如果找到一个散列值个模式字符串相同的子字符串，则继续验证两者是否匹配
 * </p>
 *
 * @author wangzi
 */
public class RabinKarp {
    /**
     * 字母表大小
     */
    private final int RADIX = 256;
    /**
     * 模式字符串
     */
    private final String pattern;
    /**
     * 模式字符串的散列值
     */
    private final long patternHash;
    /**
     * 模式字符串的长度
     */
    private final int patternLength;
    /**
     * 一个很大的素数
     */
    private final long prime;
    /**
     * RADIX^(patternLength-1) % prime
     */
    private long RM;

    public RabinKarp(String pattern) {
        this.pattern = pattern;
        this.patternLength = pattern.length();
        this.prime = longRandomPrime();
        this.patternHash = hash(pattern, patternLength);

        // 用于减去第一个数字时的计算
        RM = 1;
        for (int i = 1; i <= patternLength - 1; i++) {
            RM = (RADIX * RM) % prime;
        }
    }

    /**
     * 计算散列值
     */
    private long hash(String key, int m) {
        long h = 0;
        for (int j = 0; j < m; j++) {
            h = (RADIX * h + key.charAt(j)) % prime;
        }
        return h;
    }

    /**
     * 字符串比较
     */
    private boolean check(String txt, int i) {
        for (int j = 0; j < patternLength; j++) {
            if (pattern.charAt(j) != txt.charAt(i + j)) {
                return false;
            }
        }
        return true;
    }

    public int search(String txt) {
        if (txt.length() < patternLength) {
            return -1;
        }
        long txtHash = hash(txt, patternLength);

        if ((patternHash == txtHash) && check(txt, 0)) {
            return 0;
        }

        for (int i = patternLength; i < txt.length(); i++) {
            // 减去第一个数字，加上最后一个数字，再次进行匹配
            txtHash = (txtHash + prime - RM * txt.charAt(i - patternLength) % prime) % prime;
            txtHash = (txtHash * RADIX + txt.charAt(i)) % prime;

            int offset = i - patternLength + 1;
            if ((patternHash == txtHash) && check(txt, offset)) {
                return offset;
            }
        }

        return -1;
    }

    private static long longRandomPrime() {
        BigInteger prime = BigInteger.probablePrime(31, new Random());
        return prime.longValue();
    }

    public static void main(String[] args) {
        String pattern = "ABABAC";
        String txt = "BCBAABACAABABACAA";
        RabinKarp karp = new RabinKarp(pattern);

        int offset = karp.search(txt);
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
