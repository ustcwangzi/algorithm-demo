/**
 * <p>Title: SubMatrixMaxSum</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/1/7</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.arrayandmatrix;

/**
 * <p>在数组中找到一个局部最小的位置</p>
 * <p>
 *     若数组长度为1，则array[0]是局部最小；array长度为N(N>1)时，若array[0]<array[1]，则array[0]为局部最小；
 *     若array[N-1]<array[N-2]，则array[N-1]为局部最小；若0<i<N-1，既有array[i]<array[i-1]，又有array[i]<array[i+1]，
 *     则array[i]最局部最小。
 *     现给定无序数组array，已知array中任意两个相邻的数都不相等，获取array中任意一个局部最小出现的位置。
 *     解决方案：
 *        1、若array为空或长度为0，返回-1表示不存在局部最小；
 *        2、若array长度为1或array[0]<array[1]，说明array[0]为局部最小，返回1；
 *        3、若array[N-1]<array[N-2]，说明array[N-1]为局部最小，返回N-1；
 *        4、若array长度大于2且array的左右两头均不是局部最小，则令left=1，right=N-2，然后进入步骤五做二分查找；
 *        5、令mid=(left+right)/2，然后进行以下判断：
 *        5.1、如果array[mid]>array[mid-1]，说明在array[left...mid-1]上肯定存在局部最小，令right=mid-1，重复步骤五；
 *        5.2、不满足5.1，但array[mid]>array[mid+1]，说明array[mid+1...right]上肯定存在局部最小，令left=mid+1，重复步骤五；
 *        5.3、既不满足5.1，又不满足5.2，那么array[mid]就是局部最小，直接返回mid；
 *        6、步骤五一直进行二分查找，直到left==right停止，返回left即可。
 *        如此可见，二分查找并不是数组有序时才能使用，只要能确定二分两侧的某一侧肯定存在要找的内容，就可以使用二分查找。
 * </p>
 * <p>
 *     时间复杂度为O(logN)，空间复杂度为O(1)
 * </p>
 *
 * @author wangzi
 */
public class FindOneLessValueIndex {
    public static int getLessIndex(int[] array) {
        // 不存在局部最小
        if (array == null || array.length == 0) {
            return -1;
        }
        // array[0]为局部最小
        if (array.length == 1 || array[0] < array[1]) {
            return 0;
        }
        // array[array.length-1]为局部最小
        if (array[array.length - 1] < array[array.length - 2]) {
            return array.length - 1;
        }

        int left = 1, right = array.length - 2, mid;
        // 可以确定二分两侧的某一侧肯定存在要找的内容，因此可使用二分法查找
        while (left < right) {
            mid = (left + right) / 2;
            if (array[mid] > array[mid - 1]) {
                right = mid - 1;
            } else if (array[mid] > array[mid + 1]) {
                left = mid + 1;
            } else {
                return mid;
            }
        }
        return left;
    }

    public static void main(String[] args) {
        int[] array = {6, 5, 3, 4, 6, 7, 8};
        int index = getLessIndex(array);
        System.out.println("index: " + index + " ,value: " + array[index]);
    }
}
