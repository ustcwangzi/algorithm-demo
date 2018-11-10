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
 * <p>
 *     已知一棵二叉树中所有节点的值都不同，给定这棵树先序和中序数组，不重建二叉树，直接生成后序数组
 *     根据当前的先序和中序数组，设置后序数组最右边的元素，然后划分出左子树的先序、中序数组，以及右子树的先序、中序数组，
 *     先根据右子树的划分设置好后序数组，再根据左子树的划分，从右到左依次设置到后序数组全部位置
 *     eg. 先序数组为{1, 2, 4, 5, 3, 6, 7}
 *         中序数组为{4, 2, 5, 1, 6, 3, 7}
 *         1、设置posArray[6]=1，左子树划分为{2, 4, 5}、{4, 2, 5}，右子树划分为{3, 6, 7}、{6, 3, 7}
 *         2、根据{3, 6, 7}、{6, 3, 7}设置posArray[5]=3，左子树划分为{6}、{6}，右子树划分为{7}、{7}
 *         3、根据{7}、{7}设置posArray[4]=7
 *         4、根据{6}、{6}设置posArray[3]=6
 *         5、根据{2, 4, 5}、{4, 2, 5}设置posArray[2]=2，左子树划分为{4}、{4}，右子树划分为{5}、{5}
 *         6、根据{5}、{5}设置posArray[1]=5
 *         7、根据{4}、{4}设置posArray[0]=4
 * </p>
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
