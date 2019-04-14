/**
 * <p>Title: FindHalfMajorityTest</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/4/14</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz;

import com.wz.arrayandmatrix.FindKMajority;

import java.util.Arrays;

/**
 * <p>数组中找到出现次数大于一半的数</p>
 *
 * @author wangzi
 */
public class FindHalfMajorityTest {
    private static int solution(int[] array) {
        int result = -1;
        int times = 0;
        // 每次删除两个不同的数
        for (int cur : array) {
            if (times == 0) {
                // 找到第一个要删除的数
                result = cur;
                times++;
            } else if (result == cur) {
                // 找到第一个要删除的数次数加一
                times++;
            } else {
                // 找到第二个要删除的数，删除，同时将第一个要删除的数次数减一
                times--;
            }
        }
        times = 0;
        // 统计最后剩下的数出现次数
        for (int cur : array) {
            if (cur == result) {
                times++;
            }
        }
        return times > array.length / 2 ? result : -1;
    }

    public static void main(String[] args) {
        int times = 100;
        boolean result = true;
        for (int i = 0; i < times; i++) {
            int[] array = RandomUtils.genRandomArray();
            if (solution(array) != FindKMajority.halfMajor(array)) {
                result = false;
                System.out.println("Error, array:" + Arrays.toString(array));
            }
        }
        if (result) {
            System.out.println("Past");
        }
    }
}
