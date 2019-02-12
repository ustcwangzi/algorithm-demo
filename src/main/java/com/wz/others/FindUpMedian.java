/**
 * <p>Title: FindUpMedian</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/2/12</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.others;

/**
 * <p>在两个长度相等的排序数组中找到上中位数</p>
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
