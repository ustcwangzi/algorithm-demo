/**
 * <p>Title: KMPAlgorithm</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/2/18</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.others;

/**
 * <p>KMP算法</p>
 * <p>
 *     KMP算法的核心，是一个被称为部分匹配表PMT(Partial Match Table)的数组。
 *     先解释一下字符串的前缀和后缀。如果字符串A和B，存在A=BS，其中S是任意的非空字符串，那就称B为A的前缀。
 *     例如，”Harry”的前缀包括{”H”, ”Ha”, ”Har”, ”Harr”}，我们把所有前缀组成的集合，称为字符串的前缀集合。
 *     同样可以定义后缀A=SB， 其中S是任意的非空字符串，那就称B为A的后缀，例如，”Potter”的后缀包括{”otter”, ”tter”, ”ter”, ”er”, ”r”}，
 *     然后把所有后缀组成的集合，称为字符串的后缀集合。要注意的是，字符串本身并不是自己的后缀。
 *
 *     PMT中的值是字符串的前缀集合与后缀集合的交集中最长元素的长度。
 *     例如，对于”aba”，它的前缀集合为{”a”, ”ab”}，后缀集合为{”ba”, ”a”}。两个集合的交集为{”a”}，那么长度最长的元素就是字符串”a”了，
 *     长度为1，所以对于”aba”而言，它在PMT表中对应的值就是1。
 *     再比如，对于字符串”ababa”，它的前缀集合为{”a”, ”ab”, ”aba”, ”abab”}，它的后缀集合为{”baba”, ”aba”, ”ba”, ”a”}，
 *     两个集合的交集为{”a”, ”aba”}，其中最长的元素为”aba”，长度为3，所以对于”ababa”而言，它在PMT表中对应的值就是3。
 *     因此，对于"abababca"，其PMT表如下：
 *     index: 0 1 2 3 4 5 6 7
 *     array: a b a b a b c a
 *       pmt: 0 0 1 2 3 4 0 1
 *     接下来看如何根据PMT表来加速字符串的查找，如下图要在主字符串"ababababca"中查找模式字符串"abababca"。
 *     index: 0 1 2 3 4 5 6 7 8 9
 *       str: a b a b a b a b c a
 *               --------
 *     match: a b a b a b c a
 *           --------
 *     在str[6]处失配，那么主字符串和模式字符串的前边6位就是相同的。又因为模式字符串的前6位中，前4位前缀和后4位后缀是相同的(pmt[5]==4)，
 *     所以我们推知主字符串str[6]之前的4位和模式字符串开头的4位是相同的。就是图中的划线部分。那这部分就不用再比较了。
 *
 *     因此，可以使用PMT加速字符串的查找了。如果是在j位失配，那么影响j指针回溯的位置的其实是第j−1位的PMT值，所以不直接使用PMT数组，
 *     而是将PMT数组向后偏移一位，把新得到的数组称为next数组。其中在把PMT进行向右偏移时，第0位设成-1，这只是为了编程方便，并没有其他的意义。
 *     index: 0 1 2 3 4 5 6 7
 *     array: a b a b a b c a
 *       pmt: 0 0 1 2 3 4 0 1
 *      next:-1 0 0 1 2 3 4 0
 *
 *     再看一下如何快速求得next数组。
 *     其实，求next数组的过程完全可以看成字符串匹配的过程，即以模式字符串为主字符串，以模式字符串的前缀为目标字符串，一旦字符串匹配成功，
 *     那么当前的next值就是匹配成功的字符串的长度。
 *     具体来说，就是P串错开一位匹配，0起始的P串与1起始的P串，前者是瞄准了P的前缀，后者抛弃了P[0]所以是瞄准p的后缀，
 *     双方的公共部分即是公共前后缀。
 *     index: 0 1 2 3 4 5 6 7
 *     array: a b a b a b c a
 *              a b a b a b c a              next[2]=0
 *                a b a b a b c a            next[3]=1
 *                a b a b a b c a            next[4]=2
 *                a b a b a b c a            next[5]=3
 *                a b a b a b c a            next[6]=4
 *                        a b a b a b c a    next[7]=0
 *     匹配成功时，有next[i]=j；失配时，令j=next[j]继续匹配(因为next[j]之前的字符都已经是匹配过的)。
 *     这种解决方法有个弊端，比如"abacabab"中匹配"abab"，"abab"的next数组为[-1,0,0,1]，
 *         str: a b a c a b a b
 *     pattern: a b a b
 *        next:-1 0 0 1
 *     c与b不匹配，跳到位置1：
 *         str: a b a c a b a b
 *     pattern:       a b a b
 *        next:      -1 0 0 1
 *     可以看到c与a还是不匹配的，因为当P[j] != S[i]时，下次匹配必然是P[next[j]]与S[i]，如果P[j]==P[next[j]]，则P[next[j]]!=S[i]，
 *     因此，计算next数组时，匹配成功时，还要比较P[j]与P[next[j]]是否相等，若相等，需要再次递归，即next[j] = next[next[j]]。
 *     优化过后，"abab"的next数组为[-1 0 -1 0]。
 * </p>
 * <p>
 *     文本串的长度为n，模式串的长度为m，那么匹配过程的时间复杂度为O(n)，计算next的时间复杂的为O(m)，时间复杂度为O(m + n)。
 * </p>
 *
 * @author wangzi
 */
public class KMPAlgorithm {

    public static int kmpSearch(String str, String pattern) {
        if (str == null || pattern == null || pattern.length() < 1 || str.length() < pattern.length()) {
            return -1;
        }
        char[] strArray = str.toCharArray();
        char[] patternArray = pattern.toCharArray();
        int[] nextArray = getNextArray(patternArray);

        int i = 0, j = 0;
        while (i < str.length() && j < pattern.length()) {
            if (j == -1 || strArray[i] == patternArray[j]) {
                i++;
                j++;
            } else {
                j = nextArray[j];
            }
        }
        return j == pattern.length() ? i - j : -1;
    }

    private static int[] getNextArray(char[] array) {
        if (array.length == 1) {
            return new int[]{-1};
        }
        int[] next = new int[array.length];
        next[0] = -1;
        int j = 0, k = -1;
        while (j < next.length - 1) {
            // array[k]表示前缀，array[j]表示后缀
            if (k == -1 || array[j] == array[k]) {
                ++j;
                ++k;

                // 优化前代码
                // next[j] = k;

                // 以下为优化后代码
                if (array[j] == array[k]) {
                    // 当两个字符相等时要跳过
                    next[j] = next[k];
                } else {
                    next[j] = k;
                }
            } else {
                k = next[k];
            }
        }
        return next;
    }

    public static void main(String[] args) {
        String str = "abcabcababaccc";
        String pattern = "ababa";
        System.out.println(kmpSearch(str, pattern));
    }
}
