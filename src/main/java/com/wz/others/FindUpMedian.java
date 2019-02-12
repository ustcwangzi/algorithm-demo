/**
 * <p>Title: FindUpMedian</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/2/12</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.others;

/**
 * <p>在两个长度相等的有序数组中找到上中位数</p>
 * <p>
 *     给定两个有序数组self和other，已知两个数组的长度都为N，求两个数组中所有数的上中位数。
 *     例如：
 *        self=[1,2,3,4]，other=[3,4,5,6]，共8个数，那么上中位数是第4个数，即3；
 *        self=[0,1,2]，other=[3,4,5]，共6个数，那么上中位数是第3个数，即2。
 *     解决方案：
 *        使用二分查找，过程如下：
 *        1、找到两个数组的中间位置mid1和mid2，mid1=(start1+end1)/2，mid2=(start2+end2)/2；
 *        2、如果self[mid1] == other[mid2]，不管每个数组中元素的个数是奇数还是偶数，这两个数都可以是整体的上中位数，返回其中一个就可以；
 *        3、如果self[mid1] > other[mid2]
 *        3.1、每个数组的个数是奇数的情况下：
 *             数组self中mid1位置以后的数都不可能是整体的上中位数，数组other中mid2位置以前的数都不可能是整体的上中位数，
 *             所以现在只需要考虑self[start1...mid1]、other[mid2...end2]，这两部分的元素个数相同，它们的上中位数就是整体的上中位数；
 *        3.2、每个数组的个数是偶数的情况下：
 *             数组self中mid1位置以后的数都不可能是整体的上中位数，数组other中mid2位置以后包括mid2位置，都不可能是整体的上中位数，
 *             所以现在只需要考虑self[start1...mid1]、other[mid2+1...end2]，这两部分的元素个数相同，它们的上中位数就是整体的上中位数；
 *        4、如果self[mid1] < other[mid2]
 *        4.1、每个数组的个数是奇数的情况下：
 *             数组self中mid1位置以前的数都不可能是整体的上中位数，数组other中mid2位置以后的数都不可能是整体的上中位数，
 *             所以现在只需要考虑self[mid1...end1]、other[start2...mid2]，这两部分的元素个数相同，它们的上中位数就是整体的上中位数；
 *        4.2、每个数组的个数是偶数的情况下：
 *             数组self中mid1位置以前包括mid1位置，都不可能是整体的上中位数，数组other中mid2位置以后的数都不可能是整体的上中位数，
 *             所以现在只需要考虑self[mid1+1...end1]、other[start2...mid2]，这两部分的元素个数相同，它们的上中位数就是整体的上中位数；
 * </p>
 * <p>
 *     时间复杂度为O(logN)，空间复杂度为O(1)
 * </p>
 *
 * @author wangzi
 */
public class FindUpMedian {
    public static int getUpMedian(int[] self, int[] other) {
        if (self == null || self.length == 0 || other == null || self.length != other.length) {
            throw new RuntimeException("invalid array");
        }
        int start1 = 0, mid1, end1 = self.length - 1;
        int start2 = 0, mid2, end2 = other.length - 1;
        int offset;
        while (start1 < end1) {
            mid1 = (start1 + end1) / 2;
            mid2 = (start2 + end2) / 2;
            // 元素个数为奇数，offset为0，元素个数为偶数，offset为1
            offset = ((end1 - start1 + 1) & 1) ^ 1;
            if (self[mid1] > other[mid2]) {
                // 在self[start1...mid1]、other[mid2+offset...end2]寻找
                end1 = mid1;
                start2 = mid2 + offset;
            } else if (self[mid1] < other[mid2]) {
                // 在self[mid1+offset...end1]、other[start2...mid2]寻找
                start1 = mid1 + offset;
                end2 = mid2;
            } else {
                return self[mid1];
            }
        }
        return Math.min(self[start1], other[start2]);
    }

    public static void main(String[] args) {
        int[] self = {1, 2, 3, 4};
        int[] other = {3, 4, 5, 6};
        System.out.println(getUpMedian(self, other));

        self = new int[]{0, 1, 2};
        other = new int[]{3, 4, 5};
        System.out.println(getUpMedian(self, other));
    }
}
