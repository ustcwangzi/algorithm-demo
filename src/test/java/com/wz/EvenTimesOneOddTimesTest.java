package com.wz;

import com.wz.bitoperation.EvenTimesOddTimes;

/**
 * <p>其他数都出现偶数次的数组中只有一个数出现奇数次，找到这个数</p>
 *
 * @author wangzi
 */
public class EvenTimesOneOddTimesTest {
    private static int solution(int[] array) {
        // 0与任何数异或还是这个数，任何数与本身异或都是0
        // 其他数都出现偶数次，因此异或为0，再与奇数次那个数异或，剩下的就是结果
        int result = 0;
        for (int cur : array) {
            result ^= cur;
        }
        return result;
    }

    public static void main(String[] args) {
        int[] array1 = {3, 3, 2, 3, 1, 1, 1, 3, 1, 1, 1};
        if (solution(array1) == EvenTimesOddTimes.findOneOddTimesNum(array1)) {
            System.out.println("Past");
        }
        int[] array2 = {-3, -3, -2, -3, -1, -1, -1, -3, -1, -1, -1};
        if (solution(array2) == EvenTimesOddTimes.findOneOddTimesNum(array2)) {
            System.out.println("Past");
        }
    }
}
