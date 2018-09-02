/**
 * <p>Title: SelectionSort</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/9/2</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.grokking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>选择排序</p>
 * <p>运行时间为O(n*n)</p>
 *
 * @author wangzi
 */
public class SelectionSort {
    private static List<Integer> selectionSort(List<Integer> list) {
        int size = list.size();
        List<Integer> result = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {
            int smallestIndex = findSmallest(list);
            result.add(list.get(smallestIndex));
            list.remove(smallestIndex);
        }
        return result;
    }

    private static int findSmallest(List<Integer> list) {
        int smallest = list.get(0);
        int smallestIndex = 0;
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i) < smallest) {
                smallest = list.get(i);
                smallestIndex = i;
            }
        }
        return smallestIndex;
    }

    private static int[] selectionSort(int[] array) {
        int[] result = new int[array.length];

        for (int i = 0; i < result.length; i++) {
            int smallestIndex = findSmallest(array);
            result[i] = array[smallestIndex];
            array = getArrayWithoutSmallest(array, smallestIndex);
        }

        return result;
    }

    private static int findSmallest(int[] array) {
        int smallest = array[0];
        int smallestIndex = 0;
        for (int i = 1; i < array.length; i++) {
            if (array[i] < smallest) {
                smallest = array[i];
                smallestIndex = i;
            }
        }
        return smallestIndex;
    }

    private static int[] getArrayWithoutSmallest(int[] array, int smallestIndex) {
        int[] result = new int[array.length - 1];

        for (int i = 0; i < array.length; i++) {
            if (i < smallestIndex) {
                result[i] = array[i];
            } else if (i > smallestIndex) {
                result[i - 1] = array[i];
            }
        }

        return result;
    }

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>(Arrays.asList(5, 3, 6, 2, 10));
        System.out.println(selectionSort(list));

        int[] arr = {5, 3, 6, 2, 10};
        System.out.println(Arrays.toString(selectionSort(arr)));
    }
}
