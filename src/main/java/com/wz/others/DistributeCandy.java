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
 *     问题一解决方案一：
 *        首先初始化每个人一个糖果，然后遍历两遍得分数组，
 *        第一遍从左向右遍历，如果右边的孩子等级高，加一个糖果，这样保证了一个方向上高等级的糖果多。
 *        然后再从右向左遍历一遍，如果相邻两个左边的等级高，而左边的糖果又少的话，则左边糖果数为右边糖果数加一。
 *        最后再把所有孩子的糖果数都加起来即可。
 *     问题一解决方案二：
 *        首先引入左坡和右坡的概念，从左到右依次遍历数组，值递增的部分为左坡，递减的部分为右坡。
 *        定义了左坡和右坡后，整个数组就可以分解成很多对左坡和右坡，挨个计算每对左坡和右坡即可。
 *        假设数组为[1,4,5,9,3,2]，左坡和右坡分别是[1,4,5,9]，[9,3,2]。
 *        对左坡来说，从左到右糖果的分配为[1,2,3,4]，对右坡来说，糖果的分配为[3,2,1]，
 *        但是两种分配方式对9这个坡顶的分配是不同的，如何决定？哪个坡更高，就按哪个坡来分配。
 *        因为左坡和右坡都是严格的递增和递减，不存在相同的数值，所以，坡的高度就是各自序列的长度。
 *        很明显左坡的高度大于右坡的高度(4 > 3)。所以坡顶的分配按照左坡来，最终的分配为[1,2,3,4,2,1]。
 *     问题二解决方案一：
 *        与问题一解决方案一类似，只是在遍历array时，如果两个孩子得分一样，就保持糖果个数一样。
 *     问题二解决方案二：
 *        对左坡和右坡重新定义。从左到右依次遍历数组，值不递减的部分为左坡，不递增的部分为右坡。
 *        比如，[1,2,2,1]，左坡为[1,2,2]，右坡为[2,1]。
 *        假设数组为[0,1,2,3,3,3,2,2,2,2,2,1,1]，左坡和右坡，分别是[0,1,2,3,3,3]，[3,2,2,2,2,2,1,1]。
 *        对左坡来说，从左到右糖果的分配为[1,2,3,4,4,4]，对右坡来说，糖果的分配为[3,2,2,2,2,2,1]，
 *        但是两种分配方式对[3,3,3]这三个坡顶的分配还是不同的，如何决定？还是根据坡的高度，哪个坡更高，就按哪个坡来分配。
 *        因为左坡和右坡不是严格的递增和递减，可能存在相同的数值，所以，坡的高度不能再通过序列长度来计算。
 *        很明显左坡的高度大于右坡的高度(4 > 3)。所以坡顶的分配按照左坡来，最终的分配为[1,2,3,4,4,4,2,2,2,2,2,1,1]。
 * </p>
 * <p>
 *     问题一方案一和问题二的方案一，时间复杂度为O(N)，空间复杂度为O(N)
 *     问题一方案二和问题二的方案二，时间复杂度为O(N)，空间复杂度为O(1)
 * </p>
 *
 * @author wangzi
 */
public class DistributeCandy {

    public static int candyOne(int[] array) {
        if (array == null || array.length == 0) {
            return 0;
        }
        int result = 0;
        int[] numbers = new int[array.length];
        // 每个人至少得一个
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = 1;
        }
        // 正向遍历，如果右边的孩子得分高，给右边的孩子再加一个糖果
        for (int i = 0; i < array.length - 1; i++) {
            if (array[i + 1] > array[i]) {
                numbers[i + 1] = numbers[i] + 1;
            }
        }
        // 逆向遍历，如果左边的孩子得分高并且糖果少的话，给左边的孩子再加一个糖果
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

    public static int candyWithEqualOne(int[] array) {
        if (array == null || array.length == 0) {
            return 0;
        }
        int result = 0;
        int[] numbers = new int[array.length];
        // 每个人至少得一个
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = 1;
        }
        // 正向遍历，如果右边的孩子得分高，给右边的孩子再加一个糖果
        // 得分相同，则糖果个数保持一致
        for (int i = 0; i < array.length - 1; i++) {
            if (array[i + 1] > array[i]) {
                numbers[i + 1] = numbers[i] + 1;
            } else if (array[i + 1] == array[i]) {
                numbers[i + 1] = numbers[i];
            }
        }
        // 逆向遍历，如果左边的孩子得分高并且糖果少的话，给左边的孩子再加一个糖果
        // 得分相同，则糖果个数保持一致
        for (int i = array.length - 1; i > 0; i--) {
            if (array[i - 1] > array[i]) {
                numbers[i - 1] = Math.max(numbers[i - 1], numbers[i] + 1);
            } else if (array[i - 1] == array[i]) {
                numbers[i - 1] = numbers[i];
            }
        }
        for (int i : numbers) {
            result += i;
        }
        return result;
    }

    public static int candyWithEqualTwo(int[] array) {
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

    private static int[] rightCandyAndBase(int[] array, int left, int right) {
        int base = 1;
        int candy = 1;
        for (int i = right - 1; i >= left; i--) {
            if (array[i] == array[i + 1]) {
                // 得分相同，糖果数相同
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
        System.out.println(candyWithEqualOne(array));
        System.out.println(candyWithEqualTwo(array));
    }
}
