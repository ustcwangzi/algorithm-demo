/**
 * <p>Title: PreInArrayToPosArray</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/11/10</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.binarytree;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>通过先序和中序数组生成后序数组</p>
 *
 * @author wangzi
 */
public class PreInArrayToPosArray {
    public static int[] getPosArray(int[] preArray, int[] inArray) {
        if (preArray == null || inArray == null) {
            return null;
        }

        int length = preArray.length;
        int[] posArray = new int[length];
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < length; i++) {
            map.put(inArray[i], i);
        }
        setPos(preArray, 0, length - 1, inArray, 0, length - 1, posArray, length - 1, map);
        return posArray;
    }

    /**
     * 从右向左填充后序数组posArray，posStart为本次填充的位置，返回值为下一个该填充的位置
     */
    private static int setPos(int[] preArray, int preStart, int preEnd, int[] inArray, int inStart, int inEnd,
                              int[] posArray, int posStart, Map<Integer, Integer> map) {
        if (preStart > preEnd) {
            return posStart;
        }

        posArray[posStart--] = preArray[preStart];
        int index = map.get(preArray[preStart]);
        posStart = setPos(preArray, preEnd - inEnd + index + 1, preEnd, inArray, index + 1, inEnd, posArray, posStart, map);
        return setPos(preArray, preStart + 1, preStart + index - inStart, inArray, inStart, index - 1, posArray, posStart, map);
    }

    public static void main(String[] args) {
        int[] preArray = {1, 2, 4, 5, 8, 9, 3, 6, 7};
        int[] inArray = {4, 2, 8, 5, 9, 1, 6, 3, 7};
        int[] posArray = getPosArray(preArray, inArray);
        System.out.println(Arrays.toString(posArray));
    }
}
