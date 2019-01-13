/**
 * <p>Title: SmallestMissNum</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/1/13</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.arrayandmatrix;

/**
 * <p>数组中未出现的最小正整数</p>
 * <p>
 *     给定一个无序整型数组array，找到数组中未出现的最小正整数。
 *     例如，array=[1,0,2,4]，返回3。
 *     解决方案：
 *        1、生成变量left、right，left表示遍历到目前为止，数组已包含的正整数范围是[1...left]，初始时left=0；
 *           right表示遍历到目前为止，在后续出现最优状况的情况下，数组可能包含的正正数范围是[1...right]，初始时right=N，
 *           因为还没有开始遍历，所以后续出现最优状况是array包含1～N所有的整数，right同时表示array当前的结束位置
 *        2、从左到右遍历array，遍历到位置left
 *        3、如果array[left]==left+1，没有遍历array[left]之前，array已经包含的正整数范围是[1...left]，
 *           此时出现了array[left]==left+1，所以array包含的正整数范围可以扩到[1...left+1]，即令left++
 *        4、如果array[left]<=left，没有遍历array[left]之前，array在后续最优的情况下可能包含的正整数范围是[1...right]，
 *           已经包含的正整数范围是[1...left]，所以需要[left+1...right]上的数，而此时出现了array[left]<=left，
 *           说明[left+1...right]上的数少了一个，即array在后续最优的情况下可能包含的正整数范围缩小到了[1...right-1]，
 *           此时把array最后位置的数(array[right-1])放在left位置上，下一步检查这个数，然后令right--
 *        5、如果array[left]>right，与步骤四同理，把array最后位置的数(array[right-1])放在left位置上，下一步检查这个数，令right--
 *        6、如果array[array[left]-1]==array[left]，步骤四和步骤五未中，说明array[left]是在array[left+1...right]范围的数，
 *           而这个数应该在位置array[left]-1上，而此时发现array[left]-1位置上的数是array[left]，说明出现了两个array[left]，
 *           即在[left+1...right]上出现了重复值，那么[left+1...right]范围上的数又少了一个，所以步骤四一样，
 *           把array最后位置的数(array[right-1])放在left位置上，下一步检查这个数，令right--
 *        7、如果步骤四、步骤五和步骤六都没中，说明发现了[left+1...right]范围上的数，且未出现重复，那么array[left]应该放到
 *           array[left]-1位置上，所以把left位置上的数与array[left]-1位置上的数交换，下一步继续遍历left位置上的数
 *        8、重复步骤二，直到left==right，array已经包含的正整数范围是[1...left]，返回left+1即可。
 * </p>
 * <p>
 *     时间复杂度为O(N)，空间复杂度为O(1)
 * </p>
 *
 * @author wangzi
 */
public class SmallestMissNum {

    public static int missNum(int[] array) {
        if (array == null || array.length == 0) {
            return 1;
        }
        // array已经包含的正整数范围是array[1...left]
        int left = 0;
        // 在后续出现最优情况下，array可能包含的正整数范围是array[1...right]
        int right = array.length;
        while (left < right) {
            if (array[left] == left + 1) {
                // array包含的正整数范围扩展到array[1...left+1]
                left++;
            } else if (array[left] <= left || array[left] > right || array[array[left] - 1] == array[left]) {
                // array[left+1...right]上的数少了一个，将array最后位置的数放在left位置上，下一步检查这个数
                array[left] = array[--right];
            } else {
                // 发现了array[left+1...right]上的数，并且未重复，交换left与array[left]-1位置上的数
                swap(array, left, array[left] - 1);
            }
        }
        return left + 1;
    }

    private static void swap(int[] array, int self, int other) {
        if (self == other) {
            return;
        }
        int tmp = array[self];
        array[self] = array[other];
        array[other] = tmp;
    }

    public static void main(String[] args) {
        int[] array = {-1, 0, 2, 1, 3, 5};
        ;
        System.out.println(missNum(array));
    }
}
