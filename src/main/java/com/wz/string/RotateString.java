/**
 * <p>Title: RotateString</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/12/7</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.string;

/**
 * <p>翻转字符串</p>
 * <p>
 *     问题一：
 *        给定一个字符类型的数组array，请在单词间做逆序调整，只要做到单词逆序即可，空格位置无特殊要求。
 *        例如，"dog loves pig"调整为"pig loves dog"。
 *     问题二：
 *        给定一个字符类型的数组array和一个整数size，请把大小为size的左半区整体移动到右边，右半区整体移动到左边。
 *        例如，"ABCDE"，size=3，调整为"DEABC"。
 *     问题一解答：
 *        首先把array整体逆序，逆序后，遍历array找到每一个单词，然后再把每一个单词里的字符逆序即可。
 *     问题二解答：
 *        先把array[o...size-1]部分逆序，再把array[size...N-1]部分逆序，最后把array整体逆序即可。
 *        比如"ABCDE"，size=3，先把array[0...2]逆序变成"CBADE"，再把array[3...4]逆序变成"CBAED"，最后整体逆序变成"EDABC"。
 * </p>
 *
 * @author wangzi
 */
public class RotateString {
    public static void rotateWord(char[] array) {
        if (array == null || array.length == 0) {
            return;
        }

        // 整体逆序
        reverse(array, 0, array.length - 1);
        int left = -1, right = -1;
        for (int i = 0; i < array.length; i++) {
            // 找到每个单词的开始、结束位置
            if (array[i] != ' ') {
                left = i == 0 || array[i - 1] == ' ' ? i : left;
                right = i == array.length - 1 || array[i + 1] == ' ' ? i : right;
            }
            // 单词逆序
            if (left != -1 && right != -1) {
                reverse(array, left, right);
                left = -1;
                right = -1;
            }
        }
    }

    public static void rotatePart(char[] array, int size) {
        if (array == null || array.length == 0 || size >= array.length) {
            return;
        }

        reverse(array, 0, size - 1);
        reverse(array, size, array.length - 1);
        reverse(array, 0, array.length - 1);
    }

    private static void reverse(char[] array, int start, int end) {
        char tmp;
        while (start < end) {
            tmp = array[start];
            array[start] = array[end];
            array[end] = tmp;
            start++;
            end--;
        }
    }

    public static void main(String[] args) {
        char[] array1 = {'d', 'o', 'g', ' ', 'l', 'o', 'v', 'e', 's', ' ', 'p', 'i', 'g'};
        System.out.println(array1);
        rotateWord(array1);
        System.out.println(array1);

        char[] array2 = {'1', '2', '3', '4', '5', 'A', 'B', 'C'};
        System.out.println(array2);
        rotatePart(array2, 5);
        System.out.println(array2);
    }
}
