/**
 * <p>Title: QuickSort</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/9/2</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.grokking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>快速排序</p>
 * <p>平均运行时间为O(n*log n)，最快情况下为 O(n*n)</p>
 *
 * @author wangzi
 */
public class QuickSort {
    private static List<Integer> quickSort(List<Integer> list) {
        if (list.size() < 2) {
            return list;
        }
        Integer pivot = list.get(0);

        // 小于pivot
        List<Integer> less = list.stream().skip(1).filter(element -> element <= pivot)
                .collect(Collectors.toList());
        // 大于pivot
        List<Integer> greater = list.stream().skip(1).filter(element -> element > pivot)
                .collect(Collectors.toList());

        return Stream.of(quickSort(less).stream(), Stream.of(pivot), quickSort(greater).stream())
                .flatMap(Function.identity()).collect(Collectors.toList());
    }

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>(Arrays.asList(5, 3, 6, 2, 10));
        System.out.println(quickSort(list));
    }
}
