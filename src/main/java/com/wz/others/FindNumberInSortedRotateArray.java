/**
 * <p>Title: FindNumberInSortedRotateArray</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/1/27</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.others;

/**
 * <p>在有序旋转数组中找到一个数</p>
 * <p>
 *     有序数组array可能经过一次旋转处理，也可能没有，且array可能存在重复的数。
 *     例如[1,2,3,4,5,6,7]，可以旋转成[4,5,6,7,1,2,3]等。给定一个可能旋转过的有序数组array，再给定一个数n，返回array中是否含有n。
 *     解决方案：
 *        如果一个有序数组经过旋转后，只有一个位置会出现降序，其余部分都是升序。可使用二分查找法。
 *        初始时，low=0，high=array.length-1，mid=(low+high)/2。
 *        1、在array[low] != array[mid]的情况下，如果array[low] < array[mid]，说明array[low...mid]是有序的，
 *           如果number大于array[low]并且小于array[mid]，说明number在左半区，令high = mid-1；
 *           如果number不在array[low...mid]区间，则一定在右半区，令low = mid+1。
 *        2、在array[mid] != array[high]的情况下，如果array[mid] < array[high]，说明array[mid...high]是有序的，
 *           如果number大于array[mid]并且小于array[high]，说明number在右半区，令low = mid+1；
 *           如果number不在array[mid...high]区间，则一定在左半区，令high = mid-1。
 *        3、在array数组中可能存在重复的值，那么就可能发生array[low] == array[mid] == array[high]的情况，
 *           此时从low位置开始向右遍历，直到array[low] != array[mid]。
 *           如果遍历到mid位置都一直与array[mid]相等，说明左半区都是一个值，所以number一定出现在右半区，令low = mid + 1。
 * </p>
 *
 * @author wangzi
 */
public class FindNumberInSortedRotateArray {
    public static boolean isContains(int[] array, int number) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("array is empty");
        }
        int low = 0, mid, high = array.length - 1;
        while (low <= high) {
            mid = (low + high) / 2;
            if (array[mid] == number) {
                return true;
            }
            if (array[low] == array[mid] && array[mid] == array[high]) {
                while (low != mid && array[low] == array[mid]) {
                    low++;
                }
                // array[low...mid]都是同一个值，number一定在array[mid...high]
                if (low == mid) {
                    low = mid + 1;
                    continue;
                }
            }
            if (array[low] != array[mid]) {
                // array[low...mid]有序
                if (array[mid] > array[low]) {
                    if (number >= array[low] && number < array[mid]) {
                        high = mid - 1;
                    } else {
                        low = mid + 1;
                    }
                } else {
                    // array[mid...high]有序
                    if (number > array[mid] && number <= array[high]) {
                        low = mid + 1;
                    } else {
                        high = mid - 1;
                    }
                }
            } else {
                // array[mid] != array[high]
                // array[mid...high]有序
                if (array[mid] < array[high]) {
                    if (number > array[mid] && number <= array[high]) {
                        low = mid + 1;
                    } else {
                        high = mid - 1;
                    }
                } else {
                    // array[low...mid]有序
                    if (number >= array[low] && number < array[mid]) {
                        high = mid - 1;
                    } else {
                        low = mid + 1;
                    }
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        int[] array = {4, 5, 6, 7, 1, 2, 3};
        System.out.println(isContains(array, 2));

        array = new int[]{4, 4, 4, 4, 4, 1, 2, 3};
        System.out.println(isContains(array, 2));

        array = new int[]{4, 1, 2, 3, 4, 4, 4, 4, 4};
        System.out.println(isContains(array, 2));

        array = new int[]{1, 2, 3, 1, 1, 1, 1};
        System.out.println(isContains(array, 2));
    }
}
