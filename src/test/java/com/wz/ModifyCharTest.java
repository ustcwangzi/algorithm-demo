package com.wz;

import com.wz.string.ModifyAndReplace;

import java.util.Arrays;

/**
 * <p>将数组中的'*'字符挪到右边，其他字符挪到左边</p>
 *
 * @author wangzi
 */
public class ModifyCharTest {
    private static char[] solution(char[] array) {
        int index = array.length - 1;
        // 从右到左遍历，非'*'直接复制，其余位置直接使用'*'填充
        for (int i = array.length - 1; i > -1; i--) {
            if (array[i] != '*') {
                array[index--] = array[i];
            }
        }
        while (index > -1) {
            array[index--] = '*';
        }
        return array;
    }

    public static void main(String[] args) {
        int times = 100;
        boolean result = true;
        for (int i = 0; i < times; i++) {
            char[] array = RandomUtils.genRandomCharArray();
            if (!Arrays.equals(solution(Arrays.copyOf(array, array.length)),
                    ModifyAndReplace.modify(Arrays.copyOf(array, array.length)))) {
                result = false;
                System.out.println("Error, array:" + Arrays.toString(array));
            }
        }
        if (result) {
            System.out.println("Past");
        }
    }
}
