/**
 * <p>Title: FindUpMedianTest</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/3/2</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz;

import com.wz.others.FindUpMedian;

import java.util.Arrays;

/**
 * <p>在两个长度相等的有序数组中找到上中位数</p>
 *
 * @author wangzi
 */
public class FindUpMedianTest {

    private static int solution(int[] self, int[] other) {
        int start1 = 0, mid1, end1 = self.length - 1;
        int start2 = 0, mid2, end2 = other.length - 1;
        while (start1 < end1) {
            mid1 = (start1 + end1) / 2;
            mid2 = (start2 + end2) / 2;
            // 元素个数为奇数，offset为0，元素个数为偶数，offset为1
            int offset = ((end1 - start1 + 1) & 1) ^ 1;
            if (self[mid1] > other[mid2]) {
                // self[start1...mid1]、other[mid2+offset...end2]
                end1 = mid1;
                start2 = mid2 + offset;
            } else if (self[mid1] < other[mid2]) {
                // self[mid1+offset...end1]、other[start2...mid2]
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
                if (solution(selfCopy, otherCopy) != FindUpMedian.getUpMedian(selfCopy, otherCopy)) {
                    result = false;
                    System.out.println("Error, array:" + Arrays.toString(selfCopy) + ", other:" + Arrays.toString(otherCopy));
                }
            }
        }
        if (result) {
            System.out.println("Past");
        }
    }
}
