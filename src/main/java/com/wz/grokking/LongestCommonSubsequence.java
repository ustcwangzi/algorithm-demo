/**
 * <p>Title: LongestCommonSubsequence</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/9/9</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.grokking;

import java.util.Arrays;

/**
 * <p>最长公共子序列</p>
 *
 * @author wangzi
 */
public class LongestCommonSubsequence {
    private static int[][] searchCell(String wordA, String wordB) {
        if (wordA == null || wordB == null) {
            return null;
        }

        int[][] cell = new int[wordA.length()][wordB.length()];
        for (int i = 0; i < wordA.length(); i++) {
            for (int j = 0; j < wordB.length(); j++) {
                if (wordA.charAt(i) == wordB.charAt(j)) {
                    if (i > 0 && j > 0) {
                        cell[i][j] = cell[i - 1][j - 1] + 1;
                    } else {
                        cell[i][j] = 1;
                    }
                } else {
                    if (i == 0 && j > 0) {
                        cell[i][j] = cell[i][j - 1];
                    } else if (i > 0 && j == 0) {
                        cell[i][j] = cell[i - 1][j];
                    } else if (i > 0 && j > 0) {
                        cell[i][j] = Math.max(cell[i - 1][j], cell[i][j - 1]);
                    } else {
                        cell[i][j] = 0;
                    }
                }
            }
        }
        return cell;
    }

    public static void main(String[] args) {
        String wordA = "fish";
        String wordB = "hish";
        int[][] cell = searchCell(wordA, wordB);
        Arrays.stream(cell).forEach(c -> System.out.println(Arrays.toString(c)));
    }
}
