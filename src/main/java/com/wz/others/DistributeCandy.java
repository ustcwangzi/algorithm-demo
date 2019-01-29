/**
 * <p>Title: DistributeCandy</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/1/29</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.others;

/**
 * <p>分糖果问题</p>
 * <p>
 *     一群孩子做游戏，现在请你根据游戏得分来发糖果，要求如下：
 *     1、每个孩子不管得分多少，起码分到1个糖果
 *     2、任意两个相邻的孩子之间，得分较多的孩子必须多拿一些糖果
 *     给定一个数组array代表得分数组，请返回最少需要多少糖果。
 *     例如：
 *        array = [1,2,2]，糖果分配[1,2,1]，即可满足要求且数量最少，所以返回4。
 *     补充问题：
 *     原有的两个规则不变，再加一条规则：
 *     3、任意两个相邻的孩子之间如果得分相同，糖果数必须相同
 *     例如：
 *        array = [1,2,2]，糖果分配[1,2,2]，即可满足要求且数量最少，所以返回5。
 * </p>
 * @author wangzi
 */
public class DistributeCandy {

    public static int candyOne(int[] array) {
        if (array == null || array.length == 0) {
            return 0;
        }
        int result = 0;
        int[] numbers = new int[array.length];
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = 1;
        }
        for (int i = 0; i < array.length - 1; i++) {
            if (array[i + 1] > array[i]) {
                numbers[i + 1] = numbers[i] + 1;
            }
        }
        for (int i = array.length - 1; i > 0; i--) {
            if (array[i - 1] > array[i]) {
                numbers[i - 1] = Math.max(numbers[i - 1], numbers[i] + 1);
            }
        }
        for (int i : numbers) {
            result += i;
        }
        return result;
    }

    public static int candyTwo(int[] array) {
        if (array == null || array.length == 0) {
            return 0;
        }
        int index = nextMinIndexWithEqual(array, 0);
        int result = rightCandy(0, index++);
        int leftBase = 1;
        int next = 0;
        int rightCandy = 0;
        int rightBase = 0;
        while (index < array.length) {
            if (array[index] > array[index - 1]) {
                result += ++leftBase;
                index++;
            } else if (array[index] < array[index - 1]) {
                next = nextMinIndexWithEqual(array, index - 1);
                rightCandy = rightCandy(index - 1, next++);
                rightBase = next - index + 1;
                result += rightCandy + (rightBase > leftBase ? -leftBase : -rightBase);
                leftBase = 1;
                index = next;
            } else {
                result += 1;
                leftBase = 1;
                index++;
            }
        }
        return result;
    }

    private static int nextMinIndexWithEqual(int[] array, int start) {
        for (int i = start; i < array.length - 1; i++) {
            if (array[i] <= array[i + 1]) {
                return i;
            }
        }
        return array.length - 1;
    }

    private static int rightCandy(int left, int right) {
        int length = right - left + 1;
        return length + length * (length - 1) / 2;
    }

    public static int candy(int[] array) {
        if (array == null || array.length == 0) {
            return 0;
        }
        int index = nextMinIndex(array, 0);
        int[] data = rightCandyAndBase(array, 0, index++);
        int result = data[0];
        int leftBase = 1;
        int same = 1;
        int next = 0;
        while (index < array.length) {
            if (array[index] > array[index - 1]) {
                result += ++leftBase;
                same = 1;
                index++;
            } else if (array[index] < array[index - 1]) {
                next = nextMinIndex(array, index - 1);
                data = rightCandyAndBase(array, index - 1, next++);
                if (data[1] <= leftBase) {
                    result += data[0] - data[1];
                } else {
                    result += -leftBase * same + data[0] - data[1] + data[1] * same;
                }
                index = next;
                leftBase = 1;
                same = 1;
            } else {
                result += leftBase;
                same++;
                index++;
            }
        }
        return result;
    }

    private static int nextMinIndex(int[] array, int start) {
        for (int i = start; i != array.length - 1; i++) {
            if (array[i] < array[i + 1]) {
                return i;
            }
        }
        return array.length - 1;
    }

    public static int[] rightCandyAndBase(int[] array, int left, int right) {
        int base = 1;
        int candy = 1;
        for (int i = right - 1; i >= left; i--) {
            if (array[i] == array[i + 1]) {
                candy += base;
            } else {
                candy += ++base;
            }
        }
        return new int[]{candy, base};
    }

    public static void main(String[] args) {
        int[] array = {3, 0, 5, 5, 4, 4, 0};
        System.out.println(candyOne(array));
        System.out.println(candyTwo(array));
        System.out.println(candy(array));
    }
}
