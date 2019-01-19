/**
 * <p>Title: PaperFolding</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/1/19</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.others;

/**
 * <p>折纸问题</p>
 *
 * @author wangzi
 */
public class PaperFolding {

    public static void printAllFolds(int n) {
        printProcess(1, n, true);
    }

    private static void printProcess(int i, int n, boolean down) {
        if (i > n) {
            return;
        }
        printProcess(i + 1, n, true);
        System.out.println(down ? "down " : "up ");
        printProcess(i + 1, n, false);
    }

    public static void main(String[] args) {
        printAllFolds(4);
    }
}
