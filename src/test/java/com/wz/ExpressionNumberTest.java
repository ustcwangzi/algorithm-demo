/**
 * <p>Title: ExpressionNumberTest</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019-05-12</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz;

import com.wz.recursionanddp.ExpressionNumber;

import java.util.Random;

/**
 * <p>表达式得到期望结果的组成数量</p>
 *
 * @author wangzi
 */
public class ExpressionNumberTest {
    private static int solution(String express, boolean desired) {
        char[] array = express.toCharArray();
        if (!isValid(array)) {
            return 0;
        }
        // t[i][j] 表示 array[i][j]组成true的数量
        int[][] t = new int[array.length][array.length];
        // f[i][j] 表示 array[i][j]组成false的数量
        int[][] f = new int[array.length][array.length];

        for (int j = 2; j < array.length; j++) {
            t[j][j] = array[j] == '1' ? 1 : 0;
            f[j][j] = array[j] == '0' ? 1 : 0;
            for (int i = j - 2; i > -1; i -= 2) {
                // 对array[i][j] 进行划分
                for (int k = i; k < j; k += 2) {
                    if (array[k + 1] == '&') {
                        // 左true、右true
                        t[i][j] = t[i][k] * t[k + 2][j];
                        // 左true、右false + 左false、右true + 左false、右false
                        f[i][j] = t[i][k] * f[k + 2][j] + f[i][k] * t[k + 2][j] + f[i][k] * f[k + 2][j];
                    } else if (array[k + 1] == '|') {
                        // 左true、右true + 左true、右false + 左false、右true
                        t[i][j] = t[i][k] * t[k + 2][j] + t[i][k] * f[k + 2][j] + f[i][k] * t[k + 2][j];
                        // 左false、右false
                        f[i][j] = f[i][k] * f[k + 2][j];
                    } else {
                        // 左true、右false + 左false、右true
                        t[i][j] = t[i][k] * f[k + 2][j] + f[i][k] * t[k + 2][j];
                        // 左true、右true + 左false、右false
                        f[i][j] = t[i][k] * t[k + 2][j] + f[i][k] * f[k + 2][j];
                    }
                }
            }
        }

        return desired ? t[0][array.length - 1] : f[0][array.length - 1];
    }

    /**
     * 表达式长度必须为奇数
     * 表达式偶数位置的字符必须为'0'或'1'
     * 表达式奇数位置的字符必须为'&'或'|'或'^'
     */
    private static boolean isValid(char[] array) {
        if ((array.length & 1) == 0) {
            return false;
        }

        for (int i = 0; i < array.length; i++) {
            char cur = array[i];
            if ((i & 1) == 1) {
                if (cur != '0' && cur != '1') {
                    return false;
                }
            } else {
                if ((cur != '&') && (cur != '|') && (cur != '^')) {
                    return false;
                }
            }
        }

        return true;
    }

    public static void main(String[] args) {
        int times = 100;
        boolean result = true;
        for (int i = 0; i < times; i++) {
            String str = RandomUtils.genRandomExpress((i & 1) == 1 ? i : i + 1);
            boolean desired = new Random().nextBoolean();
            if (solution(str, desired) != ExpressionNumber.numTwo(str, desired)) {
                result = false;
                System.out.println("Error, str:" + str);
            }
        }
        if (result) {
            System.out.println("Past");
        }
    }
}
