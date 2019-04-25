package com.wz;

import com.wz.string.ZeroLeftOneStringNumber;

/**
 * <p>N位二进制字符串，0左边必有1的二进制字符串数量</p>
 *
 * @author wangzi
 */
public class ZeroLeftOneStringNumberTest {
    private static int solution(int n) {
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        // 从右到左考虑字符串，假设p(n)表示有n个字符的情况下，满足条件的字符串个数
        // 如果要满足0左边必定有1，那么字符串的开头一定不能为0
        // 只包含一个字符时，满足条件的只有"1"，p(1)=1；
        // 包含两个字符时，满足条件的有"10"和"11"，p(2)=2；
        // 包含三个字符时，str[2]一定是1，str[1]可以是0或1，str[1]为1时，可以组成个数是p(2)，
        // str[1]为0时，可以组成个数是p(1)，即：p(3)=p(2)+p(1)；
        // 包含四个字符时，str[3]一定是1，str[2]可以是0或1，str[2]为1时，可以组成个数是p(3)，
        // str[2]为0时，可以组成个数是p(2)，即：p(4)=p(3)+p(2)；
        // 包含N个字符时，str[N-1]一定是1，str[N-2]可以是0或1，str[N-2]为1时，可以组成个数是p(N-1)，
        // str[N-2]为0时，可以组成个数是p(N-2)，即：p(N)=p(N-1)+p(N-2)。
        // 当N为1，2，3，4，5，6...时，结果为1，2，3，5，8，13...，直接顺序求解即可
        int result = 1, pre = 1, tmp;
        for (int i = 2; i < n + 1; i++) {
            tmp = result;
            result += pre;
            pre = tmp;
        }
        return result;
    }

    public static void main(String[] args) {
        int times = 100;
        boolean result = true;
        for (int i = 0; i < times; i++) {
            if (solution(i) != ZeroLeftOneStringNumber.getNumTwo(i) ||
                    solution(i) != ZeroLeftOneStringNumber.getNumThree(i)) {
                result = false;
                System.out.println("Error, number:" + i);
            }
        }
        if (result) {
            System.out.println("Past");
        }
    }
}
