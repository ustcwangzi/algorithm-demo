package com.wz;

import com.wz.string.ZeroLeftOneStringNumber;

public class ZeroLeftOneStringNumberTest {
    private static int solution(int n) {
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
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
