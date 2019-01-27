/**
 * <p>Title: FindMinInSortedRotateArray</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/1/27</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.others;

/**
 * <p>在有序旋转数组中找到最小值</p>
 * <p>
 *     有序数组array可能经过一次旋转处理，也可能没有，且array中可能存在重复的数。
 *     例如[1,2,3,4,5,6,7]，可以旋转成[4,5,6,7,1,2,3]等。给定一个可能旋转过的有序数组array，获取array中的最小值。
 *     解决方案：
 *        把未经过旋转前，有序数组array中最左边的数，在经过旋转后所处的位置叫作"断点"。如果未经过旋转处理，则断点在位置0。
 *        因此只要找到断点，就找到了最小值。
 *        假设目前在array[low...high]范围上找最小值，初始时low=0，high=array.length-1，具体过程如下：
 *        1、如果array[low]<array[high]，说明array[low...high]没有经过旋转，断点就是array[low]
 *        2、令mid=(low+high)/2，即mid是array[low...high]的中间位置
 *        2.1、如果array[low]>array[mid]，说明断点一定在array[low...mid]上，令high=mid，然后回到步骤一
 *        2.2、如果array[mid]>array[high]，说明断点一定在array[mid...high]上，令low=mid，然后回到步骤一
 *        3、如果步骤一和步骤二都不满足，说明array[low]==array[mid]==array[high]
 *        3.1、low<mid时，从low开始向右遍历array(low++)
 *        3.2、如果array[low]==array[mid]，low继续向右移动，即low++，直到low>=mid
 *        3.3、如果array[low]<array[mid]，即发生了降序，说明此时low就是断点，直接返回
 *        3.4、如果array[low]>array[mid]，说明断点在array[low...mid]上，令high=mid，回到步骤一继续二分
 *        3.5、如果low>=mid，说明array[low...high]上的值都一样，断点在array[mid...high]上，此时low==mid，回到步骤一继续二分
 * </p>
 *
 * @author wangzi
 */
public class FindMinInSortedRotateArray {
    public static int findMin(int[] array) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("array is empty");
        }
        int low = 0, mid, high = array.length - 1;
        while (low < high) {
            if (low == high - 1) {
                break;
            }
            if (array[low] < array[high]) {
                // 没有经过旋转，断点就是array[low]
                return array[low];
            }
            mid = (low + high) / 2;
            if (array[low] > array[mid]) {
                // 断点在[low...mid]上
                high = mid;
                continue;
            }
            if (array[mid] > array[high]) {
                // 断点在[mid...high]上
                low = mid;
                continue;
            }
            // array[low]==array[mid]==array[high]
            while (low < mid) {
                if (array[low] == array[mid]) {
                    low++;
                } else if (array[low] < array[mid]) {
                    // 发现的降序必然是断点
                    return array[low];
                } else {
                    // 断点在[low...mid]上，继续二分
                    high = mid;
                    break;
                }
            }
        }
        return Math.min(array[low], array[high]);
    }

    public static void main(String[] args) {
        int[] array = {4, 5, 5, 5, 1, 2, 3};
        System.out.println(findMin(array));

        array = new int[]{4, 4, 4, 4, 4, 1, 2, 3, 4};
        System.out.println(findMin(array));

        array = new int[]{4, 1, 2, 3, 4, 4, 4, 4, 4};
        System.out.println(findMin(array));

        array = new int[]{1, 2, 3, 1, 1, 1, 1};
        System.out.println(findMin(array));
    }
}
