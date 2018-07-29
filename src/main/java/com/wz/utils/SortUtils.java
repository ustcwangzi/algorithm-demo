/**
 * <p>Title: SortUtils</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/5/1</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.utils;

import java.util.Arrays;

/**
 * <p>排序工具类</p>
 *
 * @author wangzi
 */
public class SortUtils {
    public static Integer[] arr = {36, 8, 67, 98, 32, 21};

    /**
     * 元素比较
     * self < other : true
     */
    public static boolean less(Comparable self, Comparable other) {
        return self.compareTo(other) < 0;
    }

    /**
     * 元素交换
     */
    public static void exch(Comparable[] arr, int i, int j) {
        Comparable t = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
    }

    /**
     * 单行输出元素
     */
    public static void show(Comparable[] arr) {
        Arrays.asList(arr).forEach(a -> System.out.print(a + " "));
        System.out.println();
    }

    /**
     * 元素是否有序
     */
    public static boolean isSorted(Comparable[] arr) {
        for (int i = 1; i < arr.length; i++) {
            if (less(arr[i], arr[i - 1])) {
                return false;
            }
        }
        return true;
    }

    /**
     * 元素是否有序
     */
    public static boolean isSorted(Comparable[] arr, int low, int high) {
        for (int i = low + 1; i <= high; i++) {
            if (less(arr[i], arr[i - 1])) {
                return false;
            }
        }
        return true;
    }
}
