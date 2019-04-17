package com.wz;

import com.wz.bitoperation.EvenTimesOddTimes;

import java.util.Arrays;

/**
 * <p>其他数都出现偶数次的数组中有两个数出现奇数次，找到这两个数</p>
 *
 * @author wangzi
 */
public class EvenTimesTwoOddTimesTest {
    private static int[] solution(int[] array) {
        int[] result = new int[2];
        // 因为只有两个数出现奇数次，假设为a、b，则tmp的结果为a^b
        int tmp = 0, one = 0;
        for (int cur : array) {
            tmp ^= cur;
        }

        // 找到tmp不为0的那一位，假设为第k位
        // 那么a和b的第k位必然一个是0，另一个是1，假设a的第k位是1
        int k = tmp & (~tmp + 1);
        for (int cur : array) {
            // 将one和第k位不为0的数异或，因为只有a出现了奇数次，因为one的结果就是a
            if ((cur & k) != 0) {
                one ^= cur;
            }
        }
        result[0] = one;
        // b=tmp^a=tmp^one
        result[1] = tmp ^ one;
        return result;
    }

    public static void main(String[] args) {
        int[] array1 = {4, 3, 4, 2, 2, 2, 4, 1, 1, 1, 3, 3, 1, 1, 1, 4, 2, 2};
        if (Arrays.equals(solution(array1), EvenTimesOddTimes.findTwoOddTimesNum(array1))) {
            System.out.println("Past");
        }
        int[] array2 = {-4, -3, -4, -2, -2, -2, -4, -1, -1, -1, -3, -3, -1, -1, -1, -4, -2, -2};
        if (Arrays.equals(solution(array2), EvenTimesOddTimes.findTwoOddTimesNum(array2))) {
            System.out.println("Past");
        }
    }
}
