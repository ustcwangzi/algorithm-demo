/**
 * <p>Title: FindKthNumber</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/2/13</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.others;

/**
 * <p>在两个有序数组中找到第K小的数</p>
 *
 * @author wangzi
 */
public class FindKthNumber {

    public static int findKthNumber(int[] self, int[] other, int k) {
        if (self == null || self.length == 0 || other == null || other.length == 0) {
            throw new RuntimeException("invalid array");
        }
        if (k < 1 || k > self.length + other.length) {
            throw new RuntimeException("invalid k");
        }

        int[] longArray = self.length >= other.length ? self : other;
        int[] shortArray = self.length < other.length ? self : other;
        int lenLong = longArray.length, lenShort = shortArray.length;
        if (k <= lenShort) {
            return getUpMedian(shortArray, 0, k - 1, longArray, 0, k - 1);
        }
        if (k > lenLong) {
            if (shortArray[k - lenLong - 1] >= longArray[lenLong - 1]) {
                return shortArray[k - lenLong - 1];
            }
            if (longArray[k - lenShort - 1] >= shortArray[lenShort - 1]) {
                return longArray[k - lenShort - 1];
            }
            return getUpMedian(shortArray, k - lenLong, lenShort - 1, longArray, k - lenShort, lenLong - 1);
        }
        if (longArray[k - lenShort - 1] >= shortArray[lenShort - 1]) {
            return longArray[k - lenShort - 1];
        }
        return getUpMedian(shortArray, 0, lenShort - 1, longArray, k - lenShort, k - 1);
    }

    /**
     * self[start1...end1]、other[start2...end2]上查找上中位数
     */
    private static int getUpMedian(int[] self, int start1, int end1, int[] other, int start2, int end2) {
        int mid1, mid2, offset;
        while (start1 < end1) {
            mid1 = (start1 + end1) / 2;
            mid2 = (start2 + end2) / 2;
            // 元素个数为奇数，offset为0，元素个数为偶数，offset为1
            offset = ((end1 - start1 + 1) & 1) ^ 1;
            if (self[mid1] > other[mid2]) {
                end1 = mid1;
                start2 = mid2 + offset;
            } else if (self[mid1] < other[mid2]) {
                start1 = mid1 + offset;
                end2 = mid2;
            } else {
                return self[mid1];
            }
        }
        return Math.min(self[start1], other[start2]);
    }

    public static void main(String[] args) {
        int[] self = {1, 2, 3, 4, 5};
        int[] other = {3, 4, 5};
        for (int i = 1; i <= self.length + other.length; i++) {
            System.out.println(i + "th: " + findKthNumber(self, other, i));
        }
    }
}
