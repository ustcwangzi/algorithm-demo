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
 * <p>
 *     给定两个有序数组self和other，再给定正整数K，求两个数组中所有数的第K小的数。
 *     例如：
 *        self=[1,2,3,4,5]，other=[3,4,5]，k=1，1是所有数中第1小的数，因此返回1；
 *        self=[1,2,3]，other=[3,4,5,6]，k=4，3是所有数中第4小的数，因此返回3。
 *     解决方案：
 *        本题需要使用到"在两个长度相等的有序数组中找到上中位数"，即getUpMedian()，思路见com.wz.others.FindUpMedian。
 *        假设两个数组，长度较短的为shortArray，长度记为lenShort；长度较长的为longArray，长度记为lenLong。那么对于k，有以下三种情况：
 *        1、k < lenShort。
 *           那么在shortArray中选前k个数，在longArray中也选前k个数，这两段数组中的中位数就是整体的第k个最小的数；
 *        2、k > lenLong。
 *           对于longArray中的前k-lenShort-1个数，都不能满足要求，因为即使它们比shortArray中所有的数都大，也无法达到第k个。
 *           longArray中的第k-lenShort个数，如果它比shortArray中的所有数都大，那么它就是第k小数，否则，它也不是。
 *           对于shortArray中的前k-lenLong-1个数，都不能满足要求，因为即使它们比longArray中所有的数都大，也无法达到第k个。
 *           shortArray中的第k-lenLong个数，如果它比longArray中的所有数都大，那么它就是第k小数，否则，它也不是。
 *           接下来只需考虑shortArray[k-lenLong...lenShort-1]和longArray[k-LenShort...lenLong-1]，其上中位数就是整体的第k小数；
 *        3、lenShort <= k <= lenLong。
 *           对于longArray中的前k-lenShort-1个数，都不能满足要求，因为即使它们比shortArray中所有的数都大，也无法达到第k个。
 *           longArray中的第k-lenShort个数，如果它比shortArray中的所有数都大，那么它就是第k小数，否则，它也不是。
 *           对于longArray中的第k个数以后部分，也都不能满足要求，因为这些数之前至少已经有k个数了。
 *           接下来只需要考虑shortArray[0...lenShort-1]和longArray[k-lenShort...k-1]，其上中位数就是整体的第k小数。
 * </p>
 * <p>
 *     时间复杂度为O(log(min{M,N}))，空间复杂度为O(1)
 * </p>
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
