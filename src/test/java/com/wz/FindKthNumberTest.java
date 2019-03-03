/**
 * <p>Title: FindKthNumberTest</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/3/3</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz;

import com.wz.others.FindKthNumber;

import java.util.Arrays;

/**
 * <p></p>
 *
 * @author wangzi
 */
public class FindKthNumberTest {

    private static int solution(int[] self, int[] other, int k) {
        int[] longArray = self.length >= other.length ? self : other;
        int[] shortArray = self.length < other.length ? self : other;
        int lenLong = longArray.length, lenShort = shortArray.length;
        if (k <= lenShort) {
            return getUpMedian(shortArray, 0, k - 1, longArray, 0, k - 1);
        }
        if (k > lenLong) {
            if (longArray[k - lenShort - 1] >= shortArray[lenShort - 1]) {
                return longArray[k - lenShort - 1];
            }
            if (shortArray[k - lenLong - 1] >= longArray[lenLong - 1]) {
                return shortArray[k - lenLong - 1];
            }
            return getUpMedian(shortArray, k - lenLong, lenShort - 1, longArray, k - lenShort, lenLong - 1);
        }
        if (longArray[k - lenShort - 1] >= shortArray[lenShort - 1]) {
            return longArray[k - lenShort - 1];
        }
        return getUpMedian(shortArray, 0, lenShort - 1, longArray, k - lenShort, k - 1);
    }

    private static int getUpMedian(int[] self, int start1, int end1, int[] other, int start2, int end2) {
        int mid1, mid2, offset;
        while (start1 < end1) {
            mid1 = (start1 + end1) / 2;
            mid2 = (start2 + end2) / 2;
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
        int[] self = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int[] other = {3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        boolean result = true;
        for (int i = 0; i < self.length - 1; i++) {
            for (int j = i + 1; j < self.length; j++) {
                int[] selfCopy = Arrays.copyOfRange(self, i, j);
                int[] otherCopy = Arrays.copyOfRange(other, i, j);
                for (int k = 1; k < selfCopy.length + otherCopy.length - 1; k++) {
                    if (solution(selfCopy, otherCopy, k) != FindKthNumber.findKthNumber(selfCopy, otherCopy, k)) {
                        result = false;
                        System.out.println("Error, array:" + Arrays.toString(selfCopy) +
                                ", other:" + Arrays.toString(otherCopy) + ", k:" + k);
                    }
                }
            }
        }
        if (result) {
            System.out.println("Past");
        }
    }
}
