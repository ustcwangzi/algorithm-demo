package com.wz;

import com.wz.binarytree.PreInArrayToPosArray;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>通过先序和中序数组生成后续数组</p>
 *
 * @author wangzi
 */
public class PreInArrayToPosArrayTest {
    private static int[] solution(int[] preArray, int[] inArray) {
        int length = preArray.length;
        Map<Integer, Integer> map = new HashMap<>(length);
        for (int i = 0; i < length; i++) {
            map.put(inArray[i], i);
        }
        int[] posArray = new int[length];
        setPos(preArray, 0, length - 1, inArray, 0, length - 1, posArray, length - 1, map);
        return posArray;
    }

    private static int setPos(int[] preArray, int preStart, int preEnd, int[] inArray, int inStart, int inEnd,
                              int[] posArray, int posIndex, Map<Integer, Integer> map) {
        if (preStart > preEnd) {
            return posIndex;
        }
        posArray[posIndex--] = preArray[preStart];
        int inIndex = map.get(preArray[preStart]);
        posIndex = setPos(preArray, preEnd - inEnd + inIndex + 1, preEnd, inArray, inIndex + 1, inEnd, posArray, posIndex, map);
        return setPos(preArray, preStart + 1, preStart - inStart + inIndex, inArray, inStart, inIndex - 1, posArray, posIndex, map);
    }

    public static void main(String[] args) {
        int[] preArray = {1, 2, 4, 5, 3, 6, 7};
        int[] inArray = {4, 2, 5, 1, 6, 3, 7};
        System.out.println(Arrays.equals(solution(preArray, inArray), PreInArrayToPosArray.getPosArray(preArray, inArray)));
        preArray = new int[]{1, 2, 4, 5, 8, 9, 3, 6, 7};
        inArray = new int[]{4, 2, 8, 5, 9, 1, 6, 3, 7};
        System.out.println(Arrays.equals(solution(preArray, inArray), PreInArrayToPosArray.getPosArray(preArray, inArray)));
    }
}
